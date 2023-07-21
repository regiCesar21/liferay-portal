<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceAccountAddressAdminDisplayContext commerceAccountAddressAdminDisplayContext = (CommerceAccountAddressAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commerceAccountId = commerceAccountAddressAdminDisplayContext.getCommerceAccountId();
SearchContainer<CommerceAddress> commerceAddressSearchContainer = commerceAccountAddressAdminDisplayContext.getSearchContainer();

PortletURL portletURL = commerceAccountAddressAdminDisplayContext.getPortletURL();
%>

<c:if test="<%= commerceAccountAddressAdminDisplayContext.hasPermission(commerceAccountId, ActionKeys.UPDATE) %>">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="commerceAddresses"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= portletURL %>"
				selectedDisplayStyle="list"
			/>

			<portlet:renderURL var="addCommerceAddressURL">
				<portlet:param name="mvcRenderCommandName" value="/commerce_account_admin/edit_commerce_address" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="commerceAccountId" value="<%= String.valueOf(commerceAccountId) %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add-address") %>'
					url="<%= addCommerceAddressURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= portletURL %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteCommerceAddresses();" %>'
				icon="times"
				label="delete"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>

	<div class="container-fluid-1280">
		<aui:form action="<%= portletURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="deleteCommerceAddressIds" type="hidden" />

			<liferay-ui:search-container
				id="commerceAddresses"
				iteratorURL="<%= portletURL %>"
				searchContainer="<%= commerceAddressSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.model.CommerceAddress"
					cssClass="entry-display-style"
					keyProperty="commerceAddressId"
					modelVar="commerceAddress"
				>
					<liferay-ui:search-container-column-text
						href="<%= commerceAccountAddressAdminDisplayContext.getEditCommerceAddressURL(commerceAddress.getCommerceAddressId()) %>"
						value="<%= HtmlUtil.escape(commerceAddress.getName()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="type"
						value="<%= LanguageUtil.get(request, CommerceAddressConstants.getAddressTypeLabel(commerceAddress.getType())) %>"
					/>

					<liferay-ui:search-container-column-text
						value="<%= HtmlUtil.escape(commerceAddress.getStreet1()) %>"
					/>

					<liferay-ui:search-container-column-text
						value="<%= HtmlUtil.escape(commerceAddress.getCity()) %>"
					/>

					<liferay-ui:search-container-column-text
						value="<%= HtmlUtil.escape(commerceAddress.getZip()) %>"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/address_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>

	<aui:script>
		function <portlet:namespace />deleteCommerceAddresses() {
			if (
				confirm(
					'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-addresses" />'
				)
			) {
				var form = window.document['<portlet:namespace />fm'];

				form.setAttribute('method', 'post');
				form['<portlet:namespace /><%= Constants.CMD %>'].value =
					'<%= Constants.DELETE %>';
				form[
					'<portlet:namespace />deleteCommerceAddressIds'
				].value = Liferay.Util.listCheckedExcept(
					form,
					'<portlet:namespace />allRowIds'
				);

				submitForm(
					form,
					'<portlet:actionURL name="/commerce_account_admin/edit_commerce_address" />'
				);
			}
		}
	</aui:script>
</c:if>