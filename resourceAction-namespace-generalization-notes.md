# Nota — generalização do prefixo `"system."` em `resourceAction`

Registrado em 2026-09-21, para referência futura. **Nada disto foi implementado** — é só o desenho de uma solução, para quando a primeira Feature Context real (AI Hub, Consent Management, FIPS, etc.) for implementada.

## O problema

`AuditMessage.setResource(String resourceType, String resourceAction)` monta o valor final assim:

```java
_resourceAction = StringBundler.concat(
	"system.", resourceType, StringPool.PERIOD, resourceAction);
```

O `"system."` está fixo (hardcoded), sempre, independente do evento. Segundo o Product Definition do Confluence ([Product Definition - Audit Management](https://liferay.atlassian.net/wiki/spaces/ENGAPPSECURITY/pages/4510220351/Product+Definition+-+Audit+Management), §2.1/§2.3), o formato correto é `<featureContext>.<resource>.<action>` — o primeiro segmento deveria ser o `featureContext` do evento (`AuditMessage.getContextName()`), não sempre `"system"`.

Exemplos citados no documento, além de `system.user.login`:
- `aihub.agent.execute`, `aihub.document.create` (Feature Context `AI_HUB`)
- `consent.preference.update` (Feature Context `CONSENT_MANAGEMENT`)
- `fips.key.zeroize` (Feature Context `FIPS`)

## Por que não é um bug ativo hoje

Verificado via `git grep` em todo o repositório: **nenhum produtor de `AuditMessage` hoje chama `setContextName(...)` com um valor de Feature Context real**. Todo evento de auditoria existente tem `contextName` nulo, então `"system"` é sempre a resposta certa por enquanto — o hardcode está correto por coincidência, não por design.

**O risco:** no dia em que um módulo de Feature Context (AI Hub, Consent Management, FIPS) passar a chamar `setContextName("AI_HUB")` antes de montar um `AuditMessage`, o `resourceAction` gerado continuaria saindo `"system.<resource>.<action>"` em vez do `"aihub.<resource>.<action>"` exigido pela spec — porque o código nunca olha para `_contextName` nessa derivação.

## Por que não é escopo do LPD-97700

Pesquisei no Jira: já existem épicos e stories dedicados a emitir auditoria para FIPS (`FIPS Level 1 Requirements`, `FIPS State Transition Logging`, ex.: LPD-99383, LPD-99221) e para AI Hub (`AI Hub chatbot observability and telemetry`, ex.: LPD-101199, LPD-101198) — nenhum deles é filho do épico "Baseline Event Schema" (LPD-96921), que é o pai do 97700. Também não encontrei, no código deste checkout, nenhum uso de `AuditMessage`/`resourceAction`/`setResource(` dentro dos módulos de FIPS ou AI Hub — sugerindo que essas stories usam (ou usarão) um caminho de auditoria próprio, ainda não conectado ao `resourceType`/`resourceAction` centralizado que o 97700 está construindo. Não encontrei nenhuma story que amarre as duas pontas.

## O design proposto (quando for a hora)

Sem criar nenhuma classe nova — tudo dentro de `AuditMessage.java` (portal-kernel):

```java
public static void registerResourceActionNamespace(
	String contextName, String namespace) {

	_resourceActionNamespaces.put(contextName, namespace);
}

public static void unregisterResourceActionNamespace(String contextName) {
	_resourceActionNamespaces.remove(contextName);
}

public void setResource(String resourceType, String resourceAction) {
	_resourceType = resourceType;

	String resourceActionNamespace = _getResourceActionNamespace();

	_resourceAction = StringBundler.concat(
		resourceActionNamespace, StringPool.PERIOD, resourceType,
		StringPool.PERIOD, resourceAction);
}

private String _getResourceActionNamespace() {
	if (Validator.isNull(_contextName)) {
		return "system";
	}

	String namespace = _resourceActionNamespaces.get(_contextName);

	return (namespace != null) ? namespace :
		StringUtil.toLowerCase(_contextName);
}

private static final Map<String, String> _resourceActionNamespaces =
	new ConcurrentHashMap<>();
```

Cada Feature Context, quando for implementado, registra o próprio namespace com uma linha dentro de uma classe que já vai existir naquele módulo (o "builder" de `AuditMessage` daquele contexto, no mesmo padrão do `MFAFIDO2AuditMessageBuilder`), num bloco estático — sem componente OSGi dedicado, sem classe nova:

```java
static {
	AuditMessage.registerResourceActionNamespace("AI_HUB", "aihub");
}
```

**Por que não uma transformação automática de texto** (tipo `contextName.toLowerCase()`)? Porque não existe fórmula mecânica que resolva os dois exemplos do documento ao mesmo tempo: `AI_HUB` → `aihub` (só tira o underscore) mas `CONSENT_MANAGEMENT` → `consent` (corta a palavra "MANAGEMENT" inteira). Cada Feature Context precisa declarar o próprio namespace explicitamente.

## Escopo do que mudaria

- ~15-20 linhas em `AuditMessage.java` + alguns casos novos em `AuditMessageTest.java` (contextName nulo / registrado / não registrado).
- Zero classes novas, em qualquer módulo.
- Zero mudança de comportamento observável hoje (nenhum Feature Context real existe ainda, então `_getResourceActionNamespace()` sempre cai no caso `null` → `"system"`).
- Pendência futura: só a linha de `registerResourceActionNamespace(...)` dentro do módulo de cada Feature Context, quando ele for implementado.
