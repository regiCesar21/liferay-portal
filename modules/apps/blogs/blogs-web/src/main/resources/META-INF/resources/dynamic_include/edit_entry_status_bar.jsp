<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<%
BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

BlogsPortletInstanceConfiguration blogsPortletInstanceConfiguration = BlogsPortletInstanceConfigurationUtil.getBlogsPortletInstanceConfiguration(themeDisplay);
%>

<liferay-frontend:info-bar>
	<c:choose>
		<c:when test="<%= entry != null %>">
			<small class="text-capitalize text-muted" id="<portlet:namespace />saveStatus">
				<aui:workflow-status markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= entry.getStatus() %>" />

				<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - entry.getStatusDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
			</small>
		</c:when>
		<c:otherwise>
			<small class="text-capitalize text-muted" id="<portlet:namespace />saveStatus"></small>
		</c:otherwise>
	</c:choose>

	<c:if test="<%= blogsPortletInstanceConfiguration.enableReadingTime() %>">
		<small class="reading-time-wrapper text-muted">
			<liferay-reading-time:reading-time
				displayStyle="descriptive"
				id="readingTime"
				model="<%= entry %>"
			/>
		</small>
	</c:if>
</liferay-frontend:info-bar>