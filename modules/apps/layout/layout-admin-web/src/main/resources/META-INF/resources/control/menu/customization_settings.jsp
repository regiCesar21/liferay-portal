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
	<div class="control-menu-nav-item customization-link d-block d-md-none ml-1">
		<clay:button
			additionalProps='<%=
				HashMapBuilder.<String, Object>put(
					"portletNamespace", portletNamespace
				).build()
			%>'
			aria-label='<%= LanguageUtil.get(request, "this-page-can-be-customized") %>'
			data-qa-id="customizations"
			displayType="unstyled"
			icon="pencil"
			id='<%= portletNamespace + "customizationButton" %>'
			monospaced="<%= true %>"
			propsTransformer="js/CustomizationButtonPropsTransformer"
			small="<%= true %>"
			title='<%= LanguageUtil.get(request, "this-page-can-be-customized") %>'
		/>
	</div>
</div>