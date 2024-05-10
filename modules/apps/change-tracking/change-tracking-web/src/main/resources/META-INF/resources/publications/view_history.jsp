<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
ViewHistoryDisplayContext viewHistoryDisplayContext = (ViewHistoryDisplayContext)request.getAttribute(CTWebKeys.VIEW_HISTORY_DISPLAY_CONTEXT);

SearchContainer<CTProcess> searchContainer = viewHistoryDisplayContext.getSearchContainer();

ViewHistoryManagementToolbarDisplayContext viewHistoryManagementToolbarDisplayContext = new ViewHistoryManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, searchContainer, viewHistoryDisplayContext);

Format format = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<clay:navigation-bar
	navigationItems="<%= viewHistoryDisplayContext.getViewNavigationItems() %>"
/>

<clay:management-toolbar
	displayContext="<%= viewHistoryManagementToolbarDisplayContext %>"
/>

<clay:container-fluid>
	<liferay-ui:search-container
		cssClass="publications-table"
		searchContainer="<%= searchContainer %>"
		var="reviewChangesSearchContainer"
	>
		<liferay-ui:search-container-row
			className="com.liferay.change.tracking.model.CTProcess"
			escapedModel="<%= true %>"
			keyProperty="ctProcessId"
			modelVar="ctProcess"
		>

			<%
			CTCollection ctCollection = viewHistoryDisplayContext.getCtCollection(ctProcess);
			User ctProcessUser = UserLocalServiceUtil.fetchUserById(ctProcess.getUserId());
			int status = viewHistoryDisplayContext.getStatus(ctProcess);
			%>

			<liferay-portlet:renderURL var="changesURL">
				<portlet:param name="mvcRenderCommandName" value="/change_tracking/view_changes" />
				<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
			</liferay-portlet:renderURL>

			<c:choose>
				<c:when test='<%= Objects.equals(viewHistoryDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-text>
						<span class="lfr-portal-tooltip" title="<%= ctProcessUser.getFullName() %>">
							<liferay-ui:user-portrait
								userId="<%= ctProcess.getUserId() %>"
							/>
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="autofit-col-expand"
					>
						<c:choose>
							<c:when test="<%= status == BackgroundTaskConstants.STATUS_SUCCESSFUL %>">
								<a href="<%= changesURL %>">
									<%@ include file="/publications/publication_info_escaped.jspf" %>
								</a>
							</c:when>
							<c:otherwise>
								<%@ include file="/publications/publication_info_escaped.jspf" %>
							</c:otherwise>
						</c:choose>

						<div>
							<clay:label
								displayType="<%= viewHistoryDisplayContext.getStatusStyle(status) %>"
								label="<%= viewHistoryDisplayContext.getStatusLabel(status) %>"
							/>
						</div>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="publication"
					>
						<c:choose>
							<c:when test="<%= status == BackgroundTaskConstants.STATUS_SUCCESSFUL %>">
								<a href="<%= changesURL %>">
									<%@ include file="/publications/publication_info_escaped.jspf" %>
								</a>
							</c:when>
							<c:otherwise>
								<%@ include file="/publications/publication_info_escaped.jspf" %>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smaller"
						name="published-date"
						value="<%= format.format(ctProcess.getCreateDate()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smallest text-center"
						name="published-by"
					>
						<span class="lfr-portal-tooltip" title="<%= ctProcessUser.getFullName() %>">
							<liferay-ui:user-portrait
								userId="<%= ctProcess.getUserId() %>"
							/>
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smaller"
						name="status"
					>
						<clay:label
							displayType="<%= viewHistoryDisplayContext.getStatusStyle(status) %>"
							label="<%= viewHistoryDisplayContext.getStatusLabel(status) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:otherwise>
			</c:choose>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest"
			>
				<liferay-portlet:renderURL var="revertURL">
					<portlet:param name="mvcRenderCommandName" value="/change_tracking/undo_ct_collection" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
					<portlet:param name="revert" value="true" />
				</liferay-portlet:renderURL>

				<a class="btn btn-secondary btn-sm <%= (status != BackgroundTaskConstants.STATUS_SUCCESSFUL) ? "disabled" : StringPool.BLANK %>" href="<%= revertURL %>" type="button">
					<liferay-ui:message key="revert" />
				</a>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= viewHistoryDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
			searchContainer="<%= searchContainer %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>