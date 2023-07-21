<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalPreviewArticleContentTemplateDisplayContext journalPreviewArticleContentTemplateDisplayContext = new JournalPreviewArticleContentTemplateDisplayContext(renderRequest, renderResponse);
%>

<aui:form name="previewFm">
	<nav class="component-tbar subnav-tbar-light tbar tbar-article">
		<ul class="tbar-nav">
			<li class="tbar-item">
				<aui:select cssClass="mr-3" label="" name="ddmTemplateId" onChange="previewArticleContentTemplate()" wrapperCssClass="form-group-sm mb-0 ml-4">
					<aui:option label="no-template" selected="<%= Objects.equals(journalPreviewArticleContentTemplateDisplayContext.getDDMTemplateId(), -1) %>" value="<%= -1 %>" />

					<%
					for (DDMTemplate ddmTemplate : journalPreviewArticleContentTemplateDisplayContext.getDDMTemplates()) {
					%>

						<aui:option label="<%= HtmlUtil.escape(ddmTemplate.getName(locale)) %>" selected="<%= Objects.equals(journalPreviewArticleContentTemplateDisplayContext.getDDMTemplateId(), ddmTemplate.getTemplateId()) %>" value="<%= ddmTemplate.getTemplateId() %>" />

					<%
					}
					%>

				</aui:select>
			</li>
			<li class="tbar-item">
				<div class="journal-article-button-row tbar-section text-right">
					<aui:button
						cssClass="btn-sm selector-button"
						data='<%=
							HashMapBuilder.<String, Object>put(
								"ddmtemplateid", journalPreviewArticleContentTemplateDisplayContext.getDDMTemplateId()
							).build()
						%>'
						value="apply"
					/>
				</div>
			</li>
		</ul>
	</nav>
</aui:form>

<%
JournalArticleDisplay articleDisplay = journalPreviewArticleContentTemplateDisplayContext.getArticleDisplay();
%>

<div class="m-4">
	<%= articleDisplay.getContent() %>

	<c:if test="<%= articleDisplay.isPaginate() %>">
		<liferay-ui:page-iterator
			cur="<%= articleDisplay.getCurrentPage() %>"
			curParam="page"
			delta="<%= 1 %>"
			id="articleDisplayPages"
			maxPages="<%= 25 %>"
			portletURL="<%= journalPreviewArticleContentTemplateDisplayContext.getPageIteratorPortletURL() %>"
			total="<%= articleDisplay.getNumberOfPages() %>"
			type="article"
		/>
	</c:if>
</div>

<script>
	function previewArticleContentTemplate() {
		var ddmTemplateId = document.getElementById(
			'<portlet:namespace />ddmTemplateId'
		);

		location.href = Liferay.Util.addParams(
			'<portlet:namespace />ddmTemplateId=' + ddmTemplateId.value,
			'<%= journalPreviewArticleContentTemplateDisplayContext.getPortletURL() %>'
		);
	}

	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />previewFm',
		'<%= HtmlUtil.escapeJS(journalPreviewArticleContentTemplateDisplayContext.getEventName()) %>'
	);
</script>