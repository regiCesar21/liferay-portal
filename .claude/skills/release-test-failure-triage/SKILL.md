---

allowed-tools: [Agent, Bash, Glob, Grep, Read, Skill]
argument-hint: "[JQL, Jira issues URL, or filter/parent ticket]"
description: Triage a batch of auto-generated release-test-failure Jira tickets (e.g. everything under a saved filter or a parent Epic like 'U153 New Test Failures'). Verifies the classifier's cited root cause against the actual code and git history, classifies each as bug / regression bug / flaky / test fix, then posts a comment and — for test fixes — files and links a tracking ticket. Use when the user asks to investigate, triage, or classify a batch of release-test-failure or 'Investigate X failing in <version>' tickets.
name: release-test-failure-triage

---

# Release-Test-Failure Batch Triage

Investigate a batch of Jira tickets created by the release-test-failure classifier (agent:claude-opus-4-8), decide what each one actually is, and leave the ticket in a state someone else can act on without re-investigating.

This is a **triage** workflow, not a fix-and-PR workflow. It never reproduces locally, never touches Tomcat, and never opens a PR. For fixing a single already-triaged failure end-to-end (repro, bisect, PR), use the `test-fix` skill instead — this skill is what decides *whether* that's warranted.

## Input

Resolve `${ARGUMENTS}` to a JQL query:

- A full Jira issues URL (`https://liferay.atlassian.net/issues?jql=...`) — URL-decode the `jql` query parameter and use it directly.
- Raw JQL — use it directly.
- A filter ID and/or parent ticket key (e.g. "filter 15242" or "parent LPD-105048") — build `(filter = <id> or parent = <PARENT>)`, optionally narrowed by `and component in componentsLeadByUser(<accountId>)` when the user wants only the components they lead (ask for their Jira account ID if not already known — it's a 24-hex-char string, visible in their Jira profile URL or via `mcp__claude_ai_Atlassian__atlassianUserInfo` if that tool is available).

## Fetch the Ticket List

Per `.claude/rules/jira.md`, use `curl` with `${JIRA_API_USER}`/`${JIRA_API_TOKEN}`. Use `/rest/api/3/search/jql` — the older `/rest/api/3/search` endpoint is retired and returns an error telling you to migrate.

```bash
curl --silent --url "https://liferay.atlassian.net/rest/api/3/search/jql" \
	--user "${JIRA_API_USER}:${JIRA_API_TOKEN}" \
	-G \
	--data-urlencode 'jql=<resolved JQL> ORDER BY key ASC' \
	--data-urlencode 'fields=summary,status,resolution,components,labels' \
	--data-urlencode 'maxResults=100'
```

Filter the result to **Open** tickets — skip anything `Closed`/`Resolved` (already handled), and call out `In Progress` tickets separately without classifying them unless asked.

## Investigate Each Open Ticket

Fetch the full issue including description and comments:

```bash
curl --silent --url "https://liferay.atlassian.net/rest/api/3/issue/<KEY>?fields=summary,description,comment,labels,status" \
	--user "${JIRA_API_USER}:${JIRA_API_TOKEN}"
```

The description (ADF) typically carries: an error snippet, a Testray link, a "Claude reasoning" paragraph from the classifier, and a "Possible cause" section citing a commit/ticket.

**Do not trust the classifier's cited cause.** In past batches roughly half of the cited commits turned out to be red herrings — a plausible-sounding commit in the same file/area that never actually touches the failing code path. Verify independently every time:

1. Identify the failing test file/class and the exact assertion or exception.
2. Use **read-only** git commands only — `git log`, `git show`, `git blame`, `grep` — to check whether the cited commit actually touches the relevant code path, and whether the test or the code it exercises changed recently. **Never run `git checkout`, `git switch`, `git stash`, or `git reset`** — this is very likely a shared working tree; leaving it on a different branch or losing uncommitted state breaks other work silently.
3. Look for environment/config gating issues — a common false "product bug" pattern. Example: a Playwright spec asserting FIPS-mode enforcement lived in a project whose `env` never set `fips.enabled=true`, so the assertions could never pass regardless of the feature.
4. Look for test-code defects:
   - A non-retrying assertion on inherently-async UI, e.g. Playwright's `.count()`/`.textContent()` resolved synchronously *before* being wrapped in `expect()`, so there's no auto-retry — should be `expect.poll(...)` or wrapped in `expect(async () => {...}).toPass()`.
   - A stale locator or test fixture after an intentional rename or default-value change elsewhere in the codebase (check `git log` on the JSP/component the locator targets).
   - Brittle string/substring parsing of structured output (CSV, JSON) instead of parsing it properly — fragile to unrelated column/field changes.
5. Check whether a **sibling test was already fixed** for the same intentional change but this one was missed — grep for other tests of the same class/utility and diff their assertions against this one's.

Classify as exactly one of:

- **bug** — real product defect, unrelated to any recent change.
- **regression bug** — real product defect introduced by an identifiable commit within the failure window.
- **flaky** — non-deterministic, no identifiable *fixable* root cause (infra noise, a startup race, or already self-tagged flaky by the classifier/Testray).
- **test fix** — a concrete, fixable defect in the test code, fixture, or test environment itself — not the product.

The flaky/test-fix line matters: call it flaky only when there is nothing to fix and retrying is the only lever. If you can point at a specific line and a specific correction, it's a test fix, even though it *manifests* as intermittent failure — labeling it "flaky" just lets it keep resurfacing unfixed.

### Parallelizing a large batch

For more than ~3 tickets, group by component or suspected shared root cause and investigate each group with a forked sub-agent in parallel (read-only: Jira fetch + git log/grep, no writes), instead of sequentially burning the main session's context. Each sub-investigation reports back per ticket: classification, a tight one-paragraph justification with concrete file:line/commit evidence, and whether the classifier's cited cause held up or was a red herring. Do every Jira write (comments, ticket creation, linking) from the main session afterward, not from the forks — keeps writes auditable and easy to confirm in one place.

## Group by Shared Root Cause

Before filing anything, group tickets that share the *exact* same root cause (e.g. three specs failing because one environment flag is never enabled, or two config fixtures with the identical invalid placeholder). File **one** fix ticket per shared root cause, not one per Jira ticket, and link all of the originals to it.

## Act on the Classification

**flaky** and **bug**/**regression bug**: post a short comment (format below). No fix ticket is filed in this workflow — a regression belongs to the owning team's normal fix process, not a triage-session ticket; a flaky test has nothing to file.

**test fix**: file a tracking ticket and link it, then comment.

1. Create a Jira Task (use the `jira-task` skill, or `POST /rest/api/3/issue` directly):
   - `issuetype`: Task (`10002`)
   - `components`: match the affected area (look up the ID via `GET /rest/api/3/project/LPD/components`, matching by name)
   - `priority`: Low
   - `labels`: **always include `"tech-debt"`**, even if not explicitly requested
   - `summary`: an imperative fix statement ("Fix X's Y", "Update Z to use...")
   - `description` (ADF): root-cause paragraph(s) citing concrete file:line/commit evidence, a suggested-fix paragraph, then a trailing "Reference:" (or "References:" for a bundled ticket) line linking back to the original ticket(s). Mirror the shape of LPD-101476.

2. Link the new ticket to the original(s) with the **Fix** issue link type — with the **new ticket as `inwardIssue`** and the **original as `outwardIssue`**:

   ```bash
   curl --silent --url "https://liferay.atlassian.net/rest/api/3/issueLink" \
   	--user "${JIRA_API_USER}:${JIRA_API_TOKEN}" \
   	--header "Content-Type: application/json" \
   	--data '{"type":{"name":"Fix"},"inwardIssue":{"key":"<NEW_FIX_TICKET>"},"outwardIssue":{"key":"<ORIGINAL_TICKET>"}}'
   ```

   This is the direction that renders "fixes" on the new ticket and "is fixed by" on the original — the naive reading of `inwardIssue`/`outwardIssue` is backwards from what it sounds like, this was confirmed empirically against the live Jira instance. If this is the first time doing it in a session, ask the user to eyeball the rendered label once before repeating it across a whole batch.

3. Post a short comment on the original linking to the new ticket (format below).

## Comment Format

Keep it to 2-4 sentences, plain prose, no headers:

> Classification: `<bug|regression bug|flaky|test fix>`, not a `<the thing it isn't>`. `<One to three sentences of root-cause evidence — call out explicitly when the classifier's cited cause was a red herring, and what the real cause is instead.>` `<Fix tracked in <LINK>. | No fix ticket needed.>`

## JSON-Escaping Gotcha

When building an ADF comment/description body via a bash heredoc, embedded double quotes (code snippets, quoted strings) will break the JSON if not escaped, and `curl` will return HTTP 400. Either escape every inner `"` carefully, or build the payload with Python (`json.dump`) and pass function arguments instead of interpolating raw text into a heredoc.
