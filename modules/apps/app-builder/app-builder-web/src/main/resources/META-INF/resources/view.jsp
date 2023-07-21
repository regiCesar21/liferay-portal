<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/data-engine-taglib/data_layout_builder/css/main.css") %>" rel="stylesheet" />
</liferay-util:html-top>

<div id="<portlet:namespace />-app-builder-root">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="baseResourceURL" />

	<react:component
		module="js/index.es"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"basePortletURL", String.valueOf(renderResponse.createRenderURL())
			).put(
				"baseResourceURL", String.valueOf(baseResourceURL)
			).put(
				"defaultDelta", PropsValues.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA
			).put(
				"deltaValues", PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES
			).put(
				"pathFriendlyURLPublic", PortalUtil.getPathFriendlyURLPublic()
			).put(
				"scope", AppBuilderAppConstants.SCOPE_STANDARD
			).put(
				"showNativeObjectsTab", request.getAttribute(AppBuilderWebKeys.SHOW_NATIVE_OBJECTS_TAB)
			).put(
				"showTranslationManager", request.getAttribute(AppBuilderWebKeys.SHOW_TRANSLATION_MANAGER)
			).build()
		%>'
	/>
</div>