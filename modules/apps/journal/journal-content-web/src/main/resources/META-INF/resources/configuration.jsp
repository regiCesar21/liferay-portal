<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:error exception="<%= NoSuchArticleException.class %>" message="the-web-content-could-not-be-found" />

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" varImpl="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	<aui:input name="preferences--assetEntryId--" type="hidden" value="<%= journalContentDisplayContext.getAssetEntryId() %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<div id="<portlet:namespace />articlePreview">
					<liferay-util:include page="/journal_resources.jsp" servletContext="<%= application %>">
						<liferay-util:param name="refererPortletName" value="<%= liferayPortletResponse.getNamespace() %>" />
					</liferay-util:include>
				</div>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button name="saveButton" type="submit" />

		<aui:button href='<%= ParamUtil.getString(request, "redirect") %>' type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script require="metal-dom/src/all/dom as dom">
	var articlePreview = document.getElementById(
		'<portlet:namespace />articlePreview'
	);
	var assetEntryIdInput = document.getElementById(
		'<portlet:namespace />assetEntryId'
	);

	dom.delegate(articlePreview, 'click', '.web-content-selector', function (
		event
	) {
		event.preventDefault();

		Liferay.Util.openSelectionModal({
			onSelect: function (selectedItem) {
				if (selectedItem) {
					retrieveWebContent(selectedItem.assetclasspk);
				}
			},
			selectEventName: '<portlet:namespace />selectedItem',
			title: '<liferay-ui:message key="select-web-content" />',
			url: '<%= journalContentDisplayContext.getItemSelectorURL() %>',
		});
	});

	dom.delegate(articlePreview, 'click', '.selector-button', function (event) {
		event.preventDefault();
		retrieveWebContent(-1);
	});

	function retrieveWebContent(assetClassPK) {
		var uri = '<%= configurationRenderURL %>';

		uri = Liferay.Util.addParams(
			'<portlet:namespace />articleResourcePrimKey=' + assetClassPK,
			uri
		);

		Liferay.Util.navigate(uri);
	}
</aui:script>