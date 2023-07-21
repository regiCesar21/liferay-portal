<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DispatchTriggerDisplayContext dispatchTriggerDisplayContext = (DispatchTriggerDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL portletURL = dispatchTriggerDisplayContext.getPortletURL();

portletURL.setParameter("tabs1", "dispatch-trigger");
portletURL.setParameter("searchContainerId", "dispatchTriggers");
%>

<clay:navigation-bar
	navigationItems="<%= dispatchTriggerDisplayContext.getNavigationItems() %>"
/>

<liferay-util:include page="/dispatch_trigger_toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="dispatchTriggers" />
</liferay-util:include>

<div id="<portlet:namespace />dispatchTriggerContainer">
	<div class="closed container" id="<portlet:namespace />infoPanelId">
		<aui:form action="<%= portletURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
			<aui:input name="deleteDispatchTriggerIds" type="hidden" />

			<liferay-ui:search-container
				id="dispatchTriggers"
				searchContainer="<%= dispatchTriggerDisplayContext.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.dispatch.model.DispatchTrigger"
					keyProperty="dispatchTriggerId"
					modelVar="dispatchTrigger"
				>

					<%
					PortletURL rowURL = renderResponse.createRenderURL();

					rowURL.setParameter("mvcRenderCommandName", "/dispatch/edit_dispatch_trigger");
					rowURL.setParameter("redirect", currentURL);
					rowURL.setParameter("dispatchTriggerId", String.valueOf(dispatchTrigger.getDispatchTriggerId()));
					%>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/dispatch_trigger_action.jsp"
					/>

					<liferay-ui:search-container-column-text
						cssClass="important table-cell-expand"
						href="<%= rowURL %>"
						property="name"
					/>

					<liferay-ui:search-container-column-text
						name="task-executor-type"
						value="<%= dispatchTriggerDisplayContext.getDispatchTaskExecutorName(dispatchTrigger.getDispatchTaskExecutorType(), locale) %>"
					/>

					<liferay-ui:search-container-column-text
						name="system"
						value='<%= dispatchTrigger.isSystem() ? LanguageUtil.get(request, "yes") : LanguageUtil.get(request, "no") %>'
					/>

					<liferay-ui:search-container-column-date
						name="create-date"
						property="createDate"
					/>

					<liferay-ui:search-container-column-text
						name="next-fire-date"
						value="<%= dispatchTriggerDisplayContext.getNextFireDateString(dispatchTrigger.getDispatchTriggerId()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="important table-cell-ws-nowrap"
						name="status"
					>

						<%
						DispatchTaskStatus dispatchTaskStatus = dispatchTrigger.getDispatchTaskStatus();
						%>

						<h6 class="status-row-<%= row.getRowId() %> background-task-status-row background-task-status-<%= dispatchTaskStatus.getLabel() %> <%= dispatchTaskStatus.getCssClass() %>">
							<liferay-ui:message key="<%= dispatchTaskStatus.getLabel() %>" />
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						cssClass="table-cell-ws-nowrap"
						path="/trigger/buttons.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="list"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>
</div>