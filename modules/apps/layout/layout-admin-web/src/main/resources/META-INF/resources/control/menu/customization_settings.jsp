<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/control/menu/init.jsp" %>

<%
String portletNamespace = PortalUtil.getPortletNamespace(LayoutAdminPortletKeys.GROUP_PAGES);
%>

<div class="active control-menu-link customization-link d-block d-md-none">
	<liferay-ui:icon
		data='<%=
			HashMapBuilder.<String, Object>put(
				"qa-id", "customizations"
			).build()
		%>'
		icon="pencil"
		id='<%= portletNamespace + "customizationButton" %>'
		label="<%= false %>"
		linkCssClass="btn btn-monospaced btn-sm control-menu-icon"
		markupView="lexicon"
		message="this-page-can-be-customized"
		url="javascript:;"
	/>
</div>