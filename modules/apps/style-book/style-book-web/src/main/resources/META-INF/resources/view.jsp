<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<c:if test='<%= SessionErrors.contains(liferayPortletRequest, "styleBookEntryPreviewFileExtensionInvalid") %>'>
	<aui:script>
		Liferay.Util.openToast({
			message: '<liferay-ui:message key="file-type-is-invalid" />',
			title: Liferay.Language.get('error'),
			toastProps: {
				autoClose: 5000,
			},
			type: 'danger',
		});
	</aui:script>
</c:if>

<%
StyleBookDisplayContext styleBookDisplayContext = new StyleBookDisplayContext(request, liferayPortletRequest, liferayPortletResponse);

StyleBookManagementToolbarDisplayContext styleBookManagementToolbarDisplayContext = new StyleBookManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, styleBookDisplayContext.getStyleBookEntriesSearchContainer());
%>

<clay:management-toolbar
	displayContext="<%= styleBookManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="/style_book/delete_style_book_entry" var="deleteStyleBookEntryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<clay:container-fluid>
	<aui:form action="<%= deleteStyleBookEntryURL %>" name="fm">
		<liferay-ui:search-container
			searchContainer="<%= styleBookDisplayContext.getStyleBookEntriesSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.style.book.model.StyleBookEntry"
				keyProperty="styleBookEntryId"
				modelVar="styleBookEntry"
			>

				<%
				row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
				%>

				<liferay-ui:search-container-column-text>
					<clay:vertical-card
						verticalCard="<%= new StyleBookVerticalCard(styleBookEntry, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
					/>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="icon"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>

<aui:form name="styleBookEntryFm">
	<aui:input name="styleBookEntryIds" type="hidden" />
</aui:form>

<portlet:actionURL name="/style_book/update_style_book_entry_preview" var="styleBookEntryPreviewURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= styleBookEntryPreviewURL %>" name="styleBookEntryPreviewFm">
	<aui:input name="styleBookEntryId" type="hidden" />
	<aui:input name="fileEntryId" type="hidden" />
</aui:form>

<liferay-frontend:component
	componentId="<%= StyleBookWebKeys.STYLE_BOOK_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
	module="js/StyleBookEntryDropdownDefaultEventHandler.es"
/>

<liferay-frontend:component
	componentId="<%= styleBookManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	context="<%= styleBookManagementToolbarDisplayContext.getComponentContext() %>"
	module="js/ManagementToolbarDefaultEventHandler.es"
/>