<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ImportStyleBookDisplayContext importStyleBookDisplayContext = new ImportStyleBookDisplayContext(renderRequest);
%>

<portlet:actionURL name="/style_book/import_style_book_entries" var="importStyleBookEntriesURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= importStyleBookEntriesURL %>"
	enctype="multipart/form-data"
	name="fm"
>
	<liferay-frontend:edit-form-body>

		<%
		List<String> draftStyleBookEntryZipProcessorImportResultEntryNames = importStyleBookDisplayContext.getStyleBookEntryZipProcessorImportResultEntryNames(StyleBookEntryZipProcessor.ImportResultEntry.Status.IMPORTED_DRAFT);
		%>

		<c:if test="<%= ListUtil.isNotEmpty(draftStyleBookEntryZipProcessorImportResultEntryNames) %>">
			<clay:alert
				dismissible="<%= true %>"
				message='<%= LanguageUtil.format(request, "the-following-style-books-have-validation-issues.-they-have-been-left-in-draft-status-x", "<strong>" + StringUtil.merge(draftStyleBookEntryZipProcessorImportResultEntryNames, StringPool.COMMA_AND_SPACE) + "</strong>", false) %>'
			/>
		</c:if>

		<%
		List<String> invalidStyleBookEntryZipProcessorImportResultEntryNames = importStyleBookDisplayContext.getStyleBookEntryZipProcessorImportResultEntryNames(StyleBookEntryZipProcessor.ImportResultEntry.Status.INVALID);
		%>

		<c:if test="<%= ListUtil.isNotEmpty(invalidStyleBookEntryZipProcessorImportResultEntryNames) %>">
			<clay:alert
				dismissible="<%= true %>"
				displayType="warning"
				message='<%= LanguageUtil.format(request, "the-following-style-books-could-not-be-imported-x", "<strong>" + StringUtil.merge(invalidStyleBookEntryZipProcessorImportResultEntryNames, StringPool.COMMA_AND_SPACE) + "</strong>", false) %>'
			/>
		</c:if>

		<liferay-ui:error exception="<%= DuplicateStyleBookEntryKeyException.class %>">

			<%
			DuplicateStyleBookEntryKeyException duplicateStyleBookEntryKeyException = (DuplicateStyleBookEntryKeyException)errorException;
			%>

			<liferay-ui:message arguments="<%= duplicateStyleBookEntryKeyException.getMessage() %>" key="a-style-book-with-the-key-x-already-exists" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= StyleBookEntryFileException.class %>" message="the-selected-file-is-not-a-valid-zip-file" />

		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<aui:input label="select-file" name="file" type="file">
					<aui:validator name="required" />

					<aui:validator name="acceptFiles">
						'zip'
					</aui:validator>
				</aui:input>

				<aui:input checked="<%= true %>" label="overwrite-existing-style-books" name="overwrite" type="checkbox" />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" value="import" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>