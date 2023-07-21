<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

WorkflowTask workflowTask = workflowTaskDisplayContext.getWorkflowTask();
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/portal_workflow_task/update_task" var="updateURL" />

<div class="task-action">
	<aui:form action="<%= updateURL %>" method="post" name="updateFm">
		<div class="modal-body task-action-content">
			<div class="modal-item-last">
				<aui:input name="workflowTaskId" type="hidden" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />

				<aui:input bean="<%= workflowTask %>" ignoreRequestValue="<%= true %>" model="<%= WorkflowTask.class %>" name="dueDate" required="<%= true %>" />

				<aui:input cols="55" cssClass="task-content-comment" name="comment" placeholder="comment" rows="1" type="textarea" />
			</div>
		</div>

		<div class="modal-footer">
			<div class="modal-item-last">
				<div class="btn-group">
					<div class="btn-group-item">
						<aui:button name="close" type="cancel" />
					</div>

					<div class="btn-group-item">
						<aui:button name="done" primary="<%= true %>" value="done" />
					</div>
				</div>
			</div>
		</div>
	</aui:form>
</div>

<aui:script use="aui-base">
	var maxLength = Liferay.AUI.getDateFormat().replace(/%[mdY]/gm, '').length + 8;

	A.all('#<portlet:namespace />dueDate').set('maxLength', maxLength);

	var done = A.one('#<portlet:namespace />done');

	if (done) {
		done.on('click', function (event) {
			var data = new FormData(
				document.querySelector('#<portlet:namespace />updateFm')
			);

			Liferay.Util.fetch('<%= updateURL.toString() %>', {
				body: data,
				method: 'POST',
			}).then(function () {
				Liferay.Util.getOpener().<portlet:namespace />refreshPortlet(
					'<%= PortalUtil.escapeRedirect(redirect.toString()) %>'
				);
				Liferay.Util.getWindow(
					'<portlet:namespace />updateDialog'
				).destroy();
			});
		});
	}
</aui:script>