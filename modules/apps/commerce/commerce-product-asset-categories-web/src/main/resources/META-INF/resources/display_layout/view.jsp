<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CategoryCPDisplayLayoutDisplayContext categoryCPDisplayLayoutDisplayContext = (CategoryCPDisplayLayoutDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:data-set-display
	contextParams='<%=
		HashMapBuilder.<String, String>put(
			"commerceChannelId", String.valueOf(categoryCPDisplayLayoutDisplayContext.getCommerceChannelId())
		).build()
	%>'
	creationMenu="<%= categoryCPDisplayLayoutDisplayContext.getCreationMenu() %>"
	dataProviderKey="<%= CommerceCategoryDisplayPageClayTable.NAME %>"
	id="<%= CommerceCategoryDisplayPageClayTable.NAME %>"
	itemsPerPage="<%= 10 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= categoryCPDisplayLayoutDisplayContext.getPortletURL() %>"
/>