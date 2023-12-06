<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceRegionsDisplayContext commerceRegionsDisplayContext = (CommerceRegionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commerceCountryId = commerceRegionsDisplayContext.getCommerceCountryId();

CommerceRegion commerceRegion = commerceRegionsDisplayContext.getCommerceRegion();

long commerceRegionId = commerceRegionsDisplayContext.getCommerceRegionId();
%>

<portlet:actionURL name="/commerce_country/edit_commerce_region" var="editCommerceRegionActionURL" />

<aui:form action="<%= editCommerceRegionActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveCommerceRegion();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceRegion == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="commerceCountryId" type="hidden" value="<%= String.valueOf(commerceCountryId) %>" />
	<aui:input name="commerceRegionId" type="hidden" value="<%= String.valueOf(commerceRegionId) %>" />

	<div class="lfr-form-content">
		<liferay-ui:error exception="<%= CommerceRegionNameException.class %>" message="please-enter-a-valid-name" />

		<aui:model-context bean="<%= commerceRegion %>" model="<%= CommerceRegion.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input autoFocus="<%= true %>" name="name" />

				<aui:input name="code" />

				<aui:input name="priority" />

				<aui:input checked="<%= (commerceRegion == null) ? false : commerceRegion.isActive() %>" name="active" type="toggle-switch" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= portletDisplay.getURLBack() %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceRegion() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>