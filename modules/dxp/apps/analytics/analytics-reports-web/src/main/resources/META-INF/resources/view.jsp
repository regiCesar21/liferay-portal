<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String analyticsReportsPanelState = SessionClicks.get(request, "com.liferay.analytics.reports.web_panelState", "closed");
%>

<div class="lfr-analytics-reports-sidebar" id="analyticsReportsSidebar">
	<div class="sidebar-header">
		<clay:content-row
			cssClass="sidebar-section"
		>
			<clay:content-col
				expand="<%= true %>"
			>
				<h1 class="sr-only"><liferay-ui:message key="content-performance-panel" /></h1>

				<span><liferay-ui:message key="content-performance" /></span>
			</clay:content-col>

			<clay:content-col>
				<clay:button
					cssClass="sidenav-close"
					displayType="unstyled"
					icon="times-small"
					monospaced="<%= true %>"
				/>
			</clay:content-col>
		</clay:content-row>
	</div>

	<div class="sidebar-body">
		<c:if test='<%= Objects.equals(analyticsReportsPanelState, "open") %>'>
			<liferay-util:include page="/analytics_reports_panel.jsp" servletContext="<%= application %>" />
		</c:if>
	</div>
</div>

<aui:script>
	var analyticsReportsPanelToggle = document.getElementById(
		'<portlet:namespace />analyticsReportsPanelToggleId'
	);

	var sidenavInstance = Liferay.SideNavigation.initialize(
		analyticsReportsPanelToggle
	);

	sidenavInstance.on('open.lexicon.sidenav', function (event) {
		Liferay.Util.Session.set(
			'com.liferay.analytics.reports.web_panelState',
			'open'
		);
	});

	sidenavInstance.on('closed.lexicon.sidenav', function (event) {
		Liferay.Util.Session.set(
			'com.liferay.analytics.reports.web_panelState',
			'closed'
		);
	});

	Liferay.once('screenLoad', function () {
		Liferay.SideNavigation.destroy(analyticsReportsPanelToggle);
	});
</aui:script>