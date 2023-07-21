<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

AccountRoleDisplay accountRoleDisplay = (AccountRoleDisplay)row.getObject();

Role role = accountRoleDisplay.getRole();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="editAccountRoleURL">
		<portlet:param name="mvcPath" value="/account_entries_admin/edit_account_role.jsp" />
		<portlet:param name="backURL" value="<%= currentURL %>" />
		<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />
		<portlet:param name="accountRoleId" value="<%= String.valueOf(accountRoleDisplay.getAccountRoleId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message='<%= AccountRoleConstants.isSharedRole(role) ? "assign-users" : "edit" %>'
		url="<%= editAccountRoleURL %>"
	/>

	<c:if test="<%= role.getType() != RoleConstants.TYPE_ACCOUNT %>">
		<portlet:actionURL name="/account_admin/delete_account_roles" var="deleteAccountRolesURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="accountRoleIds" value="<%= String.valueOf(accountRoleDisplay.getAccountRoleId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			confirmation="are-you-sure-you-want-to-delete-this-role"
			message="delete"
			url="<%= deleteAccountRolesURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>