<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/alloy_mvc/jsp/todo/views/init.jsp" %>

<aui:model-context bean="${todoList}" model="<%= TodoList.class %>" />

<portlet:actionURL var="updateTodoListURL">
	<portlet:param name="controller" value="todo_lists" />
	<portlet:param name="action" value="update" />
</portlet:actionURL>

<aui:form action="${updateTodoListURL}" method="post">
	<portlet:renderURL var="viewTodoListURL">
		<portlet:param name="controller" value="todo_lists" />
		<portlet:param name="action" value="view" />
		<portlet:param name="id" value="${todoList.todoListId}" />
	</portlet:renderURL>

	<aui:input name="redirect" type="hidden" value="${viewTodoListURL}" />
	<aui:input name="id" type="hidden" value="${todoList.todoListId}" />

	<aui:input name="name" />

	<aui:button-row>
		<portlet:renderURL var="todoListsURL">
			<portlet:param name="controller" value="todo_lists" />
			<portlet:param name="action" value="index" />
		</portlet:renderURL>

		<portlet:actionURL var="deleteTodoListURL">
			<portlet:param name="controller" value="todo_lists" />
			<portlet:param name="action" value="delete" />
			<portlet:param name="id" value="${todoList.todoListId}" />
			<portlet:param name="redirect" value="${todoListsURL}" />
		</portlet:actionURL>

		<aui:button href="${deleteTodoListURL}" icon="icon-trash" value="delete" />

		<aui:button icon="icon-ok" type="submit" value="update" />

		<aui:button href="${viewTodoListURL}" icon="icon-remove" value="cancel" />
	</aui:button-row>
</aui:form>