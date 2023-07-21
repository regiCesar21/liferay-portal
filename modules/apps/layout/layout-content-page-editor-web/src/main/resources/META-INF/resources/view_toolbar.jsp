<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ContentPageEditorDisplayContext contentPageEditorDisplayContext = (ContentPageEditorDisplayContext)request.getAttribute(ContentPageEditorWebKeys.LIFERAY_SHARED_CONTENT_PAGE_EDITOR_DISPLAY_CONTEXT);
%>

<div class="management-bar navbar navbar-expand-md page-editor__toolbar <%= contentPageEditorDisplayContext.isMasterLayout() ? "page-editor__toolbar--master-layout" : StringPool.BLANK %>" id="<%= contentPageEditorDisplayContext.getPortletNamespace() %>pageEditorToolbar">
	<clay:container-fluid>
		<ul class="navbar-nav">
		</ul>

		<ul class="navbar-nav">
			<c:if test="<%= contentPageEditorDisplayContext.isSingleSegmentsExperienceMode() %>">
				<li class="nav-item">
					<button class="btn btn-secondary btn-sm mr-3" disabled type="submit">
						<liferay-ui:message key="discard-variant" />
					</button>
				</li>
			</c:if>

			<li class="nav-item">
				<button class="btn btn-primary btn-sm" disabled type="submit">
					<c:choose>
						<c:when test="<%= contentPageEditorDisplayContext.isMasterLayout() %>">
							<liferay-ui:message key="publish-master" />
						</c:when>
						<c:when test="<%= contentPageEditorDisplayContext.isSingleSegmentsExperienceMode() %>">
							<liferay-ui:message key="save-variant" />
						</c:when>
						<c:when test="<%= contentPageEditorDisplayContext.isWorkflowEnabled() %>">
							<liferay-ui:message key="submit-for-publication" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="publish" />
						</c:otherwise>
					</c:choose>
				</button>
			</li>
		</ul>
	</clay:container-fluid>
</div>