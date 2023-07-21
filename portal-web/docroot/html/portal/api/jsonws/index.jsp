<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/portal/api/jsonws/init.jsp" %>

<c:if test="<%= PropsValues.JSONWS_WEB_SERVICE_API_DISCOVERABLE %>">
	<style>
		<%@ include file="/html/portal/api/jsonws/css.jspf" %>
	</style>

	<div id="wrapper">
		<header class="card fixed-top px-3 rounded-0" id="banner" role="banner">
			<div id="heading">
				<h1 class="align-items-center d-flex m-0 site-title">
					<a class="logo" href="<%= HtmlUtil.escapeAttribute(jsonWSContextPath) %>" title="JSONWS API">
						<img alt="<%= HtmlUtil.escapeAttribute("JSONWS API") %>" height="<%= themeDisplay.getCompanyLogoHeight() %>" src="<%= HtmlUtil.escape(themeDisplay.getCompanyLogo()) %>" width="<%= themeDisplay.getCompanyLogoWidth() %>" />
					</a>

					<span class="site-name">
						JSONWS API
					</span>
				</h1>
			</div>
		</header>

		<div id="content">
			<div id="main-content">
				<div class="container-fluid">
					<aui:row>
						<aui:col cssClass="lfr-api-navigation p-3" width="<%= 25 %>">
							<liferay-util:include page="/html/portal/api/jsonws/actions.jsp" />
						</aui:col>

						<aui:col cssClass="lfr-api-details p-3" width="<%= 75 %>">
							<liferay-util:include page="/html/portal/api/jsonws/action.jsp" />
						</aui:col>
					</aui:row>
				</div>
			</div>
		</div>

		<footer class="card fixed-bottom m-0 p-2 rounded-0" id="footer" role="contentinfo">
			<p class="m-0 powered-by">
				<liferay-ui:message key="powered-by" /> <a href="http://www.liferay.com" rel="external">Liferay</a>
			</p>
		</footer>
	</div>

	<script data-senna-track="permanent" id="APIScrollIntoView" type="text/javascript">
		Liferay.once('endNavigate', function(event) {
			var selected = document.querySelector('#services .lfr-api-signature.selected');

			if (selected) {
				selected.scrollIntoView({behavior: 'smooth'});
			}
		});
	</script>
</c:if>