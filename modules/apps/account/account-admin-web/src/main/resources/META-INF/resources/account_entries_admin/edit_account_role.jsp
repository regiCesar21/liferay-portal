<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL");

long accountEntryId = ParamUtil.getLong(request, "accountEntryId");

if (Validator.isNull(backURL)) {
	PortletURL viewAccountRolesURL = renderResponse.createRenderURL();

	viewAccountRolesURL.setParameter("mvcRenderCommandName", "/account_admin/edit_account_entry");
	viewAccountRolesURL.setParameter("screenNavigationCategoryKey", AccountScreenNavigationEntryConstants.CATEGORY_KEY_ROLES);
	viewAccountRolesURL.setParameter("accountEntryId", String.valueOf(accountEntryId));

	backURL = viewAccountRolesURL.toString();
}

long accountRoleId = ParamUtil.getLong(request, "accountRoleId");

AccountRole accountRole = AccountRoleLocalServiceUtil.fetchAccountRole(accountRoleId);

Role role = null;

if (accountRole != null) {
	role = accountRole.getRole();
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/account_entries_admin/edit_account_role.jsp");
portletURL.setParameter("accountEntryId", String.valueOf(accountEntryId));
portletURL.setParameter("accountRoleId", String.valueOf(accountRoleId));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((role == null) ? LanguageUtil.get(request, "add-new-role") : role.getTitle(locale));
%>

<liferay-frontend:screen-navigation
	containerWrapperCssClass=""
	context="<%= accountRole %>"
	headerContainerCssClass=""
	key="<%= AccountScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_ACCOUNT_ROLE %>"
	portletURL="<%= portletURL %>"
/>