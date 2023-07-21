<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/alloy_mvc/jsp/todo/views/init.jsp" %>

<aui:fieldset>
	<aui:field-wrapper name="name">
		${todoList.name}
	</aui:field-wrapper>
</aui:fieldset>

<aui:button-row>
	<portlet:renderURL var="todoListsURL">
		<portlet:param name="controller" value="todo_lists" />
		<portlet:param name="action" value="index" />
	</portlet:renderURL>

	<aui:button href="${todoListsURL}" icon="icon-arrow-left" value="back" />

	<portlet:renderURL var="editTodoListURL">
		<portlet:param name="controller" value="todo_lists" />
		<portlet:param name="action" value="edit" />
		<portlet:param name="id" value="${todoList.todoListId}" />
	</portlet:renderURL>

	<aui:button href="${editTodoListURL}" icon="icon-pencil" value="edit" />

	<portlet:renderURL var="createTodoItemURL">
		<portlet:param name="controller" value="todo_items" />
		<portlet:param name="action" value="create" />
		<portlet:param name="todoListId" value="${todoList.todoListId}" />
	</portlet:renderURL>

	<aui:button href="${createTodoItemURL}" icon="icon-plus" value="create-todo-item" />
</aui:button-row>

<liferay-ui:search-container
	emptyResultsMessage="there-are-no-items-for-this-list"
	iteratorURL="${portletURL}"
	orderByCol="${todoItemsOrderByCol}"
	orderByColParam="todoItemsOrderByCol"
	orderByType="${todoItemsOrderByType}"
	orderByTypeParam="todoItemsOrderByType"
>
	<liferay-ui:search-container-results
		results="${todoItems}"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.alloy.mvc.sample.model.TodoItem"
		escapedModel="${true}"
		keyProperty="todoItemId"
		modelVar="todoItem"
	>
		<portlet:renderURL var="viewTodoItemURL">
			<portlet:param name="controller" value="todo_items" />
			<portlet:param name="action" value="view" />
			<portlet:param name="id" value="${todoItem.todoItemId}" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="${viewTodoItemURL}"
			name="priority"
			orderable="${true}"
			orderableProperty="priority"
			translate="${true}"
			value="${TodoItemConstantsMethods.getPriorityLabel(todoItem.priority)}"
		/>

		<liferay-ui:search-container-column-text
			href="${viewTodoItemURL}"
			name="description"
			orderable="${true}"
			orderableProperty="priority"
			value="${todoItem.description}"
		/>

		<liferay-ui:search-container-column-text
			href="${viewTodoItemURL}"
			name="status"
			orderable="${true}"
			orderableProperty="status"
			translate="${true}"
			value="${TodoItemConstantsMethods.getStatusLabel(todoItem.status)}"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>