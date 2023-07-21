<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
RemoteAppAdminDisplayContext remoteAppAdminDisplayContext = (RemoteAppAdminDisplayContext)renderRequest.getAttribute(RemoteAppAdminWebKeys.REMOTE_APP_ADMIN_DISPLAY_CONTEXT);
%>

<clay:data-set-display
	actionParameterName="remoteAppEntryId"
	creationMenu="<%= remoteAppAdminDisplayContext.getCreationMenu() %>"
	dataProviderKey="<%= RemoteAppAdminConstants.REMOTE_APP_ENTRY_DATA_SET_DISPLAY %>"
	formId='<%= liferayPortletResponse.getNamespace() + "fm" %>'
	id="<%= RemoteAppAdminConstants.REMOTE_APP_ENTRY_DATA_SET_DISPLAY %>"
	itemsPerPage="<%= 10 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= remoteAppAdminDisplayContext.getCurrentPortletURL() %>"
	selectedItemsKey="remoteAppEntryId"
	selectionType="multiple"
	style="fluid"
/>