<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String displayStyle = ParamUtil.getString(request, "displayStyle");
String searchContainerId = ParamUtil.getString(request, "searchContainerId");
String selectEventName = ParamUtil.getString(request, "selectEventName");
%>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace /><%= HtmlUtil.escape(searchContainerId) %>'
	);

	searchContainer.on('rowToggled', function (event) {
		var allSelectedElements = event.elements.allSelectedElements;

		var selectedData = [];

		allSelectedElements.each(function () {
			<c:choose>
				<c:when test='<%= Objects.equals(displayStyle, "list") %>'>
					var row = this.ancestor('tr');
				</c:when>
				<c:otherwise>
					var row = this.ancestor('li');
				</c:otherwise>
			</c:choose>

			var data = row.getDOM().dataset;

			selectedData.push({
				id: data.id,
				name: data.name,
			});
		});

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escape(selectEventName) %>',
			{
				data: selectedData.length ? selectedData : null,
			}
		);
	});
</aui:script>