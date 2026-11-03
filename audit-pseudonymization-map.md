# LPD-97701 — Mapa de pseudonimização (investigação read-only)

Checkout: branch `master`, HEAD `9563c2a LPD-104797 SF`. Todas as citações são do estado real do checkout nesse commit.

## 1. Read paths que re-resolvem identidade

Grep executado: `\.fetchUser\(|\.getUser\(|\.getUserById\(|\.fetchUserById\(` em `modules/apps/portal-security-audit` e `modules/dxp/apps/portal-security-audit` (excluindo `/build/`).

| Caminho | O que re-resolve | Precisa reroute? |
|---|---|---|
| `modules/apps/portal-security-audit/portal-security-audit-rest-impl/.../resource/v1_0/AuditEventResourceImpl.java:89` — `_userLocalService.fetchUser(serviceBuilderAuditEvent.getUserId())` dentro de `setCreator(() -> CreatorUtil.toCreator(null, _portal, _userLocalService.fetchUser(...)))` (linhas 86-90) | `userId` (coluna persistida) → `User` real, exposto como objeto `creator` no DTO REST | **Sim.** Se `userId` vira surrogate pseudonimizado, `fetchUser` retorna `null` (surrogate não bate com PK real) ou, pior, colide com um usuário real não relacionado e vaza os dados dele no `creator`. |
| `modules/dxp/apps/portal-security-audit/portal-security-audit-web/.../portlet/action/ExportAuditEventsMVCResourceCommand.java:234` — `_getEmailAddress(AuditEvent)`: `_userLocalService.fetchUser(auditEvent.getUserId())` | `userId` → `user.getEmailAddress()`, exportado na coluna CSV `userEmailAddress` | **Sim.** Mesmo risco: mascarar só a coluna `userEmailAddress` armazenada não adianta se o export CSV re-resolve `userId` ao vivo. |
| `.../ExportAuditEventsMVCResourceCommand.java:248` — `_getScreenName(AuditEvent)`: `_userLocalService.fetchUser(auditEvent.getUserId())` | `userId` → `user.getScreenName()`, exportado na coluna CSV `userLogin` | **Sim.** Mesmo padrão do item acima — coluna nova exposta que não está nem na lista de "Pseudonymized By Default" da §2.1, mas vaza identidade via re-lookup. |
| `modules/apps/portal-security-audit/portal-security-audit-storage-service/.../model/impl/AuditEventModelImpl.java:508-515` — getter **`@generated`** `getUserUuid()`: `UserLocalServiceUtil.getUserById(getUserId())` | `userId` → `user.getUuid()` | **Sim, mas é @generated.** Service Builder gera automaticamente `getUserUuid()`/`setUserUuid()` para qualquer entidade com coluna `userId` (ver `AuditEventModel.java:113`). Não tem chamador hoje dentro do módulo audit (busquei em todo o repo — só a própria declaração/impl/wrapper), mas é getter **público** da API gerada; qualquer consumidor externo (staging, script, outro módulo) pode chamá-lo. Não dá para "consertar" com um patch pontual — teria que ser neutralizado no nível do transform (nunca escrever um `userId` que colida com um PK real de usuário) ou aceito como limitação documentada, já que não se pode editar código `@generated`. |
| `.../AuditEventModelImpl.java:812-819` — getter **`@generated`** `getImpersonatedUserUuid()`: `UserLocalServiceUtil.getUserById(getImpersonatedUserId())` | `impersonatedUserId` → `user.getUuid()` | **Sim, mesmo caso do item acima** (gerado, sem chamador interno hoje, mas público). |
| `modules/apps/portal-security-audit/portal-security-audit-wiring/.../servlet/filter/AuditFilter.java:95` — `_userLocalService.fetchUser(userId)` | Resolve o **real user** a partir do `PrincipalThreadLocal` para popular `AuditRequestThreadLocal` (que por sua vez alimenta `AuditMessage.userEmailAddress` no construtor) | **Não** — isto roda no **write path** (produtor), antes do `route()`/transform, não é um consumidor de um `userId` já persistido/mascarado. |
| Emissores em `RoleModelListener`, `UserModelListener`, `LoginAuthFailure`, `ImpersonationAction`, `LoginPostAction`, `LogoutPostAction`, `UserGroupRoleModelListener`, `OrganizationModelListener` (DXP + OSS event-generators) — todos usam `getUser(...)`/`getUserById(...)` | Resolvem o `User` para **construir** o `AuditMessage` (identity fields + `additionalInfo`) | **Não** — também são write path, executam antes de `DefaultAuditRouter.route()`. |

**Telas de listagem e de detalhe — verificadas, NÃO re-resolvem identidade:**
- `modules/dxp/apps/portal-security-audit/portal-security-audit-web/.../display/context/AuditDisplayContext.java` (listagem, via `search_columns.jspf`) — coluna `user-id`/`user-name` (linhas 10-18 de `search_columns.jspf`) usa `property="userId"`/`property="userName"` direto do bean `AuditEvent`, sem chamar `UserLocalService`. Se as colunas persistidas vierem mascaradas, a listagem simplesmente mostra o valor mascarado — sem leak, sem necessidade de reroute.
- `modules/dxp/apps/portal-security-audit/portal-security-audit-web/.../resources/view_audit_event.jsp` (detalhe, linhas 74-80) — mesma coisa: `auditEvent.getUserId()` / `auditEvent.getUserName()` direto do bean, sem lookup.
- `LoggingAuditMessageProcessor.java` / `CSVLogMessageFormatter.java` / `JSONLogMessageFormatter.java` — formatters consomem só `auditMessage.toJSONObject()` (`CSVLogMessageFormatter.java:37`), nenhum faz `fetchUser`/`getUser`. **NÃO ENCONTRADO** nenhum re-lookup nos formatters de log.

## 2. Identidade em `additionalInfo` (free-text masking)

Grep `additionalInfoJSONObject\.put\(` nos event-generators (OSS `portal-security-audit-event-generators-user-management` + DXP `portal-security-audit-event-generators`) e no builder compartilhado `AuditMessageBuilder`.

| Chave | Emissor | Tipo do valor |
|---|---|---|
| `doAsUserId`, `doAsUserName`, `doAsUserEmailAddress` | `portal-kernel/.../audit/AuditMessage.java:81-97` (derivado automaticamente no **construtor master** do `AuditMessage`, a partir de `PrincipalThreadLocal`, sempre que `realUserId != doAsUserId` e a chave ainda não existe) — e também `portal-security-audit-event-generators-api/.../util/AuditMessageBuilder.java:81-86` (mesmo padrão, builder compartilhado dos ~40 emissores) | `doAsUserId` = id numérico (como String); `doAsUserName` = nome; `doAsUserEmailAddress` = email |
| `userEmailAddress`, `userId`, `userName` | `ImpersonationAction.java:84-90` (DXP) — identidade do usuário **efetivo** (impersonado), não do real user | email / id numérico / nome completo |
| `userEmailAddress`, `userId` | `RoleModelListener.java:183-187` (DXP) — quando um `User` é associado/removido de uma role | email / id numérico |
| `emailAddress`, `screenName`, `userId`, `userName` | `UserModelListener.java:91-98` (OSS `event-generators-user-management`) — criação/remoção de usuário | email / screen name / id numérico / nome completo |
| `roleId`, `roleName`, `organizationId`, `organizationName`, `userGroupId`, `userGroupName`, `groupId`, `groupName` | `RoleModelListener.java` / `UserGroupRoleModelListener.java` / `OrganizationModelListener.java` / `UserGroupModelListener.java` | não são identidade de **usuário** (são nomes de recursos), fora do escopo §2.1 |
| `headers` (objeto serializado, contém os HTTP headers da falha de login) | `LoginAuthFailure.java:120-124` | pode conter `X-Forwarded-For`/IP em texto livre dentro do JSON serializado dos headers — **não é uma chave de identidade de usuário fixa**, é um blob; sinalizo como risco correlato de IP mas fora do escopo direto da tabela de chaves de usuário |

**Conclusão do item 2: a identidade em `additionalInfo` está sempre sob chaves conhecidas e fixas** (`userId`, `userName`, `userEmailAddress`, `emailAddress`, `screenName`, `doAsUserId`, `doAsUserName`, `doAsUserEmailAddress`). Não encontrei nenhum caso de nome/email de usuário concatenado dentro de uma `message`/`description` em texto livre — os ~10 pontos de `.put(` de identidade encontrados no repo são todos pares chave/valor discretos. Isso torna a máscara **"por chave conhecida" viável** (ver seção final).

Achado correlato fora do additionalInfo do `AuditMessage`: `AuditFilter.java:288-300` grava `clientIP`/`userEmailAddress`/`userId`/`userLogin` num **SLF4J MDC `LogContext`** (`AuditLogContext.setContext`, thread-local, não é o `additionalInfo` do `AuditMessage`) que fica disponível para qualquer linha de log emitida durante a requisição — inclusive logs de outros módulos que nada têm a ver com audit. Isso é um vetor de vazamento de identidade em log de texto livre que **não é coberto** pelo transform do `route()` (que só vê o `AuditMessage`), mas está fora do escopo do AC da 97701 tal como descrito — sinalizo como abertura, não como algo a resolver aqui.

## 3. Encaixe no `route()` pós-97700

**Discrepância importante com a premissa da tarefa:** no checkout atual (`master`, HEAD `9563c2a`), `DefaultAuditRouter.route()` **NÃO** chama `AuditResourceActionUtil.resolve()`. Essa chamada existe apenas no commit `d94d9a1` (`LPD-97700 Derive resourceAction centrally in the audit router`), que vive isolado na branch `backup-lpd-97700-preamend` — **não está mergeada em `master`** (`git merge-base --is-ancestor d94d9a1 HEAD` → não é ancestral).

`route()` atual (`modules/apps/portal-security-audit/portal-security-audit-router/.../internal/DefaultAuditRouter.java:46-80`):

```java
public void route(AuditMessage auditMessage) throws AuditException {
    if (!AuditConfigurationUtil.isEnabled(auditMessage.getCompanyId())) {
        ...
        return;
    }

    List<AuditMessageProcessor> globalAuditMessageProcessors =
        _serviceTrackerMap.getService(StringPool.STAR);
    ...
    List<AuditMessageProcessor> auditMessageProcessors =
        _serviceTrackerMap.getService(auditMessage.getEventType());
    ...
}
```

O commit `d94d9a1` (não mergeado) insere a chamada assim:

```diff
 		if (!AuditConfigurationUtil.isEnabled(auditMessage.getCompanyId())) {
 			...
 			return;
 		}
+
+		AuditResourceActionUtil.resolve(auditMessage);
 
 		List<AuditMessageProcessor> globalAuditMessageProcessors =
```

Ou seja: `resolve()` entra logo após o early-return do `isEnabled()`, **antes** do fan-out para `globalAuditMessageProcessors`/`auditMessageProcessors`. O ponto de encaixe do transform de pseudonimização da 97701 é **imediatamente depois dessa chamada** (quando ela existir em master) — ou, se a 97701 for implementada antes da 97700 mergear, o ponto equivalente é "logo após o early-return do `isEnabled()`, antes do primeiro `.process()`", com o transform de pseudonimização vindo **depois** de `AuditResourceActionUtil.resolve()` assim que essa chamada estiver presente (ordem exigida: resolve() enriquece `resourceAction`/`resourceType` antes de qualquer processor rodar; a máscara deve rodar depois disso e antes do fan-out, para que tanto `PersistentAuditMessageProcessor` quanto `LoggingAuditMessageProcessor` só vejam a versão já mascarada).

`AuditMessage` continua **mutável** nesse ponto — `route()` recebe a mesma instância que os emissores construíram via `new AuditMessage(...)`/`AuditMessageBuilder`, todos os setters (`setUserId`, `setUserName`, `setUserEmailAddress`, `setImpersonatedUserId`, etc., e `getAdditionalInfo()` retorna o `JSONObject` mutável, não uma cópia) estão disponíveis e nenhuma chamada a setter ocorre entre a construção e o `route()`. Confirmado por leitura direta do arquivo — nenhum ponto do `route()` atual (nem do diff da 97700) faz cópia defensiva do `AuditMessage`.

## 4. Hook de "enabled" (config)

Já existe uma store de configuração de audit ativa e em uso: `AuditConfiguration` (`modules/apps/portal-security-audit/portal-security-audit-api/.../configuration/AuditConfiguration.java`):

```java
@ExtendedObjectClassDefinition(
    category = "audit", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(id = "com.liferay.portal.security.audit.configuration.AuditConfiguration", ...)
public interface AuditConfiguration {
    @Meta.AD(deflt = "true", name = "enabled", required = false)
    public boolean enabled();
}
```

Lida hoje só em `AuditConfigurationUtil.isEnabled(companyId)` (`.../configuration/AuditConfigurationUtil.java:43-48`), que já é chamada no topo do `route()` (linha 47 do `DefaultAuditRouter.java` atual). **Correção ao texto do CLAUDE.md**: apesar do doc dizer "Config is global (`Scope.SYSTEM`) today", o código real é `Scope.COMPANY` com fallback dinâmico — `AuditConfigurationUtil.getCompanyId(companyId)` (linhas 22-30) só usa o `companyId` real se a feature flag `LPD-6417` estiver habilitada para esse company; caso contrário força `CompanyConstants.SYSTEM`. Ou seja, na prática hoje (sem a flag) o comportamento é company-agnostic/system-wide, mas o mecanismo de scoping por company já existe e está pronto.

Isso responde diretamente ao item: **sim, existe um ponto pronto**. A 97701 pode adicionar um novo `@Meta.AD(deflt = "true", name = "pseudonymization-enabled", required = false) boolean pseudonymizationEnabled();` na mesma interface `AuditConfiguration`, e o transform pode ler via `AuditConfigurationUtil.getConfiguration(AuditConfiguration.class, auditMessage.getCompanyId()).pseudonymizationEnabled()` — o mesmo padrão de `isEnabled()`. A 97702 (toggle) herda o mesmo mecanismo de configuração/scoping sem precisar de infraestrutura nova.

## 5. Estado real das colunas-alvo (pós-97699)

`service.xml` (`modules/apps/portal-security-audit/portal-security-audit-storage-service/service.xml:19-50`) e `portlet-model-hints.xml` (mesmo módulo) confirmados:

| Campo | Existe como coluna? | Tipo real |
|---|---|---|
| `userId` | Sim (`service.xml:19`) | `long` |
| `userName` | Sim (`service.xml:20`) | `String` (max-length 200 em `portlet-model-hints.xml`) |
| `userEmailAddress` | Sim (`service.xml:50`) | `String` (sem max-length explícito → default `VARCHAR(75)`) |
| `clientIP` | Sim (`service.xml:30`) | `String` (max-length 255, já existia antes da 97699 — upgrade `1.0.0→1.0.1` alterou para `VARCHAR(255)`) |
| `objectName` | Sim (`service.xml:40`) | `String` (sem max-length → default `VARCHAR(75)`) |
| `impersonatedUserId` | Sim (`service.xml:37`) | `long` |
| `impersonatedUserName` | Sim (`service.xml:38`) | `String` |
| `impersonatedUserEmailAddress` | Sim (`service.xml:36`) | `String` |

**`userId` e `impersonatedUserId` são `long`, confirmado** — a 97701 pode gravar o surrogate numérico diretamente neles, sem retype para String.

**"Producer gap" de `objectName` confirmado**: grep por `setObjectName(` em todo o repo (`modules/`, `portal-kernel/`, `portal-impl/`) não encontra **nenhum** emissor de produção chamando `auditMessage.setObjectName(...)`. As únicas ocorrências fora de getters/setters gerados são em testes (`AuditEventPersistenceTest.java:156,565` e `AuditEventLocalServiceTest.java:55`, todas com `RandomTestUtil.randomString()`). Ou seja: hoje, em produção, `objectName` é **sempre null/vazio** — confirma o gap mencionado na story (população fica para stories futuras, provavelmente 97705/97706, não a 97701).

`_toAuditEvent` (`AuditEventLocalServiceImpl.java:273-311`) já copia todos os 14 campos, incluindo `userEmailAddress` (linha 312) — símetro confirmado, nada pendente da 97699 aqui.

## 6. Store `AuditPseudonym` — referências de padrão

**Dentro do próprio `-storage-service` (`service.xml` do módulo audit): NÃO ENCONTRADO nenhum finder composto único** — o único finder existente é:

```xml
<finder name="CompanyId" return-type="Collection">
    <finder-column name="companyId" />
</finder>
```

(`modules/apps/portal-security-audit/portal-security-audit-storage-service/service.xml:60-62`), sem `unique="true"` e de uma coluna só. A entidade `AuditPseudonym` será a **primeira** entidade com finder único desse módulo — não há padrão local para copiar; o padrão precisa vir de fora.

**Padrão externo encontrado** (`modules/apps/account/account-service/service.xml`), exatamente o formato pedido — companyId + chave composta com finder único:

```xml
<finder name="AEI_AUI" return-type="AccountEntryUserRel" unique="true">
    <finder-column name="accountEntryId" />
    <finder-column name="accountUserId" />
</finder>
```

(linhas 111-114) e também:

```xml
<finder name="A_O" return-type="AccountEntryOrganizationRel" unique="true">
    <finder-column name="accountEntryId" />
    <finder-column name="organizationId" />
</finder>
```

(linhas 83-86). Este é o padrão a copiar para o finder único de `AuditPseudonym` (ex.: `companyId` + campo mascarado + tipo de campo, ou o que a 97701 definir).

**Localização do `service.xml` e do `UpgradeStepRegistrator`** (onde a nova entidade `AuditPseudonym` precisa entrar):
- `service.xml`: `modules/apps/portal-security-audit/portal-security-audit-storage-service/service.xml` (mesmo arquivo da entidade `AuditEvent`, só teria uma segunda tag `<entity>` adicionada).
- `UpgradeStepRegistrator`: `modules/apps/portal-security-audit/portal-security-audit-storage-service/src/main/java/com/liferay/portal/security/audit/storage/internal/upgrade/registry/AuditStorageServiceUpgradeStepRegistrator.java`. Versão atual confirmada = **`2.3.0`** (não `2.2.0` como o CLAUDE.md registra — isso já reflete o `addColumns` da 97699, `registry.register("2.2.0", "2.3.0", ...)`, já mergeado; `bnd.bnd:8` confirma `Liferay-Require-SchemaVersion: 2.3.0`). Diferente do caso da 97699 (que só adicionava colunas a uma tabela existente), uma entidade nova precisa de **`createTable`**, não `addColumns` — é um passo de upgrade de formato diferente do que o `CLAUDE.md` documenta como padrão da 97699; vale a pena a 97701 confirmar com o time se o próprio Service Builder cobre o `createTable` automaticamente no registrator ou se precisa de um step explícito (não busquei um exemplo de `createTable` em upgrade porque está fora do escopo desta investigação read-only, mas é um ponto de atenção).

## Aberturas que o código FECHA

- **Encaixe no `route()`**: confirmado o ponto exato — logo após o early-return de `AuditConfigurationUtil.isEnabled()`, antes do fan-out para os `AuditMessageProcessor`s, e depois de `AuditResourceActionUtil.resolve()` (quando essa chamada da 97700 estiver em `master` — hoje não está, ver ressalva no item 3). `AuditMessage` é mutável nesse ponto, todos os setters funcionam.
- **Read paths reais que precisam de reroute**: mapeados e restritos a exatamente 4 pontos de produção — `creator` no REST (`AuditEventResourceImpl.java:89`), export CSV (`ExportAuditEventsMVCResourceCommand.java:234,248`), e os getters `@generated` `getUserUuid()`/`getImpersonatedUserUuid()` do modelo SB. A listagem, a tela de detalhe e os formatters de log **não** re-resolvem — só exibem a coluna já persistida, então mascarar a coluna já resolve esses caminhos automaticamente.
- **Tipos de coluna**: `userId`/`impersonatedUserId` são `long` (surrogate numérico direto, sem retype); as demais colunas-alvo (`userName`, `userEmailAddress`, `clientIP`, `objectName`, `impersonatedUserName`, `impersonatedUserEmailAddress`) são `String`, todas nullable, todas já existem.
- **Hook de config**: já existe `AuditConfiguration`/`AuditConfigurationUtil.isEnabled()` sendo lido no topo do `route()`; adicionar `pseudonymizationEnabled()` na mesma interface é um padrão direto de copiar, com scoping por company já pronto (via feature flag `LPD-6417`) para a 97702 plugar depois.
- **`additionalInfo` é mascarável por chave conhecida**: todo `.put(` de identidade encontrado usa chaves fixas (`userId`, `userName`, `userEmailAddress`, `emailAddress`, `screenName`, `doAsUserId`, `doAsUserName`, `doAsUserEmailAddress`) — nenhuma concatenação de nome/email em texto livre dentro de `message`/`description`.
- **`objectName` producer gap**: confirmado — nenhum emissor de produção popula `objectName` hoje.

## Aberturas que ainda dependem do Bence/PD

- **Ratificação do `/24` no mascaramento de `clientIP`**: nada no código decide isso hoje — `clientIP` é só um `String` armazenado e exibido cru; a granularidade da máscara é 100% decisão de produto, sem pista no código.
- **Granularidade de `featureContext`/`contextName` no finder do `AuditPseudonym`**: idem, decisão de produto — o código não tem hoje nenhum uso de `contextName` em finder composto para copiar como precedente direto (o único finder composto único do repo, `account-service`, usa chaves de relacionamento, não um campo de contexto/feature).
- **Máscara de free-text no `additionalInfo`: recomendo "por chave conhecida", não varredura.** Sustentado pelo item 2 — em ~10 pontos de emissão de identidade no `additionalInfo`, encontrados por grep em todo o módulo audit (OSS+DXP), 100% usam `.put("chaveFixa", valor)` discreto; zero casos de nome/email embutido em string livre tipo `message`. Uma varredura de texto seria over-engineering para o padrão real do código — a lista de chaves fixas (`userId`, `userName`, `userEmailAddress`, `emailAddress`, `screenName`, `doAsUserId`, `doAsUserName`, `doAsUserEmailAddress`) cobre o observado. Ressalva: o `AuditFilter`'s SLF4J MDC `LogContext` (`AuditFilter.java:288-300`) grava identidade em contexto de log fora do `AuditMessage`/`additionalInfo` — isso não é coberto por nenhuma máscara no `route()` porque não passa por ali; se o PD considerar log applicativo geral dentro do raio da 97701, é uma abertura adicional a decidir (hoje fora do meu entendimento do AC).
- **Getters `@generated` `getUserUuid()`/`getImpersonatedUserUuid()`**: são API pública gerada pelo Service Builder (não editável à mão) que re-resolve `userId`/`impersonatedUserId` para UUID via `UserLocalServiceUtil.getUserById`. Sem chamador interno hoje, mas nenhuma forma de "reroutar" via código sem tocar em arquivo `@generated` — decisão de produto/arquitetura sobre se isso é um risco aceito (não há caller hoje) ou se precisa de mitigação (ex.: garantir que o pool de surrogates nunca colida com PKs reais de usuário).
- **Formato do `createTable` para `AuditPseudonym` no upgrade**: identifiquei onde o `UpgradeStepRegistrator` vive e a versão atual (`2.3.0`), mas não investigei (fora do escopo desta tarefa read-only) se o padrão `addColumns` documentado no CLAUDE.md se estende a criação de tabela nova ou se precisa de uma classe de `UpgradeProcess` dedicada — recomendo confirmar isso tecnicamente antes de escrever o upgrade da 97701.
