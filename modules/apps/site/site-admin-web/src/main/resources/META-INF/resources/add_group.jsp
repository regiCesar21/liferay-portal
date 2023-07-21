<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AddGroupDisplayContext addGroupDisplayContext = (AddGroupDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:container-fluid>
	<liferay-frontend:edit-form
		action="<%= addGroupDisplayContext.getAddGroupURL() %>"
		method="post"
		name="fm"
		onSubmit="event.preventDefault();"
	>
		<liferay-frontend:edit-form-body>
			<aui:input autoFocus="<%= true %>" label="name" name="name" required="<%= true %>" />

			<c:if test="<%= addGroupDisplayContext.isShowLayoutSetVisibilityPrivateCheckbox() %>">
				<aui:input label="create-default-pages-as-private-available-only-to-members-if-unchecked-they-will-be-public-available-to-anyone" name="layoutSetVisibilityPrivate" type="checkbox" />
			</c:if>

			<c:if test="<%= addGroupDisplayContext.hasRequiredVocabularies() %>">
				<aui:fieldset cssClass="mb-4">
					<div class="h3 sheet-subtitle"><liferay-ui:message key="categorization" /></div>

					<c:choose>
						<c:when test="<%= addGroupDisplayContext.isShowCategorization() %>">
							<liferay-asset:asset-categories-selector
								className="<%= Group.class.getName() %>"
								classPK="<%= 0 %>"
								groupIds="<%= addGroupDisplayContext.getGroupIds() %>"
								showOnlyRequiredVocabularies="<%= true %>"
								visibilityTypes="<%= AssetVocabularyConstants.VISIBILITY_TYPES %>"
							/>
						</c:when>
						<c:otherwise>
							<div class="alert alert-warning text-justify">
								<liferay-ui:message key="sites-have-required-vocabularies.-you-need-to-create-at-least-one-category-in-all-required-vocabularies-in-order-to-create-a-site" />
							</div>
						</c:otherwise>
					</c:choose>
				</aui:fieldset>
			</c:if>
		</liferay-frontend:edit-form-body>

		<liferay-frontend:edit-form-footer>
			<clay:button
				id='<%= liferayPortletResponse.getNamespace() + "addButton" %>'
				label="add"
				type="submit"
			/>

			<clay:button
				cssClass="btn-cancel"
				displayType="secondary"
				label="cancel"
			/>
		</liferay-frontend:edit-form-footer>
	</liferay-frontend:edit-form>
</clay:container-fluid>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "addGroup" %>'
	module="js/AddGroup"
/>