<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/asset/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);
%>

<%= kbArticle.getContent() %>

<%
List<FileEntry> attachmentsFileEntries = new ArrayList<FileEntry>();

if (kbArticle != null) {
	attachmentsFileEntries = kbArticle.getAttachmentsFileEntries();
}
%>

<c:if test="<%= !attachmentsFileEntries.isEmpty() %>">
	<div class="kb-attachments">
		<h5><liferay-ui:message key="attachments" /></h5>

		<clay:row>

			<%
			for (FileEntry fileEntry : attachmentsFileEntries) {
			%>

				<clay:col
					md="4"
				>
					<liferay-frontend:horizontal-card
						text="<%= fileEntry.getTitle() %>"
						url='<%= PortletFileRepositoryUtil.getDownloadPortletFileEntryURL(themeDisplay, fileEntry, "status=" + WorkflowConstants.STATUS_APPROVED) %>'
					>
						<liferay-frontend:horizontal-card-col>
							<liferay-document-library:mime-type-sticker
								fileVersion="<%= fileEntry.getFileVersion() %>"
							/>
						</liferay-frontend:horizontal-card-col>
					</liferay-frontend:horizontal-card>
				</clay:col>

			<%
			}
			%>

		</clay:row>
	</div>
</c:if>