<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
FragmentServiceConfigurationDisplayContext fragmentServiceConfigurationDisplayContext = (FragmentServiceConfigurationDisplayContext)request.getAttribute(FragmentServiceConfigurationDisplayContext.class.getName());
%>

<aui:form action="<%= fragmentServiceConfigurationDisplayContext.getEditFragmentServiceConfigurationURL() %>" method="post" name="fm">
	<div class="sheet">
		<liferay-ui:error exception="<%= ConfigurationModelListenerException.class %>" message="there-was-an-unknown-error" />

		<div class="sheet-header">
			<h2>
				<liferay-ui:message key="fragment-configuration-name" />
			</h2>
		</div>

		<div class="sheet-section">
			<div class="mb-3">
				<clay:checkbox
					checked="<%= fragmentServiceConfigurationDisplayContext.isPropagateContributedFragmentChangesEnabled() %>"
					id='<%= liferayPortletResponse.getNamespace() + "propagateContributedFragmentChanges" %>'
					label='<%= LanguageUtil.get(request, "propagate-contributed-fragment-changes-automatically") %>'
					name='<%= liferayPortletResponse.getNamespace() + "propagateContributedFragmentChanges" %>'
				/>

				<div aria-hidden="true" class="form-feedback-group">
					<div class="form-text text-weight-normal">
						<liferay-ui:message key="propagate-contributed-fragment-changes-automatically-description" />
					</div>
				</div>
			</div>

			<div>
				<clay:checkbox
					checked="<%= fragmentServiceConfigurationDisplayContext.isPropagateChangesEnabled() %>"
					id='<%= liferayPortletResponse.getNamespace() + "propagateChanges" %>'
					label='<%= LanguageUtil.get(request, "propagate-fragment-changes-automatically") %>'
					name='<%= liferayPortletResponse.getNamespace() + "propagateChanges" %>'
				/>

				<div aria-hidden="true" class="form-feedback-group">
					<div class="form-text text-weight-normal">
						<liferay-ui:message key="propagate-fragment-changes-automatically-description" />
					</div>
				</div>
			</div>
		</div>

		<div class="sheet-footer">
			<aui:button primary="<%= true %>" type="submit" value="save" />

			<aui:button type="cancel" />
		</div>
	</div>
</aui:form>