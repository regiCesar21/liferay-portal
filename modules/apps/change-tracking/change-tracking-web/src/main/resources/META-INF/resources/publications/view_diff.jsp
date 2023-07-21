<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<liferay-portlet:renderURL var="backURL">
	<portlet:param name="mvcRenderCommandName" value="/change_tracking/view_publications" />
</liferay-portlet:renderURL>

<%
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<liferay-util:include page="/publications/render_diff.jsp" servletContext="<%= application %>" />