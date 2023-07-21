<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-site-navigation:breadcrumb
	breadcrumbEntries="<%= layoutsAdminDisplayContext.getPortletBreadcrumbEntries() %>"
/>

<liferay-ui:search-container
	id="pages"
	searchContainer="<%= layoutsAdminDisplayContext.getLayoutsSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Layout"
		keyProperty="plid"
		modelVar="layout"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand table-cell-minw-200 table-title"
			href="<%= layoutsAdminDisplayContext.isShowViewLayoutAction(layout) ? layoutsAdminDisplayContext.getViewLayoutURL(layout) : StringPool.BLANK %>"
			name="title"
			value="<%= layout.getName(locale) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand table-cell-minw-200"
			name="relative-path"
		>
			<liferay-site-navigation:breadcrumb
				breadcrumbEntries="<%= layoutsAdminDisplayContext.getRelativeBreadcrumbEntries(layout) %>"
			/>
		</liferay-ui:search-container-column-text>

		<%
		LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(layout.getType());

		ResourceBundle layoutTypeResourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, layoutTypeController.getClass());
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-ws-nowrap"
			name="type"
			value='<%= LanguageUtil.get(request, layoutTypeResourceBundle, "layout.types." + layout.getType()) %>'
		/>

		<liferay-ui:search-container-column-date
			cssClass="table-cell-ws-nowrap"
			name="create-date"
			property="createDate"
		/>

		<liferay-ui:search-container-column-jsp
			path="/layout_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="list"
		markupView="lexicon"
	/>
</liferay-ui:search-container>