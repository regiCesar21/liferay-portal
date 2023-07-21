<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceAccount commerceAccount = commerceOrderContentDisplayContext.getCommerceAccount();
%>

<liferay-ui:error exception="<%= CommerceOrderAccountLimitException.class %>" message="unable-to-create-a-new-order-as-the-open-order-limit-has-been-reached" />

<liferay-ddm:template-renderer
	className="<%= CommerceOpenOrderContentPortlet.class.getName() %>"
	contextObjects='<%=
		HashMapBuilder.<String, Object>put(
			"commerceOrderContentDisplayContext", commerceOrderContentDisplayContext
		).build()
	%>'
	displayStyle="<%= commerceOrderContentDisplayContext.getDisplayStyle(CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT) %>"
	displayStyleGroupId="<%= commerceOrderContentDisplayContext.getDisplayStyleGroupId(CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT) %>"
	entries="<%= commerceOrderSearchContainer.getResults() %>"
>
	<clay:data-set-display
		dataProviderKey="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDERS %>"
		id="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDERS %>"
		itemsPerPage="<%= 10 %>"
		namespace="<%= liferayPortletResponse.getNamespace() %>"
		pageNumber="<%= 1 %>"
		portletURL="<%= commerceOrderContentDisplayContext.getPortletURL() %>"
		style="stacked"
	/>

	<portlet:actionURL name="/commerce_open_order_content/edit_commerce_order" var="editCommerceOrderURL" />

	<div class="commerce-cta is-visible">
		<c:if test="<%= commerceOrderContentDisplayContext.hasPermission(CommerceOrderActionKeys.ADD_COMMERCE_ORDER) %>">
			<aui:form action="<%= editCommerceOrderURL %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="deleteCommerceOrderIds" type="hidden" />

				<clay:button
					disabled="<%= commerceAccount == null %>"
					elementClasses="btn-fixed btn-primary"
					label='<%= LanguageUtil.get(request, "add-order") %>'
					size="lg"
					style="primary"
					type="submit"
				/>
			</aui:form>
		</c:if>
	</div>
</liferay-ddm:template-renderer>