<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DispatchLogDisplayContext dispatchLogDisplayContext = (DispatchLogDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

DispatchLog dispatchLog = dispatchLogDisplayContext.getDispatchLog();
%>

<portlet:actionURL name="/dispatch/edit_dispatch_log" var="editDispatchLogActionURL" />

<div class="container-fluid-1280 sheet">
	<aui:form action="<%= editDispatchLogActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="dispatchLogId" type="hidden" value="<%= String.valueOf(dispatchLog.getDispatchLogId()) %>" />

		<div class="lfr-form-content">
			<aui:fieldset>
				<aui:input disabled="<%= true %>" label="start-date" name="startDate" value="<%= dispatchLogDisplayContext.getDateString(dispatchLog.getStartDate()) %>" />

				<%
				DispatchTaskStatus dispatchTaskStatus = DispatchTaskStatus.valueOf(dispatchLog.getStatus());
				%>

				<aui:input disabled="<%= true %>" name="status" value="<%= LanguageUtil.get(request, dispatchTaskStatus.getLabel()) %>" />

				<aui:input disabled="<%= true %>" label="runtime" name="runTime" value='<%= dispatchLogDisplayContext.getExecutionTimeMills() + " ms" %>' />

				<aui:input disabled="<%= true %>" label="error" name="error" type="textarea" value="<%= dispatchLog.getError() %>" />

				<aui:input disabled="<%= true %>" label="output" name="output" type="textarea" value="<%= dispatchLog.getOutput() %>" />
			</aui:fieldset>

			<aui:button-row>
				<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" />
			</aui:button-row>
		</div>
	</aui:form>
</div>