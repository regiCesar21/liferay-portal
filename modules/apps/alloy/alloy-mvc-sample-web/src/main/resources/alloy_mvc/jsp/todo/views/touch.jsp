<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

@generated
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.model.Portlet" %><%@
page import="com.liferay.portal.kernel.service.PortletLocalServiceUtil" %>

<%@ page import="java.util.Set" %>

<liferay-theme:defineObjects />

<%
Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());

Set<String> paths = application.getResourcePaths("/alloy_mvc/jsp/" + portlet.getFriendlyURLMapping() + "/controllers/");

for (String path : paths) {
	int x = path.lastIndexOf("/");
	int y = path.indexOf("_controller.jsp");

	if (y == -1) {
		continue;
	}

	String controller = path.substring(x + 1, y);
%>

	<portlet:resourceURL var="resourceURL">
		<portlet:param name="controller" value="<%= controller %>" />
		<portlet:param name="action" value="touch" />
	</portlet:resourceURL>

	<iframe height="0" src="<%= resourceURL %>" style="display: none; visibility: hidden;" width="0"></iframe>

<%
}
%>