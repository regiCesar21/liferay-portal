<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DispatchTriggerDisplayContext dispatchTriggerDisplayContext = (DispatchTriggerDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId='<%= ParamUtil.getString(request, "searchContainerId", "dispatchTrigger") %>'
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= dispatchTriggerDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<liferay-portlet:renderURL var="addDispatchTriggerURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
			<portlet:param name="mvcRenderCommandName" value="/dispatch/edit_dispatch_trigger" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:renderURL>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>

			<%
			for (String dispatchTaskExecutorType : dispatchTriggerDisplayContext.getDispatchTaskExecutorTypes()) {
			%>

				<liferay-portlet:renderURL var="addDispatchTriggerURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
					<portlet:param name="mvcRenderCommandName" value="/dispatch/edit_dispatch_trigger" />
					<portlet:param name="backURL" value="<%= currentURL %>" />
					<portlet:param name="dispatchTaskExecutorType" value="<%= dispatchTaskExecutorType %>" />
				</liferay-portlet:renderURL>

				<liferay-frontend:add-menu-item
					title="<%= dispatchTriggerDisplayContext.getDispatchTaskExecutorName(dispatchTaskExecutorType, locale) %>"
					url="<%= addDispatchTriggerURL %>"
				/>

			<%
			}
			%>

		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= dispatchTriggerDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteDispatchTrigger();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:script>
	function <portlet:namespace />deleteDispatchTrigger() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-trigger" />'
			)
		) {
			var form = window.document['<portlet:namespace />fm'];

			form.setAttribute('method', 'post');
			form['<portlet:namespace /><%= Constants.CMD %>'].value =
				'<%= Constants.DELETE %>';
			form[
				'<portlet:namespace />deleteDispatchTriggerIds'
			].value = Liferay.Util.listCheckedExcept(
				form,
				'<portlet:namespace />allRowIds'
			);

			submitForm(
				form,
				'<portlet:actionURL name="/dispatch/edit_dispatch_trigger" />'
			);
		}
	}
</aui:script>