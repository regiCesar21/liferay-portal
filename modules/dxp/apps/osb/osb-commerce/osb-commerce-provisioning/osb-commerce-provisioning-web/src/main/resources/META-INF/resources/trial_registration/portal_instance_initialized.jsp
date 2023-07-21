<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long commerceOrderItemId = ParamUtil.getLong(request, "commerceOrderItemId");

TrialRegistrationDisplayContext trialRegistrationDisplayContext = (TrialRegistrationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="container" id="trial-registration">
	<div class="row">
		<div class="col-md-6 col-xs-12">
			<div>
				<h1><%= LanguageUtil.get(request, "welcome-to-your-demo") %></h1>
			</div>

			<div class="instance-status">
				<p><%= LanguageUtil.format(request, "welcome-message", "hello@liferay.com") %></p>
				<p><%= LanguageUtil.get(request, "trial-period-expires-in-days") %></p>

				<a class="btn btn-primary" href="<%= trialRegistrationDisplayContext.getPortalInstanceURL(commerceOrderItemId) %>" role="button" target="_blank">
					<%= LanguageUtil.get(request, "start-your-demo") %>
				</a>
			</div>
		</div>

		<div class="col-md-6 col-xs-12">
		</div>
	</div>
</div>