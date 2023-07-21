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

AccountUserDisplay accountUserDisplay = (AccountUserDisplay)row.getObject();
%>

<portlet:actionURL name="/account_admin/remove_account_users" var="removeAccountUsersURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="accountEntryId" value="<%= String.valueOf(accountEntryDisplay.getAccountEntryId()) %>" />
	<portlet:param name="accountUserIds" value="<%= String.valueOf(accountUserDisplay.getUserId()) %>" />
</portlet:actionURL>

<liferay-ui:icon-delete
	confirmation="are-you-sure-you-want-to-remove-this-user"
	icon="times-circle"
	message="remove"
	showIcon="<%= true %>"
	url="<%= removeAccountUsersURL %>"
/>