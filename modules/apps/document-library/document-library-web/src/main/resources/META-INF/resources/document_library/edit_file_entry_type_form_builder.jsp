<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DLFileEntryType fileEntryType = (DLFileEntryType)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY_TYPE);

long fileEntryTypeId = BeanParamUtil.getLong(fileEntryType, request, "fileEntryTypeId");

com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure = (com.liferay.dynamic.data.mapping.model.DDMStructure)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE);

long ddmStructureId = BeanParamUtil.getLong(ddmStructure, request, "structureId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((fileEntryType == null) ? LanguageUtil.get(request, "new-document-type") : fileEntryType.getName(locale));
%>

<clay:container-fluid>
	<portlet:actionURL name="/document_library/edit_file_entry_type" var="editFileEntryTypeURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/edit_file_entry_type" />
	</portlet:actionURL>

	<aui:form action="<%= editFileEntryTypeURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveStructure();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (fileEntryType == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="fileEntryTypeId" type="hidden" value="<%= fileEntryTypeId %>" />
		<aui:input name="fileEntryTypeUuid" type="hidden" value="" />
		<aui:input name="ddmStructureId" type="hidden" value="<%= ddmStructureId %>" />
		<aui:input name="definition" type="hidden" />

		<liferay-ui:error exception="<%= DuplicateFileEntryTypeException.class %>" message="please-enter-a-unique-document-type-name" />
		<liferay-ui:error exception="<%= NoSuchMetadataSetException.class %>" message="please-enter-a-valid-metadata-set-or-enter-a-metadata-field" />
		<liferay-ui:error exception="<%= StorageFieldRequiredException.class %>" message="please-fill-out-all-required-fields" />
		<liferay-ui:error exception="<%= StructureDefinitionException.class %>" message="please-enter-a-valid-definition" />
		<liferay-ui:error exception="<%= StructureDuplicateElementException.class %>" message="please-enter-unique-metadata-field-names-(including-field-names-inherited-from-the-parent)" />
		<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-name" />

		<aui:model-context bean="<%= fileEntryType %>" model="<%= DLFileEntryType.class %>" />

		<aui:fieldset-group cssClass="edit-file-entry-type" markupView="lexicon">
			<aui:fieldset collapsible="<%= true %>" extended="<%= false %>" id="detailsMetadataFields" persistState="<%= true %>" title="details">
				<liferay-util:include page="/document_library/ddm/details.jsp" servletContext="<%= application %>" />
			</aui:fieldset>

			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" id="mainMetadataFields" label="main-metadata-fields">
				<liferay-util:include page="/document_library/ddm/ddm_form_builder.jsp" servletContext="<%= application %>" />
			</aui:fieldset>

			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" id="additionalMetadataFields" label="additional-metadata-fields">
				<liferay-util:include page="/document_library/ddm/additional_metadata_fields.jsp" servletContext="<%= application %>" />
			</aui:fieldset>

			<c:if test="<%= fileEntryType == null %>">
				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions" markupView="lexicon">
					<liferay-util:include page="/document_library/ddm/permissions.jsp" servletContext="<%= application %>" />
				</aui:fieldset>
			</c:if>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</clay:container-fluid>

<aui:script>
	function <portlet:namespace />saveStructure() {
		document.<portlet:namespace />fm.<portlet:namespace />definition.value = window.<portlet:namespace />formBuilder.getContentValue();

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%
if (fileEntryType == null) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-document-type"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit-document-type"), currentURL);
}
%>