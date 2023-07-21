<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
InfoListProviderDisplayContext infoListProviderDisplayContext = (InfoListProviderDisplayContext)request.getAttribute(AssetListWebKeys.INFO_LIST_PROVIDER_DISPLAY_CONTEXT);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%= assetListDisplayContext.getNavigationItems("collection-providers") %>'
/>

<div class="container-fluid-1280 lfr-search-container-wrapper" id="<portlet:namespace />collectionProviders">
	<liferay-ui:breadcrumb
		showLayout="<%= false %>"
	/>

	<liferay-ui:search-container
		id="entries"
		searchContainer="<%= infoListProviderDisplayContext.getSearchContainer() %>"
		var="collectionsSearch"
	>
		<liferay-ui:search-container-row
			className="com.liferay.info.list.provider.InfoListProvider"
			cssClass="entry"
			modelVar="infoListProvider"
		>

			<%
			row.setCssClass("entry-card entry-display-style lfr-asset-item " + row.getCssClass());
			%>

			<liferay-ui:search-container-column-icon
				icon="list"
			/>

			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
			>
				<h5>
					<%= HtmlUtil.escape(infoListProviderDisplayContext.getTitle(infoListProvider)) %>
				</h5>

				<h6 class="text-default">
					<strong><liferay-ui:message key="<%= HtmlUtil.escape(infoListProviderDisplayContext.getSubtitle(infoListProvider)) %>" /></strong>
				</h6>
			</liferay-ui:search-container-column-text>

			<%
			InfoListProviderActionDropdownItems infoListProviderActionDropdownItems = new InfoListProviderActionDropdownItems(infoListProvider, liferayPortletRequest, liferayPortletResponse);
			%>

			<liferay-ui:search-container-column-text>
				<clay:dropdown-actions
					defaultEventHandler="infoListProviderDropdownDefaultEventHandler"
					dropdownItems="<%= infoListProviderActionDropdownItems.getActionDropdownItems() %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<liferay-frontend:component
	componentId="infoListProviderDropdownDefaultEventHandler"
	module="js/InfoListProviderDropdownDefaultEventHandler.es"
/>