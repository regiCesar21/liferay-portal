<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long commerceOrderItemId = ParamUtil.getLong(request, "commerceOrderItemId");
%>

<div class="container" id="trial-registration">
	<div class="row">
		<div class="col-md-6 col-xs-12">
			<div>
				<h1><%= LanguageUtil.get(request, "hello") %> <%= ParamUtil.getString(request, "userFirstName") %></h1>

				<p><%= LanguageUtil.get(request, "loading-instance") %></p>
			</div>

			<div class="instance-status" id="<portlet:namespace />instanceStatus">
				<p><%= LanguageUtil.get(request, "wait-time") %></p>

				<ul>
					<li>
						<span>
							<svg class="lexicon-icon lexicon-icon-search" focusable="false" role="presentation">
								<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#check"></use>
							</svg>
						</span>

						<%= LanguageUtil.get(request, "creating-an-unique-link") %>
					</li>
					<li>
						<span>
							<svg class="lexicon-icon lexicon-icon-search" focusable="false" role="presentation">
								<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#check"></use>
							</svg>
						</span>

						<%= LanguageUtil.get(request, "setting-up-administrator") %>
					</li>
					<li>
						<span aria-hidden="true" class="loading-animation loading-animation-sm"></span>

						<%= LanguageUtil.get(request, "setting-up-a-new-instance") %>
					</li>
				</ul>
			</div>

			<div class="hide" id="<portlet:namespace />instanceCreationFailure">
				<div class="alert alert-danger">
					<%= LanguageUtil.format(request, "unable-to-create-instance", "hello@liferay.com") %>
				</div>
			</div>
		</div>

		<div class="col-md-6 col-xs-12">
		</div>
	</div>
</div>

<portlet:resourceURL id="portalInstanceStatus" var="portalInstanceStatusResourceURL">
	<portlet:param name="commerceOrderItemId" value="<%= String.valueOf(commerceOrderItemId) %>" />
</portlet:resourceURL>

<portlet:renderURL var="portalInstanceInitializedURL">
	<portlet:param name="mvcRenderCommandName" value="portalInstanceInitialized" />
	<portlet:param name="commerceOrderItemId" value="<%= String.valueOf(commerceOrderItemId) %>" />
</portlet:renderURL>

<aui:script use="aui-base">
	setTimeout(function () {
		function callOnTimeOut() {
			var resourceURL = '<%= portalInstanceStatusResourceURL %>';

			fetch(resourceURL, {
				credentials: 'include',
				headers: new Headers({'x-csrf-token': Liferay.authToken}),
				method: 'post',
			})
				.then(function (res) {
					return res.json();
				})
				.then(function (payload) {
					if (payload.status === 0) {
						window.location = '<%= portalInstanceInitializedURL %>';
					}
					else if (payload.status === 6) {
						A.one('#<portlet:namespace />instanceStatus').hide();
						A.one(
							'#<portlet:namespace />instanceCreationFailure'
						).show();
					}
					else {
						setTimeout(function () {
							callOnTimeOut();
						}, 5000);
					}
				});
		}

		callOnTimeOut();
	}, 5000);
</aui:script>