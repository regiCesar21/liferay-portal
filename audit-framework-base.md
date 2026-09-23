# Audit Framework — Base de Compreensão + Plano de Código

> **Épico:** LPD-96921 *Baseline Event Schema* (Critical, dono: Christian Borges de Moura)
> **Primeira task:** LPD-97699 *Add new baseline schema fields to the audit event model* (Story, Ready for Development, additive-only)
> **Componente:** Application Security > Audit Framework

---

## Como usar este arquivo

Este é a **base compartilhada** (a fonte). O `CLAUDE.md` do repo é derivado dela, enxuto e imperativo — quando esta base mudar, atualize o `CLAUDE.md` junto.

- **Blocos 1–3:** arquitetura que o Claude Code precisa saber pra não re-derivar nem errar.
- **Bloco 4:** o épico e a task atual, com os dados reais dos tickets.
- **Bloco 5:** plano de código da LPD-97699 (camadas a tocar).
- **Bloco 6:** guardrails.

---

## 1. Arquitetura em uma tela (write path)

```
AuditFilter → produtor (AuditMessage) → AuditRouterUtil → DefaultAuditRouter
                                                              ├─ PersistentAuditMessageProcessor  (banco)
                                                              └─ LoggingAuditMessageProcessor     (log)
```

- **Síncrono desde a LPD-11677** (out/2022, quando o barramento assíncrono `liferay/audit` foi removido). Não assuma fila/async.
- Os **"dois frameworks"** da documentação da iniciativa são, na verdade, **dois processors no mesmo router** — não dois sistemas.
- **Persistência:** `PersistentAuditMessageProcessor` → `AuditEventLocalServiceImpl._toAuditEvent` → tabela `Audit_AuditEvent`. O `_toAuditEvent` lê os **getters do `AuditMessage` diretamente**.
- **Logging:** `LoggingAuditMessageProcessor` → formatters (JSON/CSV) que consomem `AuditMessage.toJSONObject()`.
- **`BatchProcessor`:** utilitário **de kernel compartilhado** (não é específico do audit). Buffer em `ConcurrentLinkedQueue`, flush por **tamanho (2000)** ou **tempo (60s)**. **Não pode ser modificado** — é genérico do kernel.

## 2. Identidade e impersonation

- A âncora de identidade é o **`realUserId`**, resolvido via `AuditRequestThreadLocal` (um `AutoResetThreadLocal`), populado pelo `AuditFilter` na entrada da requisição HTTP.
- `userId` / `userName` / `userEmailAddress` = **o ator real** (o admin que age). Ver LPS-172507.
- O sujeito impersonado hoje vive em `additionalInfo` como `doAsUser*` — a task 97699 o promove a campos de primeira classe (ver Bloco 4).

## 3. Modelo de dados e mapa de módulos

Quatro tipos distintos no pipeline — **não confundir**:

| Papel | Tipo |
|---|---|
| Mensagem in-flight (kernel) | `com.liferay.portal.kernel.audit.AuditMessage` |
| Modelo persistido (Service Builder) | `AuditEvent` → tabela `Audit_AuditEvent` |
| Bean de leitura (API) | `AuditEvent` (read) |
| DTO REST | `AuditEvent` (headless) |

**Módulos:**
- `portal-kernel/src/com/liferay/portal/kernel/audit/AuditMessage.java`
- `modules/apps/portal-security-audit/` (OSS)
- `modules/dxp/apps/portal-security-audit/` (DXP; UI em `portal-security-audit-web`)

Config hoje é **global (`Scope.SYSTEM`)** — relevante pro épico maior (instance-level), **não** pra 97699.

---

## 4. Épico e task (dados reais dos tickets)

### LPD-96921 — Baseline Event Schema · Épico · Critical

Meta: **um baseline imutável e estável** entre todos os Feature Contexts, com PII minimizada por padrão e regras de evolução de schema definidas.

- Novos campos do baseline: `featureContext`, `requestId`, `correlationId`, `accountId`, `resourceType`, `resourceAction` (hierárquico `<context>.<resource>.<action>`), campos de impersonation, `httpMethod`, `userEmailAddress`, `roles`.
- **Classificação dupla:** o `eventType` legado é mantido (retrocompat) ao lado do `resourceAction` canônico.
- **Pseudonimização reversível ligada por padrão:** `userId`, `userName`, email, `clientIP` → `/24` CIDR, `objectName`.

### LPD-97699 — Add new baseline schema fields · Story · Ready for Development

**Escopo:** DB model (`Audit_AuditEvent`) + event object + emission APIs. **Aditivo apenas** — campos existentes não são tocados.

**16 campos do baseline:**
`featureContext`, `userEmailAddress`, `requestIdGenerated`, `requestId`, `correlationId`, `accountId`, `resourceType`, `resourceAction`, `objectName`, `impersonated`, `impersonatedUserId`, `impersonatedUserName`, `impersonatedUserEmailAddress`, `httpMethod`, `userAgent`, `roles`.

**Já existem — NÃO são colunas novas (reusar):**
- `featureContext` → reusar o campo **`contextName`** existente (dormente hoje; populado depois pela LPD-97725).
- `accountId` → reusar o campo **`accountEntryId`** existente (já persistido, já exposto no REST como `accountId`).

→ Logo, são **14 campos realmente novos**, não 16. E — confirmado no código — desses 14, apenas **13 são campos novos no `AuditMessage`**: o `userEmailAddress` já existe no kernel (só falta coluna + cópia no `_toAuditEvent`). Ou seja: **14 colunas novas, 13 campos novos no kernel.**

**Correções de tipo (vs PD §2.1):**
- **`xRequestId` não é String — é `requestIdGenerated`, boolean.** A §2.1 lista `xRequestId` como Boolean; a implementação (PR #3280) confirmou que o Boolean estava certo e o errado era o nome/semântica: o campo é uma **flag** de se o request ID foi auto-gerado pelo Liferay (vs. veio do cliente), não o valor do header. O valor em si é o `requestId` (String). Não é "correção de tipo" — é rename semântico mantendo o Boolean.
- `auditEventId` continua **`long` PK**, não UUID.
- `classPK` continua **String**, não Long.

**Impersonation (opção A, já resolvida):**
- `userId` / `userName` / `userEmailAddress` continuam sendo **o ator real** (o admin).
- Os novos `impersonated` (flag) + `impersonatedUserId` / `impersonatedUserName` / `impersonatedUserEmailAddress` capturam o **sujeito impersonado**, promovidos do `additionalInfo` `doAsUser*`.
- O `impersonatingUser*` do PD é **descartado** (redundante com `userId`).

**Critérios de aceite:**
1. Todos os campos novos existem no event model e são persistidos pelo Database Processor.
2. O status required/conditional/optional de cada campo bate com a tabela §2.1 do PD.
3. Eventos e consumidores existentes não são afetados (campos nullable ou default, sem breaking change).
4. O upgrade process trata a mudança de schema em instalações já existentes.

### As stories irmãs do épico — quem popula o quê

Ponto central: **a 97699 só cria estrutura vazia (colunas + campos + mapeamento + upgrade + REST DTO). Quem POPULA cada campo é outra story.** A 97699 é o gargalo — todas dependem dela. Ordem de merge: **97699 primeiro, depois em ordem numérica.**

| Ticket | Papel | O que faz (e o que a 97699 NÃO faz) |
|---|---|---|
| **LPD-97699** | Schema (esta) | Colunas, campos no `AuditMessage`, `_toAuditEvent`, upgrade, REST openapi. **Não popula nada além do que já vem do emissor hoje.** |
| **LPD-97700** | Popula `resourceAction` + `resourceType` | Derivação **central no `DefaultAuditRouter.route()`** (não nos ~15 emissores), a partir de `eventType` + `className`. Tem tabela de mapeamento por evento. |
| **LPD-97701** | Engine de pseudonimização | Transforma `userId`/`userName`/`userEmailAddress`/`clientIP`/`objectName`/impersonated\* uma vez no `route()`; cria entidade SB nova `AuditPseudonym` (store reversível, módulo `-storage-service`). |
| **LPD-97702** | Toggle on/off da pseudonimização | Setting em Instance Settings; default enabled; emite `AUDIT_CONFIG_CHANGE`. |
| **LPD-97704** | Popula impersonation | `impersonated=true` + `impersonatedUser*` a partir de `additionalInfo.doAsUser*`, só em sessão Act As. |
| **LPD-97705** | Popula request-context | `httpMethod`/`userAgent` em eventos HTTP; **null** em não-HTTP (sem herdar de thread pooled). `requestId` + a flag `requestIdGenerated` (se o ID foi auto-gerado) andam juntos. `requestId`/`correlationId` são de **outro épico (LPD-96923)**. |
| **LPD-97706** | Popula `roles` | `User.getAllRoles()` como **JSON array de `Role.getName()`**, plaintext, snapshot point-in-time. |
| **LPD-97707** | Versionamento de schema | Constante `SCHEMA_VERSION` no kernel (valor inicial `2.3`). Metadata, não coluna. |
| **LPD-98362** | Re-identificação | Operação gated que lê a store da 97701. Fora do teu caminho. |
| **LPD-98764** | Consolidação do seam | Move todo o enrich de `route()` → `AuditMessageBuilder` e migra os ~33 `new AuditMessage(...)`. **Open, ainda não feita.** |

---

## 5. Plano de código — LPD-97699 (escopo enxuto)

> **Regra mental:** a 97699 cria **estrutura vazia**. Não popula campo que é de story irmã (`resourceAction`, `roles`, `httpMethod`, impersonated\*, pseudonimização). Só declara, persiste e faz upgrade.

**Fatos confirmados no código** (relatório `audit-framework-code-map.md`):
- `AuditMessage` tem **8 construtores** afunilando num master de 12 args + o construtor JSON `AuditMessage(String)`. É mutável, mas o `route()` **não chama setter nenhum hoje** — enrich no `route()` é código novo.
- `service.xml` fica em `portal-security-audit-storage-service`; `contextName` e `accountEntryId` **já são colunas**.
- `_toAuditEvent` copia por getters diretos e **não copia `userEmailAddress`** hoje (não há coluna).
- DTO REST `AuditEvent` é `@Generated` a partir de `portal-security-audit-rest-impl/rest-openapi.yaml` (regen via `buildREST`). Identidade só via objeto `creator` (montado com `fetchUser(userId)` no read).
- **Não há** `UpgradeProcess` que adicione coluna no módulo de audit — o padrão é `UpgradeProcessFactory.addColumns(...)` direto no `AuditStorageServiceUpgradeStepRegistrator` (idempotência via `Release_`). Versão de schema atual = `2.2.0` (`bnd.bnd`); próximo par = `2.2.0`→`2.3.0`.
- Tamanho de coluna String vem do `META-INF/portlet-model-hints.xml` (não do `service.xml`). Default `VARCHAR(75)`; precedente `clientIP` 75→255 = LPS-93033.
- Harness de persistência já existe: `AuditEventLocalServiceTest` (`addAuditEvent`→`fetchAuditEvent`→assert) para estender com assert por coluna; e o `AuditEventPersistenceTest` `@generated` cobre round-trip das 14 automaticamente.

**Contagem que importa:** **14 campos do baseline** = **14 colunas novas**, mas só **13 campos novos no `AuditMessage`** — porque `userEmailAddress` já existe lá (só falta coluna + cópia). Os 2 reusados (`featureContext`→`contextName`, `accountId`→`accountEntryId`) não contam como novos.

**As 5 camadas reais:**

1. **`AuditMessage` (kernel).** Adicionar os **13 campos novos** como `private` + getter/setter. **Não** adicionar ao construtor master de 12 args (population é via setter, feita depois pelas stories irmãs). Adicionar cada um dos 13 nos **dois lados** — `toJSONObject()` e o construtor JSON. Não há assimetria hoje; a regra é **não introduzir** uma. `userEmailAddress` já está no kernel — não re-declarar.

2. **Service Builder (`portal-security-audit-storage-service`).** **14 colunas** novas em `Audit_AuditEvent`. Nullability: `String` → nullable; `long`/`boolean` são primitivos no SB e não aceitam NULL → **defaulted** (`0`/`false`). `impersonatedUserId` = `long`, `impersonated` = `boolean`. **Tamanho de coluna String NÃO vai no `service.xml`** — vai no `META-INF/portlet-model-hints.xml` (hand-editado). Defaults são `VARCHAR(75)`; para fugir disso, adicionar hint. Decisões: **`userAgent`** → `<hint name="max-length">255</hint>` (faixa de `clientHost`/`clientIP`); **`roles`** → `<hint-collection name="CLOB" />` (mapeia `TEXT`, igual `additionalInfo`; JSON array que cresce). Demais colunas String → default 75 serve. Rodar Service Builder (`buildService` regenera o `AuditEventPersistenceTest` `@generated`, que já cobre round-trip das 14 colunas).

3. **`AuditEventLocalServiceImpl._toAuditEvent`.** Uma linha `setX(getX())` para as **14 colunas** — incluindo `userEmailAddress`, que hoje não é copiado.

4. **Upgrade — padrão do módulo, não classe dedicada.** O módulo de audit **não usa** classe `UpgradeProcess` pra coluna simples; usa `UpgradeProcessFactory.addColumns(...)` **direto no `AuditStorageServiceUpgradeStepRegistrator`** (idempotência implícita via `Release_`; `hasColumn` só quando há dado a popular). Como a 97699 é aditiva pura (sem backfill), adicionar **uma linha** `registry.register("2.2.0", "2.3.0", UpgradeProcessFactory.addColumns("Audit_AuditEvent", "…14 colunas…"))` e bumpar `Liferay-Require-SchemaVersion` para `2.3.0` no `bnd.bnd`. (Versão atual = `2.2.0`; o último passo do registrator, `2.1.0`→`2.2.0`, foi justamente quem adicionou `accountEntryId`+`contextName` — prova de que esses dois já são coluna.) Precedente direto pra tamanho: commit da **LPS-93033** (clientIP 75→255) = editar hints + upgrade separado.

5. **REST.** Editar `rest-openapi.yaml` com as propriedades dos campos aditivos **não-identidade** + rodar `buildREST`. **Nunca editar o DTO `@Generated`.** Os campos de identidade/impersonation tocam a modelagem `creator` e interagem com a máscara da 97701 → deixar como **decisão de design pendente**, não expor agora.

**O que a 97699 explicitamente NÃO faz:** derivar `resourceAction`/`resourceType` (97700), ler `doAsUser*` (97704), capturar `httpMethod`/`userAgent`/`requestIdGenerated` (97705), resolver `roles` (97706), mascarar PII (97701). Toda essa população acontece **depois**, a maioria centralizada no `route()`.

### Duas decisões arquiteturais que precedem o código

**A. `AuditMessage` fica mutável — não congelar.** As stories de população (97700/97701/97706) enriquecem em `DefaultAuditRouter.route()` como seam interino, e a **LPD-98764** move isso pra `AuditMessageBuilder` depois. Se a 97699 tornar `AuditMessage` imutável, quebra esse enrich.

**B. Tipo das duas colunas `long` sob pseudonimização é decisão SUA na 97699.** A 97701 dá **surrogate numérico** a `userId` e `impersonatedUserId` **direto na coluna long** (sem retype pra String), e diz que "a forma exata é congelada pela 97699". Não improvise — é a única decisão que a store de pseudônimos herda de você.

### Decisão de produto em aberto (levar pro Christian/Bence)

O DTO REST expõe identidade **só** como `creator` (via `fetchUser(userId)`), não como `userId`/`userName` cru. Quando a 97701 mascarar o `userId` (surrogate numérico), o `fetchUser` não acha o usuário real. A 97701 já reroteia esse ponto — mas **como a identidade deve aparecer no REST depois da máscara** (o `creator` some? vira placeholder? objeto "pseudonymized"?) atravessa 97699 (schema REST), 97701 (máscara) e 98362 (re-id). Decidir antes de fechar o `openapi` na 97699.

---

## 6. Guardrails

- **Aditivo apenas.** Nada de renomear ou remover campo existente. Nenhum consumidor atual pode quebrar (nullable ou default).
- **Não popular campo de outra story.** `resourceAction`, `roles`, `httpMethod`, `userAgent`, `requestIdGenerated`, impersonated\* e a máscara de PII são de stories irmãs. A 97699 só declara/persiste.
- **`userEmailAddress`** = coluna + cópia no `_toAuditEvent`; não re-declarar o campo no kernel.
- **Campos via setter, não construtor.** Não threadar os 13 no master de 12 args. `AuditMessage` continua mutável.
- **Simetria de serialização.** Cada campo novo entra nos dois lados (`toJSONObject()` ⇄ construtor JSON) — não introduzir assimetria.
- **Colunas nullable / default** — `String` nullable; `long`/`boolean` defaulted. O "Required" da §2.1 é contrato de emissão, não `NOT NULL` de banco.
- **DTO REST é `@Generated`** — editar `rest-openapi.yaml` + `buildREST`, nunca o `.java` gerado.
- **Upgrade = uma linha `addColumns` no `AuditStorageServiceUpgradeStepRegistrator`** (`2.2.0`→`2.3.0`), + bump do `Liferay-Require-SchemaVersion` no `bnd.bnd`. Não criar classe `UpgradeProcess` (só se houver dado a popular).
- **Tamanho de coluna String vai no `portlet-model-hints.xml`**, não no `service.xml`: `userAgent` → max-length 255; `roles` → `CLOB`.
- **Não tocar no `BatchProcessor`** (kernel genérico compartilhado).
- **Source Formatter.** Seguir as convenções do liferay-portal.

---

## Fontes

- **Jira:** LPD-96921 (épico), LPD-97699 (story); referências LPD-97700/01/02/04/05/06/07, LPD-98362, LPD-98764, LPD-97725, LPD-98129, LPS-172507.
- **Código:** confirmado em `audit-framework-code-map.md` — `portal-kernel/.../audit/AuditMessage.java`, `DefaultAuditRouter` (módulo `-router`), `portal-security-audit-storage-service`, `portal-security-audit-rest-impl`.
- **Skill:** `investigate-test-failure` (caminhos de módulo, repro local de Poshi).
