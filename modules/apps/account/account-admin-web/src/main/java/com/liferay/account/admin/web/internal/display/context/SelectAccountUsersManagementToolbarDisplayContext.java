/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.display.context;

import com.liferay.account.admin.web.internal.display.AccountUserDisplay;
import com.liferay.account.configuration.AccountEntryEmailDomainsConfiguration;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class SelectAccountUsersManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public SelectAccountUsersManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<AccountUserDisplay> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("navigation", (String)null);
		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public CreationMenu getCreationMenu() {
		if (!isShowCreateButton()) {
			return null;
		}

		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addAccountEntryUser");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "new-user"));
			}
		).build();
	}

	@Override
	public String getDefaultEventHandler() {
		if (!isShowCreateButton()) {
			return null;
		}

		return "SELECT_ACCOUNT_USERS_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public String getNavigation() {
		return ParamUtil.getString(
			liferayPortletRequest, getNavigationParam(),
			ArrayUtil.isEmpty(getNavigationKeys()) ? "all-users" :
				getNavigationKeys()[0]);
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public Boolean isDisabled() {
		return false;
	}

	public boolean isOpenModalOnRedirect() {
		return ParamUtil.getBoolean(httpServletRequest, "openModalOnRedirect");
	}

	@Override
	public Boolean isSelectable() {
		return !isSingleSelect();
	}

	public boolean isShowCreateButton() {
		return ParamUtil.getBoolean(liferayPortletRequest, "showCreateButton");
	}

	public boolean isShowFilter() {
		return ParamUtil.getBoolean(liferayPortletRequest, "showFilter", true);
	}

	public boolean isSingleSelect() {
		return ParamUtil.getBoolean(liferayPortletRequest, "singleSelect");
	}

	@Override
	protected String[] getNavigationKeys() {
		if (!isShowFilter()) {
			return new String[0];
		}

		try {
			AccountEntryEmailDomainsConfiguration
				accountEntryEmailDomainsConfiguration =
					ConfigurationProviderUtil.getCompanyConfiguration(
						AccountEntryEmailDomainsConfiguration.class,
						PortalUtil.getCompanyId(liferayPortletRequest));

			if (accountEntryEmailDomainsConfiguration.
					enableEmailDomainValidation()) {

				return new String[] {"valid-domain-users", "all-users"};
			}
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(configurationException, configurationException);
			}
		}

		return new String[] {"all-users"};
	}

	@Override
	protected String getOrderByCol() {
		return ParamUtil.getString(
			liferayPortletRequest, getOrderByColParam(), "last-name");
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"first-name", "last-name", "email-address"};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SelectAccountUsersManagementToolbarDisplayContext.class);

}