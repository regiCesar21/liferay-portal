<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DDMTemplate ddmTemplate = journalContentDisplayContext.getDDMTemplate();
%>

<liferay-ui:icon
	data='<%=
		HashMapBuilder.<String, Object>put(
			"destroyOnHide", true
		).put(
			"id", HtmlUtil.escape(portletDisplay.getNamespace()) + "editAsset"
		).put(
			"title", HtmlUtil.escape(ddmTemplate.getName(locale))
		).build()
	%>'
	id="editTemplateIcon"
	message="edit-template"
	url="<%= journalContentDisplayContext.getURLEditTemplate() %>"
	useDialog="<%= true %>"
/>