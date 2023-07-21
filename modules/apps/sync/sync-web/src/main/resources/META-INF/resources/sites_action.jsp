<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Group group = (Group)row.getObject();

String groupId = String.valueOf(group.getGroupId());
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:choose>
		<c:when test='<%= GetterUtil.getBoolean(group.getTypeSettingsProperty("syncEnabled"), !group.isCompany()) %>'>
			<liferay-ui:icon
				label="<%= true %>"
				message="default-file-permissions"
				url='<%= "javascript:" + liferayPortletResponse.getNamespace() + "editDefaultFilePermissions(" + groupId + ");" %>'
			/>

			<portlet:actionURL name="updateSites" var="disableSiteURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="enabled" value="<%= Boolean.FALSE.toString() %>" />
				<portlet:param name="groupIds" value="<%= groupId %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete
				confirmation="disabling-a-sync-site-will-delete-all-associated-files-from-all-clients"
				label="<%= true %>"
				message="disable-sync-site"
				url="<%= disableSiteURL %>"
			/>
		</c:when>
		<c:otherwise>
			<portlet:actionURL name="updateSites" var="enableSiteURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="enabled" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="groupIds" value="<%= groupId %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				label="<%= true %>"
				message="enable-sync-site"
				url="<%= enableSiteURL %>"
			/>
		</c:otherwise>
	</c:choose>
</liferay-ui:icon-menu>

<aui:script>
	function <portlet:namespace />editDefaultFilePermissions(groupId) {

		<%
		String selectEventName = liferayPortletResponse.getNamespace() + "itemSelected";
		%>

		<portlet:renderURL var="editDefaultFilePermissionsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="groupIds" value="{groupId}" />
			<portlet:param name="mvcPath" value="/edit_default_file_permissions.jsp" />
			<portlet:param name="selectEventName" value="<%= selectEventName %>" />
		</portlet:renderURL>

		var url = Liferay.Util.sub(
			decodeURIComponent('<%= editDefaultFilePermissionsURL %>'),
			{
				groupId: groupId,
			}
		);

		Liferay.Util.openSelectionModal({
			id: '<portlet:namespace />editDefaultFilePermissionsDialog',
			onSelect: function (selectedItem) {
				Liferay.Util.fetch(selectedItem.uri, {method: 'POST'})
					.then(function (response) {
						return response.text();
					})
					.then(function () {
						Liferay.Portlet.refresh('#p_p_id<portlet:namespace />');
					})
					.catch(function (error) {
						Liferay.Util.openToast({
							message: Liferay.Language.get(
								'an-unexpected-system-error-occurred'
							),
							type: 'danger',
						});
					});
			},
			selectEventName: '<%= selectEventName %>',
			title: '<liferay-ui:message key="default-file-permissions" />',
			url: url,
		});
	}
</aui:script>