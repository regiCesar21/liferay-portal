<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ page import="com.liferay.alloy.mvc.AlloyController" %><%@
page import="com.liferay.alloy.mvc.AlloyException" %><%@
page import="com.liferay.alloy.mvc.AlloyServiceInvoker" %><%@
page import="com.liferay.alloy.mvc.sample.model.TodoItem" %><%@
page import="com.liferay.alloy.mvc.sample.model.TodoList" %><%@
page import="com.liferay.alloy.mvc.sample.model.impl.TodoListModelImpl" %><%@
page import="com.liferay.alloy.mvc.sample.service.TodoItemLocalServiceUtil" %><%@
page import="com.liferay.alloy.mvc.sample.service.TodoListLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.util.OrderByComparator" %><%@
page import="com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<%@ page import="java.util.List" %>

<%@ include file="/alloy_mvc/jsp/util/todo_item_constants.jspf" %>