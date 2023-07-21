<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<portlet:actionURL name="/saml/saas/admin/export" var="exportURL">
	<portlet:param name="mvcRenderCommandName" value="/admin" />
</portlet:actionURL>

<div class="container-fluid container-fluid-max-xl sheet">
	<liferay-ui:error key="exportError" message="an-error-has-occurred-during-the-export-process" />

	<div class="button-holder">
		<h3 class="text-default">
			<liferay-ui:message key="export-the-saml-configuration-from-this-instance-to-your-production-instance" />
		</h3>

		<div class="alert alert-warning">
			<liferay-ui:message key="the-saml-configuration-of-your-production-instance-will-be-completely-overwritten" />
		</div>

		<aui:form action="<%= exportURL %>" method="post" name="fm">
			<aui:button type="submit" value="export-saml-configuration" />
		</aui:form>
	</div>
</div>