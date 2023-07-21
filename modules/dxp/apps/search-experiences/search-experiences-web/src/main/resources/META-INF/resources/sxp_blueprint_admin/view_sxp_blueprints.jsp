<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewSXPBlueprintsDisplayContext viewSXPBlueprintsDisplayContext = (ViewSXPBlueprintsDisplayContext)request.getAttribute(SXPWebKeys.VIEW_SXP_BLUEPRINTS_DISPLAY_CONTEXT);
%>

<aui:form action="<%= viewSXPBlueprintsDisplayContext.getPortletURL() %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= String.valueOf(viewSXPBlueprintsDisplayContext.getPortletURL()) %>" />

	<div id="<portlet:namespace />viewSXPBlueprints">
		<react:component
			module="sxp_blueprint_admin/js/view_sxp_blueprints/index"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"apiURL", viewSXPBlueprintsDisplayContext.getAPIURL()
				).put(
					"defaultLocale", LocaleUtil.toLanguageId(LocaleUtil.getDefault())
				).put(
					"deleteSXPBlueprintURL",
					PortletURLBuilder.createActionURL(
						liferayPortletResponse
					).setActionName(
						"/sxp_blueprint_admin/edit_sxp_blueprint"
					).setCMD(
						Constants.DELETE
					).buildString()
				).put(
					"editSXPBlueprintURL",
					PortletURLBuilder.createRenderURL(
						liferayPortletResponse
					).setMVCRenderCommandName(
						"/sxp_blueprint_admin/edit_sxp_blueprint"
					).buildString()
				).put(
					"formName", "fm"
				).put(
					"hasAddSXPBlueprintPermission", viewSXPBlueprintsDisplayContext.hasAddSXPBlueprintPermission()
				).put(
					"namespace", liferayPortletResponse.getNamespace()
				).build()
			%>'
		/>
	</div>
</aui:form>

<liferay-frontend:component
	module="sxp_blueprint_admin/js/utils/openInitialSuccessToastHandler"
/>