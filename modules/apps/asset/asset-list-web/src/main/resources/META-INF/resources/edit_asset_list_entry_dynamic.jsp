<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

if (Validator.isNotNull(backURL)) {
	portletDisplay.setURLBack(backURL);
}
else if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	backURL = portletURL.toString();
}
else {
	backURL = redirect;
}

AssetListEntry assetListEntry = assetListDisplayContext.getAssetListEntry();
%>

<portlet:actionURL name="/asset_list/update_asset_list_entry_dynamic" var="updateAssetListEntryDynamicURL">
	<portlet:param name="mvcPath" value="/edit_asset_list_entry.jsp" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= updateAssetListEntryDynamicURL %>"
	cssClass="pt-0"
	fluid="<%= true %>"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="assetListEntryId" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryId() %>" />
	<aui:input name="segmentsEntryId" type="hidden" value="<%= assetListDisplayContext.getSegmentsEntryId() %>" />
	<aui:input name="type" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryType() %>" />

	<aui:model-context bean="<%= assetListEntry %>" model="<%= AssetListEntry.class %>" />

	<liferay-frontend:edit-form-body>
		<h3 class="sheet-title">
			<clay:content-row
				verticalAlign="center"
			>
				<clay:content-col>
					<%= HtmlUtil.escape(editAssetListDisplayContext.getSegmentsEntryName(editAssetListDisplayContext.getSegmentsEntryId(), locale)) %>
				</clay:content-col>

				<clay:content-col
					cssClass="inline-item-after"
				>
					<liferay-util:include page="/asset_list_entry_variation_action.jsp" servletContext="<%= application %>" />
				</clay:content-col>
			</clay:content-row>
		</h3>

		<liferay-frontend:form-navigator
			formModelBean="<%= assetListEntry %>"
			id="<%= AssetListFormConstants.FORM_NAVIGATOR_ID %>"
			showButtons="<%= false %>"
		/>
	</liferay-frontend:edit-form-body>

	<c:if test="<%= !editAssetListDisplayContext.isLiveGroup() %>">
		<liferay-frontend:edit-form-footer>
			<aui:button disabled="<%= editAssetListDisplayContext.isNoAssetTypeSelected() %>" id="saveButton" onClick='<%= liferayPortletResponse.getNamespace() + "saveSelectBoxes();" %>' type="submit" />

			<aui:button href="<%= backURL %>" type="cancel" />
		</liferay-frontend:edit-form-footer>
	</c:if>
</liferay-frontend:edit-form>