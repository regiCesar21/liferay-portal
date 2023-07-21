/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.rest.internal.dto.v1_0.converter;

import com.liferay.account.rest.dto.v1_0.Account;
import com.liferay.account.rest.dto.v1_0.AccountUser;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.User",
	service = {AccountUserResourceDTOConverter.class, DTOConverter.class}
)
public class AccountUserResourceDTOConverter
	implements DTOConverter<User, AccountUser> {

	@Override
	public String getContentType() {
		return Account.class.getSimpleName();
	}

	@Override
	public User getObject(String externalReferenceCode) throws Exception {
		User user = _userLocalService.fetchUserByReferenceCode(
			CompanyThreadLocal.getCompanyId(), externalReferenceCode);

		if (user == null) {
			user = _userLocalService.getUser(
				GetterUtil.getLong(externalReferenceCode));
		}

		return user;
	}

	public long getUserId(String externalReferenceCode) throws Exception {
		User user = getObject(externalReferenceCode);

		return user.getUserId();
	}

	@Override
	public AccountUser toDTO(DTOConverterContext dtoConverterContext, User user)
		throws Exception {

		if (user == null) {
			return null;
		}

		Contact contact = user.getContact();

		return new AccountUser() {
			{
				emailAddress = user.getEmailAddress();
				externalReferenceCode = user.getExternalReferenceCode();
				firstName = user.getFirstName();
				id = user.getUserId();
				lastName = user.getLastName();
				middleName = user.getMiddleName();
				screenName = user.getScreenName();

				setPrefix(
					() -> {
						long prefixId = contact.getPrefixId();

						if (prefixId <= 0) {
							return null;
						}

						ListType prefixListType =
							_listTypeLocalService.getListType(prefixId);

						return prefixListType.getName();
					});
				setSuffix(
					() -> {
						long suffixId = contact.getSuffixId();

						if (suffixId <= 0) {
							return null;
						}

						ListType suffixListType =
							_listTypeLocalService.getListType(suffixId);

						return suffixListType.getName();
					});
			}
		};
	}

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}