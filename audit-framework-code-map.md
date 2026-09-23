# Audit Framework Code Map

Read-only investigation. No files were edited, no build/Service Builder/REST Builder tools were run, no Jira tickets were touched. All findings below are direct file citations from the current checkout.

## 1. Kernel — `AuditMessage`

File: `portal-kernel/src/com/liferay/portal/kernel/audit/AuditMessage.java`

**(a) Current fields** (all `private`, no `final`, all have setters — see divergence note below):

```
private long _accountEntryId;
private JSONObject _additionalInfoJSONObject;
private String _className;
private String _classPK;
private String _clientHost;
private String _clientIP;
private long _companyId = -1;
private String _contextName;
private String _eventType;
private long _groupId = -1;
private String _message;
private String _serverName;
private int _serverPort;
private String _sessionID;
private Date _timestampDate;
private String _userEmailAddress;
private long _userId = -1;
private String _userLogin;
private String _userName;
```

**(b) Constructors.** There are 8 constructors, all ultimately delegating to the full 12-arg constructor:

```java
public AuditMessage(
    long groupId, long companyId, long userId, String userName,
    Date timestampDate, long accountEntryId,
    JSONObject additionalInfoJSONObject, String className, String classPK,
    String contextName, String eventType, String message)
```

This is the "master" constructor (lines 46–109). It sets thread-local-derived fields (`_clientHost`, `_clientIP`, `_serverName`, `_serverPort`, `_sessionID`, `_userEmailAddress`) from `AuditRequestThreadLocal`, and derives `doAsUserId`/`doAsUserName`/`doAsUserEmailAddress` into `additionalInfo` (see item 8).

The JSON-deserializing constructor:

```java
public AuditMessage(String message) throws JSONException {
    JSONObject jsonObject = JSONFactoryUtil.createJSONObject(message);
    _accountEntryId = jsonObject.getLong(_ACCOUNT_ENTRY_ID);
    ...
}
```

(lines 158–202)

**(c) `toJSONObject()`** (lines 360–404) writes all 19 fields: `accountEntryId`, `additionalInfo`, `className`, `classPK`, `clientHost`, `clientIP`, `companyId`, `contextName`, `eventType`, `groupId`, `message`, `serverName`, `serverPort`, `sessionID`, `timestamp`, `userEmailAddress`, `userId`, `userLogin`, `userName`.

**(d) `contextName` and `accountEntryId` already exist** as fields, constructor params, getters/setters, JSON-read fields, and `toJSONObject()` entries. They are **not new** — the framework already has them end-to-end.

**Asymmetry check:** none found. Every key written by `toJSONObject()` is also read by the JSON constructor (`_ACCOUNT_ENTRY_ID`, `_CONTEXT_NAME`, etc. — all 19 match exactly). This directly contradicts the ticket-slice assumption that there is a gap between `toJSONObject()` and the JSON constructor for `contextName`/`accountEntryId` — there is none; both already round-trip correctly.

## 2. Router

- `DefaultAuditRouter`: `modules/apps/portal-security-audit/portal-security-audit-router/src/main/java/com/liferay/portal/security/audit/router/internal/DefaultAuditRouter.java`
- `AuditRouterUtil`: `portal-kernel/src/com/liferay/portal/kernel/audit/AuditRouterUtil.java` (thin static wrapper around a `Snapshot<AuditRouter>`, calls `auditRouter.route(auditMessage)` at line 29 — no logic of its own)

`route()` in full (lines 46–80):

```java
@Override
public void route(AuditMessage auditMessage) throws AuditException {
    if (!AuditConfigurationUtil.isEnabled(auditMessage.getCompanyId())) {
        if (_log.isDebugEnabled()) {
            _log.debug(...);
        }
        return;
    }

    List<AuditMessageProcessor> globalAuditMessageProcessors =
        _serviceTrackerMap.getService(StringPool.STAR);

    if (globalAuditMessageProcessors != null) {
        for (AuditMessageProcessor globalAuditMessageProcessor :
                globalAuditMessageProcessors) {
            globalAuditMessageProcessor.process(auditMessage);
        }
    }

    List<AuditMessageProcessor> auditMessageProcessors =
        _serviceTrackerMap.getService(auditMessage.getEventType());

    if (auditMessageProcessors != null) {
        for (AuditMessageProcessor auditMessageProcessor :
                auditMessageProcessors) {
            auditMessageProcessor.process(auditMessage);
        }
    }
}
```

- **No enrichment/mutation before fan-out today.** The only thing `route()` does before dispatch is the enabled-check; it never calls a setter on `auditMessage`. Processors are called directly at lines 66 (global, event-type `"*"`) and 77 (event-type-specific).
- **`AuditMessage` is mutable** — it has a full set of setters (`setContextName`, `setAccountEntryId`, `setUserId`, etc., lines 280–357) — but `route()` itself never calls any of them. No other caller in the router module calls a setter after construction either (grep confirms no `auditMessage.set` calls in this module). So mutability exists in the type but is unused on the routing path today — any planned "central enrichment in `route()`" (per the 97700 assumption) would be new code, not something already partially there.

## 3. Service Builder / persisted model

Module: `modules/apps/portal-security-audit/portal-security-audit-storage-service` — confirmed, this **is** `portal-security-audit-storage-service`.

`service.xml` entity `AuditEvent` (namespace `Audit`, table `Audit_AuditEvent`), full column list:

```xml
<column name="auditEventId" primary="true" type="long" />
<column name="groupId" type="long" />
<column name="companyId" type="long" />
<column name="userId" type="long" />
<column name="userName" type="String" />
<column name="createDate" type="Date" />
<column name="accountEntryId" type="long" />
<column name="additionalInfo" type="String" />
<column name="className" type="String" />
<column name="classPK" type="String" />
<column name="clientHost" type="String" />
<column name="clientIP" type="String" />
<column name="contextName" type="String" />
<column name="eventType" type="String" />
<column name="message" type="String" />
<column name="serverName" type="String" />
<column name="serverPort" type="int" />
<column name="sessionID" type="String" />
```

`contextName` and `accountEntryId` are **already columns** on the entity — confirming item 1's finding; there is no new column to add for these two fields.

`AuditEventLocalServiceImpl._toAuditEvent` (`.../storage/service/impl/AuditEventLocalServiceImpl.java`, lines 274–299) copies fields via **direct getters**, one line per field, no transformation/mapping table:

```java
private AuditEvent _toAuditEvent(AuditMessage auditMessage, long auditEventId) {
    AuditEvent auditEvent = auditEventPersistence.create(auditEventId);
    auditEvent.setGroupId(auditMessage.getGroupId());
    auditEvent.setCompanyId(auditMessage.getCompanyId());
    auditEvent.setUserId(auditMessage.getUserId());
    auditEvent.setUserName(auditMessage.getUserName());
    auditEvent.setCreateDate(auditMessage.getTimestampDate());
    auditEvent.setAccountEntryId(auditMessage.getAccountEntryId());
    auditEvent.setAdditionalInfo(String.valueOf(auditMessage.getAdditionalInfo()));
    auditEvent.setClassName(auditMessage.getClassName());
    auditEvent.setClassPK(auditMessage.getClassPK());
    auditEvent.setClientHost(auditMessage.getClientHost());
    auditEvent.setClientIP(auditMessage.getClientIP());
    auditEvent.setContextName(auditMessage.getContextName());
    auditEvent.setEventType(auditMessage.getEventType());
    auditEvent.setMessage(auditMessage.getMessage());
    auditEvent.setServerName(auditMessage.getServerName());
    auditEvent.setServerPort(auditMessage.getServerPort());
    auditEvent.setSessionID(auditMessage.getSessionID());
    return auditEvent;
}
```

Note: `userEmailAddress` and `userLogin` from `AuditMessage` are **not** persisted onto `AuditEvent` — there is no corresponding column and `_toAuditEvent` doesn't copy them. Only `userId`/`userName` are stored.

## 4. Upgrade

Only one `UpgradeProcess` exists anywhere under `modules/apps/portal-security-audit`:

`modules/apps/portal-security-audit/portal-security-audit-router/src/main/java/com/liferay/portal/security/audit/router/internal/upgrade/v1_0_0/AuditConfigurationUpgradeProcess.java`

This one does **not** touch table columns at all — it migrates an OSGi `AuditConfiguration` (config-admin) setting per company, via `ConfigurationAdmin`/`ConfigurationProvider`, not `alterTableAddColumn`/`hasColumn`.

**No `alterTableAddColumn`/`hasColumn` pattern exists anywhere in the audit module tree** — grep for both terms under `modules/apps/portal-security-audit` returns nothing. This makes sense given item 3: `contextName`/`accountEntryId` are already baseline columns in `service.xml`, so no column-adding upgrade was ever needed for them, and there is no existing "template" upgrade in this module to copy from for a 97699-style column upgrade. For reference, similar `alterTableAddColumn` patterns do exist elsewhere in the repo (e.g. `modules/apps/account/account-service/.../upgrade/v2_1_0/AccountGroupUpgradeProcess.java`, `.../account/.../v1_1_0/AccountEntryUpgradeProcess.java`) if a template is needed — but none live in the audit module itself.

## 5. REST / headless

A REST Builder module bundle exists:

- `portal-security-audit-rest-api`, `portal-security-audit-rest-impl`, `portal-security-audit-rest-client`, `portal-security-audit-rest-test` under `modules/apps/portal-security-audit`.
- OpenAPI spec: `modules/apps/portal-security-audit/portal-security-audit-rest-impl/rest-openapi.yaml`
- Generated DTO: `modules/apps/portal-security-audit/portal-security-audit-rest-api/src/main/java/com/liferay/portal/security/audit/rest/dto/v1_0/AuditEvent.java` (generated, `@Generated` — leave alone per rest-builder rule)
- Invoked the standard way: `buildREST` from `portal-security-audit-rest-impl`, or `ant build-rests` from `portal-impl` for all modules at once.

Only one endpoint exists: `GET /audit-events` (`operationId: getAuditEventsPage`, tag `AuditEvent`), implemented in `AuditEventResourceImpl.getAuditEventsPage(...)`.

**Important divergence:** the REST DTO's `AuditEvent` schema does **not** expose `userId`/`userName`/`userEmailAddress` as top-level fields at all. Its schema properties are: `accountId`, `additionalInfo`, `clientHost`, `clientIP`, `contextName`, `creator`, `dateCreated`, `entityId`, `entityType`, `eventType`, `groupId`, `serverName`, `id`. Identity is represented only via a `creator` field typed `$ref: .../headless-delivery-impl/rest-openapi.yaml#Creator` — i.e. the headless-delivery `Creator` shape (id, name, etc.), not raw `userId`/`userName` echoing the storage model directly.

## 6. Read paths of identity

Grep of `fetchUser(`/`getUser(` across `modules/apps/portal-security-audit` (excluding `/build/` and test-only usages of `TestPropsValues`/`UserTestUtil`):

- **`AuditEventResourceImpl.java`** (`.../portal-security-audit-rest-impl/.../resource/v1_0/AuditEventResourceImpl.java`, lines 85–89), inside `_toAuditEvent`:

  ```java
  setCreator(
      () -> CreatorUtil.toCreator(
          null, _portal,
          _userLocalService.fetchUser(
              serviceBuilderAuditEvent.getUserId())));
  ```

  This is the **only** identity re-resolution point on the REST read path — it re-fetches the `User` by the stored `userId` on every page of results, to build the `Creator` DTO.

- **`AuditFilter.java`** (`.../portal-security-audit-wiring/.../servlet/filter/AuditFilter.java`, line 94): `_userLocalService.fetchUser(userId)`. This is **not** a read path over stored audit events — it runs on every HTTP request to populate `AuditRequestThreadLocal` (real user email/login) before an `AuditMessage` is even built, i.e. at emission time, not at query/detail/export time.

- No dedicated audit-event detail-screen or CSV-export code exists under this module tree with its own `fetchUser`/`getUser` call. `CSVLogMessageFormatter` (`.../portal-security-audit-router/.../CSVLogMessageFormatter.java`) formats already-populated `AuditMessage` fields and does **not** call `fetchUser`/`getUser` (confirmed empty grep result). There is no `*-web` admin UI module for browsing audit events — the only web module found is `portal-security-audit-configuration-web`, which is for the enable/disable configuration screen, not event browsing.

**Conclusion for item 6:** there is exactly one production re-resolution-from-`userId` call site to worry about for a "drop/reroute" change: `AuditEventResourceImpl._toAuditEvent`'s `Creator` construction. There is no separate UI detail screen or CSV export path that re-resolves identity independently.

## 7. Emission points

Call sites of `new AuditMessage(` across the whole repo (excluding `/build/` output), **excluding** the two central builder/factory classes and test files:

Central/builder classes (not counted as emission sites, these are the shared construction points):
- `portal-kernel/src/com/liferay/portal/kernel/audit/AuditMessageFactory.java` — 6 call sites (lines 23, 33, 43, 51, 58, 66, 72). **This is a second central construction point not mentioned in the ticket assumptions** — the tickets assume only `AuditMessageBuilder` is central; `AuditMessageFactory` in `portal-kernel` is a second, separate one.
- `modules/apps/portal-security-audit/portal-security-audit-event-generators-api/.../util/AuditMessageBuilder.java` — 1 call site (line 95).

Test files (excluded from the production count): `AuditEventResourceTest.java`, `DefaultAuditRouterTest.java`, `AuditMessageTest.java`.

**Production call sites outside both central classes — 15 files, ~34 call sites:**

| File | Call sites |
|---|---|
| `modules/apps/oauth2-provider/oauth2-provider-rest/.../LiferayDynamicRegistrationService.java` | 2 |
| `modules/apps/oauth2-provider/oauth2-provider-rest/.../DynamicRegistrationServiceContainerRequestFilter.java` | 1 |
| `modules/apps/portal-scheduler/portal-scheduler/.../SchedulerEngineAuditorImpl.java` | 1 |
| `modules/dxp/apps/antivirus/antivirus-async-store/.../AntivirusScannerHelper.java` | 1 |
| `modules/dxp/apps/multi-factor-authentication/multi-factor-authentication-email-otp-web/.../EmailOTPBrowserMFAChecker.java` | 5 |
| `modules/dxp/apps/multi-factor-authentication/multi-factor-authentication-fido2-web/.../MFAFIDO2AuditMessageBuilder.java` | 6 |
| `modules/dxp/apps/multi-factor-authentication/multi-factor-authentication-ip-address-impl/.../IPAddressHeadlessMFAChecker.java` | 3 |
| `modules/dxp/apps/multi-factor-authentication/multi-factor-authentication-timebased-otp-web/.../TimeBasedOTPBrowserSetupMFAChecker.java` | 6 |
| `modules/dxp/apps/portal-security-audit/portal-security-audit-event-generators/.../LoginPostAction.java` | 1 |
| `modules/dxp/apps/portal-security-audit/portal-security-audit-event-generators/.../LogoutPostAction.java` | 1 |
| `modules/dxp/apps/portal-security-audit/portal-security-audit-event-generators/.../ImpersonationAction.java` | 1 |
| `modules/dxp/apps/portal-security-audit/portal-security-audit-event-generators/.../LoginAuthDNE.java` | 1 |
| `modules/dxp/apps/portal-security-audit/portal-security-audit-event-generators/.../LoginAuthFailure.java` | 1 |
| `portal-impl/src/com/liferay/portal/action/LayoutAction.java` | 1 |
| `portal-impl/src/com/liferay/portal/service/impl/CompanyServiceImpl.java` | 3 |

**Conclusion for item 7:** the assumption that a single central `route()`-time derivation (97700) "covers all" emission sites is only true for whatever `route()` itself derives, since it runs after every one of these `new AuditMessage(...)` sites regardless of which constructor they call (all constructors funnel through `AuditRouterUtil.route()` downstream — confirmed no call site bypasses it in this grep). But it does **not** make the 15 call sites uniform at construction time: they call different overloaded constructors (several use the 6-arg or 4-arg overloads that don't accept `contextName`/`accountEntryId`/`additionalInfo` directly), so any field only derivable from constructor arguments — as opposed to something `route()` can compute from already-set state — would still need per-call-site changes or a new constructor-level default. `MFAFIDO2AuditMessageBuilder.java` (in `multi-factor-authentication-fido2-web`) is itself a per-feature "builder" analogous to the central `AuditMessageBuilder`, but is not the same class — a third, feature-local pattern worth flagging for 98764's scope.

## 8. Impersonation / `doAsUser`

Grep of `doAsUserId`, `doAsUserName`, `getRealUser` restricted to `portal-kernel/.../audit/` and `modules/apps/portal-security-audit`:

- **Where `doAsUser*` is placed today:** in `AuditMessage`'s master constructor (`portal-kernel/.../audit/AuditMessage.java`, lines 77–97):

  ```java
  long realUserId = auditRequestThreadLocal.getRealUserId();
  long doAsUserId = 0;
  if (PrincipalThreadLocal.getName() != null) {
      doAsUserId = GetterUtil.getLong(PrincipalThreadLocal.getName());
  }
  if ((realUserId > 0) && (doAsUserId != realUserId) &&
      !_additionalInfoJSONObject.has("doAsUserId")) {
      _additionalInfoJSONObject.put(
          "doAsUserEmailAddress", PortalUtil.getUserEmailAddress(doAsUserId)
      ).put(
          "doAsUserId", String.valueOf(doAsUserId)
      ).put(
          "doAsUserName", PortalUtil.getUserName(doAsUserId, StringPool.BLANK)
      );
  }
  ```

  This runs inside every `AuditMessage` construction (any constructor, since all delegate to this one) — it is **at construction time**, not something `route()` adds later. A second, independent copy of the same `doAsUserId`/`doAsUserName` logic exists in `AuditMessageBuilder.java` (`.../event-generators-api/.../util/AuditMessageBuilder.java`, lines 72–86) — i.e. this derivation is **already duplicated** across two places in the codebase, not centralized even today.

- **`getRealUserEmailAddress` resolution:** `AuditRequestThreadLocal.getRealUserEmailAddress()` (`portal-kernel/.../audit/AuditRequestThreadLocal.java`, line 43) is a plain thread-local getter; the value is populated once per HTTP request in `AuditFilter.doFilterTry` (`.../portal-security-audit-wiring/.../AuditFilter.java`, lines 93–107) via `_userLocalService.fetchUser(userId)` on the **real** user id read from the HTTP session (`WebKeys.USER_ID`), not the do-as/impersonated principal. `AuditMessage`'s constructor then copies this into `_userEmailAddress` (line 75) unconditionally — confirming the audited "actor" email is always the real user's email, never the impersonated one, and impersonation info is only ever a side-channel in `additionalInfo`.

This confirms the "Option A" assumption: today, `doAsUser*` context lives only inside `additionalInfo`, is derived twice (constructor + `AuditMessageBuilder`, not once), and the top-level `userEmailAddress`/`userId`/`userName` fields always represent the real, non-impersonated actor.

## Divergências

- **`AuditMessage` lives in `portal-kernel`** — confirmed, no divergence.
- **`service.xml` lives in `portal-security-audit-storage-service`** — confirmed, no divergence.
- **`_toAuditEvent` reads getters directly** — confirmed, no divergence. But note: `userEmailAddress`/`userLogin` are read on `AuditMessage` yet never persisted to `AuditEvent` (no column, no copy) — a real, silent gap if a future ticket assumes those are already stored.
- **`route()` is synchronous and does no enrichment today** — confirmed. It also never calls a setter on `AuditMessage`, even though the type is fully mutable — any "enrich in `route()`" work is genuinely new, not a refactor of existing code.
- **`contextName`/`accountEntryId` already exist** — confirmed everywhere: `AuditMessage` (field/ctor/getter/setter/JSON in+out), `service.xml` column, `_toAuditEvent` copy, REST OpenAPI schema and query param, and `_buildDynamicQuery`/finder-style filtering in `AuditEventLocalServiceImpl`. There is **no gap to close** for these two fields specifically — if the 97699/97700 tickets are framed as "add `contextName`/`accountEntryId`," that framing is already stale; the fields are fully wired end-to-end today.
- **New, previously-unassumed findings:**

  1. `AuditMessageFactory` (`portal-kernel`) is a second central `AuditMessage`-construction point, separate from `AuditMessageBuilder`, with 6 of its own call sites. Any change to a "single" central builder (98764) needs to account for both.

  1. `doAsUserId`/`doAsUserName` derivation is already duplicated in two places (`AuditMessage` constructor and `AuditMessageBuilder`), not single-sourced — worth resolving as part of, not after, 97704's centralization work.

  1. The REST DTO (`AuditEvent` in `rest-openapi.yaml`) does not expose raw `userId`/`userName` at all, only a `creator` object built by re-fetching the `User` from `userId` at read time in `AuditEventResourceImpl`. This is the one and only identity re-resolution point on the read side — there is no separate admin UI screen or CSV-export path that does its own re-resolution.

  1. No `alterTableAddColumn`/`hasColumn` upgrade pattern exists anywhere in the audit module tree to use as a template for a 97699-style column upgrade — the only existing `UpgradeProcess` in the tree is a config-migration one, unrelated to table columns.