<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DisplayPageUsagesDisplayContext displayPageUsagesDisplayContext = new DisplayPageUsagesDisplayContext(request, renderRequest, renderResponse);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(displayPageUsagesDisplayContext.getRedirect());

LayoutPageTemplateEntry layoutPageTemplateEntry = LayoutPageTemplateEntryServiceUtil.fetchLayoutPageTemplateEntry(displayPageUsagesDisplayContext.getLayoutPageTemplateEntryId());

renderResponse.setTitle(LanguageUtil.format(request, "usages-x", layoutPageTemplateEntry.getName()));
%>

<clay:management-toolbar
	displayContext="<%= new DisplayPageUsagesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, displayPageUsagesDisplayContext.getSearchContainer()) %>"
/>

<clay:container-fluid>
	<liferay-ui:search-container
		searchContainer="<%= displayPageUsagesDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.display.page.model.AssetDisplayPageEntry"
			keyProperty="assetDisplayPageEntryId"
			modelVar="assetDisplayPageEntry"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				name="title"
				value="<%= HtmlUtil.escape(displayPageUsagesDisplayContext.getTitle(assetDisplayPageEntry, themeDisplay.getLocale())) %>"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
				name="modified-date"
				value="<%= assetDisplayPageEntry.getModifiedDate() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>