<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalSelectDDMTemplateDisplayContext journalSelectDDMTemplateDisplayContext = new JournalSelectDDMTemplateDisplayContext(renderRequest, renderResponse);
%>

<clay:management-toolbar
	displayContext="<%= new JournalSelectDDMTemplateManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, journalSelectDDMTemplateDisplayContext) %>"
/>

<aui:form cssClass="container-fluid-1280" method="post" name="selectDDMTemplateFm">
	<liferay-ui:search-container
		searchContainer="<%= journalSelectDDMTemplateDisplayContext.getTemplateSearch() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.dynamic.data.mapping.model.DDMTemplate"
			keyProperty="templateId"
			modelVar="ddmTemplate"
		>
			<c:choose>
				<c:when test='<%= Objects.equals(journalSelectDDMTemplateDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
					%>

					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							verticalCard="<%= new JournalSelectDDMTemplateVerticalCard(ddmTemplate, renderRequest, journalSelectDDMTemplateDisplayContext) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="name"
					>
						<c:choose>
							<c:when test="<%= ddmTemplate.getTemplateId() != journalSelectDDMTemplateDisplayContext.getDDMTemplateId() %>">
								<aui:a
									cssClass="selector-button"
									data='<%=
										HashMapBuilder.<String, Object>put(
											"ddmtemplateid", ddmTemplate.getTemplateId()
										).put(
											"ddmtemplatekey", ddmTemplate.getTemplateKey()
										).put(
											"description", ddmTemplate.getDescription(locale)
										).put(
											"imageurl", ddmTemplate.getTemplateImageURL(themeDisplay)
										).put(
											"name", ddmTemplate.getName(locale)
										).build()
									%>'
									href="javascript:;"
								>
									<%= HtmlUtil.escape(ddmTemplate.getName(locale)) %>
								</aui:a>
							</c:when>
							<c:otherwise>
								<%= HtmlUtil.escape(ddmTemplate.getName(locale)) %>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="description"
						value="<%= HtmlUtil.escape(ddmTemplate.getDescription(locale)) %>"
					/>

					<liferay-ui:search-container-column-date
						name="modified-date"
						value="<%= ddmTemplate.getModifiedDate() %>"
					/>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= journalSelectDDMTemplateDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	document.addEventListener('DOMContentLoaded', function () {
		Liferay.Util.selectEntityHandler(
			'#<portlet:namespace />selectDDMTemplateFm',
			'<%= HtmlUtil.escapeJS(journalSelectDDMTemplateDisplayContext.getEventName()) %>'
		);
	});
</aui:script>