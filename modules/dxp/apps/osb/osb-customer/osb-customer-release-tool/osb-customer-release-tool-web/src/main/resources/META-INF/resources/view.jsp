<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1");

String product = ParamUtil.getString(request, "product");
double productVersion = ParamUtil.getDouble(request, "productVersion");

JSONArray fixPackFiltersJSONArray = releaseToolDisplayContext.getFixPackFiltersJSONArray();

if (fixPackFiltersJSONArray.length() == 1) {
	JSONObject jsonObject = fixPackFiltersJSONArray.getJSONObject(0);

	product = jsonObject.getString("product");
	productVersion = jsonObject.getDouble("version");
}

double fromFixPackVersion = ParamUtil.getDouble(request, "fromFixPackVersion");
double toFixPackVersion = ParamUtil.getDouble(request, "toFixPackVersion");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("product", product);
portletURL.setParameter("productVersion", String.valueOf(productVersion));
portletURL.setParameter("fromFixPackVersion", String.valueOf(fromFixPackVersion));
portletURL.setParameter("toFixPackVersion", String.valueOf(toFixPackVersion));
%>

<liferay-portlet:renderURL var="fixPacksURL">
	<portlet:param name="tabs1" value="<%= tabs1 %>" />
	<portlet:param name="fromFixPackVersion" value="<%= String.valueOf(fromFixPackVersion) %>" />
	<portlet:param name="orderByType" value="desc" />
	<portlet:param name="product" value="<%= product %>" />
	<portlet:param name="productVersion" value="<%= String.valueOf(productVersion) %>" />
	<portlet:param name="toFixPackVersion" value="<%= String.valueOf(toFixPackVersion) %>" />
</liferay-portlet:renderURL>

<div class="hide sticky-header" id="<portlet:namespace />stickyHeader"></div>

<div class="main-heading">
	<aui:container cssClass="container-fluid-max-xl heading-container" fluid="<%= true %>">
		<c:choose>
			<c:when test="<%= productName.equals(ProductConstants.DXP) %>">
				<div class="heading-icon">
					<svg class="lexicon-icon-product-logo">
						<use xlink:href="#dxp-logo-block"></use>
					</svg>
				</div>

				<div class="heading-text">
					<h1>
						<liferay-ui:message key="liferay-dxp-release-notes" />
					</h1>

					<h5 class="section-subtitle">
						<liferay-ui:message key="display-the-fix-pack-information-relevant-to-your-liferay-build" />
					</h5>
				</div>
			</c:when>
			<c:otherwise>
				<div class="heading-icon">
					<svg class="lexicon-icon-product-logo">
						<use xlink:href="#commerce-logo-block"></use>
					</svg>
				</div>

				<div class="heading-text">
					<h1>
						<liferay-ui:message key="liferay-commerce-release-notes" />
					</h1>

					<h5 class="section-subtitle">
						<liferay-ui:message key="display-the-fix-pack-information-relevant-to-your-liferay-build" />
					</h5>
				</div>
			</c:otherwise>
		</c:choose>
	</aui:container>
</div>

<div class="container-fluid container-fluid-max-xl fixpack-filters" id="<portlet:namespace />fixpackFilters"></div>

<aui:container cssClass="container-fluid-max-xl" fluid="<%= true %>" id="mainContent">
	<c:choose>
		<c:when test="<%= (fromFixPackVersion < 1) && (productVersion < 1) && (toFixPackVersion < 1) %>">
			<div class="card main-content-card taglib-empty-result-message">
				<div class="card-row card-row-padded">
					<div class="taglib-empty-result-message-header"></div>
					<div class="text-center text-muted">
						<liferay-ui:message key="content-collection-is-empty-select-your-settings-above-to-show-details" />
					</div>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<liferay-ui:tabs
				cssClass="container-fluid container-fluid-max-xl"
				names="highlights,changelog,module-changes"
				url="<%= portletURL.toString() %>"
			/>

			<c:choose>
				<c:when test='<%= tabs1.equals("changelog") %>'>
					<liferay-util:include page="/changelog.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs1.equals("module-changes") %>'>
					<liferay-util:include page="/module_changes.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/highlights.jsp" servletContext="<%= application %>" />
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</aui:container>

<aui:script>
	ReleaseTool.render(
		ReleaseTool.FixpackFilters,
		{
			actionURL: '<%= fixPacksURL %>',
			filtersJSON: <%= fixPackFiltersJSONArray %>,
			fixpackURL: '<%= releaseToolDisplayContext.getFixPackDownloadURL(product, productVersion, toFixPackVersion) %>',
			fromFixPackVersion: '<%= String.valueOf(fromFixPackVersion) %>',
			productName: '<%= HtmlUtil.escapeJS(productName) %>',
			productVersion: '<%= String.valueOf(productVersion) %>',
			tabName: '<%= HtmlUtil.escape(tabs1) %>',
			toFixPackVersion: '<%= String.valueOf(toFixPackVersion) %>'
		},
		document.getElementById('<portlet:namespace />fixpackFilters')
	);

	ReleaseTool.render(
		ReleaseTool.StickyHeader,
		null,
		document.getElementById('<portlet:namespace />stickyHeader')
	);
</aui:script>