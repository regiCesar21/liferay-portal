# Alvaro's review notes — LPD-97700 (PR #3357, `alvarosaugarlr`)

Source: https://github.com/liferay-appsec/liferay-portal/pull/3357 (`LPD-97700-v2`, "Derive resourceAction and resourceType in AuditMessage"). Captured 2026-09-18 for reference in the next story — none of these have been applied yet.

## Overall review comment

> Nice work — resolving this in `AuditMessage`'s own constructor is the right call, and the writeup of why the router-level lookup table was dropped is convincing. Almost everything below is naming rather than design.
>
> One overall thought before the line notes: after this change the audit vocabulary is half derived and half hand-written, with no single place that lists it. That is fine as long as every hand-written override changes the *meaning* rather than just the spelling — a few of them currently only change the spelling, which is what the comments below point at.

## Inline comments

### `portal-kernel/src/com/liferay/portal/kernel/audit/AuditMessage.java:493`
Three naming issues in `setResourceAction`'s two-arg overload (rule [105](pr-reviewer/rules/105-name-a-method-after-what-it-actually-does.md)):
1. Method is named `setResourceAction` but assigns both `_resourceType` and `_resourceAction`.
2. Parameter is named `action`, while the same concept is `resourceAction` everywhere else (field, getter, JSON key, one-arg overload's param).
3. The two spellings don't even hold the same value: `_resourceAction` ends up `"system.user.add"`, the `action` param holds `"add"`.

Suggested fix — rename to `setResource(String resourceType, String resourceAction)`:
```java
public void setResource(String resourceType, String resourceAction) {
	_resourceType = resourceType;
	_resourceAction = StringBundler.concat(
		"system", StringPool.PERIOD, resourceType, StringPool.PERIOD,
		resourceAction);
}
```

Separate note (not necessarily for this PR): once every producer goes through this overload, `setResourceAction(String)` and `setResourceType(String)` have no remaining production caller — `grep -rn '\.setResourceAction(\|\.setResourceType(' --include=*.java .` only turns up `AuditEventLocalServiceImpl` (that's on `AuditEvent`, a different type) and `AuditEventLocalServiceTest`. Worth a sentence in the PR description since it's the first thing a reviewer greps for.

### `portal-kernel/src/com/liferay/portal/kernel/audit/AuditMessage.java:78`
The `unknown` fallback covers `className` but not `eventType`. `StringUtil.toLowerCase(null)` returns `null`, and `StringBundler.concat` renders that as the literal string `"null"` — so a null `eventType` produces `"system.user.null"` instead of falling back.

No current producer passes a null `eventType`, but the `AuditMessage(long, long, String, String)` constructor doesn't prevent it, and `_testConstructorResourceAction` only covers the null/dotless/trailing-dot cases for `className`. Either guard `eventType` the same way, or add the case to the test.

### `portal-kernel/src/com/liferay/portal/kernel/audit/AuditMessage.java:666`
`_RESOURCE_ACTION_NAMESPACE` and `_UNKNOWN_RESOURCE_TYPE` are each referenced exactly once — rule [502](pr-reviewer/rules/502-inline-private-constant-used-once.md) asks to inline single-reference private constants. (Neighbours like `_RESOURCE_ACTION`/`_RESOURCE_TYPE` are not a counterexample — they're read on the way in and written on the way out, so two references apiece.)

### `.../portal-security-audit-event-generators-user-management/.../UserModelListener.java:149`
The generic rule would derive `agreed_to_terms_of_use` from `EventTypes.AGREED_TO_TERMS_OF_USE`; this override writes `agree_to_terms_of_use` — a verb-tense-only difference. That gives the audit vocabulary two spellings of one action, and the override has to be hand-maintained forever to keep them apart. If present tense is deliberate (e.g. to match a published schema), say so in the commit message; otherwise drop the override.

### `.../portal-scheduler/.../SchedulerEngineAuditorImpl.java:66`
Same shape: audit message built with `SchedulerEngine.class.getName()`, generic rule derives `schedulerengine`, override writes `scheduler`. Spelling change, not a meaning change — anything that audits `SchedulerEngine` without going through this overload will disagree. Either keep the derived value or note why `scheduler` specifically must appear.

### `.../multi-factor-authentication-email-otp-web/.../EmailOTPBrowserMFAChecker.java:420`
Using one `mfa` resource type across all four MFA mechanisms is right, but it's four separate string literals in four separate modules (here, plus `MFAFIDO2AuditMessageBuilder`, `IPAddressHeadlessMFAChecker`, `TimeBasedOTPBrowserSetupMFAChecker`) with nothing tying them together — nothing stops a fifth checker from writing `multi_factor_authentication` instead. All four modules already depend on `multi-factor-authentication-spi`, the natural home for the constant. Same issue for the repeated `verify` / `verify_failure` literals across the same four files.

### `.../portal-security-audit-event-generators-user-management-test/.../UserModelListenerTest.java:123`
The shared assertion helper doesn't pay for itself: declaration + separating blank line = 11 lines vs. ~18 lines if the 3 call sites (3 lines each) were inlined. Rule [601](pr-reviewer/rules/601-consolidate-parallel-test-methods.md) reserves the `_<testMethodName>` helper pattern for removing repeated *setup*; here the setup differs and only the assertions are shared, which is also why the helper can't follow the naming convention (three tests, three different names, one shared helper).

More importantly, the same two assertions are hand-written in the four other test files this PR touches (`OrganizationModelListenerTest`, `UserGroupModelListenerTest`, `RoleModelListenerTest`, `LoginPostActionTest`). Having the helper in one file and the manual form in four leaves the change saying the same thing two ways — inlining here is both shorter and keeps all five consistent.

### `portal-impl/test/unit/com/liferay/portal/kernel/audit/AuditMessageTest.java:58`
The seven cases are the right shape (one call per case, helper named after the test method, sorted by first argument) — but should this be folded into `testConstructor` right above (same subject) rather than a second `@Test`? Would match rule [601](pr-reviewer/rules/601-consolidate-parallel-test-methods.md) and drop the scenario suffix.

### `.../portal-security-audit-event-generators-test/.../RoleModelListenerTest.java:67`
This test and `testOnBeforeAddAssociation` above it test the same method and assert the same two values — per rule [601](pr-reviewer/rules/601-consolidate-parallel-test-methods.md) they should be one `testOnBeforeAddAssociation` calling a `_testOnBeforeAddAssociation` helper twice (once for the user association, once for the organization group), dropping the `WithOrganizationGroup` suffix. The case itself is valuable to keep — it's the one that would have caught the resource type being decided by `additionalInfo` key order.

## Pending reply — not yet posted (comment id 4066907396, round 2, `AuditMessage.java:502` — "The second parameter is not a `resourceAction`, and the separator is spelled two ways")

The `action` naming half of this comment is already implemented as asked. The separator-spelling half — his suggested code (`StringBundler.concat("system", StringPool.PERIOD, resourceType, StringPool.PERIOD, action)`) — was tried and reverted: it fails Liferay's mandatory `ConcatCheck` source-formatter rule. Drafted reply, held back (2026-09-22) per instruction to batch pending replies together rather than post one at a time.

> The `action` rename is in. The separator can't be unified the way the snippet shows, though — `ConcatCheck`'s `checkCombineOperand` flags any string literal sitting directly next to a `StringPool.*`/`CharPool.*` reference in a `StringBundler.concat` call (with a short excluded list — `DEFAULT_CHARSET_NAME`, `DELETE`, `NO_BREAK_SPACE` — none of which apply here) and asks for them to be folded into one literal instead. Running `ant format-source-current-branch` against `StringBundler.concat("system", StringPool.PERIOD, resourceType, StringPool.PERIOD, action)` produces exactly that: `Combine the literal string "system" with "StringPool.PERIOD"`.
>
> Since `"system"` is the only literal in the call and `resourceType`/`action` are variables, the separator touching `"system"` is the one forced inline (`"system."`) — there's no variant where both separators are spelled the same way and the build still passes format-source. What's there now (`"system.", resourceType, StringPool.PERIOD, action`) is the only combination the checker accepts.

**Investigation trail:**
- `modules/util/source-formatter/.../ConcatCheck.java` → `_checkConcatMethodCall` → `BaseStringConcatenationCheck.checkCombineOperand` (`literalStringDetailAST`, `operandDetailAST`): fires when a `STRING_LITERAL` arg is adjacent to a `TokenTypes.DOT` operand whose `FullIdent` starts with `"CharPool."` or `"StringPool."`, unless it ends in `.DEFAULT_CHARSET_NAME`, `.DELETE`, or `.NO_BREAK_SPACE`.
- Reproduced live: edited `setResource` to the suggested 5-arg form, ran `ant format-source-current-branch` from `portal-impl`, got `BUILD FAILED` with `Combine the literal string "system" with "StringPool.PERIOD": ./portal-kernel/.../AuditMessage.java 505 (Checkstyle:ConcatCheck)`. Reverted back to the 4-arg form and the same build passes clean.

## Pending reply — not yet posted (comment id 4047500212, round 1, `SchedulerEngineAuditorImpl.java`)

Alvaro asked to either drop the `scheduler` override (let the generic derivation produce `schedulerengine`) or explain why `scheduler` has to appear. Investigated whether the generic-derivation route is actually viable — it isn't, for reasons beyond spelling. Drafted reply, held back per instruction (2026-09-22) to keep working the other pending items first:

> `scheduler` is deliberate — and there's a concrete reason dropping the override isn't safe, beyond spelling.
>
> `eventType` at this call site is fixed to the constant `SchedulerEngine.SCHEDULER`, not the trigger state. That's not incidental: `eventType` is a stable, documented contract at three layers — the whiteboard routing key `DefaultAuditRouter.route()` looks up via `_serviceTrackerMap.getService(auditMessage.getEventType())`, the persisted `Audit_AuditEvent.eventType` column (with its own `LIKE`-based finder for querying by it), and a public, documented query parameter on the REST `/audit-events` endpoint (`rest-openapi.yaml`). All three assume `eventType` stays `"SCHEDULER"` for every scheduler audit event, so someone can filter "give me every scheduler event" in one shot.
>
> The actual trigger state (`COMPLETE`/`NORMAL`/`PAUSED`) only exists in `message`. So the generic rule can only ever derive `action = "scheduler"` here (from the fixed `eventType`) — dropping the override wouldn't just rename `resourceType`, it would collapse `resourceAction` to the same value for every trigger state, since the generic derivation has no visibility into `message`. And moving the trigger state into `eventType` instead — so the generic rule could pick it up — breaks that same stable contract for anyone already filtering by `eventType=SCHEDULER` in the DB, the REST API, or a future whiteboard subscriber.
>
> So this override isn't skipping the generic rule out of convenience: `eventType` and the audited outcome are two different things by design here, unlike the listeners where they happen to be the same field. `scheduler` is centralized as `AuditResourceConstants.RESOURCE_TYPE_SCHEDULER` for the same reason `mfa` is shared across four unrelated checker classes.

**Investigation trail (why generic derivation isn't viable here, confirmed against the actual code, not assumed):**
- `SchedulerEngine.SCHEDULER` (`"SCHEDULER"`) has exactly one production usage in the whole tree — this call site. No other production whiteboard consumer subscribes to it today.
- But `eventType` is copied verbatim into the persisted `Audit_AuditEvent.eventType` column (`AuditEventLocalServiceImpl._toAuditEvent`), which has its own case-insensitive `LIKE` finder built for querying by it.
- `eventType` is also a documented `query` parameter on the public REST `/audit-events` endpoint (`portal-security-audit-rest-impl/rest-openapi.yaml`).
- Conclusion: repurposing `eventType` to carry the trigger state (the only way generic derivation could compute the real per-state action) would silently change a persisted+public field's semantics for every future/existing consumer filtering by `eventType=SCHEDULER`. Dropping the override without that change would instead collapse every trigger state into one identical `resourceAction`, losing the exact signal this story exists to capture. Neither path satisfies "just let the generic rule derive it."

## Takeaways for the next story

- **Naming discipline on `AuditMessage` setters:** don't introduce a setter name/param spelling that diverges from the field/getter/JSON-key spelling for the same concept (`resourceAction` vs `action`). Consider `setResource(resourceType, resourceAction)` as the two-arg entry point.
- **Null-safety in derivation:** any new derived-field logic in `AuditMessage`'s constructor needs to guard every input it string-concatenates, not just the ones the current tests happen to cover (`eventType` was missed alongside `className`).
- **Single-reference private constants get inlined** (rule 502) — check before adding a new one.
- **Hand-written overrides must change meaning, not spelling** — if a derived value and a manual override differ only in casing/tense/wording, that's a signal to either fix the derivation rule or drop the override, not to keep both.
- **Shared literal constants for repeated cross-module values** (e.g. `mfa`, `verify`, `verify_failure` across the four MFA checker modules) belong in the shared SPI module the callers already depend on, not copy-pasted per module.
- **Test consolidation (rule 601)** applies aggressively here: don't add a private assertion-only helper unless it's used consistently everywhere the same assertions appear across the PR's touched test files; parallel test methods that assert the same two values from different setups should share one helper called twice, not exist as two full methods.
