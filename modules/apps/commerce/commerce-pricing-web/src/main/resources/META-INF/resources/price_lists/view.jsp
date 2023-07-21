<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="pt-4">
	<aui:form action="<%= currentURL %>" cssClass="container-fluid-1280" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deletePriceLists" type="hidden" />

		<clay:headless-data-set-display
			apiURL="<%= commercePriceListDisplayContext.getPriceListsApiUrl(portletName) %>"
			clayDataSetActionDropdownItems="<%= commercePriceListDisplayContext.getPriceListClayDataSetActionDropdownItems() %>"
			creationMenu="<%= commercePriceListDisplayContext.getPriceListCreationMenu() %>"
			formId="fm"
			id="<%= CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRICE_LISTS %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			pageNumber="<%= 1 %>"
			portletURL="<%= currentURLObj %>"
			style="stacked"
		/>
	</aui:form>
</div>