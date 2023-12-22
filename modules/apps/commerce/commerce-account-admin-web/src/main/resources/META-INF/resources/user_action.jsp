<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceAccountUserRelAdminDisplayContext commerceAccountUserRelAdminDisplayContext = (CommerceAccountUserRelAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceAccountUserRel commerceAccountUserRel = (CommerceAccountUserRel)row.getObject();

User commerceAccountUser = commerceAccountUserRel.getUser();

String editUserRoleId = "editUserRoles" + commerceAccountUser.getUserId();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= commerceAccountUserRelAdminDisplayContext.hasPermission(commerceAccountUserRel.getCommerceAccountId(), ActionKeys.UPDATE) %>">
		<liferay-ui:icon
			id="<%= editUserRoleId %>"
			message="edit-roles"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= commerceAccountUserRelAdminDisplayContext.hasPermission(commerceAccountUserRel.getCommerceAccountId(), ActionKeys.DELETE) %>">
		<portlet:actionURL name="/commerce_account_admin/edit_commerce_account_user_rel" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceAccountId" value="<%= String.valueOf(commerceAccountUserRel.getCommerceAccountId()) %>" />
			<portlet:param name="commerceAccountUserId" value="<%= String.valueOf(commerceAccountUserRel.getCommerceAccountUserId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			message="remove"
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>

<aui:script use="liferay-item-selector-dialog">
	window.document
		.querySelector('#<portlet:namespace /><%= editUserRoleId %>')
		.addEventListener('click', function (event) {
			event.preventDefault();

			document.<portlet:namespace />fm.<portlet:namespace />originalRoleIds.value = '<%= commerceAccountUserRelAdminDisplayContext.getUserRoleIds(commerceAccountUserRel) %>';

			var itemSelectorDialog = new A.LiferayItemSelectorDialog({
					eventName: 'userRoleItemSelector',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.UPDATE %>';
								document.<portlet:namespace />fm.<portlet:namespace />commerceAccountUserId.value = <%= String.valueOf(commerceAccountUser.getUserId()) %>;
								document.<portlet:namespace />fm.<portlet:namespace />roleIds.value = selectedItems.value;

								submitForm(document.<portlet:namespace />fm, '<portlet:actionURL name="/commerce_account_admin/edit_commerce_account_user_rel" />');
							}
						}
					},
					title: '<liferay-ui:message key="edit-roles" />',
					url: '<%= commerceAccountUserRelAdminDisplayContext.getUserRoleItemSelectorUrl(commerceAccountUserRel) %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>