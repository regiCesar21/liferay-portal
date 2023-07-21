<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
CTEntryDiffDisplay ctEntryDiffDisplay = (CTEntryDiffDisplay)request.getAttribute(CTWebKeys.CT_ENTRY_DIFF_DISPLAY);
%>

<div class="publications-diff-table-wrapper">
	<table class="table table-autofit">
		<tr class="publications-diff-no-border-top table-divider">
			<c:if test="<%= !ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_ADDITION) %>">
				<td class="publications-diff-td"><%= HtmlUtil.escape(ctEntryDiffDisplay.getLeftTitle()) %></td>
			</c:if>

			<c:if test="<%= !ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_DELETION) %>">
				<td class="publications-diff-td"><%= HtmlUtil.escape(ctEntryDiffDisplay.getRightTitle()) %></td>
			</c:if>
		</tr>
		<tr>
			<c:if test="<%= !ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_ADDITION) %>">
				<td class="publications-diff-td">

					<%
					ctEntryDiffDisplay.renderLeftCTRow();
					%>

				</td>
			</c:if>

			<c:if test="<%= !ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_DELETION) %>">
				<td class="publications-diff-td">

					<%
					ctEntryDiffDisplay.renderRightCTRow();
					%>

				</td>
			</c:if>
		</tr>
	</table>
</div>