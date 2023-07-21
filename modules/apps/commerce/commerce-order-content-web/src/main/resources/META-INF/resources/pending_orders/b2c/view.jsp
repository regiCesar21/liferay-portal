<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ddm:template-renderer
	className="<%= CommerceOpenOrderContentPortlet.class.getName() %>"
	contextObjects='<%=
		HashMapBuilder.<String, Object>put(
			"commerceOrderContentDisplayContext", commerceOrderContentDisplayContext
		).build()
	%>'
	displayStyle="<%= commerceOrderContentDisplayContext.getDisplayStyle(CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT) %>"
	displayStyleGroupId="<%= commerceOrderContentDisplayContext.getDisplayStyleGroupId(CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT) %>"
	entries="<%= commerceOrderSearchContainer.getResults() %>"
>
	<div class="container-fluid-1280" id="<portlet:namespace />ordersContainer">
		<div class="commerce-orders-container" id="<portlet:namespace />entriesContainer">
			<clay:data-set-display
				dataProviderKey="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDERS %>"
				id="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDERS %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= commerceOrderContentDisplayContext.getPortletURL() %>"
				style="stacked"
			/>
		</div>
	</div>
</liferay-ddm:template-renderer>