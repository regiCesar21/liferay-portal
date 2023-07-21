<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
User selUser = PortalUtil.getSelectedUser(request, false);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("p_u_i_d", String.valueOf(selUser.getUserId()));
portletURL.setParameter("mvcPath", "/account_users_admin/edit_account_user.jsp");
%>

<liferay-frontend:screen-navigation
	containerCssClass="col-lg-8"
	containerWrapperCssClass="container-fluid container-fluid-max-xl container-form-lg"
	context="<%= selUser %>"
	headerContainerCssClass=""
	key="<%= AccountScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_ACCOUNT_USER %>"
	menubarCssClass="menubar menubar-transparent menubar-vertical-expand-lg"
	navCssClass="col-lg-3"
	portletURL="<%= portletURL %>"
/>

<%
String screenNavigationCategoryKey = ParamUtil.getString(request, "screenNavigationCategoryKey", AccountScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL);

String screenNavigationEntryKey = ParamUtil.getString(request, "screenNavigationEntryKey");

if (Validator.isNull(screenNavigationEntryKey)) {
	screenNavigationEntryKey = AccountScreenNavigationEntryConstants.ENTRY_KEY_INFORMATION;
}

AccountUserDisplay accountUserDisplay = AccountUserDisplay.of(selUser);
%>

<c:if test="<%= Objects.equals(AccountScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL, screenNavigationCategoryKey) && Objects.equals(AccountScreenNavigationEntryConstants.ENTRY_KEY_INFORMATION, screenNavigationEntryKey) %>">
	<c:if test="<%= accountUserDisplay.isValidateEmailAddress() || Validator.isNotNull(AccountUserDisplay.getBlockedDomains(themeDisplay.getCompanyId())) %>">

		<%
		Map<String, Object> context = HashMapBuilder.<String, Object>put(
			"accountEntryNames", accountUserDisplay.getAccountEntryNamesString(request)
		).build();

		if (Validator.isNotNull(AccountUserDisplay.getBlockedDomains(themeDisplay.getCompanyId()))) {
			context.put("blockedDomains", AccountUserDisplay.getBlockedDomains(themeDisplay.getCompanyId()));
		}

		if (accountUserDisplay.isValidateEmailAddress()) {
			context.put("validDomains", accountUserDisplay.getValidDomainsString());

			PortletURL viewValidDomainsURL = renderResponse.createRenderURL();

			viewValidDomainsURL.setParameter("mvcPath", "/account_users_admin/account_user/view_valid_domains.jsp");
			viewValidDomainsURL.setParameter("validDomains", accountUserDisplay.getValidDomainsString());
			viewValidDomainsURL.setWindowState(LiferayWindowState.POP_UP);

			context.put("viewValidDomainsURL", viewValidDomainsURL.toString());
		}
		%>

		<liferay-frontend:component
			componentId="AccountUserEmailDomainValidator"
			context="<%= context %>"
			module="account_users_admin/js/AccountUserEmailDomainValidator.es"
		/>
	</c:if>
</c:if>