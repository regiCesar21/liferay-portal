<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-util:include page="/common/view_account_search_header.jsp" servletContext="<%= application %>" />

<%
String redirect = ParamUtil.getString(request, "redirect");

Product product = (Product)renderRequest.getAttribute(ProvisioningWebKeys.PRODUCT);

Map<String, String> properties = product.getProperties();
%>

<div class="add-items provisioning-products">
	<liferay-ui:header
		backURL="<%= redirect %>"
		cssClass="add-items-header"
		title="<%= product.getName() %>"
	/>

	<div cssClass="container-fluid container-fluid-max-xl">
		<div class="add-items-sheet sheet sheet-lg">
			<aui:input disabled="<%= true %>" inlineLabel="left" name="type" value='<%= properties.get("type") %>' />

			<%
			List<String> salesforceIdMappings = new ArrayList<>();

			ExternalLink[] externalLinks = product.getExternalLinks();

			if (externalLinks != null) {
				for (ExternalLink externalLink : externalLinks) {
					String domain = externalLink.getDomain();
					String entityName = externalLink.getEntityName();

					if (domain.equals(ExternalLinkDomain.SALESFORCE) && entityName.equals(ExternalLinkEntityName.SALESFORCE_PRODUCT)) {
						salesforceIdMappings.add(externalLink.getEntityId());
					}
				}
			}

			for (String salesforceIdMapping : salesforceIdMappings) {
			%>

				<aui:input disabled="<%= true %>" inlineLabel="left" name="salesforceIdMapping" value="<%= salesforceIdMapping %>" />

			<%
			}
			%>

		</div>
	</div>
</div>