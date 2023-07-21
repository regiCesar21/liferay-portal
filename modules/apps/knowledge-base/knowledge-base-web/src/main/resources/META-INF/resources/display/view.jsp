<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/display/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

KBNavigationDisplayContext kbNavigationDisplayContext = new KBNavigationDisplayContext(renderRequest, portalPreferences, kbDisplayPortletInstanceConfiguration, kbArticle);

request.setAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_NAVIGATION_DISPLAY_CONTEXT, kbNavigationDisplayContext);
%>

<c:choose>
	<c:when test="<%= kbArticle != null %>">
		<clay:row>
			<c:if test="<%= kbNavigationDisplayContext.isLeftNavigationVisible() %>">
				<clay:col
					md="3"
				>
					<liferay-util:include page="/display/view_navigation.jsp" servletContext="<%= application %>" />
				</clay:col>
			</c:if>

			<clay:col
				md='<%= kbNavigationDisplayContext.isLeftNavigationVisible() ? "9" : "12" %>'
			>
				<c:if test="<%= kbNavigationDisplayContext.isTopNavigationVisible() %>">
					<div class="kbarticle-navigation">
						<liferay-util:include page="/display/content_root_selector.jsp" servletContext="<%= application %>" />
					</div>
				</c:if>

				<liferay-util:include page="/display/view_article.jsp" servletContext="<%= application %>" />
			</clay:col>
		</clay:row>
	</c:when>
	<c:otherwise>

		<%
		renderRequest.setAttribute(KBWebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		%>

		<div class="alert alert-info portlet-configuration">
			<aui:a href="<%= portletDisplay.getURLConfiguration() %>" label="please-configure-this-portlet-to-make-it-visible-to-all-users" onClick="<%= portletDisplay.getURLConfigurationJS() %>" />
		</div>
	</c:otherwise>
</c:choose>