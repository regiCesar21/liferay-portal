<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SegmentsDisplayContext segmentsDisplayContext = (SegmentsDisplayContext)request.getAttribute(SegmentsWebKeys.SEGMENTS_DISPLAY_CONTEXT);
String[] excludedRoleNames = (String[])request.getAttribute(SegmentsWebKeys.EXCLUDED_ROLE_NAMES);
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
String eventName = (String)request.getAttribute("view.jsp-eventName");

SegmentsEntry segmentsEntry = (SegmentsEntry)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= SegmentsEntryPermission.contains(permissionChecker, segmentsEntry, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="/segments/edit_segments_entry" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="segmentsEntryId" value="<%= String.valueOf(segmentsEntry.getSegmentsEntryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= SegmentsEntryPermission.contains(permissionChecker, segmentsEntry, ActionKeys.VIEW) %>">
		<portlet:renderURL var="previewMembersURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="/segments/preview_segments_entry_users" />
			<portlet:param name="segmentsEntryId" value="<%= String.valueOf(segmentsEntry.getSegmentsEntryId()) %>" />
			<portlet:param name="clearSessionCriteria" value="<%= Boolean.TRUE.toString() %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="view-members"
			url="<%= previewMembersURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<%
	Group group = GroupLocalServiceUtil.getGroup(segmentsEntry.getGroupId());
	%>

	<c:if test="<%= !group.isCompany() && SegmentsEntryPermission.contains(permissionChecker, segmentsEntry, ActionKeys.ASSIGN_USER_ROLES) %>">

		<%
		ItemSelector itemSelector = (ItemSelector)request.getAttribute(SegmentsWebKeys.ITEM_SELECTOR);

		RoleItemSelectorCriterion roleItemSelectorCriterion = new RoleItemSelectorCriterion(RoleConstants.TYPE_SITE);

		roleItemSelectorCriterion.setCheckedRoleIds(segmentsEntry.getRoleIds());
		roleItemSelectorCriterion.setDesiredItemSelectorReturnTypes(new UUIDItemSelectorReturnType());
		roleItemSelectorCriterion.setExcludedRoleNames(excludedRoleNames);

		PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(RequestBackedPortletURLFactoryUtil.create(renderRequest), eventName, roleItemSelectorCriterion);
		%>

		<liferay-ui:icon
			cssClass='<%= segmentsDisplayContext.isRoleSegmentationEnabled() ? "" : "action disabled" %>'
			data='<%=
				HashMapBuilder.<String, Object>put(
					"itemSelectorURL", itemSelectorURL.toString()
				).put(
					"segmentsEntryId", segmentsEntry.getSegmentsEntryId()
				).build()
			%>'
			linkCssClass="assign-site-roles-link"
			message="assign-site-roles"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= SegmentsEntryPermission.contains(permissionChecker, segmentsEntry, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= SegmentsEntry.class.getName() %>"
			modelResourceDescription="<%= segmentsEntry.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(segmentsEntry.getSegmentsEntryId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= SegmentsEntryPermission.contains(permissionChecker, segmentsEntry, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/segments/delete_segments_entry" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="segmentsEntryId" value="<%= String.valueOf(segmentsEntry.getSegmentsEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>