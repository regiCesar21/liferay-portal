<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/data_set_display/init.jsp" %>

<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/frontend-taglib-clay/data_set_display/styles/main.css") %>" rel="stylesheet" />

<div class="table-root" id="<%= containerId %>">
	<span aria-hidden="true" class="loading-animation my-7"></span>
</div>

<aui:script require='<%= module + " as dataSetDisplay" %>'>
	var container = document.getElementById('<%= containerId %>');

	dataSetDisplay.default(
		{
			actionParameterName:
				'<%= HtmlUtil.escapeJS(GetterUtil.getString(actionParameterName)) %>',
			activeViewSettings: <%= activeViewSettingsJSON %>,
			apiURL: '<%= HtmlUtil.escapeJS(apiURL) %>',
			appURL: '<%= HtmlUtil.escapeJS(appURL) %>',
			bulkActions: <%= jsonSerializer.serializeDeep(bulkActionDropdownItems) %>,
			componentId: '<%= HtmlUtil.escapeJS(containerId) %>',
			creationMenu: <%= jsonSerializer.serializeDeep(creationMenu) %>,
			currentURL: '<%= PortalUtil.getCurrentURL(request) %>',
			dataProviderKey: '<%= dataProviderKey %>',
			formId: '<%= HtmlUtil.escapeJS(GetterUtil.getString(formId)) %>',
			id: '<%= id %>',
			nestedItemsKey:
				'<%= HtmlUtil.escapeJS(GetterUtil.getString(nestedItemsKey)) %>',
			nestedItemsReferenceKey:
				'<%= HtmlUtil.escapeJS(GetterUtil.getString(nestedItemsReferenceKey)) %>',
			pagination: {
				deltas: <%= jsonSerializer.serializeDeep(clayPaginationEntries) %>,
				initialDelta: <%= itemsPerPage %>,
				initialPageNumber: <%= pageNumber %>,
			},
			showManagementBar: <%= showManagementBar %>,
			showPagination: <%= showPagination %>,
			showSearch: <%= showSearch %>,
			namespace: '<%= namespace %>',
			portletId: '<%= portletDisplay.getRootPortletId() %>',
			portletURL: '<%= HtmlUtil.escapeJS(portletURL.toString()) %>',
			selectedItems: <%= jsonSerializer.serializeDeep(selectedItems) %>,
			selectedItemsKey: '<%= GetterUtil.getString(selectedItemsKey) %>',
			selectionType: '<%= GetterUtil.getString(selectionType) %>',
			sorting: <%= jsonSerializer.serializeDeep(sortItemList) %>,
			style: '<%= style %>',
			views: <%= jsonSerializer.serializeDeep(clayDataSetDisplayViewsContext) %>,
		},
		container
	);
</aui:script>