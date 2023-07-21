<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.dispatch.constants.DispatchConstants" %><%@
page import="com.liferay.dispatch.constants.DispatchWebKeys" %><%@
page import="com.liferay.dispatch.exception.NoSuchLogException" %><%@
page import="com.liferay.dispatch.exception.NoSuchTriggerException" %><%@
page import="com.liferay.dispatch.executor.DispatchTaskClusterMode" %><%@
page import="com.liferay.dispatch.executor.DispatchTaskStatus" %><%@
page import="com.liferay.dispatch.model.DispatchLog" %><%@
page import="com.liferay.dispatch.model.DispatchTrigger" %><%@
page import="com.liferay.dispatch.web.internal.display.context.DispatchLogDisplayContext" %><%@
page import="com.liferay.dispatch.web.internal.display.context.DispatchTriggerDisplayContext" %><%@
page import="com.liferay.dispatch.web.internal.display.context.SchedulerResponseDisplayContext" %><%@
page import="com.liferay.dispatch.web.internal.security.permisison.resource.DispatchTriggerPermission" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.cluster.ClusterExecutorUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.scheduler.TriggerState" %><%@
page import="com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="java.util.Calendar" %><%@
page import="java.util.Date" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);
%>