<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceInventoryDisplayContext commerceInventoryDisplayContext = (CommerceInventoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<commerce-ui:panel
	bodyClasses="p-0"
	title='<%= LanguageUtil.get(request, "incoming") %>'
>
	<clay:data-set-display
		contextParams='<%=
			HashMapBuilder.<String, String>put(
				"sku", commerceInventoryDisplayContext.getSku()
			).build()
		%>'
		creationMenu="<%= commerceInventoryDisplayContext.getReplenishmentCreationMenu() %>"
		dataProviderKey="<%= CommerceInventoryDataSetConstants.COMMERCE_DATA_SET_KEY_INVENTORY_REPLENISHMENT %>"
		id="<%= CommerceInventoryDataSetConstants.COMMERCE_DATA_SET_KEY_INVENTORY_REPLENISHMENT %>"
		itemsPerPage="<%= 10 %>"
		namespace="<%= liferayPortletResponse.getNamespace() %>"
		pageNumber="<%= 1 %>"
		portletURL="<%= commerceInventoryDisplayContext.getPortletURL() %>"
		showSearch="<%= false %>"
	/>
</commerce-ui:panel>