<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/account_admin/edit_account_entry");
portletURL.setParameter("accountEntryId", String.valueOf(accountEntryDisplay.getAccountEntryId()));
%>

<liferay-frontend:screen-navigation
	containerWrapperCssClass=""
	context="<%= accountEntryDisplay %>"
	headerContainerCssClass=""
	key="<%= AccountScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_ACCOUNT_ENTRY %>"
	portletURL="<%= portletURL %>"
/>