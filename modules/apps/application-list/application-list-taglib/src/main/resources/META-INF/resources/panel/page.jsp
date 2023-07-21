<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/panel/init.jsp" %>

<%
List<PanelCategory> childPanelCategories = (List<PanelCategory>)request.getAttribute("liferay-application-list:panel:childPanelCategories");
PanelCategory panelCategory = (PanelCategory)request.getAttribute("liferay-application-list:panel:panelCategory");
%>

<c:if test="<%= !childPanelCategories.isEmpty() %>">
	<div class="list-group">

		<%
		for (PanelCategory childPanelCategory : childPanelCategories) {
		%>

			<liferay-application-list:panel-category-content
				panelCategory="<%= childPanelCategory %>"
				showOpen="<%= childPanelCategories.size() == 1 %>"
			/>

		<%
		}
		%>

	</div>

	<%
	PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);

	for (PanelApp panelApp : panelAppRegistry.getPanelApps(panelCategory.getKey())) {
	%>

		<c:if test="<%= panelApp.isShow(permissionChecker, themeDisplay.getScopeGroup()) %>">
			<div class="list-group">
				<div class="list-group-heading panel-app-root panel-header <%= Objects.equals(themeDisplay.getPpid(), panelApp.getPortletId()) ? "active" : StringPool.BLANK %>">
					<liferay-application-list:panel-app
						panelApp="<%= panelApp %>"
					/>
				</div>
			</div>
		</c:if>

	<%
	}
	%>

</c:if>