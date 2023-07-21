<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/shared_assets/init.jsp" %>

<%
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)renderRequest.getAttribute(AssetRenderer.class.getName());

AssetRendererFactory<?> assetRendererFactory = assetRenderer.getAssetRendererFactory();

AssetEntry assetEntry = assetRendererFactory.getAssetEntry(assetRendererFactory.getClassName(), assetRenderer.getClassPK());

SharingEntry sharingEntry = (SharingEntry)renderRequest.getAttribute(SharingEntry.class.getName());

String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL portletURL = liferayPortletResponse.createRenderURL();

	redirect = portletURL.toString();
}

Group scopeGroup = themeDisplay.getScopeGroup();

if (scopeGroup.equals(themeDisplay.getControlPanelGroup())) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);
}
else {
	portletDisplay.setPortletDecorate(false);
	portletDisplay.setShowBackIcon(false);
}
%>

<div class="tbar upper-tbar">
	<clay:container-fluid>
		<ul class="tbar-nav">
			<c:if test="<%= !scopeGroup.equals(themeDisplay.getControlPanelGroup()) %>">
				<li class="d-none d-sm-flex tbar-item">
					<clay:link
						borderless="<%= true %>"
						displayType="secondary"
						href="<%= redirect %>"
						icon="angle-left"
						monospaced="<%= true %>"
						outline="<%= true %>"
						small="<%= true %>"
						type="button"
					/>
				</li>
			</c:if>

			<li class="tbar-item tbar-item-expand">
				<div class="tbar-section text-left">
					<h2 class="my-4 text-truncate-inline upper-tbar-title" title="<%= HtmlUtil.escapeAttribute(assetRenderer.getTitle(locale)) %>">
						<span class="text-truncate"><%= HtmlUtil.escape(assetRenderer.getTitle(locale)) %></span>
					</h2>
				</div>
			</li>
			<li class="tbar-item">

				<%
				ViewSharedAssetsDisplayContext viewSharedAssetsDisplayContext = (ViewSharedAssetsDisplayContext)renderRequest.getAttribute(ViewSharedAssetsDisplayContext.class.getName());
				%>

				<liferay-ui:menu
					menu="<%= viewSharedAssetsDisplayContext.getSharingEntryMenu(sharingEntry) %>"
				/>
			</li>
		</ul>
	</clay:container-fluid>
</div>

<liferay-util:buffer
	var="assetContent"
>
	<liferay-asset:asset-display
		renderer="<%= assetRenderer %>"
	/>

	<c:if test="<%= assetRenderer.isCommentable() %>">
		<liferay-comment:discussion
			className="<%= assetEntry.getClassName() %>"
			classPK="<%= assetEntry.getClassPK() %>"
			formName='<%= "fm" + assetEntry.getClassPK() %>'
			ratingsEnabled="<%= false %>"
			redirect="<%= currentURL %>"
			userId="<%= assetRenderer.getUserId() %>"
		/>
	</c:if>
</liferay-util:buffer>

<clay:container-fluid>
	<c:choose>
		<c:when test="<%= scopeGroup.equals(themeDisplay.getControlPanelGroup()) %>">
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<%= assetContent %>
				</aui:fieldset>
			</aui:fieldset-group>
		</c:when>
		<c:otherwise>
			<%= assetContent %>
		</c:otherwise>
	</c:choose>
</clay:container-fluid>