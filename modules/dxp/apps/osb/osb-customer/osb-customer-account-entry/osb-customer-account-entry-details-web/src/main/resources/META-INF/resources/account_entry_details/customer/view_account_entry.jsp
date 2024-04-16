<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/account_entry_details/init.jsp" %>

<%
Account koroneikiAccount = accountEntryViewDisplayContext.getAccount();

String tabs1 = ParamUtil.getString(request, "tabs1", "overview");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/view_account_entry");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("koroneikiAccountKey", String.valueOf(koroneikiAccount.getKey()));

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "project-details"), portletURL.toString(), null, false);

String tabNames = StringPool.BLANK;

if (!accountEntryViewDisplayContext.hasOnlyLiferaySaas()) {
	tabNames = "overview,";
}

tabNames += "attachments";

if (GitHubConfigurationValues.GITHUB_FEATURE_ENABLED && !accountEntryViewDisplayContext.hasOnlyLiferaySaas()) {
	tabNames += ",source-code-access";
}
%>

<h1>
	<%= HtmlUtil.escape(koroneikiAccount.getName()) %>
</h1>

<liferay-ui:tabs
	names="<%= tabNames %>"
	url="<%= portletURL.toString() %>"
/>

<liferay-ui:error exception="<%= AccountEnvironmentEnvASException.class %>" message="please-select-a-valid-application-server" />
<liferay-ui:error exception="<%= AccountEnvironmentEnvBrowserException.class %>" message="please-select-a-valid-browser" />
<liferay-ui:error exception="<%= AccountEnvironmentEnvCSException.class %>" message="please-select-a-valid-cloud-service" />
<liferay-ui:error exception="<%= AccountEnvironmentEnvDBException.class %>" message="please-select-a-valid-database" />
<liferay-ui:error exception="<%= AccountEnvironmentEnvLFRException.class %>" message="please-select-a-valid-liferay-version" />
<liferay-ui:error exception="<%= AccountEnvironmentEnvOSException.class %>" message="please-select-a-valid-operating-system" />
<liferay-ui:error exception="<%= AccountEnvironmentEnvSearchException.class %>" message="please-select-a-valid-search" />
<liferay-ui:error exception="<%= AccountEnvironmentNameException.class %>" message="please-provide-a-unique-environment-name-for-the-product" />
<liferay-ui:error exception="<%= DuplicateAccountEnvironmentException.class %>" message="please-provide-a-unique-environment-name" />

<c:choose>
	<c:when test='<%= tabs1.equals("overview") && !accountEntryViewDisplayContext.hasOnlyLiferaySaas() %>'>
		<liferay-util:include page="/account_entry_details/customer/overview.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("source-code-access") && GitHubConfigurationValues.GITHUB_FEATURE_ENABLED && !accountEntryViewDisplayContext.hasOnlyLiferaySaas() %>'>
		<liferay-util:include page="/account_entry_details/source_code_access.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/account_entry_details/attachments.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>