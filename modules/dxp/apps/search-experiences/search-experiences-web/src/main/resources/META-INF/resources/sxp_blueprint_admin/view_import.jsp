<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	redirect = PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCRenderCommandName(
		"/sxp_blueprint_admin/view_sxp_blueprints"
	).buildString();
}
%>

<div>
	<span aria-hidden="true" class="loading-animation"></span>

	<react:component
		module="sxp_blueprint_admin/js/view_sxp_blueprints/ImportSXPBlueprintModal"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"redirectURL", redirect
			).build()
		%>'
	/>
</div>