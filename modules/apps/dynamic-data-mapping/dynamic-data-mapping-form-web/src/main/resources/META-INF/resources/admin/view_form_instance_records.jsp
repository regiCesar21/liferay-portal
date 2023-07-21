<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
DDMFormViewFormInstanceRecordsDisplayContext ddmFormViewFormInstanceRecordsDisplayContext = ddmFormAdminDisplayContext.getFormViewRecordsDisplayContext();

renderResponse.setTitle(LanguageUtil.get(request, "form-entries"));
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= ddmFormViewFormInstanceRecordsDisplayContext.getNavigationItems() %>"
/>

<clay:alert
	message='<%= LanguageUtil.get(resourceBundle, "view-current-fields-warning-message") %>'
	style="info"
	title='<%= LanguageUtil.get(resourceBundle, "info") %>'
/>

<liferay-util:include page="/admin/form_instance_records_search_container.jsp" servletContext="<%= application %>" />