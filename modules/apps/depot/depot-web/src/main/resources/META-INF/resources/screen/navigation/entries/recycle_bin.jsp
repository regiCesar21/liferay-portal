<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DepotEntry depotEntry = (DepotEntry)request.getAttribute(DepotAdminWebKeys.DEPOT_ENTRY);

Group group = depotEntry.getGroup();

UnicodeProperties typeSettingsProperties = group.getTypeSettingsProperties();

boolean groupTrashEnabled = PropertiesParamUtil.getBoolean(typeSettingsProperties, request, "trashEnabled", true);

int trashEntriesMaxAge = PropertiesParamUtil.getInteger(typeSettingsProperties, request, "trashEntriesMaxAge", PrefsPropsUtil.getInteger(depotEntry.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE));
%>

<liferay-frontend:fieldset
	collapsible="<%= true %>"
	cssClass="panel-group-flush"
	label='<%= LanguageUtil.get(request, "recycle-bin") %>'
>
	<aui:input id="trashEnabled" label="enable-recycle-bin" name="TypeSettingsProperties--trashEnabled--" type="toggle-switch" value="<%= groupTrashEnabled %>" />

	<div class="trash-entries-max-age">
		<aui:input disabled="<%= !groupTrashEnabled %>" helpMessage="trash-entries-max-age-help" label="trash-entries-max-age" name="TypeSettingsProperties--trashEntriesMaxAge--" type="text" value="<%= ((trashEntriesMaxAge % 1) == 0) ? GetterUtil.getInteger(trashEntriesMaxAge) : String.valueOf(trashEntriesMaxAge) %>">
			<aui:validator name="min"><%= PropsValues.TRASH_ENTRY_CHECK_INTERVAL %></aui:validator>
		</aui:input>
	</div>

	<script>
		var trashEnabledCheckbox = document.getElementById(
			'<portlet:namespace />trashEnabled'
		);

		if (trashEnabledCheckbox) {
			var trashEnabledDefault = trashEnabledCheckbox.checked;

			trashEnabledCheckbox.addEventListener('change', function (event) {
				var trashEnabled = trashEnabledCheckbox.checked;

				if (!trashEnabled && trashEnabledDefault) {
					if (
						!confirm(
							'<%= HtmlUtil.escapeJS(LanguageUtil.get(request, "disabling-the-recycle-bin-prevents-the-restoring-of-content-that-has-been-moved-to-the-recycle-bin")) %>'
						)
					) {
						trashEnabledCheckbox.checked = true;

						trashEnabled = true;
					}
				}

				var trashEntriesMaxAge = document.getElementById(
					'<portlet:namespace />trashEntriesMaxAge'
				);

				if (trashEntriesMaxAge) {
					Liferay.Util.toggleDisabled(trashEntriesMaxAge, !trashEnabled);
				}
			});
		}
	</script>
</liferay-frontend:fieldset>