<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ContentDashboardItemTypeItemSelectorViewManagementToolbarDisplayContext contentDashboardItemTypeItemSelectorViewManagementToolbarDisplayContext = (ContentDashboardItemTypeItemSelectorViewManagementToolbarDisplayContext)request.getAttribute(ContentDashboardItemTypeItemSelectorViewManagementToolbarDisplayContext.class.getName());

ContentDashboardItemTypeItemSelectorViewDisplayContext contentDashboardItemTypeItemSelectorViewDisplayContext = (ContentDashboardItemTypeItemSelectorViewDisplayContext)request.getAttribute(ContentDashboardItemTypeItemSelectorViewDisplayContext.class.getName());
%>

<clay:management-toolbar
	displayContext="<%= contentDashboardItemTypeItemSelectorViewManagementToolbarDisplayContext %>"
/>

<clay:container-fluid>
	<liferay-ui:search-container
		id="contentDashboardItemTypes"
		searchContainer="<%= contentDashboardItemTypeItemSelectorViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType"
			modelVar="contentDashboardItemType"
		>

			<%
			InfoItemReference infoItemReference = contentDashboardItemType.getInfoItemReference();

			row.setPrimaryKey(
				HtmlUtil.toInputSafe(
					JSONUtil.put(
						"className", infoItemReference.getClassName()
					).put(
						"classPK", infoItemReference.getClassPK()
					).toString()));
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
				value="<%= contentDashboardItemType.getFullLabel(locale) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />contentDashboardItemTypes'
	);

	searchContainer.on('rowToggled', function (event) {
		var allSelectedElements = event.elements.allSelectedElements;
		var arr = [];

		allSelectedElements.each(function () {
			var payload = JSON.parse(Liferay.Util.unescape(this.getDOM().value));

			arr.push({
				classPK: payload.classPK,
				className: payload.className,
			});
		});

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(contentDashboardItemTypeItemSelectorViewDisplayContext.getItemSelectedEventName()) %>',
			{
				data: arr,
			}
		);
	});
</aui:script>