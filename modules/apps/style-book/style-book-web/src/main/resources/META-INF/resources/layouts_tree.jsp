<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-util:buffer
	var="linkTemplate"
>
	<button class="{cssClass} btn btn-unstyled" data-label="{label}" data-url="{url}" id="{id}" title="{title}">
		{label}
	</button>
</liferay-util:buffer>

<%
Group group = StagingUtil.getStagingGroup(themeDisplay.getSiteGroupId());
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
%>

<liferay-layout:layouts-tree
	draggableTree="<%= false %>"
	groupId="<%= group.getGroupId() %>"
	linkTemplate="<%= linkTemplate %>"
	privateLayout="<%= privateLayout %>"
	rootLinkTemplate='<span class="{cssClass}" id="{id}" title="{title}">{label}</span>'
	rootNodeName="<%= group.getLayoutRootNodeName(privateLayout, locale) %>"
	treeId="layoutsTree"
/>