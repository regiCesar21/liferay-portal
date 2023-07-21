<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SelectOrganizationsDisplayContext selectOrganizationsDisplayContext = (SelectOrganizationsDisplayContext)request.getAttribute(SegmentsWebKeys.SELECT_ORGANIZATIONS_DISPLAY_CONTEXT);
%>

<clay:management-toolbar
	displayContext="<%= (SelectOrganizationsManagementToolbarDisplayContext)request.getAttribute(SegmentsWebKeys.SEGMENTS_SELECT_ORGANIZATION_MANAGEMENT_TOOLBAL_DISPLAY_CONTEXT) %>"
/>

<aui:form cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="<%= selectOrganizationsDisplayContext.getSearchContainerId() %>"
		searchContainer="<%= selectOrganizationsDisplayContext.getOrganizationSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Organization"
			escapedModel="<%= true %>"
			keyProperty="organizationId"
			modelVar="organization"
		>

			<%
			row.setData(
				HashMapBuilder.<String, Object>put(
					"id", organization.getOrganizationId()
				).put(
					"name", organization.getName()
				).build());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content table-title"
				name="name"
				orderable="<%= true %>"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="parent-organization"
				value="<%= HtmlUtil.escape(organization.getParentOrganizationName()) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="type"
				orderable="<%= true %>"
				value="<%= LanguageUtil.get(request, organization.getType()) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= selectOrganizationsDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<liferay-util:include page="/field/select_js.jsp" servletContext="<%= application %>">
	<liferay-util:param name="displayStyle" value="<%= selectOrganizationsDisplayContext.getDisplayStyle() %>" />
	<liferay-util:param name="searchContainerId" value="selectSegmentsEntryOrganizations" />
	<liferay-util:param name="selectEventName" value="<%= selectOrganizationsDisplayContext.getEventName() %>" />
</liferay-util:include>