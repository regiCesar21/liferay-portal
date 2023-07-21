<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long accountEntryId = ParamUtil.getLong(request, "accountEntryId");

SearchContainer<Organization> organizationSearchContainer = AssignableAccountOrganizationSearchContainerFactory.create(accountEntryId, liferayPortletRequest, liferayPortletResponse);
%>

<clay:management-toolbar
	displayContext="<%= new SelectAccountOrganizationsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, organizationSearchContainer) %>"
/>

<clay:container-fluid>
	<liferay-ui:search-container
		searchContainer="<%= organizationSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Organization"
			keyProperty="organizationId"
			modelVar="organization"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-small table-cell-minw-150"
				name="name"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-small table-cell-minw-150"
				name="parent-organization"
				property="parentOrganizationName"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />organizations'
	);

	searchContainer.on('rowToggled', function (event) {
		var selectedItems = event.elements.allSelectedElements;

		var result = {};

		if (!selectedItems.isEmpty()) {
			result = {
				data: {
					value: selectedItems.get('value').join(','),
				},
			};
		}

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(liferayPortletResponse.getNamespace() + "assignAccountOrganizations") %>',
			result
		);
	});
</aui:script>