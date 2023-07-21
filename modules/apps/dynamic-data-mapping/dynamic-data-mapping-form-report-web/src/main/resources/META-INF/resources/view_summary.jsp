<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DDMFormInstanceReport ddmFormInstanceReport = ddmFormReportDisplayContext.getDDMFormInstanceReport();

String ddmFormInstanceReportData = StringPool.BLANK;

if (ddmFormInstanceReport != null) {
	ddmFormInstanceReportData = ddmFormInstanceReport.getData();
}
%>

<div id="<portlet:namespace />report">
	<react:component
		module="js/index.es"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"data", ddmFormInstanceReportData
			).put(
				"fields", ddmFormReportDisplayContext.getFieldsJSONArray()
			).put(
				"formReportRecordsFieldValuesURL", ddmFormReportDisplayContext.getFormReportRecordsFieldValuesURL()
			).put(
				"portletNamespace", PortalUtil.getPortletNamespace(DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_REPORT)
			).build()
		%>'
	/>
</div>