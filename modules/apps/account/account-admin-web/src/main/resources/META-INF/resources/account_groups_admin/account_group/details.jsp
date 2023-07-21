<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

AccountGroupDisplay accountGroupDisplay = (AccountGroupDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_GROUP_DISPLAY);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((accountGroupDisplay.getAccountGroupId() == 0) ? LanguageUtil.get(request, "add-account-group") : LanguageUtil.format(request, "edit-x", accountGroupDisplay.getName(), false));
%>

<portlet:actionURL name="/account_admin/edit_account_group" var="editAccountGroupURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="accountGroupId" value="<%= String.valueOf(accountGroupDisplay.getAccountGroupId()) %>" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editAccountGroupURL %>"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (accountGroupDisplay.getAccountGroupId() == 0) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-frontend:edit-form-body>
		<h2 class="sheet-title">
			<%= LanguageUtil.get(request, "information") %>
		</h2>

		<aui:input label="account-group-name" name="name" required="<%= true %>" type="text" value="<%= accountGroupDisplay.getName() %>">
			<aui:validator name="maxLength"><%= ModelHintsUtil.getMaxLength(AccountGroup.class.getName(), "name") %></aui:validator>
		</aui:input>

		<aui:field-wrapper cssClass="form-group lfr-input-text-container">
			<aui:input name="description" type="textarea" value="<%= accountGroupDisplay.getDescription() %>" />
		</aui:field-wrapper>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= backURL %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>