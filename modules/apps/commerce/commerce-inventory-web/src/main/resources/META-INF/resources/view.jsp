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

<div class="row">
	<div class="col-12">
		<clay:data-set-display
			creationMenu="<%= commerceInventoryDisplayContext.getInventoryItemCreationMenu() %>"
			dataProviderKey="<%= CommerceInventoryDataSetConstants.COMMERCE_DATA_SET_KEY_INVENTORY_ITEMS %>"
			id="<%= CommerceInventoryDataSetConstants.COMMERCE_DATA_SET_KEY_INVENTORY_ITEMS %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			pageNumber="<%= 1 %>"
			portletURL="<%= commerceInventoryDisplayContext.getPortletURL() %>"
			style="fluid"
		/>
	</div>
</div>