<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/alloy_mvc/jsp/todo/views/init.jsp" %>

<aui:model-context bean="${todoItem}" model="<%= TodoItem.class %>" />

<portlet:actionURL var="addTodoItemURL">
	<portlet:param name="controller" value="todo_items" />
	<portlet:param name="action" value="add" />
	<portlet:param name="todoListId" value="${param.todoListId}" />
</portlet:actionURL>

<aui:form action="${addTodoItemURL}" method="post">
	<portlet:renderURL var="todoListURL">
		<portlet:param name="controller" value="todo_lists" />
		<portlet:param name="action" value="view" />
		<portlet:param name="id" value="${param.todoListId}" />
	</portlet:renderURL>

	<aui:input name="redirect" type="hidden" value="${todoListURL}" />

	<aui:select label="priority" name="priority">
		<aui:option label="${TodoItemConstants.LABEL_LOW}" selected="${true}" value="${TodoItemConstants.PRIORITY_LOW}" />
		<aui:option label="${TodoItemConstants.LABEL_MODERATE}" value="${TodoItemConstants.PRIORITY_MODERATE}" />
		<aui:option label="${TodoItemConstants.LABEL_HIGH}" value="${TodoItemConstants.PRIORITY_HIGH}" />
	</aui:select>

	<aui:input name="description" />

	<aui:button-row>
		<aui:button icon="icon-plus" type="submit" value="add" />

		<aui:button href="${todoListURL}" icon="icon-remove" value="cancel" />
	</aui:button-row>
</aui:form>