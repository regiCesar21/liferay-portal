<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-util:include page="/common/view_account_search_header.jsp" servletContext="<%= application %>" />

<%
ProductSearchDisplayContext productSearchDisplayContext = ProvisioningWebComponentProvider.getProductSearchDisplayContext(renderRequest, renderResponse, request);
%>

<div class="title-bar">
	<h3><liferay-ui:message key="products" /></h3>
</div>

<div class="container-fluid home">
	<clay:management-toolbar
		displayContext="<%= new ViewProductsManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, productSearchDisplayContext.getSearchContainer()) %>"
		elementClasses="full-width"
	/>

	<liferay-ui:search-container
		cssClass="table-hover"
		searchContainer="<%= productSearchDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.osb.provisioning.web.internal.display.context.ProductDisplay"
			keyProperty="productKey"
			modelVar="productDisplay"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcRenderCommandName" value="/products/view_product" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="productKey" value="<%= productDisplay.getKey() %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="name"
				value="<%= HtmlUtil.escape(productDisplay.getName()) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="type"
				value="<%= productDisplay.getType() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>