<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

@generated
--%>

<%@ include file="/init.jsp" %>

<%
java.lang.String containerId = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-data-engine:data-layout-renderer:containerId"));
java.lang.Long dataDefinitionId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:dataDefinitionId")));
java.lang.Long dataLayoutId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:dataLayoutId")));
java.lang.Long dataRecordId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:dataRecordId")));
java.util.Map<java.lang.String, java.lang.Object> dataRecordValues = (java.util.Map<java.lang.String, java.lang.Object>)request.getAttribute("liferay-data-engine:data-layout-renderer:dataRecordValues");
java.lang.String namespace = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-data-engine:data-layout-renderer:namespace"));
boolean readOnly = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:readOnly")));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-data-engine:data-layout-renderer:dynamicAttributes");
%>

<%@ include file="/data_layout_renderer/init-ext.jspf" %>