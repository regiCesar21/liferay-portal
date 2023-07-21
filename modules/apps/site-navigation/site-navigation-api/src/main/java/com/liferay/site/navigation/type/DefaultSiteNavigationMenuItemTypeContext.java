/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.type;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;

import java.util.Optional;

/**
 * @author Pavel Savinov
 */
public class DefaultSiteNavigationMenuItemTypeContext
	implements SiteNavigationMenuItemTypeContext {

	public DefaultSiteNavigationMenuItemTypeContext(Company company) {
		_company = company;
	}

	public DefaultSiteNavigationMenuItemTypeContext(Group group) {
		_group = group;

		_company = CompanyLocalServiceUtil.fetchCompany(group.getCompanyId());
	}

	@Override
	public Company getCompany() {
		return _company;
	}

	@Override
	public Optional<Group> getGroupOptional() {
		return Optional.of(_group);
	}

	private final Company _company;
	private Group _group;

}