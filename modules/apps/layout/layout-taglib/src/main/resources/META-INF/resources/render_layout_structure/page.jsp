<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/render_layout_structure/init.jsp" %>

<%
RenderLayoutStructureDisplayContext renderLayoutStructureDisplayContext = (RenderLayoutStructureDisplayContext)request.getAttribute(RenderLayoutStructureDisplayContext.class.getName());

request.setAttribute("render_layout_structure.jsp-childrenItemIds", renderLayoutStructureDisplayContext.getMainChildrenItemIds());
%>

<liferay-util:include page="/render_layout_structure/render_layout_structure.jsp" servletContext="<%= application %>" />