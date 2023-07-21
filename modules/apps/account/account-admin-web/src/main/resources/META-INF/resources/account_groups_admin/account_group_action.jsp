<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

AccountGroupDisplay accountGroupDisplay = (AccountGroupDisplay)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="editAccountGroupURL">
		<portlet:param name="mvcRenderCommandName" value="/account_admin/edit_account_group" />
		<portlet:param name="backURL" value="<%= currentURL %>" />
		<portlet:param name="accountGroupId" value="<%= String.valueOf(accountGroupDisplay.getAccountGroupId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editAccountGroupURL %>"
	/>

	<portlet:actionURL name="/account_admin/delete_account_groups" var="deleteAccountGroupURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="accountGroupIds" value="<%= String.valueOf(accountGroupDisplay.getAccountGroupId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteAccountGroupURL %>"
	/>
</liferay-ui:icon-menu>