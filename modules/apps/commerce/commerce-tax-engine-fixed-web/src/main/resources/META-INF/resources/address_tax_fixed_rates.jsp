<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceTaxFixedRateAddressRelsDisplayContext commerceTaxFixedRateAddressRelsDisplayContext = (CommerceTaxFixedRateAddressRelsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/commerce_tax_methods/edit_commerce_tax_fixed_rate_address_rel" var="editCommerceTaxFixedRateAddressRelActionURL" />

<aui:form action="<%= editCommerceTaxFixedRateAddressRelActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateConfiguration" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commerceTaxFixedRateAddressRelsDisplayContext.getCommerceChannelId() %>" />
	<aui:input name="commerceTaxMethodId" type="hidden" value="<%= commerceTaxFixedRateAddressRelsDisplayContext.getCommerceTaxMethodId() %>" />

	<commerce-ui:panel
		title='<%= LanguageUtil.get(resourceBundle, "taxed-address") %>'
	>
		<aui:select name="applyTaxTo" onChange='<%= liferayPortletResponse.getNamespace() + "selectApplyTaxTo();" %>'>
			<aui:option label="shipping-address" selected="<%= commerceTaxFixedRateAddressRelsDisplayContext.isTaxAppliedToShippingAddress() %>" value="<%= true %>" />
			<aui:option label="billing-address" selected="<%= !commerceTaxFixedRateAddressRelsDisplayContext.isTaxAppliedToShippingAddress() %>" value="<%= false %>" />
		</aui:select>
	</commerce-ui:panel>

	<clay:data-set-display
		contextParams='<%=
			HashMapBuilder.<String, String>put(
				"commerceChannelId", String.valueOf(commerceTaxFixedRateAddressRelsDisplayContext.getCommerceChannelId())
			).put(
				"commerceTaxMethodId", String.valueOf(commerceTaxFixedRateAddressRelsDisplayContext.getCommerceTaxMethodId())
			).build()
		%>'
		creationMenu="<%= commerceTaxFixedRateAddressRelsDisplayContext.getCreationMenu() %>"
		dataProviderKey="<%= CommerceTaxRateSettingDataSetConstants.COMMERCE_DATA_SET_KEY_TAX_RATE_SETTING %>"
		id="<%= commerceTaxFixedRateAddressRelsDisplayContext.getDatasetView() %>"
		itemsPerPage="<%= 10 %>"
		namespace="<%= liferayPortletResponse.getNamespace() %>"
		pageNumber="<%= 1 %>"
		portletURL="<%= commerceTaxFixedRateAddressRelsDisplayContext.getPortletURL() %>"
	/>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />selectApplyTaxTo() {
		var fm = window.document.querySelector(document.<portlet:namespace />fm);

		submitForm(fm);
	}
</aui:script>