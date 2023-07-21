<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String ppid = ParamUtil.getString(request, "p_p_id");
%>

<liferay-ui:success key="displayPagePublished" message="the-display-page-template-was-published-succesfully" />

<c:choose>
	<c:when test="<%= (themeDisplay.isStatePopUp() || themeDisplay.isWidget() || layoutTypePortlet.hasStateMax()) && Validator.isNotNull(ppid) %>">

		<%
		String templateId = null;
		String templateContent = null;
		String langType = null;

		if (themeDisplay.isStatePopUp() || themeDisplay.isWidget()) {
			templateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "pop_up";
			templateContent = LayoutTemplateLocalServiceUtil.getContent("pop_up", true, theme.getThemeId());
			langType = LayoutTemplateLocalServiceUtil.getLangType("pop_up", true, theme.getThemeId());
		}
		else {
			ppid = StringUtil.split(layoutTypePortlet.getStateMax())[0];

			templateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "max";
			templateContent = LayoutTemplateLocalServiceUtil.getContent("max", true, theme.getThemeId());
			langType = LayoutTemplateLocalServiceUtil.getLangType("max", true, theme.getThemeId());
		}

		if (Validator.isNotNull(templateContent)) {
			RuntimePageUtil.processTemplate(request, response, ppid, new StringTemplateResource(templateId, templateContent), langType);
		}
		%>

	</c:when>
	<c:otherwise>

		<%
		DisplayPageLayoutTypeControllerDisplayContext displayPageLayoutTypeControllerDisplayContext = (DisplayPageLayoutTypeControllerDisplayContext)request.getAttribute(DisplayPageLayoutTypeControllerWebKeys.DISPLAY_PAGE_LAYOUT_TYPE_CONTROLLER_DISPLAY_CONTEXT);

		AssetRendererFactory<?> assetRendererFactory = displayPageLayoutTypeControllerDisplayContext.getAssetRendererFactory();
		%>

		<c:if test="<%= assetRendererFactory != null %>">
			<liferay-ui:success key='<%= assetRendererFactory.getPortletId() + "requestProcessed" %>' message="your-request-processed-successfully" />
		</c:if>

		<c:choose>
			<c:when test="<%= !displayPageLayoutTypeControllerDisplayContext.hasPermission(permissionChecker, ActionKeys.VIEW) %>">
				<div class="layout-content" id="main-content" role="main">
					<clay:container-fluid
						cssClass="pt-3"
					>
						<div class="alert alert-danger">
							<liferay-ui:message key="you-do-not-have-the-required-permissions-to-view-the-content-of-this-page" />
						</div>
					</clay:container-fluid>
				</div>
			</c:when>
			<c:otherwise>
				<div class="layout-content portlet-layout" id="main-content" role="main">
					<liferay-layout:render-fragment-layout
						fieldValues="<%= displayPageLayoutTypeControllerDisplayContext.getInfoDisplayFieldsValues() %>"
						mode="<%= FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE %>"
					/>
				<div>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<liferay-ui:layout-common />