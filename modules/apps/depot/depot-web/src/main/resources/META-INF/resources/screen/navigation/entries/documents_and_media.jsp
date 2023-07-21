<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DepotEntry depotEntry = (DepotEntry)request.getAttribute(DepotAdminWebKeys.DEPOT_ENTRY);

Group group = depotEntry.getGroup();

UnicodeProperties typeSettingsProperties = group.getTypeSettingsProperties();
%>

<liferay-frontend:fieldset
	collapsible="<%= true %>"
	cssClass="panel-group-flush"
	label='<%= LanguageUtil.get(request, "documents-and-media") %>'
>
	<aui:input helpMessage='<%= LanguageUtil.format(request, "directory-indexing-help", new Object[] {HtmlUtil.escape(group.getDescriptiveName(themeDisplay.getLocale())), themeDisplay.getPortalURL() + "/documents" + group.getFriendlyURL()}, false) %>' label="enable-directory-indexing" name="TypeSettingsProperties--directoryIndexingEnabled--" type="toggle-switch" value='<%= PropertiesParamUtil.getBoolean(typeSettingsProperties, request, "directoryIndexingEnabled") %>' />
</liferay-frontend:fieldset>