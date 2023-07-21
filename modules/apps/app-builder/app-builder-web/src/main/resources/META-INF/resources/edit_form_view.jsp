<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String componentId = liferayPortletResponse.getNamespace() + "dataLayoutBuilder";
String customObjectSidebarElementId = liferayPortletResponse.getNamespace() + "-app-builder-custom-object-sidebar";
String dataLayoutBuilderElementId = liferayPortletResponse.getNamespace() + "-app-builder-data-layout-builder";
String editFormViewRootElementId = liferayPortletResponse.getNamespace() + "-app-builder-edit-form-view";

long dataDefinitionId = ParamUtil.getLong(request, "dataDefinitionId");
long dataLayoutId = ParamUtil.getLong(request, "dataLayoutId");
boolean newCustomObject = ParamUtil.getBoolean(request, "newCustomObject");
%>

<div class="app-builder-root">
	<aui:form>
		<aui:input name="dataDefinition" type="hidden" />
		<aui:input name="dataLayout" type="hidden" />

		<portlet:renderURL var="basePortletURL" />

		<div class="app-builder-form-view-app" id="<%= editFormViewRootElementId %>">
			<react:component
				module="js/pages/form-view/EditFormViewApp.es"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"basePortletURL", basePortletURL.toString()
					).put(
						"customObjectSidebarElementId", customObjectSidebarElementId
					).put(
						"dataDefinitionId", dataDefinitionId
					).put(
						"dataLayoutBuilderElementId", dataLayoutBuilderElementId
					).put(
						"dataLayoutBuilderId", componentId
					).put(
						"dataLayoutId", dataLayoutId
					).put(
						"newCustomObject", newCustomObject
					).put(
						"showTranslationManager", request.getAttribute(AppBuilderWebKeys.SHOW_TRANSLATION_MANAGER)
					).build()
				%>'
			/>
		</div>

		<div class="app-builder-form-view-body">
			<div class="app-builder-custom-object-sidebar" id="<%= customObjectSidebarElementId %>"></div>

			<div class="data-layout-builder-wrapper" id="<%= dataLayoutBuilderElementId %>">
				<liferay-data-engine:data-layout-builder
					componentId="<%= componentId %>"
					contentType="app-builder"
					dataDefinitionId="<%= dataDefinitionId %>"
					dataLayoutId="<%= dataLayoutId %>"
					fieldSetContentType="app-builder-fieldset"
					namespace="<%= liferayPortletResponse.getNamespace() %>"
					scopes='<%= SetUtil.fromCollection(Arrays.asList("app-builder")) %>'
				/>
			</div>
		</div>
	</aui:form>
</div>