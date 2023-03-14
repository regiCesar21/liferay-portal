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
			<div>
				<span aria-hidden="true" class="loading-animation"></span>

				<react:component
					data='<%=
						HashMapBuilder.<String, Object>put(
							"namespace", liferayPortletResponse.getNamespace()
						).put(
							"propagateChanges", fragmentServiceConfigurationDisplayContext.isPropagateChangesEnabled()
						).put(
							"propagateContributedFragmentChanges", fragmentServiceConfigurationDisplayContext.isPropagateContributedFragmentChangesEnabled()
						).put(
							"propagateContributedFragmentEntriesChangesURL", fragmentServiceConfigurationDisplayContext.getPropagateContributedFragmentEntriesChangesURL()
						).build()
					%>'
					module="js/apps/FragmentServiceConfigurationApp"
				/>
			</div>
		</div>

		<div class="sheet-footer">
			<aui:button primary="<%= true %>" type="submit" value="save" />

			<aui:button type="cancel" />
		</div>
	</div>
</aui:form>