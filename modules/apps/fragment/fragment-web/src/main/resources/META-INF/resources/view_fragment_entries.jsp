<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
FragmentManagementToolbarDisplayContextFactory fragmentManagementToolbarDisplayContextFactory = FragmentManagementToolbarDisplayContextFactory.getInstance();

FragmentManagementToolbarDisplayContext fragmentManagementToolbarDisplayContext = fragmentManagementToolbarDisplayContextFactory.getFragmentManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, fragmentDisplayContext);
%>

<liferay-ui:error exception="<%= RequiredFragmentEntryException.class %>" message="the-fragment-entry-cannot-be-deleted-because-it-is-required-by-one-or-more-page-templates" />

<clay:management-toolbar
	displayContext="<%= fragmentManagementToolbarDisplayContext %>"
/>

<aui:form name="fm">
	<liferay-ui:search-container
		searchContainer="<%= fragmentDisplayContext.getFragmentEntriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="Object"
			modelVar="object"
		>

			<%
			row.setCssClass("card-page-item-asset " + row.getCssClass());

			row.setData(
				HashMapBuilder.<String, Object>put(
					"actions", fragmentDisplayContext.getAvailableActions(object)
				).build());

			FragmentEntryVerticalCardFactory fragmentEntryVerticalCardFactory = FragmentEntryVerticalCardFactory.getInstance();
			%>

			<liferay-ui:search-container-column-text>
				<c:choose>
					<c:when test="<%= object instanceof FragmentComposition %>">
						<clay:vertical-card
							verticalCard="<%= fragmentEntryVerticalCardFactory.getVerticalCard((FragmentComposition)object, renderRequest, renderResponse, searchContainer.getRowChecker(), fragmentDisplayContext.getFragmentType()) %>"
						/>
					</c:when>
					<c:otherwise>
						<clay:vertical-card
							verticalCard="<%= fragmentEntryVerticalCardFactory.getVerticalCard((FragmentEntry)object, renderRequest, renderResponse, searchContainer.getRowChecker(), fragmentDisplayContext.getFragmentType()) %>"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
			searchResultCssClass="card-page"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="/fragment/update_fragment_composition_preview" var="updateFragmentCompositionPreviewURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= updateFragmentCompositionPreviewURL %>" name="fragmentCompositionPreviewFm">
	<aui:input name="fragmentCompositionId" type="hidden" />
	<aui:input id="fragmentCompositionFileEntryId" name="fileEntryId" type="hidden" />
</aui:form>

<aui:form name="fragmentEntryFm">
	<aui:input name="fragmentCollectionId" type="hidden" />
	<aui:input name="fragmentCompositionId" type="hidden" />
	<aui:input name="fragmentCompositionIds" type="hidden" />
	<aui:input name="fragmentEntryIds" type="hidden" />
</aui:form>

<portlet:actionURL name="/fragment/update_fragment_entry_preview" var="updateFragmentEntryPreviewURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= updateFragmentEntryPreviewURL %>" name="fragmentEntryPreviewFm">
	<aui:input name="fragmentEntryId" type="hidden" />
	<aui:input name="fileEntryId" type="hidden" />
</aui:form>

<liferay-frontend:component
	componentId="<%= FragmentWebKeys.FRAGMENT_COMPOSITION_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
	module="js/FragmentCompositionDropdownDefaultEventHandler.es"
/>

<liferay-frontend:component
	componentId="<%= FragmentWebKeys.FRAGMENT_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
	module="js/FragmentEntryDropdownDefaultEventHandler.es"
/>

<liferay-frontend:component
	componentId="<%= fragmentManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	context="<%= fragmentManagementToolbarDisplayContext.getComponentContext() %>"
	module="js/ManagementToolbarDefaultEventHandler.es"
/>