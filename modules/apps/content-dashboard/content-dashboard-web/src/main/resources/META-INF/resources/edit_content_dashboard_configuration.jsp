<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ContentDashboardAdminConfigurationDisplayContext contentDashboardAdminConfigurationDisplayContext = (ContentDashboardAdminConfigurationDisplayContext)request.getAttribute(ContentDashboardWebKeys.CONTENT_DASHBOARD_ADMIN_CONFIGURATION_DISPLAY_CONTEXT);
%>

<liferay-frontend:edit-form
	action="<%= contentDashboardAdminConfigurationDisplayContext.getActionURL() %>"
	method="post"
	name="fm"
	onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveConfiguration();" %>'
>
	<aui:input name="redirect" type="hidden" value="<%= contentDashboardAdminConfigurationDisplayContext.getRedirect() %>" />
	<aui:input name="assetVocabularyNames" type="hidden" />

	<liferay-frontend:edit-form-body>
		<c:if test='<%= GetterUtil.getBoolean(SessionMessages.get(renderRequest, "emptyAssetVocabularyNames")) %>'>
			<clay:alert
				dismissible="<%= true %>"
				displayType="warning"
				message="you-have-not-selected-any-vocabularies"
			/>
		</c:if>

		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<aui:field-wrapper>
					<p class="sheet-text">
						<liferay-ui:message key="select-vocabularies-description" />
					</p>

					<liferay-ui:input-move-boxes
						leftBoxName="availableAssetVocabularyNames"
						leftList="<%= contentDashboardAdminConfigurationDisplayContext.getAvailableVocabularyNames() %>"
						leftTitle="available"
						rightBoxMaxItems="<%= 2 %>"
						rightBoxName="currentAssetVocabularyNames"
						rightList="<%= contentDashboardAdminConfigurationDisplayContext.getCurrentVocabularyNames() %>"
						rightReorder="<%= Boolean.TRUE.toString() %>"
						rightTitle="in-use"
					/>
				</aui:field-wrapper>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		var form = document.<portlet:namespace />fm;

		Liferay.Util.postForm(form, {
			data: {
				assetVocabularyNames: Liferay.Util.listSelect(
					Liferay.Util.getFormElement(form, 'currentAssetVocabularyNames')
				),
			},
		});
	}
</aui:script>