<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DispatchLogDisplayContext dispatchLogDisplayContext = (DispatchLogDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

DispatchTrigger dispatchTrigger = dispatchLogDisplayContext.getDispatchTrigger();

PortletURL portletURL = dispatchLogDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "dispatchLogs");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-util:include page="/dispatch_log_toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="dispatchLogs" />
</liferay-util:include>

<div id="<portlet:namespace />triggerLogsContainer">
	<div class="closed container-fluid-1280" id="<portlet:namespace />infoPanelId">
		<aui:form action="<%= portletURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
			<aui:input name="deleteDispatchLogIds" type="hidden" />

			<div class="trigger-lists-container" id="<portlet:namespace />entriesContainer">
				<liferay-ui:search-container
					id="dispatchLogs"
					searchContainer="<%= dispatchLogDisplayContext.getSearchContainer() %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.dispatch.model.DispatchLog"
						cssClass="entry-display-style"
						keyProperty="dispatchLogId"
						modelVar="dispatchLog"
					>

						<%
						PortletURL rowURL = renderResponse.createRenderURL();

						rowURL.setParameter("mvcRenderCommandName", "/dispatch/view_dispatch_log");
						rowURL.setParameter("redirect", currentURL);
						rowURL.setParameter("dispatchLogId", String.valueOf(dispatchLog.getDispatchLogId()));
						%>

						<liferay-ui:search-container-column-text
							cssClass="important table-cell-content"
							href="<%= rowURL %>"
							name="start-date"
						>
							<%= dispatchLogDisplayContext.getDateString(dispatchLog.getStartDate()) %>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-content"
							name="runtime"
						>
							<%= (dispatchLog.getEndDate() == null) ? StringPool.DASH : String.valueOf(dispatchLog.getEndDate().getTime() - dispatchLog.getStartDate().getTime()) + " ms" %>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-content"
							name="trigger"
							value="<%= HtmlUtil.escape(dispatchTrigger.getName()) %>"
						/>

						<liferay-ui:search-container-column-text
							name="status"
						>

							<%
							DispatchTaskStatus dispatchTaskStatus = DispatchTaskStatus.valueOf(dispatchLog.getStatus());
							%>

							<h6 class="background-task-status-row background-task-status-<%= dispatchTaskStatus.getLabel() %> <%= dispatchTaskStatus.getCssClass() %>">
								<liferay-ui:message key="<%= dispatchTaskStatus.getLabel() %>" />
							</h6>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-jsp
							cssClass="entry-action-column"
							path="/dispatch_log_action.jsp"
						/>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="list"
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</div>
		</aui:form>
	</div>
</div>