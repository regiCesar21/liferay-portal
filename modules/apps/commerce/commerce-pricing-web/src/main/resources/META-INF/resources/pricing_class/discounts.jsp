<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePricingClassDiscountDisplayContext commercePricingClassDiscountDisplayContext = (CommercePricingClassDiscountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

boolean hasPermission = commercePricingClassDiscountDisplayContext.hasPermission();

CommercePricingClass commercePricingClass = commercePricingClassDiscountDisplayContext.getCommercePricingClass();
%>

<c:if test="<%= hasPermission %>">
	<div class="col-12 pt-4">
		<clay:data-set-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"commercePricingClassId", String.valueOf(commercePricingClass.getCommercePricingClassId())
				).build()
			%>'
			dataProviderKey="<%= CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRICING_CLASSES_DISCOUNTS %>"
			id="<%= CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRICING_CLASSES_DISCOUNTS %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			pageNumber="<%= 1 %>"
			portletURL="<%= currentURLObj %>"
			style="stacked"
		/>
	</div>
</c:if>