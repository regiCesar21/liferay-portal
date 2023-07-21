<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div class="container product-publisher-container">
	<react:component
		data="<%= (Map<String, Object>)request.getAttribute(OSBCommerceProvisioningThemeWebKeys.OSB_COMMERCE_PROVISIONING_THEME_CP_ENTRIES_MAP) %>"
		module="js/components/list_renderer/ListRendererWrapper"
	/>
</div>