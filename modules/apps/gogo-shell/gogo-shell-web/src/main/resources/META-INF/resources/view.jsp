<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<portlet:actionURL name="executeCommand" var="executeCommandURL" />

<portlet:renderURL var="redirect" />

<clay:container-fluid>
	<aui:form action="<%= executeCommandURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "executeCommand();" %>'>
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

		<liferay-ui:error exception="<%= CaptchaConfigurationException.class %>" message="a-captcha-error-occurred-please-contact-an-administrator" />
		<liferay-ui:error exception="<%= CaptchaException.class %>" message="captcha-verification-failed" />
		<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />

		<liferay-ui:error key="gogo">

			<%
			Exception e = (Exception)errorException;
			%>

			<%= HtmlUtil.escape(e.getMessage()) %>
		</liferay-ui:error>

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<clay:alert
					displayType="info"
					message='<%= LanguageUtil.get(request, "command-will-only-be-executed-on-this-node") %>'
				/>

				<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>" name="command" prefix='<%= (String)SessionMessages.get(renderRequest, "prompt") %>' value='<%= (String)SessionMessages.get(renderRequest, "command") %>' />

				<liferay-captcha:captcha />
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button primary="<%= true %>" type="submit" value="execute" />
		</aui:button-row>

		<%
		String commandOutput = (String)SessionMessages.get(renderRequest, "commandOutput");
		%>

		<c:if test="<%= Validator.isNotNull(commandOutput) %>">
			<b><liferay-ui:message key="output" /></b>

			<pre><%= HtmlUtil.escape(commandOutput) %></pre>
		</c:if>
	</aui:form>
</clay:container-fluid>

<aui:script>
	function <portlet:namespace />executeCommand() {
		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			submitForm(form);
		}
	}
</aui:script>