<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

RemoteAppEntry remoteAppEntry = (RemoteAppEntry)request.getAttribute(RemoteAppAdminWebKeys.REMOTE_APP_ENTRY);

long remoteAppEntryId = BeanParamUtil.getLong(remoteAppEntry, request, "remoteAppEntryId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((remoteAppEntry == null) ? LanguageUtil.get(request, "new-remote-app") : remoteAppEntry.getName(locale));
%>

<portlet:actionURL name="/remote_app_admin/edit_remote_app_entry" var="editRemoteAppEntryURL" />

<clay:container-fluid>
	<aui:form action="<%= editRemoteAppEntryURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveRemoteAppEntry();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="remoteAppEntryId" type="hidden" value="<%= remoteAppEntryId %>" />

		<liferay-ui:error exception="<%= DuplicateRemoteAppEntryURLException.class %>" message="please-enter-a-unique-remote-app-url" />

		<aui:model-context bean="<%= remoteAppEntry %>" model="<%= RemoteAppEntry.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:field-wrapper label="name">
					<liferay-ui:input-localized
						autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
						name="name"
						xml='<%= BeanPropertiesUtil.getString(remoteAppEntry, "name") %>'
					/>
				</aui:field-wrapper>

				<aui:input name="url">
					<aui:validator name="url" />
				</aui:input>
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</clay:container-fluid>

<aui:script>
	function <portlet:namespace />saveRemoteAppEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value =
			'<%= (remoteAppEntry == null) ? Constants.ADD : Constants.UPDATE %>';

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>