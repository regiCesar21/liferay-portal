# Audit Framework — Claude Code context

**Component:** `Application Security > Audit Framework`
**Epic:** LPD-96921 (Baseline Event Schema). **Current task:** LPD-97699 (add baseline schema fields, additive-only).

## Architecture (ground truth — do not re-derive)

- **Write path:** `AuditFilter` → producer (`AuditMessage`) → `AuditRouterUtil` → `DefaultAuditRouter` → two processors: `PersistentAuditMessageProcessor` (DB) and `LoggingAuditMessageProcessor` (log formatters).
- **Synchronous** since LPD-11677 (Oct 2022; `liferay/audit` message bus removed). No async assumptions.
- **Persistence:** `PersistentAuditMessageProcessor` → `AuditEventLocalServiceImpl._toAuditEvent` → table `Audit_AuditEvent`. `_toAuditEvent` reads `AuditMessage` getters directly.
- **Logging:** formatters consume `AuditMessage.toJSONObject()`. Serialization and the JSON constructor MUST stay symmetric (root cause of LPD-98129).
- `BatchProcessor` is a **shared kernel utility** (not audit-specific). Do NOT modify it.
- Identity anchored on `realUserId` via `AuditRequestThreadLocal` (`AutoResetThreadLocal`), populated by `AuditFilter`. `userId/userName/userEmailAddress` = the real acting admin.
- Config is global (`Scope.SYSTEM`) today — not in scope for LPD-97699.
- Distinct pipeline types — do NOT conflate: `AuditMessage` (kernel, in-flight), `AuditEvent` (Service Builder model, table `Audit_AuditEvent`), API read bean, REST DTO.

## Module paths

- `portal-kernel/src/com/liferay/portal/kernel/audit/AuditMessage.java`
- `modules/apps/portal-security-audit/` (OSS)
- `modules/dxp/apps/portal-security-audit/` (DXP; UI in `portal-security-audit-web`)

## Current task — LPD-97699 (additive only)

LPD-97699 creates **empty structure only** — columns, fields, mapping, upgrade, REST openapi. It does NOT populate any field a sibling story owns. Blocking story; merge order **97699 first, then ascending**.

**Confirmed code facts (from the checkout):**
- `AuditMessage` (`portal-kernel/.../audit/AuditMessage.java`) has 8 constructors funneling to a 12-arg master + a JSON constructor `AuditMessage(String)`. Mutable, but `route()` calls no setters today.
- `service.xml`: `portal-security-audit-storage-service` (table `Audit_AuditEvent`); `contextName` + `accountEntryId` are already columns.
- `_toAuditEvent` (`AuditEventLocalServiceImpl`) copies via direct getters; it does NOT copy `userEmailAddress` today (no column).
- REST DTO `AuditEvent` is `@Generated` from `portal-security-audit-rest-impl/rest-openapi.yaml` (regen via `buildREST`) — never hand-edit. Identity is exposed only as a `creator` object built via `fetchUser(userId)` at read time in `AuditEventResourceImpl`.
- No column-adding `UpgradeProcess` exists in the audit tree — use `account-service` `AccountEntryUpgradeProcess` (`alterTableAddColumn` + `hasColumn`) as the pattern.

**Fields — two special cases:**
- **13 new fields on `AuditMessage`:** `requestIdGenerated` (boolean), `requestId`, `correlationId`, `resourceType`, `resourceAction`, `objectName`, `impersonated` (boolean), `impersonatedUserId` (long), `impersonatedUserName`, `impersonatedUserEmailAddress`, `httpMethod`, `userAgent`, `roles`.
- **`userEmailAddress` already exists on `AuditMessage`** (field/getter/setter/JSON) — it is NOT a new kernel field. It needs only a new DB column + a copy line in `_toAuditEvent`.
- **Reuse existing columns:** `featureContext` → `contextName`; `accountId` → `accountEntryId`. → **14 new columns** total (13 + `userEmailAddress`).

**Type corrections (vs PD §2.1):** `auditEventId` stays `long` PK; `classPK` stays String. Note: the PD's `xRequestId` (Boolean) is implemented as `requestIdGenerated` (boolean) — a flag for whether the request ID was auto-generated, NOT a String value of `X-Request-ID`; the Boolean was correct, the name/semantics were not (PR #3280).

**Impersonation (option A):** keep `userId/userName/userEmailAddress` as the actor (real/acting admin; `userEmailAddress` is populated from the real user via `AuditRequestThreadLocal`/`AuditFilter`, LPS-172507). `doAsUser*` lives in `additionalInfo` today (derived in the `AuditMessage` constructor). Population of `impersonated`/`impersonatedUser*` is **LPD-97704**, not this story — 97699 only declares the fields.

**The 5 layers (97699):**
1. `AuditMessage` (kernel): add the 13 new fields as private field + getter/setter. Do NOT add them to any constructor (8 exist; the master takes 12 args) — population is via setter later. Add each of the 13 to BOTH `toJSONObject()` and the JSON constructor (no asymmetry exists today; don't introduce one). Keep `AuditMessage` mutable.
2. Service Builder `service.xml` (`portal-security-audit-storage-service`): 14 new columns. `String` → nullable; `long`/`boolean` are primitives → defaulted (0/false). `impersonatedUserId` = `long`, `impersonated` = `boolean`. Column length is NOT set in `service.xml` — set it in `META-INF/portlet-model-hints.xml` (default `VARCHAR(75)`): `userAgent` → max-length 255; `roles` → `<hint-collection name="CLOB" />`. Run Service Builder.
3. `_toAuditEvent`: add a `setX(getX())` line for all 14 (including `userEmailAddress`, not copied today).
4. Upgrade = **one line** in `AuditStorageServiceUpgradeStepRegistrator`: `registry.register("2.2.0", "2.3.0", UpgradeProcessFactory.addColumns("Audit_AuditEvent", "…"))`, and bump `Liferay-Require-SchemaVersion` to `2.3.0` in `bnd.bnd`. Do NOT create an `UpgradeProcess` class — the module uses `addColumns` directly (idempotency via `Release_`); a class is only needed when there's data to backfill. Current version = `2.2.0`.
5. REST: edit `rest-openapi.yaml` (non-identity fields only) + run `buildREST`; never edit the generated DTO. Identity/impersonation REST representation interacts with `creator` + 97701 masking → leave as an open design decision, do not expose now.

**Column-shape decision 97699 owns (97701 inherits):** `userId` and `impersonatedUserId` stay `long` and hold the pseudonymization numeric surrogate directly — no String retype.

**Do NOT populate (sibling stories):** `resourceAction`/`resourceType` (97700), impersonation values (97704), `httpMethod`/`userAgent`/`requestIdGenerated` (97705), `roles` (97706), PII masking (97701). Most populate centrally in `DefaultAuditRouter.route()` (which does no enrichment today).

## Guardrails

- Additive only. Never rename/remove existing fields. No breaking changes (nullable or defaulted).
- Do NOT populate sibling-story fields. 97699 declares + persists only.
- `userEmailAddress` = column + `_toAuditEvent` copy only; do not re-declare the kernel field.
- New fields go on `AuditMessage` as setters, not constructor args. Keep it mutable.
- Add each new field to BOTH `toJSONObject()` and the JSON constructor (symmetry).
- Never hand-edit the `@Generated` REST DTO — edit `rest-openapi.yaml` + `buildREST`.
- Upgrade = one `addColumns` line in `AuditStorageServiceUpgradeStepRegistrator` (`2.2.0`→`2.3.0`) + `bnd.bnd` schema-version bump. No `UpgradeProcess` class (module pattern; `Release_` handles idempotency).
- String column length goes in `portlet-model-hints.xml`, not `service.xml`: `userAgent` → 255, `roles` → CLOB.
- Never modify `BatchProcessor`.
- Follow Source Formatter conventions.

## Test conventions (audit module)

- Integration tests: `@RunWith(Arquillian.class)` + `LiferayIntegrationTestRule`; `@Inject` the service; class named `<Entity>ServiceTest` / `<Entity>LocalServiceTest`, package `...service.test`.
- **Cleanup: use `@DeleteAfterTestRun` on the injected fixtures, NOT a manual `@After`/`tearDown` loop.** (Reviewer convention established on PR #3280 for LPD-97699 — `cdbm` asked to replace the manual tearDown with `@DeleteAfterTestRun`.)
- Data generation: `RandomTestUtil.randomString()/randomLong()/randomBoolean()` for non-PK values (not `nextX()` — that's the Service Builder generator's style, not hand-written test style).
- `assertEquals` argument order in this module follows the `@generated` `AuditEventPersistenceTest`: `(actual/fetched, expected/source)`. Keep new asserts consistent with the existing ones in the same method.
- Hand-written tests should cover the `AuditMessage` → `_toAuditEvent` → persisted `AuditEvent` bridge (what the `@generated` `AuditEventPersistenceTest` does NOT cover, since it never touches `AuditMessage`). Don't duplicate raw CRUD the generated test already guarantees.
- The persistence round-trip of new columns is covered automatically by the `@generated` `AuditEventPersistenceTest` after `buildService`.

## Git baseline (read before branching/rebasing)

- This fork's PRs target **`liferay-appsec/master`**, NOT `upstream/master` (`git@github.com:liferay/liferay-portal.git`). The two diverge.
- Rebasing onto `upstream/master` by mistake pulls unrelated files into the PR diff (happened twice this epic — once via explicit rebase, once via `ant format-source-current-branch` resolving against a stale local `master` still tracking upstream).
- Before starting a story branch, confirm `master` == `liferay-appsec/master`: `git rev-parse master liferay-appsec/master`. If they differ: `git fetch liferay-appsec master && git branch -f master liferay-appsec/master`.

## Known pre-existing failures (NOT caused by your change)

- **`AuditEventResourceTest.testGetAuditEventsPage` + `testGetAuditEventsPageWithPagination` return 403.** The endpoint returns `403 Forbidden` even for the test's own company-admin user with no filters applied; there's no `resource-actions.xml` for `AuditEvent` at all. Pre-existing (no permission code was touched by 97699). The other tests in that class pass only because the generated sort helpers early-return before actually hitting the endpoint. Any story adding REST test coverage (97700/97704/97705/97706) will inherit these two failures — do NOT misattribute them to your change; ideally root-cause before adding new REST tests.

## Environment gotchas (local HSQLDB)

- Local HSQLDB (`<bundles>/data/hypersonic`) is disposable dev data.
  - **Stale schema after a column rename:** `Release_` already marks the schema version (e.g. `2.3.0`) as applied, so `addColumns` won't rerun even after you rename a column in code. Fix: stop Tomcat, `rm -rf <bundles>/data/hypersonic`, restart (full re-upgrade).
  - **Deadlock after an interrupted process:** if a process holding an HSQLDB write lock is killed abruptly, every request blocks forever on `CountUpDownLatch$Sync` (0% CPU, threads `BLOCKED`/`WAITING`, nothing `RUNNABLE`). Same fix: wipe and restart.
- Tomcat shutdown port (8005) can silently refuse connections while the process is alive — `shutdown.sh` then does nothing. Fall back to `kill <pid>` (SIGTERM, not `-9`) and poll for exit.
- `ant format-source-current-branch` only inspects **committed** files. Commit first, then amend formatter fixes in (per `.claude/rules/commit.md`).

## Acceptance criteria (LPD-97699)

1. All new fields exist in the event model and are persisted by the Database Processor.
2. Per-field required/conditional/optional matches §2.1.
3. Existing events/consumers unaffected (nullable or defaulted).
4. Upgrade handles the schema change on existing installations.
