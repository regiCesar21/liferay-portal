<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
RedirectDisplayContext redirectDisplayContext = (RedirectDisplayContext)request.getAttribute(RedirectDisplayContext.class.getName());
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= redirectDisplayContext.getNavigationItems() %>"
/>

<c:choose>
	<c:when test="<%= redirectDisplayContext.isShowRedirectNotFoundEntries() %>">
		<liferay-util:include page="/view_redirect_not_found_entries.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/view_redirect_entries.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>