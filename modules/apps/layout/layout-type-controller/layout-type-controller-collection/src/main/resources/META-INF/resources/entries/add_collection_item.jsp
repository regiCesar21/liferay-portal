<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/entries/init.jsp" %>

<%
String label = LanguageUtil.get(resourceBundle, "new-collection-page-item");

List<AssetPublisherAddItemHolder> assetPublisherAddItemHolders = (List<AssetPublisherAddItemHolder>)request.getAttribute(CollectionPageLayoutTypeControllerWebKeys.ASSET_PUBLISHER_ADD_ITEM_HOLDERS);

if (assetPublisherAddItemHolders.size() == 1) {
	AssetPublisherAddItemHolder assetPublisherAddItemHolder = assetPublisherAddItemHolders.get(0);

	label = LanguageUtil.format(request, "new-x", new Object[] {assetPublisherAddItemHolder.getModelResource()});
}

Map<String, Object> data = HashMapBuilder.<String, Object>put(
	"title", label
).build();
%>

<liferay-ui:success key="collectionItemAdded" message="your-request-completed-successfully" />

<li class="control-menu-nav-item control-menu-nav-item-content">
	<a aria-label="<%= label %>" data-title="<%= label %>">
		<c:choose>
			<c:when test="<%= assetPublisherAddItemHolders.size() == 1 %>">

				<%
				AssetPublisherAddItemHolder assetPublisherAddItemHolder = assetPublisherAddItemHolders.get(0);

				PortletURL portletURL = assetPublisherAddItemHolder.getPortletURL();
				%>

				<liferay-ui:icon
					data="<%= data %>"
					icon="plus"
					linkCssClass="icon-monospaced lfr-portal-tooltip"
					markupView="lexicon"
					message="<%= label %>"
					url="<%= portletURL.toString() %>"
				/>
			</c:when>
			<c:otherwise>
				<liferay-ui:icon-menu
					cssClass="lfr-portal-tooltip"
					data="<%= data %>"
					direction="left-side"
					icon="plus"
					markupView="lexicon"
					message="<%= label %>"
				>

					<%
					for (AssetPublisherAddItemHolder assetPublisherAddItemHolder : assetPublisherAddItemHolders) {
						PortletURL portletURL = assetPublisherAddItemHolder.getPortletURL();
					%>

						<liferay-ui:icon
							message="<%= assetPublisherAddItemHolder.getModelResource() %>"
							url="<%= portletURL.toString() %>"
						/>

					<%
					}
					%>

				</liferay-ui:icon-menu>
			</c:otherwise>
		</c:choose>
	</a>
</li>