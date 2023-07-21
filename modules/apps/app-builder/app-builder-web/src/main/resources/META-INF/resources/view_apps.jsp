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

	<liferay-portlet:renderURL portletName="<%= AppBuilderPortletKeys.OBJECTS %>" var="objectsPortletURL" />

	<react:component
		module="js/pages/apps/index.es"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"appsTabs", request.getAttribute(AppBuilderWebKeys.APPS_TABS)
			).put(
				"basePortletURL", String.valueOf(renderResponse.createRenderURL())
			).put(
				"baseResourceURL", String.valueOf(baseResourceURL)
			).put(
				"defaultDelta", PropsValues.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA
			).put(
				"deltaValues", PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES
			).put(
				"objectsPortletURL", String.valueOf(objectsPortletURL)
			).put(
				"pathFriendlyURLPublic", PortalUtil.getPathFriendlyURLPublic()
			).put(
				"showTranslationManager", request.getAttribute(AppBuilderWebKeys.SHOW_TRANSLATION_MANAGER)
			).put(
				"userId", themeDisplay.getUserId()
			).build()
		%>'
	/>
</div>