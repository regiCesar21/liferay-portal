<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<div class="portlet-configuration-body-content">
		<div class="container-fluid-1280">
			<aui:fieldset>
				<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>" cssClass="lfr-input-text-container" label="url" name="preferences--initUrl--" value="<%= initUrl %>" />

				<aui:input cssClass="lfr-input-text-container" label='<%= LanguageUtil.get(request, "scope") + " (" + LanguageUtil.get(request, "regex") + ")" %>' name="preferences--scope--" value="<%= scope %>" />

				<aui:input cssClass="lfr-input-text-container" name="preferences--proxyHost--" value="<%= proxyHost %>" />

				<aui:input cssClass="lfr-input-text-container" name="preferences--proxyPort--" value="<%= proxyPort %>" />

				<aui:select name="preferences--proxyAuthentication--" value="<%= proxyAuthentication %>">
					<aui:option label="none" />
					<aui:option label="basic" />
					<aui:option label="ntlm" />
				</aui:select>

				<aui:input cssClass="lfr-input-text-container" name="preferences--proxyAuthenticationUsername--" value="<%= proxyAuthenticationUsername %>" />

				<aui:input cssClass="lfr-input-text-container" name="preferences--proxyAuthenticationPassword--" type="password" value="<%= proxyAuthenticationPassword %>" />

				<aui:input cssClass="lfr-input-text-container" name="preferences--proxyAuthenticationHost--" value="<%= proxyAuthenticationHost %>" />

				<aui:input cssClass="lfr-input-text-container" name="preferences--proxyAuthenticationDomain--" value="<%= proxyAuthenticationDomain %>" />

				<aui:input cssClass="lfr-textarea-container" name="preferences--stylesheet--" onKeyDown="Liferay.Util.checkTab(this); Liferay.Util.disableEsc();" type="textarea" value="<%= stylesheet %>" wrap="soft" />
			</aui:fieldset>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>