<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<commerce-ui:panel
	bodyClasses="p-0"
	title='<%= LanguageUtil.get(request, "emails") %>'
>
	<clay:data-set-display
		contextParams='<%=
			HashMapBuilder.<String, String>put(
				"commerceOrderId", String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderId())
			).build()
		%>'
		dataProviderKey="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_NOTIFICATIONS %>"
		id="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_NOTIFICATIONS %>"
		itemsPerPage="<%= 10 %>"
		namespace="<%= liferayPortletResponse.getNamespace() %>"
		pageNumber="<%= 1 %>"
		portletURL="<%= commerceOrderEditDisplayContext.getCommerceNotificationQueueEntriesPortletURL() %>"
		showManagementBar="<%= false %>"
	/>
</commerce-ui:panel>