<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String notificationNavigationItem = ParamUtil.getString(request, "notificationNavigationItem", "view-all-notification-templates");

CommerceNotificationQueueEntriesDisplayContext commerceNotificationQueueEntriesDisplayContext = (CommerceNotificationQueueEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL portletURL = commerceNotificationQueueEntriesDisplayContext.getPortletURL();

portletURL.setParameter("notificationNavigationItem", notificationNavigationItem);
%>

<clay:data-set-display
	contextParams='<%=
		HashMapBuilder.<String, String>put(
			"commerceChannelId", String.valueOf(commerceNotificationQueueEntriesDisplayContext.getCommerceChannelId())
		).build()
	%>'
	creationMenu="<%= commerceNotificationQueueEntriesDisplayContext.getNotificationTemplateCreationMenu() %>"
	dataProviderKey="<%= CommerceNotificationTemplateClayTable.NAME %>"
	id="<%= CommerceNotificationTemplateClayTable.NAME %>"
	itemsPerPage="<%= 10 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= portletURL %>"
	showSearch="<%= false %>"
/>