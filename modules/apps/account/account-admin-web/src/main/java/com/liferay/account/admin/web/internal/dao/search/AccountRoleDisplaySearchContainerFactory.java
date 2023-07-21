/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.dao.search;

import com.liferay.account.admin.web.internal.display.AccountRoleDisplay;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.comparator.RoleNameComparator;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;

/**
 * @author Pei-Jung Lan
 */
public class AccountRoleDisplaySearchContainerFactory {

	public static SearchContainer<AccountRoleDisplay> create(
		long accountEntryId, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		SearchContainer<AccountRoleDisplay> searchContainer =
			new SearchContainer(
				liferayPortletRequest,
				PortletURLUtil.getCurrent(
					liferayPortletRequest, liferayPortletResponse),
				null, "there-are-no-roles");

		searchContainer.setId("accountRoles");
		searchContainer.setOrderByCol("name");

		String orderByType = ParamUtil.getString(
			liferayPortletRequest, "orderByType", "asc");

		searchContainer.setOrderByType(orderByType);

		searchContainer.setRowChecker(
			new AccountRoleRowChecker(liferayPortletResponse));

		String keywords = ParamUtil.getString(
			liferayPortletRequest, "keywords");

		BaseModelSearchResult<AccountRole> baseModelSearchResult =
			AccountRoleLocalServiceUtil.searchAccountRoles(
				new long[] {
					accountEntryId, AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT
				},
				keywords, searchContainer.getStart(), searchContainer.getEnd(),
				new RoleNameComparator(orderByType.equals("asc")));

		List<AccountRoleDisplay> accountRoleDisplays = TransformUtil.transform(
			baseModelSearchResult.getBaseModels(),
			accountRole -> {
				if (!AccountRoleConstants.isImpliedRole(
						accountRole.getRole())) {

					return AccountRoleDisplay.of(accountRole);
				}

				return null;
			});

		searchContainer.setResults(accountRoleDisplays);
		searchContainer.setTotal(accountRoleDisplays.size());

		return searchContainer;
	}

}