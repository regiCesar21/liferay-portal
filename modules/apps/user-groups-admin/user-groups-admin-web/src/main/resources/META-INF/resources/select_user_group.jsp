<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
User selUser = PortalUtil.getSelectedUser(request);

SelectUserGroupManagementToolbarDisplayContext selectUserGroupManagementToolbarDisplayContext = new SelectUserGroupManagementToolbarDisplayContext(request, renderRequest, renderResponse);

PortletURL portletURL = selectUserGroupManagementToolbarDisplayContext.getPortletURL();

SearchContainer<UserGroup> userGroupSearch = selectUserGroupManagementToolbarDisplayContext.getSearchContainer(filterManageableUserGroups);

renderResponse.setTitle(LanguageUtil.get(request, "user-groups"));
%>

<clay:management-toolbar
	clearResultsURL="<%= selectUserGroupManagementToolbarDisplayContext.getClearResultsURL() %>"
	itemsTotal="<%= userGroupSearch.getTotal() %>"
	searchActionURL="<%= selectUserGroupManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchFormName="searchFm"
	selectable="<%= false %>"
	showSearch="<%= true %>"
	viewTypeItems="<%= selectUserGroupManagementToolbarDisplayContext.getViewTypeItems() %>"
/>

<aui:form action="<%= portletURL %>" cssClass="container-fluid-1280" method="post" name="selectUserGroupFm">
	<liferay-ui:search-container
		searchContainer="<%= userGroupSearch %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.UserGroup"
			escapedModel="<%= false %>"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
				value="<%= HtmlUtil.escape(userGroup.getName()) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="description"
				value="<%= HtmlUtil.escape(userGroup.getDescription()) %>"
			/>

			<liferay-ui:search-container-column-text>
				<c:if test="<%= UserGroupMembershipPolicyUtil.isMembershipAllowed((selUser != null) ? selUser.getUserId() : 0, userGroup.getUserGroupId()) %>">
					<aui:button
						cssClass="selector-button"
						data='<%=
							HashMapBuilder.<String, Object>put(
								"entityid", userGroup.getUserGroupId()
							).put(
								"entityname", userGroup.getName()
							).build()
						%>'
						value="choose"
					/>
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>