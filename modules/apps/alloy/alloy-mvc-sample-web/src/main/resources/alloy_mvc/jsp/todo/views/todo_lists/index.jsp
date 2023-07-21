<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/alloy_mvc/jsp/todo/views/init.jsp" %>

<aui:button-row>
	<portlet:renderURL var="createTodoListURL">
		<portlet:param name="controller" value="todo_lists" />
		<portlet:param name="action" value="create" />
	</portlet:renderURL>

	<aui:button href="${createTodoListURL}" icon="icon-plus" value="create-todo-list" />
</aui:button-row>

<liferay-ui:search-container
	emptyResultsMessage="there-are-no-todo-lists"
	iteratorURL="${portletURL}"
	orderByCol="${orderByCol}"
	orderByType="${orderByType}"
>
	<liferay-ui:search-container-results
		results="${todoLists}"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.alloy.mvc.sample.model.TodoList"
		escapedModel="${true}"
		keyProperty="todoListId"
		modelVar="todoList"
	>
		<portlet:renderURL var="viewTodoListURL">
			<portlet:param name="controller" value="todo_lists" />
			<portlet:param name="action" value="view" />
			<portlet:param name="id" value="${todoList.todoListId}" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="${viewTodoListURL}"
			orderable="${true}"
			property="name"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>