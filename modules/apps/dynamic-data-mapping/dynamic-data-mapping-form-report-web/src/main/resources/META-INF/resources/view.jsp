<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
int totalItems = ddmFormReportDisplayContext.getTotalItems();
%>

<div class="hide portlet-ddm-form-report" id="container-portlet-ddm-form-report">
	<div class="portlet-ddm-form-report-header">
		<clay:container-fluid>
			<clay:content-row
				cssClass="align-items-center"
			>
				<span class="portlet-ddm-form-report-header-title text-truncate">
					<c:choose>
						<c:when test="<%= totalItems == 1 %>">
							<liferay-ui:message arguments="<%= totalItems %>" key="x-entry" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message arguments="<%= totalItems %>" key="x-entries" />
						</c:otherwise>
					</c:choose>
				</span>
			</clay:content-row>

			<clay:content-row
				cssClass="align-items-center"
			>
				<span class="portlet-ddm-form-report-header-subtitle text-truncate">
					<c:choose>
						<c:when test="<%= totalItems > 0 %>">
							<%= ddmFormReportDisplayContext.getLastModifiedDate() %>
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="there-are-no-entries" />
						</c:otherwise>
					</c:choose>
				</span>
			</clay:content-row>
		</clay:container-fluid>
	</div>

	<clay:navigation-bar
		cssClass="portlet-ddm-form-report-tabs"
		navigationItems='<%=
			new JSPNavigationItemList(pageContext) {
				{
					add(
						navigationItem -> {
							navigationItem.setActive(true);
							navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "summary"));
						});
				}
			}
		%>'
	/>

	<hr class="m-0" />

	<div id="<portlet:namespace />summaryTabContent">
		<liferay-util:include page="/view_summary.jsp" servletContext="<%= application %>" />
	</div>
</div>

<aui:script require="metal-dom/src/dom as dom">
	dom.delegate(
		document.querySelector('.portlet-ddm-form-report-tabs'),
		'click',
		'li',
		function (event) {
			var navItem = dom.closest(event.delegateTarget, '.nav-item');
			var navItemIndex = Number(navItem.dataset.navItemIndex);
			var navLink = navItem.querySelector('.nav-link');

			document
				.querySelector('.portlet-ddm-form-report-tabs li > .active')
				.classList.remove('active');
			navLink.classList.add('active');

			var summaryTabContent = document.querySelector(
				'#<portlet:namespace />summaryTabContent'
			);

			if (navItemIndex === 0) {
				summaryTabContent.classList.remove('hide');
			}
			else {
				summaryTabContent.classList.add('hide');
			}
		}
	);
</aui:script>