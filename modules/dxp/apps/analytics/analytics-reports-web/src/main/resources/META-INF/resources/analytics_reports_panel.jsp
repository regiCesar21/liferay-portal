<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AnalyticsReportsDisplayContext analyticsReportsDisplayContext = (AnalyticsReportsDisplayContext)request.getAttribute(AnalyticsReportsWebKeys.ANALYTICS_REPORTS_DISPLAY_CONTEXT);
%>

<c:choose>
	<c:when test="<%= analyticsReportsDisplayContext.isAnalyticsSynced() %>">
		<div id="<portlet:namespace />-analytics-reports-root">
			<span aria-hidden="true" class="loading-animation loading-animation-sm"></span>

			<react:component
				module="js/AnalyticsReportsApp"
				props="<%= analyticsReportsDisplayContext.getData() %>"
			/>
		</div>
	</c:when>
	<c:otherwise>
		<div id="<portlet:namespace />-analytics-reports-root">
			<div class="p-3 pt-5 text-center">
				<liferay-ui:icon
					alt="connect-to-liferay-analytics-cloud"
					src='<%= PortalUtil.getPathContext(request) + "/assets/ac-icon.svg" %>'
				/>

				<c:choose>
					<c:when test="<%= AnalyticsReportsUtil.isAnalyticsConnected(themeDisplay.getCompanyId()) %>">
						<h4 class="mt-3"><liferay-ui:message key="sync-to-analytics-cloud" /></h4>

						<p class="text-secondary"><liferay-ui:message key="sync-to-analytics-cloud-help" /></p>

						<liferay-ui:icon
							label="<%= true %>"
							linkCssClass="btn btn-primary btn-sm mb-3"
							markupView="lexicon"
							message="open-analytics-cloud"
							target="_blank"
							url="<%= analyticsReportsDisplayContext.getLiferayAnalyticsURL() %>"
						/>
					</c:when>
					<c:otherwise>
						<h4 class="mt-3"><liferay-ui:message key="connect-to-liferay-analytics-cloud" /></h4>

						<p class="text-secondary"><liferay-ui:message key="connect-to-liferay-analytics-cloud-help" /></p>

						<liferay-ui:icon
							label="<%= true %>"
							linkCssClass="btn btn-secondary btn-sm"
							markupView="lexicon"
							message="start-free-trial"
							target="_blank"
							url="<%= AnalyticsReportsUtil.ANALYTICS_CLOUD_TRIAL_URL %>"
						/>

						<liferay-ui:icon
							label="<%= true %>"
							linkCssClass="d-block font-weight-bold mb-3 mt-5"
							markupView="lexicon"
							message="do-not-show-me-this-again"
							url="<%= analyticsReportsDisplayContext.getHideAnalyticsReportsPanelURL() %>"
						/>

						<p class="text-secondary"><liferay-ui:message key="do-not-show-me-this-again-help" /></p>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:otherwise>
</c:choose>