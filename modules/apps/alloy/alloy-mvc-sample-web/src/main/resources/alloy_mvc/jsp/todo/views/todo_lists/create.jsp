<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/alloy_mvc/jsp/todo/views/init.jsp" %>

<aui:model-context bean="${todoList}" model="<%= TodoList.class %>" />

<portlet:actionURL var="addTodoListURL">
	<portlet:param name="controller" value="todo_lists" />
	<portlet:param name="action" value="add" />
</portlet:actionURL>

<aui:form action="${addTodoListURL}" method="post">
	<portlet:renderURL var="todoListsURL">
		<portlet:param name="controller" value="todo_lists" />
		<portlet:param name="action" value="index" />
	</portlet:renderURL>

	<aui:input name="redirect" type="hidden" value="${todoListsURL}" />

	<aui:input name="name" />

	<aui:button-row>
		<aui:button icon="icon-plus" type="submit" value="add" />

		<aui:button href="${todoListsURL}" icon="icon-remove" value="cancel" />
	</aui:button-row>
</aui:form>