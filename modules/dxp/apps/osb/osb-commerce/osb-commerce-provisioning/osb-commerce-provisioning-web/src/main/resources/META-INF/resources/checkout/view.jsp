<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div id="osb-checkout-summary">
	<commerce-ui:mini-cart
		toggleable="<%= false %>"
		views='<%=
			HashMapBuilder.<String, String>put(
				"Cart", "osb-commerce-provisioning-theme-impl@1.0.0/js/components/cart_summary/CartSummary"
			).build()
		%>'
	/>
</div>