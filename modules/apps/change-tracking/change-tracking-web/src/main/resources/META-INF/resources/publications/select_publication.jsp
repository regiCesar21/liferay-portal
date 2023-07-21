<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
SearchContainer<CTCollection> searchContainer = publicationsDisplayContext.getSearchContainer();

searchContainer.setId("selectPublication");
%>

<clay:management-toolbar
	displayContext="<%= new SelectPublicationManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, searchContainer) %>"
/>

<c:if test="<%= !searchContainer.hasResults() %>">
	<div class="contact-information-empty-results-message-wrapper">
		<liferay-ui:empty-result-message
			message="<%= searchContainer.getEmptyResultsMessage() %>"
		/>
	</div>
</c:if>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "selectPublicationContainer" %>'
>
	<div class="table-responsive">
		<table class="publications-table select-publication-table table table-autofit">
			<tbody>

				<%
				for (CTCollection ctCollection : searchContainer.getResults()) {
				%>

					<tr>
						<td>
							<span class="lfr-portal-tooltip" title="<%= HtmlUtil.escape(ctCollection.getUserName()) %>">
								<liferay-ui:user-portrait
									userId="<%= ctCollection.getUserId() %>"
								/>
							</span>
						</td>
						<td class="table-cell-expand">
							<c:choose>
								<c:when test="<%= ctCollection.getCtCollectionId() == publicationsDisplayContext.getCtCollectionId() %>">
									<div class="font-italic publication-name">
										<%= HtmlUtil.escape(ctCollection.getName()) %>
									</div>

									<div class="font-italic publication-description">
										<%= HtmlUtil.escape(ctCollection.getDescription()) %>
									</div>
								</c:when>
								<c:otherwise>
									<aui:a
										cssClass="selector-button"
										data='<%=
											HashMapBuilder.<String, Object>put(
												"ctcollectionid", ctCollection.getCtCollectionId()
											).build()
										%>'
										href="javascript:;"
									>
										<div class="publication-name">
											<%= HtmlUtil.escape(ctCollection.getName()) %>
										</div>

										<div class="publication-description">
											<%= HtmlUtil.escape(ctCollection.getDescription()) %>
										</div>
									</aui:a>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>

				<%
				}
				%>

			</tbody>
		</table>
	</div>

	<liferay-ui:search-paginator
		markupView="lexicon"
		searchContainer="<%= searchContainer %>"
	/>
</clay:container-fluid>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />selectPublicationContainer',
		'<portlet:namespace />selectPublication'
	);
</aui:script>