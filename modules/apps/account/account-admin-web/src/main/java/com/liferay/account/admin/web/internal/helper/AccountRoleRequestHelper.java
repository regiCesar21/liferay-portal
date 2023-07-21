/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.helper;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.application.list.display.context.logic.PersonalMenuEntryHelper;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.product.navigation.personal.menu.PersonalMenuEntry;
import com.liferay.roles.admin.constants.RolesAdminWebKeys;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = AccountRoleRequestHelper.class)
public class AccountRoleRequestHelper {

	public void setRequestAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PANEL_APP_REGISTRY, _panelAppRegistry);
		httpServletRequest.setAttribute(
			RolesAdminWebKeys.CURRENT_ROLE_TYPE, _accountRoleTypeContributor);
		httpServletRequest.setAttribute(
			RolesAdminWebKeys.SHOW_NAV_TABS, Boolean.FALSE);

		PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(
			_panelAppRegistry, _panelCategoryRegistry);

		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY_HELPER, panelCategoryHelper);

		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY,
			_panelCategoryRegistry);

		PersonalMenuEntryHelper personalMenuEntryHelper =
			new PersonalMenuEntryHelper(_personalMenuEntries);

		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PERSONAL_MENU_ENTRY_HELPER,
			personalMenuEntryHelper);
	}

	public void setRequestAttributes(PortletRequest portletRequest) {
		setRequestAttributes(_portal.getHttpServletRequest(portletRequest));
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "_removePersonalMenuEntry"
	)
	private void _addPersonalMenuEntry(PersonalMenuEntry personalMenuEntry) {
		_personalMenuEntries.add(personalMenuEntry);
	}

	private void _removePersonalMenuEntry(PersonalMenuEntry personalMenuEntry) {
		_personalMenuEntries.remove(personalMenuEntry);
	}

	@Reference(
		target = "(component.name=com.liferay.account.internal.roles.admin.role.type.contributor.AccountRoleTypeContributor)"
	)
	private RoleTypeContributor _accountRoleTypeContributor;

	@Reference
	private PanelAppRegistry _panelAppRegistry;

	@Reference
	private PanelCategoryRegistry _panelCategoryRegistry;

	private final List<PersonalMenuEntry> _personalMenuEntries =
		new CopyOnWriteArrayList<>();

	@Reference
	private Portal _portal;

}