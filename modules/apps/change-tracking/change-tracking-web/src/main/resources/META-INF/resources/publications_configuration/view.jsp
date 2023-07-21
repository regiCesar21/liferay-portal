<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications_configuration/init.jsp" %>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<aui:form action="<%= publicationsConfigurationDisplayContext.getActionURL() %>" method="post" name="fm">
		<aui:input name="navigation" type="hidden" value="<%= publicationsConfigurationDisplayContext.getNavigation() %>" />
		<aui:input name="redirectToOverview" type="hidden" value="<%= false %>" />

		<clay:sheet>
			<%@ include file="/publications_configuration/global_settings.jspf" %>
		</clay:sheet>
	</aui:form>
</clay:container-fluid>