<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/alloy_mvc/jsp/todo/views/init.jsp" %>

<c:choose>
	<c:when test="${fn:length(pattern) > 0}">
		<liferay-ui:message arguments="${arguments}" key="${pattern}" />
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="an-unexpected-error-occurred" />
	</c:otherwise>
</c:choose>