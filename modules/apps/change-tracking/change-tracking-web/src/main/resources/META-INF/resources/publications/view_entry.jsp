<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
ViewEntryDisplayContext<?> viewEntryDisplayContext = (ViewEntryDisplayContext<?>)request.getAttribute(CTWebKeys.VIEW_ENTRY_DISPLAY_CONTEXT);
%>

<div class="publications-diff-table-wrapper">
	<table class="table table-autofit">
		<tr class="publications-diff-no-border-top table-divider">
			<td class="publications-diff-td"><%= HtmlUtil.escape(viewEntryDisplayContext.getDividerTitle(resourceBundle)) %></td>
		</tr>
		<tr>
			<td class="publications-diff-td">

				<%
				viewEntryDisplayContext.renderEntry(request, response);
				%>

			</td>
		</tr>
	</table>
</div>