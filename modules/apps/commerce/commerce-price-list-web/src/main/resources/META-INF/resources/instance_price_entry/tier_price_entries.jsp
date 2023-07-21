<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPInstanceCommerceTierPriceEntryDisplayContext cpInstanceCommerceTierPriceEntryDisplayContext = (CPInstanceCommerceTierPriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commercePriceEntryId = cpInstanceCommerceTierPriceEntryDisplayContext.getCommercePriceEntryId();

PortletURL portletURL = cpInstanceCommerceTierPriceEntryDisplayContext.getPortletURL();

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<div class="tier-price-entries-container" id="<portlet:namespace />entriesContainer">
	<aui:form action="<%= portletURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<clay:data-set-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"commercePriceEntryId", String.valueOf(commercePriceEntryId)
				).build()
			%>'
			creationMenu="<%= cpInstanceCommerceTierPriceEntryDisplayContext.getCreationMenu() %>"
			dataProviderKey="<%= CommercePriceListDataSetConstants.COMMERCE_DATA_SET_KEY_INSTANCE_TIER_PRICE_ENTRIES %>"
			formId="fm"
			id="<%= CommercePriceListDataSetConstants.COMMERCE_DATA_SET_KEY_INSTANCE_TIER_PRICE_ENTRIES %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			pageNumber="<%= 1 %>"
			portletURL="<%= portletURL %>"
			style="stacked"
		/>
	</aui:form>
</div>