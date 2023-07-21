<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<c:choose>
	<c:when test="<%= FFDocumentLibraryDDMEditorConfigurationUtil.useDataEngineEditor() %>">
		<liferay-util:include page="/document_library/edit_file_entry_type_data_layout_builder.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/document_library/edit_file_entry_type_form_builder.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>