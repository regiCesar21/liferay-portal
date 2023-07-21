<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AppBuilderApp appBuilderApp = (AppBuilderApp)request.getAttribute(AppBuilderWebKeys.APP);

AppBuilderAppPortletTabContext appBuilderAppPortletTabContext = (AppBuilderAppPortletTabContext)request.getAttribute(AppBuilderWebKeys.APP_TAB_CONTEXT);

List<Long> dataLayoutIds = appBuilderAppPortletTabContext.getDataLayoutIds();
%>

<div class="app-builder-root">
	<clay:container-fluid
		cssClass="edit-entry"
	>
		<div id="<%= liferayPortletResponse.getNamespace() %>-control-menu"></div>

		<clay:row
			cssClass="justify-content-center"
		>
			<clay:col
				lg="12"
			>
				<div class="card card-root mb-0 mt-4 shadowless-card" id="edit-app-content">
					<div class="card-body px-0">

						<%
						for (Long dataLayoutId : dataLayoutIds) {
						%>

							<c:if test="<%= dataLayoutIds.size() > 1 %>">
								<h3 class="px-4" id="<%= dataLayoutId %>_name"></h3>
							</c:if>

							<aui:form name='<%= dataLayoutId + "_fm" %>'>
								<liferay-data-engine:data-layout-renderer
									containerId='<%= liferayPortletResponse.getNamespace() + "container" + dataLayoutId %>'
									dataLayoutId="<%= dataLayoutId %>"
									dataRecordId='<%= ParamUtil.getLong(request, "dataRecordId") %>'
									namespace="<%= liferayPortletResponse.getNamespace() %>"
									readOnly="<%= appBuilderAppPortletTabContext.isReadOnly(dataLayoutId) %>"
								/>
							</aui:form>

						<%
						}
						%>

						<div id="<portlet:namespace />-edit-entry-app">
							<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="baseResourceURL" />

							<react:component
								module="js/pages/entry/EditEntryApp.es"
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
										"baseResourceURL", String.valueOf(baseResourceURL)
									).put(
										"containerElementId", liferayPortletResponse.getNamespace() + "container" + dataLayoutIds.get(0)
									).put(
										"controlMenuElementId", liferayPortletResponse.getNamespace() + "-control-menu"
									).put(
										"dataDefinitionId", appBuilderApp.getDdmStructureId()
									).put(
										"dataLayoutId", appBuilderApp.getDdmStructureLayoutId()
									).put(
										"dataLayoutIds", dataLayoutIds
									).put(
										"dataListViewId", appBuilderApp.getDeDataListViewId()
									).put(
										"dataRecordId", ParamUtil.getLong(request, "dataRecordId")
									).put(
										"portraitURL", request.getAttribute(AppBuilderWebKeys.APP_PORTRAIT_URL)
									).put(
										"redirect", ParamUtil.getString(request, "redirect")
									).put(
										"showFormView", request.getAttribute(AppBuilderWebKeys.SHOW_FORM_VIEW)
									).put(
										"showTableView", request.getAttribute(AppBuilderWebKeys.SHOW_TABLE_VIEW)
									).build()
								%>'
							/>
						</div>
					</div>
				</div>
			</clay:col>
		</clay:row>
	</clay:container-fluid>
</div>