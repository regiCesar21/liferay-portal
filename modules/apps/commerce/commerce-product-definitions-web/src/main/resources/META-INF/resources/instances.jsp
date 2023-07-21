<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPInstanceDisplayContext cpInstanceDisplayContext = (CPInstanceDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:if test="<%= CommerceCatalogPermission.contains(permissionChecker, cpInstanceDisplayContext.getCPDefinition(), ActionKeys.VIEW) %>">
	<div class="pt-4" id="<portlet:namespace />productInstancesContainer">
		<aui:form action="<%= cpInstanceDisplayContext.getPortletURL() %>" method="post" name="fm">
			<clay:data-set-display
				contextParams='<%=
					HashMapBuilder.<String, String>put(
						"cpDefinitionId", String.valueOf(cpInstanceDisplayContext.getCPDefinitionId())
					).build()
				%>'
				creationMenu="<%= cpInstanceDisplayContext.getCreationMenu() %>"
				dataProviderKey="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_INSTANCES %>"
				id="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_INSTANCES %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= currentURLObj %>"
				style="stacked"
			/>
		</aui:form>
	</div>
</c:if>