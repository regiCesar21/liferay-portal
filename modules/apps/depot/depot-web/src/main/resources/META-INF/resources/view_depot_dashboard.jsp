<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DepotAdminViewDepotDashboardDisplayContext depotAdminViewDepotDashboardDisplayContext = (DepotAdminViewDepotDashboardDisplayContext)request.getAttribute(DepotAdminViewDepotDashboardDisplayContext.class.getName());

boolean panelsShown = false;
%>

<clay:container-fluid
	cssClass="lfr-depot-dashboard-container"
>
	<liferay-ui:breadcrumb
		showLayout="<%= false %>"
	/>

	<%
	for (PanelCategory panelCategory : depotAdminViewDepotDashboardDisplayContext.getPanelCategories()) {
		Collection<PanelApp> panelApps = depotAdminViewDepotDashboardDisplayContext.getPanelApps(panelCategory);

		panelsShown = panelsShown || !panelApps.isEmpty();
	%>

		<c:if test="<%= !panelApps.isEmpty() %>">
			<div class="spliter-spaced splitter">
				<%= panelCategory.getLabel(locale) %>
			</div>

			<ul class="display-style-icon list-unstyled row">

				<%
				for (PanelApp panelApp : panelApps) {
				%>

					<li class="entry-card entry-display-style lfr-asset-item">
						<c:choose>
							<c:when test="<%= depotAdminViewDepotDashboardDisplayContext.isPrimaryPanelCategory(panelCategory) %>">
								<clay:vertical-card
									verticalCard="<%= depotAdminViewDepotDashboardDisplayContext.getDepotDashboardApplicationVerticalCard(panelApp, locale) %>"
								/>
							</c:when>
							<c:otherwise>
								<clay:horizontal-card
									horizontalCard="<%= depotAdminViewDepotDashboardDisplayContext.getDepotDashboardApplicationHorizontalCard(panelApp, locale) %>"
								/>
							</c:otherwise>
						</c:choose>
					</li>

				<%
				}
				%>

			</ul>
		</c:if>

	<%
	}
	%>

	<c:if test="<%= !panelsShown %>">
		<clay:alert
			displayType="info"
			message="you-do-not-have-access-to-any-applications-in-this-asset-library"
		/>
	</c:if>
</clay:container-fluid>