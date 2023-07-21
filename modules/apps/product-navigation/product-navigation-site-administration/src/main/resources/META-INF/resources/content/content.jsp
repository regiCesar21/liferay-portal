<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
PanelCategory panelCategory = (PanelCategory)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY);

ContentPanelCategoryDisplayContext contentPanelCategoryDisplayContext = new ContentPanelCategoryDisplayContext(renderRequest);
%>

<liferay-application-list:panel-category
	panelCategory="<%= panelCategory %>"
	showBody="<%= false %>"
>

	<%
	Group curSite = themeDisplay.getSiteGroup();

	List<Layout> scopeLayouts = LayoutLocalServiceUtil.getScopeGroupLayouts(curSite.getGroupId());
	%>

	<c:choose>
		<c:when test="<%= scopeLayouts.isEmpty() %>">
			<liferay-application-list:panel-category-body
				panelCategory="<%= panelCategory %>"
			/>
		</c:when>
		<c:otherwise>

			<%
			PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);

			List<PanelApp> panelApps = panelAppRegistry.getPanelApps(panelCategory, themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroup());
			%>

			<c:if test="<%= !panelApps.isEmpty() %>">
				<div class="scope-selector">

					<%
					Group curScopeGroup = themeDisplay.getScopeGroup();
					%>

					<clay:content-row
						verticalAlign="center"
					>
						<clay:content-col
							expand="<%= true %>"
						>
							<span class="scope-name">
								<c:choose>
									<c:when test="<%= curScopeGroup.isLayout() %>">
										<%= curScopeGroup.getDescriptiveName(locale) %> (<liferay-ui:message key="scope" />)
									</c:when>
									<c:otherwise>
										<liferay-ui:message key="default-scope" />
									</c:otherwise>
								</c:choose>
							</span>
						</clay:content-col>

						<clay:content-col>
							<clay:dropdown-menu
								borderless="<%= true %>"
								cssClass="text-light"
								displayType="secondary"
								dropdownItems="<%= contentPanelCategoryDisplayContext.getScopesDropdownItemList() %>"
								icon="cog"
								monospaced="<%= true %>"
							/>
						</clay:content-col>
					</clay:content-row>

					<liferay-application-list:panel-category-body
						panelCategory="<%= panelCategory %>"
					/>
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>
</liferay-application-list:panel-category>