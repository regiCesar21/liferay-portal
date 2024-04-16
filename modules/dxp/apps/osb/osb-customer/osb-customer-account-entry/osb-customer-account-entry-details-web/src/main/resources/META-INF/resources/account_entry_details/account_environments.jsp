<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/account_entry_details/init.jsp" %>

<%
AccountEntry accountEntry = accountEntryViewDisplayContext.getAccountEntry();

String addEnvironmentURL = StringPool.BLANK;
JSONArray accountEnvironmentsJSONArray = JSONFactoryUtil.createJSONArray();
JSONObject environmentConfigurationJSONObject = JSONFactoryUtil.createJSONObject();
boolean permitAdd = false;
boolean permitDelete = false;
boolean permitEdit = false;

if ((accountEntry != null) && (!accountEntryViewDisplayContext.hasOnlyLiferaySaas() || permissionChecker.isOmniadmin())) {
	addEnvironmentURL = accountEntryViewDisplayContext.getAccountEnvironmentAddURL(accountEntry);
	accountEnvironmentsJSONArray = accountEntryViewDisplayContext.getAccountEnvironmentsJSONArray();
	environmentConfigurationJSONObject = accountEntryViewDisplayContext.getEnvironmentConfigurationJSONObject();
	permitAdd = AccountEnvironmentPermission.contains(permissionChecker, accountEntry.getAccountEntryId(), OSBActionKeys.ADD_ACCOUNT_ENVIRONMENT);
	permitDelete = AccountEnvironmentPermission.contains(permissionChecker, accountEntry.getAccountEntryId(), OSBActionKeys.DELETE);
	permitEdit = AccountEnvironmentPermission.contains(permissionChecker, accountEntry.getAccountEntryId(), OSBActionKeys.UPDATE);
}
%>

<liferay-util:include page="/account_entry_details/customer_portal_banner.jsp" servletContext="<%= application %>" />

<c:if test="<%= !accountEntryViewDisplayContext.hasOnlyLiferaySaas() || permissionChecker.isOmniadmin() %>">
	<div class="account-environments card" id="<portlet:namespace />accountEnvironments"></div>

	<aui:script>
		AccountDetails.render(
			AccountDetails.AccountEnvironments,
			{
				addEnvironmentURL: '<%= addEnvironmentURL %>',
				environmentConfiguration: <%= environmentConfigurationJSONObject %>,
				environments: <%= accountEnvironmentsJSONArray %>,
				permitAdd: <%= permitAdd %>,
				permitDelete: <%= permitDelete %>,
				permitEdit: <%= permitEdit %>
			},
			document.getElementById('<portlet:namespace />accountEnvironments')
		);
	</aui:script>
</c:if>