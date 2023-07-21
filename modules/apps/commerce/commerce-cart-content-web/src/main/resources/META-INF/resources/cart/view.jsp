<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceCartContentDisplayContext commerceCartContentDisplayContext = (CommerceCartContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Map<String, Object> contextObjects = HashMapBuilder.<String, Object>put(
	"commerceCartContentDisplayContext", commerceCartContentDisplayContext
).build();

SearchContainer<CommerceOrderItem> commerceOrderItemSearchContainer = commerceCartContentDisplayContext.getSearchContainer();

PortletURL portletURL = commerceCartContentDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commerceOrderItems");

request.setAttribute("view.jsp-portletURL", portletURL);

List<CommerceOrderValidatorResult> commerceOrderValidatorResults = new ArrayList<>();

Map<Long, List<CommerceOrderValidatorResult>> commerceOrderValidatorResultMap = commerceCartContentDisplayContext.getCommerceOrderValidatorResults();
%>

<liferay-ui:error exception="<%= CommerceOrderValidatorException.class %>">

	<%
	CommerceOrderValidatorException commerceOrderValidatorException = (CommerceOrderValidatorException)errorException;

	if (commerceOrderValidatorException != null) {
		commerceOrderValidatorResults = commerceOrderValidatorException.getCommerceOrderValidatorResults();
	}

	for (CommerceOrderValidatorResult commerceOrderValidatorResult : commerceOrderValidatorResults) {
	%>

		<liferay-ui:message key="<%= HtmlUtil.escape(commerceOrderValidatorResult.getLocalizedMessage()) %>" />

	<%
	}
	%>

</liferay-ui:error>

<liferay-ddm:template-renderer
	className="<%= CommerceCartContentPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= commerceCartContentDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= commerceCartContentDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= commerceOrderItemSearchContainer.getResults() %>"
>
	<div class="commerce-order-items container-fluid-1280" id="<portlet:namespace />orderItemsContainer">
		<div class="commerce-order-items-container" id="<portlet:namespace />entriesContainer">
			<liferay-ui:search-container
				id="commerceOrderItems"
				iteratorURL="<%= portletURL %>"
				searchContainer="<%= commerceOrderItemSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.model.CommerceOrderItem"
					cssClass="entry-display-style"
					keyProperty="CommerceOrderItemId"
					modelVar="commerceOrderItem"
				>

					<%
					CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

					long cpDefinitionId = 0;

					String thumbnailSrc = StringPool.BLANK;

					StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

					if (cpInstance != null) {
						CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();

						cpDefinitionId = cpDefinition.getCPDefinitionId();

						thumbnailSrc = commerceCartContentDisplayContext.getCommerceOrderItemThumbnailSrc(commerceOrderItem);

						List<KeyValuePair> keyValuePairs = commerceCartContentDisplayContext.getKeyValuePairs(commerceOrderItem.getCPDefinitionId(), commerceOrderItem.getJson(), locale);

						for (KeyValuePair keyValuePair : keyValuePairs) {
							stringJoiner.add(keyValuePair.getValue());
						}
					}
					%>

					<liferay-ui:search-container-column-image
						name="product"
						src="<%= thumbnailSrc %>"
					/>

					<liferay-ui:search-container-column-text
						name="description"
					>
						<a class="font-weight-bold" href="<%= (cpDefinitionId == 0) ? StringPool.BLANK : commerceCartContentDisplayContext.getCPDefinitionURL(cpDefinitionId, themeDisplay) %>">
							<%= HtmlUtil.escape(commerceOrderItem.getName(languageId)) %>
						</a>

						<h6 class="text-default">
							<%= HtmlUtil.escape(stringJoiner.toString()) %>
						</h6>

						<c:if test="<%= !commerceOrderValidatorResultMap.isEmpty() %>">

							<%
							commerceOrderValidatorResults = commerceOrderValidatorResultMap.get(commerceOrderItem.getCommerceOrderItemId());

							for (CommerceOrderValidatorResult commerceOrderValidatorResult : commerceOrderValidatorResults) {
							%>

								<div class="alert-danger commerce-alert-danger">
									<liferay-ui:message key="<%= HtmlUtil.escape(commerceOrderValidatorResult.getLocalizedMessage()) %>" />
								</div>

							<%
							}
							%>

						</c:if>
					</liferay-ui:search-container-column-text>

					<%
					String commercePriceDisplayType = commerceCartContentDisplayContext.getCommercePriceDisplayType();
					%>

					<liferay-ui:search-container-column-text
						name="list-price"
					>
						<c:if test="<%= commerceCartContentDisplayContext.hasViewPricePermission() %>">

							<%
							CommerceMoney unitPriceCommerceMoney = commerceOrderItem.getUnitPriceMoney();

							if (commercePriceDisplayType.equals(CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {
								unitPriceCommerceMoney = commerceOrderItem.getUnitPriceWithTaxAmountMoney();
							}
							%>

							<%= HtmlUtil.escape(unitPriceCommerceMoney.format(locale)) %>
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="quantity-control-column"
						name="quantity"
					>
						<liferay-commerce-cart:quantity-control
							commerceOrderItemId="<%= commerceOrderItem.getCommerceOrderItemId() %>"
							useSelect="<%= false %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="total"
					>
						<c:if test="<%= commerceCartContentDisplayContext.hasViewPricePermission() %>">

							<%
							CommerceMoney finalPriceCommerceMoney = commerceOrderItem.getFinalPriceMoney();

							if (commercePriceDisplayType.equals(CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {
								finalPriceCommerceMoney = commerceOrderItem.getFinalPriceWithTaxAmountMoney();
							}
							%>

							<%= HtmlUtil.escape(finalPriceCommerceMoney.format(locale)) %>

							<commerce-ui:product-subscription-info
								CPInstanceId="<%= commerceOrderItem.getCPInstanceId() %>"
								showDuration="<%= false %>"
							/>
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text>
						<c:if test="<%= commerceCartContentDisplayContext.hasPermission(ActionKeys.UPDATE) %>">
							<liferay-ui:icon-delete
								label="<%= true %>"
								url="<%= commerceCartContentDisplayContext.getDeleteURL(commerceOrderItem) %>"
							/>
						</c:if>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="list"
					markupView="lexicon"
					searchContainer="<%= commerceOrderItemSearchContainer %>"
				/>
			</liferay-ui:search-container>
		</div>
	</div>

	<aui:script>
		Liferay.after('commerce:productAddedToCart', function (event) {
			Liferay.Portlet.refresh('#p_p_id<portlet:namespace />');
		});
	</aui:script>
</liferay-ddm:template-renderer>