/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.permission;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.search.spi.model.permission.SearchPermissionFilterContributor;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.kernel.model.User",
	service = SearchPermissionFilterContributor.class
)
public class UserSearchPermissionFilterContributor
	implements SearchPermissionFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, long companyId, long[] groupIds,
		long userId, PermissionChecker permissionChecker, String className) {

		if (!className.equals(User.class.getName())) {
			return;
		}

		try {
			TermsFilter termsFilter = new TermsFilter("accountEntryIds");

			UserBag userBag = permissionChecker.getUserBag();

			long[] userOrgIds = userBag.getUserOrgIds();

			for (long userOrgId : userOrgIds) {
				if (OrganizationPermissionUtil.contains(
						permissionChecker, userOrgId,
						AccountActionKeys.MANAGE_ACCOUNTS)) {

					List<AccountEntryOrganizationRel>
						accountEntryOrganizationRels =
							_accountEntryOrganizationRelLocalService.
								getAccountEntryOrganizationRelsByOrganizationId(
									userOrgId);

					for (AccountEntryOrganizationRel
							accountEntryOrganizationRel :
								accountEntryOrganizationRels) {

						termsFilter.addValue(
							String.valueOf(
								accountEntryOrganizationRel.
									getAccountEntryId()));
					}
				}
			}

			if (!termsFilter.isEmpty()) {
				booleanFilter.add(termsFilter);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}
	}

	@Reference
	protected RoleLocalService roleLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		UserSearchPermissionFilterContributor.class);

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

}