<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/alloy_mvc/jsp/todo/views/init.jsp" %>

<aui:fieldset>
	<aui:field-wrapper name="todo-list">
		${todoList.name}
	</aui:field-wrapper>

	<aui:field-wrapper name="status">
		<liferay-ui:message key="${TodoItemConstantsMethods.getStatusLabel(todoItem.status)}" />
	</aui:field-wrapper>

	<aui:field-wrapper name="priority">
		<liferay-ui:message key="${TodoItemConstantsMethods.getPriorityLabel(todoItem.priority)}" />
	</aui:field-wrapper>

	<aui:field-wrapper name="description">
		${todoItem.description}
	</aui:field-wrapper>
</aui:fieldset>

<aui:button-row>
	<portlet:renderURL var="todoListURL">
		<portlet:param name="controller" value="todo_lists" />
		<portlet:param name="action" value="view" />
		<portlet:param name="id" value="${todoItem.todoListId}" />
	</portlet:renderURL>

	<aui:button href="${todoListURL}" icon="icon-arrow-left" value="back" />

	<portlet:renderURL var="editTodoItemURL">
		<portlet:param name="controller" value="todo_items" />
		<portlet:param name="action" value="edit" />
		<portlet:param name="id" value="${todoItem.todoItemId}" />
	</portlet:renderURL>

	<aui:button href="${editTodoItemURL}" icon="icon-pencil" value="edit" />
</aui:button-row>