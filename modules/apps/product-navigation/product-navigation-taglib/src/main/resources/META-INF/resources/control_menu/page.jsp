<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/control_menu/init.jsp" %>

<%
boolean applicationsMenuApp = GetterUtil.getBoolean(request.getAttribute("liferay-product-navigation:control-menu:applicationsMenuApp"));

ProductNavigationControlMenuCategoryRegistry productNavigationControlMenuCategoryRegistry = ServletContextUtil.getProductNavigationControlMenuCategoryRegistry();

List<ProductNavigationControlMenuCategory> productNavigationControlMenuCategories = productNavigationControlMenuCategoryRegistry.getProductNavigationControlMenuCategories(ProductNavigationControlMenuCategoryKeys.ROOT);

ProductNavigationControlMenuEntryRegistry productNavigationControlMenuEntryRegistry = ServletContextUtil.getProductNavigationControlMenuEntryRegistry();

boolean hasControlMenuEntries = false;

Map<ProductNavigationControlMenuCategory, List<ProductNavigationControlMenuEntry>> productNavigationControlMenuEntriesMap = new LinkedHashMap<>();

for (ProductNavigationControlMenuCategory productNavigationControlMenuCategory : productNavigationControlMenuCategories) {
	List<ProductNavigationControlMenuEntry> productNavigationControlMenuEntries = productNavigationControlMenuEntryRegistry.getProductNavigationControlMenuEntries(productNavigationControlMenuCategory, request);

	productNavigationControlMenuEntriesMap.put(productNavigationControlMenuCategory, productNavigationControlMenuEntries);

	if (!productNavigationControlMenuEntries.isEmpty()) {
		for (ProductNavigationControlMenuEntry productNavigationControlMenuEntry : productNavigationControlMenuEntries) {
			if (productNavigationControlMenuEntry.isRelevant(request)) {
				hasControlMenuEntries = true;

				break;
			}
		}
	}
}
%>

<c:if test="<%= hasControlMenuEntries %>">
	<div class="control-menu-container">
		<liferay-util:dynamic-include key="com.liferay.product.navigation.taglib#/page.jsp#pre" />

		<div class="control-menu control-menu-level-1 control-menu-level-1-<%= applicationsMenuApp ? "light" : "dark" %> d-print-none" data-qa-id="controlMenu" id="<portlet:namespace />ControlMenu">
			<clay:container-fluid>
				<h1 class="sr-only"><liferay-ui:message key="admin-header" /></h1>

				<ul class="control-menu-level-1-nav control-menu-nav" data-namespace="<portlet:namespace />" data-qa-id="header" id="<portlet:namespace />controlMenu">

					<%
					for (Map.Entry<ProductNavigationControlMenuCategory, List<ProductNavigationControlMenuEntry>> entry : productNavigationControlMenuEntriesMap.entrySet()) {
						ProductNavigationControlMenuCategory productNavigationControlMenuCategory = entry.getKey();
						List<ProductNavigationControlMenuEntry> productNavigationControlMenuEntries = entry.getValue();
					%>

						<li class="control-menu-nav-category <%= productNavigationControlMenuCategory.getKey() %>-control-group">
							<ul class="control-menu-nav" role="<%= (productNavigationControlMenuEntries.size() == 1) ? "presentation" : "menu" %>">

								<%
								for (ProductNavigationControlMenuEntry productNavigationControlMenuEntry : productNavigationControlMenuEntries) {
									if (productNavigationControlMenuEntry.includeIcon(request, PipingServletResponse.createPipingServletResponse(pageContext))) {
										continue;
									}
								%>

									<li class="control-menu-nav-item">
										<liferay-ui:icon
											data="<%= productNavigationControlMenuEntry.getData(request) %>"
											icon="<%= productNavigationControlMenuEntry.getIcon(request) %>"
											iconCssClass="<%= productNavigationControlMenuEntry.getIconCssClass(request) %>"
											label="<%= false %>"
											linkCssClass='<%= "control-menu-icon " + productNavigationControlMenuEntry.getLinkCssClass(request) %>'
											markupView="<%= productNavigationControlMenuEntry.getMarkupView(request) %>"
											message="<%= productNavigationControlMenuEntry.getLabel(locale) %>"
											method="get"
											url="<%= productNavigationControlMenuEntry.getURL(request) %>"
										/>
									</li>

								<%
								}
								%>

							</ul>
						</li>

					<%
					}
					%>

				</ul>
			</clay:container-fluid>

			<div class="control-menu-body">

				<%
				for (ProductNavigationControlMenuCategory productNavigationControlMenuCategory : productNavigationControlMenuCategories) {
					List<ProductNavigationControlMenuEntry> productNavigationControlMenuEntries = productNavigationControlMenuEntriesMap.get(productNavigationControlMenuCategory);

					for (ProductNavigationControlMenuEntry productNavigationControlMenuEntry : productNavigationControlMenuEntries) {
						productNavigationControlMenuEntry.includeBody(request, PipingServletResponse.createPipingServletResponse(pageContext));
					}
				}
				%>

			</div>

			<div id="controlMenuAlertsContainer"></div>
		</div>

		<liferay-util:dynamic-include key="com.liferay.product.navigation.taglib#/page.jsp#post" />
	</div>

	<aui:script use="liferay-product-navigation-control-menu">
		Liferay.ControlMenu.init('#<portlet:namespace />controlMenu');

		var sidenavToggles = document.querySelectorAll(
			'#<portlet:namespace />ControlMenu [data-toggle="liferay-sidenav"]'
		);

		var sidenavInstances = Array.from(sidenavToggles).map(function (toggle) {
			return Liferay.SideNavigation.instance(toggle);
		});

		sidenavInstances.forEach(function (instance) {
			instance.on('openStart.lexicon.sidenav', function (event, source) {
				sidenavInstances.forEach(function (sidenav) {
					if (sidenav !== source) {
						sidenav.hide();
					}
				});
			});
		});
	</aui:script>
</c:if>