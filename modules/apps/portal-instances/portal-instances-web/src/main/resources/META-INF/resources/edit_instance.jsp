<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

Company selCompany = (Company)request.getAttribute(WebKeys.SEL_COMPANY);

long companyId = BeanParamUtil.getLong(selCompany, request, "companyId");

VirtualHost virtualHost = null;

try {
	virtualHost = VirtualHostLocalServiceUtil.getVirtualHost(companyId, 0);
}
catch (Exception e) {
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((selCompany == null) ? LanguageUtil.get(request, "new-instance") : HtmlUtil.escape(selCompany.getName()));
%>

<portlet:actionURL name="/portal_instances/edit_instance" var="editInstanceURL" />

<aui:form action="<%= editInstanceURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveCompany();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="companyId" type="hidden" value="<%= companyId %>" />

	<liferay-ui:error exception="<%= CompanyMxException.class %>" message="please-enter-a-valid-mail-domain" />
	<liferay-ui:error exception="<%= CompanyVirtualHostException.class %>" message="please-enter-a-valid-virtual-host" />
	<liferay-ui:error exception="<%= CompanyWebIdException.class %>" message="please-enter-a-valid-web-id" />
	<liferay-ui:error exception="<%= UserScreenNameException.class %>" message="please-enter-a-valid-screen-name" />
	<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="please-enter-a-valid-email-address" />
	<liferay-ui:error exception="<%= UserPasswordException.class %>" message="please-enter-a-valid-password" />
	<liferay-ui:error exception="<%= ContactNameException.MustHaveFirstName.class %>" message="please-enter-a-valid-first-name" />
	<liferay-ui:error exception="<%= ContactNameException.MustHaveLastName.class %>" message="please-enter-a-valid-last-name" />
	<liferay-ui:error exception="<%= ContactNameException.MustHaveMiddleName.class %>" message="please-enter-a-valid-middle-name" />
	<liferay-ui:error exception="<%= ContactNameException.MustHaveValidFullName.class %>" message="please-enter-a-valid-first-middle-and-last-name" />

	<aui:model-context bean="<%= selCompany %>" model="<%= Company.class %>" />

	<aui:fieldset-group>
		<aui:fieldset>
			<c:choose>
				<c:when test="<%= selCompany != null %>">
					<aui:input name="id" type="resource" value="<%= String.valueOf(companyId) %>" />

					<aui:input name="web-id" type="resource" value="<%= selCompany.getWebId() %>" />
				</c:when>
				<c:otherwise>
					<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="webId">
						<aui:validator name="required" />
					</aui:input>
				</c:otherwise>
			</c:choose>

			<aui:input bean="<%= virtualHost %>" fieldParam="virtualHostname" label="virtual-host" model="<%= VirtualHost.class %>" name="hostname" />

			<aui:input label="mail-domain" name="mx" />

			<aui:input name="maxUsers" />

			<c:if test="<%= (selCompany == null) || (selCompany.getCompanyId() != PortalInstancesLocalServiceUtil.getDefaultCompanyId()) %>">
				<aui:input inlineLabel="right" labelCssClass="simple-toggle-switch" name="active" type="toggle-switch" value="<%= (selCompany != null) ? selCompany.isActive() : true %>" />
			</c:if>

			<c:if test="<%= Validator.isNull(PropsUtil.get(PropsKeys.DEFAULT_ADMIN_PASSWORD)) %>">
				<aui:input label="screen-name" name="defaultAdminScreenName" required="<%= true %>" type="text" />

				<aui:input label="email-address" name="defaultAdminEmailAddress" required="<%= true %>" type="text" />

				<aui:input label="password" name="defaultAdminPassword" required="<%= true %>" type="password" />

				<%
					FullNameDefinition fullNameDefinition = FullNameDefinitionFactory.getInstance(locale);
				%>

				<c:if test='<%= fullNameDefinition.isFieldRequired("first-name") %>'>
					<aui:input label="first-name" name="defaultAdminFirstName" required="<%= true %>" type="text" value="<%= PropsUtil.get(PropsKeys.DEFAULT_ADMIN_FIRST_NAME) %>" />
				</c:if>

				<c:if test='<%= fullNameDefinition.isFieldRequired("middle-name") %>'>
					<aui:input label="middle-name" name="defaultAdminMiddleName" required="<%= true %>" type="text" value="<%= PropsUtil.get(PropsKeys.DEFAULT_ADMIN_MIDDLE_NAME) %>" />
				</c:if>

				<c:if test='<%= fullNameDefinition.isFieldRequired("last-name") %>'>
					<aui:input label="last-name" name="defaultAdminLastName" required="<%= true %>" type="text" value="<%= PropsUtil.get(PropsKeys.DEFAULT_ADMIN_LAST_NAME) %>" />
				</c:if>
			</c:if>


		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCompany() {
		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			var cmd = form.querySelector(
				'#<portlet:namespace /><%= Constants.CMD %>'
			);

			if (cmd) {
				cmd.setAttribute(
					'value',
					'<%= (selCompany == null) ? Constants.ADD : Constants.UPDATE %>'
				);
			}

			submitForm(form);
		}
	}
</aui:script>