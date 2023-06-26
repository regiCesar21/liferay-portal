<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

if (Validator.isNull(backURL)) {
	backURL = PortalUtil.getLayoutFullURL(layoutsAdminDisplayContext.getSelLayout(), themeDisplay);
}

String portletResource = ParamUtil.getString(request, "portletResource");

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(selLayout);

if ((layoutRevision != null) && StagingUtil.isIncomplete(selLayout, layoutRevision.getLayoutSetBranchId())) {
	portletDisplay.setShowStagingIcon(false);
}

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}

renderResponse.setTitle(selLayout.getName(locale));
%>

<liferay-ui:success key='<%= portletResource + "layoutUpdated" %>' message='<%= LanguageUtil.get(resourceBundle, "the-page-was-updated-succesfully") %>' />

<liferay-frontend:screen-navigation
	containerCssClass="col-lg-8"
	containerWrapperCssClass="container-fluid container-fluid-max-xl container-form-lg"
	context="<%= selLayout %>"
	headerContainerCssClass=""
	inverted="<%= true %>"
	key="<%= LayoutScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_LAYOUT %>"
	menubarCssClass="menubar menubar-transparent menubar-vertical-expand-lg"
	navCssClass="col-lg-3"
	portletURL="<%= layoutsAdminDisplayContext.getScreenNavigationPortletURL() %>"
/>

<%@ include file="/friendly_url_warning_message.jspf" %>