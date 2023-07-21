/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.rest.internal.dto.v1_0.converter;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.rest.dto.v1_0.Account;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.TransformUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	property = "dto.class.name=com.liferay.account.model.AccountEntry",
	service = {AccountResourceDTOConverter.class, DTOConverter.class}
)
public class AccountResourceDTOConverter
	implements DTOConverter<AccountEntry, Account> {

	public long getAccountEntryId(String externalReferenceCode)
		throws Exception {

		AccountEntry accountEntry = getObject(externalReferenceCode);

		return accountEntry.getAccountEntryId();
	}

	@Override
	public String getContentType() {
		return Account.class.getSimpleName();
	}

	@Override
	public AccountEntry getObject(String externalReferenceCode)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryLocalService.fetchAccountEntryByReferenceCode(
				CompanyThreadLocal.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			accountEntry = _accountEntryLocalService.getAccountEntry(
				GetterUtil.getLong(externalReferenceCode));
		}

		return accountEntry;
	}

	@Override
	public Account toDTO(
		DTOConverterContext dtoConverterContext, AccountEntry accountEntry) {

		if (accountEntry == null) {
			return null;
		}

		return new Account() {
			{
				description = accountEntry.getDescription();
				domains = StringUtil.split(accountEntry.getDomains());
				externalReferenceCode = accountEntry.getExternalReferenceCode();
				id = accountEntry.getAccountEntryId();
				name = accountEntry.getName();
				organizationIds = TransformUtil.transformToArray(
					_accountEntryOrganizationRelLocalService.
						getAccountEntryOrganizationRels(
							accountEntry.getAccountEntryId()),
					AccountEntryOrganizationRel::getOrganizationId, Long.class);
				parentAccountId = accountEntry.getParentAccountEntryId();
				status = accountEntry.getStatus();
			}
		};
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

}