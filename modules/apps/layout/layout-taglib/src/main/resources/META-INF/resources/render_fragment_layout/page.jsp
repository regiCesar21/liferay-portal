<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/render_fragment_layout/init.jsp" %>

<%
Map<String, Object> fieldValues = (Map<String, Object>)request.getAttribute("liferay-layout:render-fragment-layout:fieldValues");
LayoutStructure layoutStructure = (LayoutStructure)request.getAttribute("liferay-layout:render-fragment-layout:layoutStructure");
String mainItemId = (String)request.getAttribute("liferay-layout:render-fragment-layout:mainItemId");
String mode = (String)request.getAttribute("liferay-layout:render-fragment-layout:mode");
boolean showPreview = GetterUtil.getBoolean(request.getAttribute("liferay-layout:render-fragment-layout:showPreview"));

RenderFragmentLayoutDisplayContext renderFragmentLayoutDisplayContext = new RenderFragmentLayoutDisplayContext(request, response);
%>

<c:if test="<%= layoutStructure != null %>">

	<%
	try {
		request.setAttribute(WebKeys.SHOW_PORTLET_TOPPER, Boolean.TRUE);
	%>

		<liferay-util:buffer
			var="content"
		>
			<liferay-layout:render-layout-structure
				fieldValues="<%= fieldValues %>"
				layoutStructure="<%= layoutStructure %>"
				mainItemId="<%= mainItemId %>"
				mode="<%= mode %>"
				showPreview="<%= showPreview %>"
			/>
		</liferay-util:buffer>

		<%= renderFragmentLayoutDisplayContext.processAMImages(content) %>

	<%
	}
	finally {
		request.removeAttribute(WebKeys.SHOW_PORTLET_TOPPER);
	}
	%>

</c:if>