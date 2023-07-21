<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/headless_data_set_display/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_step_tracker") + StringPool.UNDERLINE;

String containerId = randomNamespace + "table-id";

JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
%>

<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/frontend-taglib-clay/data_set_display/styles/main.css") %>" rel="stylesheet" />

<div class="table-root" id="<%= containerId %>">
	<span aria-hidden="true" class="loading-animation my-7"></span>
</div>

<aui:script require='<%= module + " as dataSetDisplay" %>'>
	dataSetDisplay.default(
		{
			actionParameterName: '<%= GetterUtil.getString(actionParameterName) %>',
			activeViewSettings: <%= activeViewSettingsJSON %>,
			apiURL: '<%= apiURL %>',
			appURL: '<%= appURL %>',
			bulkActions: <%= jsonSerializer.serializeDeep(bulkActionDropdownItems) %>,
			creationMenu: <%= jsonSerializer.serializeDeep(creationMenu) %>,
			currentURL: '<%= PortalUtil.getCurrentURL(request) %>',
			filters: <%= jsonSerializer.serializeDeep(clayDataSetFiltersContext) %>,
			formId: '<%= GetterUtil.getString(formId) %>',
			id: '<%= id %>',
			itemsActions: <%= jsonSerializer.serializeDeep(clayDataSetActionDropdownItems) %>,
			namespace: '<%= namespace %>',
			nestedItemsKey: '<%= GetterUtil.getString(nestedItemsKey) %>',
			nestedItemsReferenceKey:
				'<%= GetterUtil.getString(nestedItemsReferenceKey) %>',
			pagination: {
				deltas: <%= jsonSerializer.serializeDeep(clayPaginationEntries) %>,
				initialDelta: <%= itemsPerPage %>,
				initialPageNumber: <%= pageNumber %>,
			},
			portletId: '<%= portletDisplay.getRootPortletId() %>',
			portletURL: '<%= portletURL %>',
			selectedItems: <%= jsonSerializer.serializeDeep(selectedItems) %>,
			selectedItemsKey: '<%= GetterUtil.getString(selectedItemsKey) %>',
			showManagementBar: <%= showManagementBar %>,
			showSearch: <%= showSearch %>,
			style: '<%= style %>',
			selectionType: '<%= GetterUtil.getString(selectionType) %>',
			showPagination: <%= showPagination %>,
			sorting: <%= jsonSerializer.serializeDeep(sortItemList) %>,
			views: <%= jsonSerializer.serializeDeep(clayDataSetDisplayViewsContext) %>,
		},
		document.getElementById('<%= containerId %>')
	);
</aui:script>