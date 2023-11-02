<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs = ParamUtil.getString(request, "tabs", "common-license-keys");
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(tabs.equals("common-license-keys"));
						navigationItem.setHref(renderResponse.createRenderURL(), "tabs", "common-license-keys");
						navigationItem.setLabel(LanguageUtil.get(request, "common-license-keys"));
					});
				add(
					navigationItem -> {
						navigationItem.setActive(tabs.equals("messageQueue"));
						navigationItem.setHref(renderResponse.createRenderURL(), "tabs", "messageQueue");
						navigationItem.setLabel(LanguageUtil.get(request, "message-queue"));
					});
			}
		}
	%>'
/>

<div class="container-fluid home">
	<c:choose>
		<c:when test='<%= tabs.equals("common-license-keys") %>'>
			<liferay-util:include page="/admin/view_common_license_keys.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:otherwise>
			<portlet:actionURL name="/admin/debug_message_queue" var="debugMessageQueueURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:actionURL>

			<aui:form action="<%= debugMessageQueueURL %>" cssClass="container-fluid container-fluid-max-xl" method="post">
				<aui:fieldset-group>
					<aui:fieldset>
						<aui:input name="routingKey" type="text" value="ebenezer-support-opportunity-entries" />

						<aui:input name="message" type="textarea" />

						<aui:input name="properties" type="textarea" />

						<aui:button type="submit" value="submit" />
					</aui:fieldset>
				</aui:fieldset-group>
			</aui:form>
		</c:otherwise>
	</c:choose>
</div>