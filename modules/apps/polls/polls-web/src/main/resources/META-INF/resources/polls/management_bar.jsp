<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/polls/init.jsp" %>

<clay:management-toolbar
	actionDropdownItems="<%= pollsDisplayContext.getActionItemsDropdownItems() %>"
	clearResultsURL="<%= pollsDisplayContext.getClearResultsURL() %>"
	componentId="pollsManagementToolbar"
	creationMenu="<%= pollsDisplayContext.getCreationMenu() %>"
	disabled="<%= pollsDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= pollsDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= pollsDisplayContext.getTotalItems() %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	searchActionURL="<%= pollsDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= pollsDisplayContext.getSearchContainerId() %>"
	searchFormName="fm1"
	sortingOrder="<%= pollsDisplayContext.getOrderByType() %>"
	sortingURL="<%= pollsDisplayContext.getSortingURL() %>"
/>

<aui:script>
	var deleteQuestions = function () {
		if (
			confirm(
				'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>'
			)
		) {
			var searchContainer = document.getElementById(
				'<portlet:namespace />poll'
			);

			if (searchContainer) {
				Liferay.Util.postForm(document.<portlet:namespace />fm, {
					data: {
						deleteQuestionIds: Liferay.Util.listCheckedExcept(
							searchContainer,
							'<portlet:namespace />allRowIds'
						),
					},

					<portlet:actionURL name="/polls/delete_question" var="deleteQuestionURL">
						<portlet:param name="mvcPath" value="/view.jsp" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
					</portlet:actionURL>

					url: '<%= deleteQuestionURL %>',
				});
			}
		}
	};

	var ACTIONS = {
		deleteQuestions: deleteQuestions,
	};

	Liferay.componentReady('pollsManagementToolbar').then(function (
		managementToolbar
	) {
		managementToolbar.on(['actionItemClicked'], function (event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>