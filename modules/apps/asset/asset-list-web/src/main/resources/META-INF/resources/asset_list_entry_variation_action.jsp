<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:icon-menu
	direction="down"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
	triggerCssClass="btn btn-unstyled component-action text-secondary"
>
	<portlet:renderURL var="viewAssetListEntryVariationItemsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/view_asset_list_items.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="assetListEntryId" value="<%= String.valueOf(editAssetListDisplayContext.getAssetListEntryId()) %>" />
		<portlet:param name="segmentsEntryId" value="<%= String.valueOf(editAssetListDisplayContext.getSegmentsEntryId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="view-items"
		method="get"
		url="<%= viewAssetListEntryVariationItemsURL %>"
		useDialog="<%= true %>"
	/>

	<c:if test="<%= (editAssetListDisplayContext.getSegmentsEntryId() != SegmentsEntryConstants.ID_DEFAULT) && !editAssetListDisplayContext.isLiveGroup() %>">
		<portlet:actionURL name="/asset_list/delete_asset_list_entry_variation" var="deleteAssetListEntryVariationURL">
			<portlet:param name="assetListEntryId" value="<%= String.valueOf(editAssetListDisplayContext.getAssetListEntryId()) %>" />
			<portlet:param name="segmentsEntryId" value="<%= String.valueOf(editAssetListDisplayContext.getSegmentsEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteAssetListEntryVariationURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>