<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div id="<portlet:namespace />-app-builder-root">

	<%
	AppBuilderApp appBuilderApp = (AppBuilderApp)request.getAttribute(AppBuilderWebKeys.APP);
	%>

	<react:component
		module="js/pages/entry/ViewEntriesApp.es"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"appDeploymentType", request.getAttribute(AppBuilderWebKeys.APP_DEPLOYMENT_TYPE)
			).put(
				"appId", appBuilderApp.getAppBuilderAppId()
			).put(
				"appTab", request.getAttribute(AppBuilderWebKeys.APP_TAB)
			).put(
				"basePortletURL", String.valueOf(renderResponse.createRenderURL())
			).put(
				"dataDefinitionId", appBuilderApp.getDdmStructureId()
			).put(
				"dataLayoutId", appBuilderApp.getDdmStructureLayoutId()
			).put(
				"dataListViewId", appBuilderApp.getDeDataListViewId()
			).put(
				"defaultDelta", PropsValues.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA
			).put(
				"deltaValues", PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES
			).put(
				"portraitURL", request.getAttribute(AppBuilderWebKeys.APP_PORTRAIT_URL)
			).put(
				"showFormView", request.getAttribute(AppBuilderWebKeys.SHOW_FORM_VIEW)
			).put(
				"showTableView", request.getAttribute(AppBuilderWebKeys.SHOW_TABLE_VIEW)
			).build()
		%>'
	/>
</div>