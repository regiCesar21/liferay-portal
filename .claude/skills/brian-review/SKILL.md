---

allowed-tools: [Agent, Bash, Glob, Grep, Read]
argument-hint: "[PR number/URL | --working-tree]"
description: Review a diff the way Brian Chan (@brianchandotcom) would, against the pr-reviewer STYLE.md and numbered rules. Use when the user asks for a Brian-style review or invokes /brian-review.
name: brian-review

---

# Brian Review

Review a diff the way Brian Chan (GitHub `brianchandotcom`) would. This skill is the interactive, in-conversation counterpart to the `pr-reviewer/run.sh` bot: it reuses the same `STYLE.md` and numbered `rules/` as the single source of truth and applies the same review discipline to a local diff, producing a readable report instead of a GitHub comment.

This skill is read-only. It never edits code, commits, or posts to GitHub.

It complements, rather than replaces, the `pre-push-code-supervisor` and `conventions-reviewer` agents. Those draw on accumulated reviewer memories; this skill is driven specifically by the codified, numbered `pr-reviewer` rule set and reproduces the bot's rejection-chance output.

## Resolve the Rule Set

Resolve the pr-reviewer folder as `$(git rev-parse --show-toplevel)/pr-reviewer`, falling back to `/home/me/dev/projects/liferay-portal/pr-reviewer` when that path does not exist. Confirm both `STYLE.md` and the `rules` directory are present. When neither resolves, abort with a one-line message naming the path that was checked.

## Resolve the Diff

Pick the target in this precedence order, then capture the diff with `git diff --unified=1` (one context line, matching the bot):

1. **PR argument.** When `${ARGUMENTS}` holds a PR number or URL, fetch its diff:

	```bash
	gh pr diff <number> --repo <owner/repo>
	```

	Parse `<owner/repo>` from a URL when given; otherwise default to `brianchandotcom/liferay-portal`, as the bot does.

1. **Working tree.** When `${ARGUMENTS}` contains `--working-tree`, review uncommitted changes:

	```bash
	git diff --unified=1 HEAD
	```

1. **Current branch (default).** Review the commits on top of `master`:

	```bash
	git diff --unified=1 "$(git merge-base HEAD master)"...HEAD
	```

When the resolved diff is empty, exit with a one-line message and do nothing else.

## Filter the Diff

Replicate the bot's scoping so results match `run.sh`. Compute the changed-file list, drop the files below, then recapture the diff restricted to the kept files (`git diff --unified=1 <range> -- <kept-files>`). The lists below are copied from `pr-reviewer/run.sh` (the `_IGNORED_*` and `_NAME_ONLY_SUFFIXES` variables) — treat that file as the source of truth and resync if it changes.

- **Generated files.** Drop any file whose head content contains `@generated`.
- **Ignored filenames.** Drop `CHANGELOG.md`, `package-lock.json`, `package.json`.
- **Ignored patterns.** Drop translation files matching `(^|/)Language_.*\.properties$`.
- **Ignored suffixes.** Drop `css`, `js`, `jsx`, `lock`, `lockfile`, `macro`, `path`, `scss`, `snap`, `testcase`, `ts`, `tsx`.
- **Name-only suffixes.** Treat images (`bmp`, `gif`, `ico`, `jpeg`, `jpg`, `png`, `svg`, `webp`) as name-only — note the change but do not review its content.

When filtering removes every file, exit with a one-line message saying the diff held nothing in scope.

## Run the Review

Spawn a single `general-purpose` subagent for the review. One isolated pass mirrors the bot's single `claude --print` invocation and keeps the rule files out of the main context. Pass the subagent the resolved pr-reviewer path and the filtered diff, and instruct it as follows:

- Read `STYLE.md`, every file under `rules`, and the diff. Review the diff against every rule.

- For any naming, ordering, or convention question, run `git grep <pattern>` against the local checkout before deciding. Never short-circuit by returning empty after only reading the diff. To check committed state rather than the working tree, pass a ref: `git grep <pattern> <ref>`.

- For any claim about where a line sits (between X and Y, after X, before Y, at line N), name X and Y exactly as they appear on the lines immediately above and below the `+` line in the diff. Re-read the diff before writing the claim. A position claim that misnames its neighbors is a hallucination even when the rule reasoning is correct.

- Flag every rule violation found. When confidence in a flag is partial, still include it and append `verify against <source>` so the developer can confirm.

- Return structured findings: one entry per violation with the file and line, the rule number and title it breaks, a one-line description, and the supporting grep evidence. Then an overall `chance` from 0 to 100 that Brian Chan would reject the PR for these violations.

## Report

Relay the subagent's findings in the conversation, mirroring the bot's phrasing.

When there are no violations, report:

```
Brian will most likely merge this PR.
```

Otherwise, lead with the rejection chance (use "an" before 8, 11, 18, and 80–89; "a" otherwise):

```
There is a 60% chance that Brian will reject this PR.
```

Follow with the violations grouped by file, one line each:

```
path/to/File.java:42 — [401 Avoid Switch and Case Statements] Replace the switch with an if / else if chain.
```

Preserve any `verify against <source>` note on the line it belongs to. Close by noting the flagged rules are citable from `pr-reviewer/rules`.