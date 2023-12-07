<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

CommerceInventoryWarehousesDisplayContext commerceInventoryWarehousesDisplayContext = (CommerceInventoryWarehousesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceInventoryWarehouse commerceInventoryWarehouse = commerceInventoryWarehousesDisplayContext.getCommerceInventoryWarehouse();
%>

<portlet:actionURL name="/commerce_inventory_warehouse/edit_commerce_inventory_warehouse" var="editCommerceInventoryWarehouseActionURL" />

<aui:form action="<%= editCommerceInventoryWarehouseActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveCommerceInventoryWarehouse();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceInventoryWarehouse == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceInventoryWarehouseId" type="hidden" value="<%= (commerceInventoryWarehouse == null) ? 0 : commerceInventoryWarehouse.getCommerceInventoryWarehouseId() %>" />
	<aui:input name="commerceChannelIds" type="hidden" />
	<aui:input name="mvccVersion" type="hidden" value="<%= (commerceInventoryWarehouse == null) ? 0 : commerceInventoryWarehouse.getMvccVersion() %>" />

	<liferay-frontend:form-navigator
		formModelBean="<%= commerceInventoryWarehouse %>"
		id="<%= CommerceInventoryWarehouseFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_WAREHOUSE %>"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceInventoryWarehouse() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>