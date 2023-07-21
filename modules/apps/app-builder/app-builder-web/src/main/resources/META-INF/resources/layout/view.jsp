<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/layout/init.jsp" %>

<%
String portletName = ParamUtil.getString(request, "portletName");

String editEntryCssClass = "";

String mvcPath = ParamUtil.getString(request, PortalUtil.getPortletNamespace(portletName) + "mvcPath");

if (mvcPath.startsWith("/edit_entry.jsp")) {
	editEntryCssClass = "edit-entry";
}
%>

<div class="app-builder-standalone">
	<header class="app-builder-standalone-header">
		<clay:container-fluid
			cssClass="p-0"
		>
			<clay:content-row
				cssClass="app-builder-standalone-menu"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<div>
						<a class="company-link" href="<%= PortalUtil.addPreservedParameters(themeDisplay, themeDisplay.getURLPortal(), false, true) %>">
							<span class="company-details text-truncate">
								<img alt="" class="company-logo" src="<%= themeDisplay.getPathImage() %>/company_logo?img_id=<%= company.getLogoId() %>&t=<%= WebServerServletTokenUtil.getToken(company.getLogoId()) %>" />

								<span class="company-name"><%= HtmlUtil.escape(company.getName()) %></span>
							</span>
						</a>
					</div>
				</clay:content-col>

				<div style="display: none;">
					<liferay-portlet:runtime
						portletName="<%= PortletKeys.LOGIN %>"
					/>
				</div>

				<clay:content-col
					cssClass="align-items-center flex-row mr-4"
				>
					<div class="app-builder-standalone-translation-manager" id="appTranslationManager"></div>
				</clay:content-col>

				<clay:content-col
					cssClass="align-items-center flex-row"
				>
					<div id="app-personal-menu"></div>
				</clay:content-col>
			</clay:content-row>

			<h1 class="app-builder-standalone-name <%= editEntryCssClass %>" id="appStandaloneName"></h1>
		</clay:container-fluid>
	</header>

	<clay:container-fluid
		cssClass='<%= " app-builder-standalone-content sheet " + editEntryCssClass %>'
	>
		<liferay-portlet:runtime
			portletName="<%= portletName %>"
		/>
	</clay:container-fluid>
</div>