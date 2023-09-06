/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.internal.upgrade.v1_0_1;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Contact;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ContactRole;
import com.liferay.osb.provisioning.identity.management.provider.ContactIdentityProvider;
import com.liferay.osb.provisioning.koroneiki.web.service.AccountWebService;
import com.liferay.osb.provisioning.koroneiki.web.service.ContactRoleWebService;
import com.liferay.osb.provisioning.koroneiki.web.service.ContactWebService;
import com.liferay.osb.provisioning.search.FilterQuery;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jenny Chen
 */
@Component(service = UpgradeAccountContactRole.class)
public class UpgradeAccountContactRole extends UpgradeProcess {

	public void upgrade(
			String accountKey, String emailAddress, String firstName,
			String lastName, String contactRoleType, String addContactRoleName,
			String deleteContactRoleName, boolean checkEmailDomain)
		throws Exception {

		Account account = _accountWebService.fetchAccount(accountKey);

		if (account == null) {
			_infoLogging(
				StringBundler.concat(
					"Account ", accountKey, " does not exist."));

			return;
		}

		Contact contact = _contactIdentityProvider.fetchContactByEmailAddress(
			emailAddress, true);

		ContactRole addContactRole = _contactRoleWebService.fetchContactRole(
			contactRoleType, addContactRoleName);

		ContactRole deleteContactRole = _contactRoleWebService.fetchContactRole(
			contactRoleType, deleteContactRoleName);

		if (contact != null) {
			List<ContactRole> contactRoles =
				_contactRoleWebService.getAccountContactRoles(
					accountKey, emailAddress, 1, 1000);

			List<String> addContactRoleKeys = new ArrayList<>();
			List<String> deleteContactRoleKeys = new ArrayList<>();

			for (ContactRole contactRole : contactRoles) {
				if (contactRole.equals(deleteContactRole)) {
					if (addContactRole != null) {
						_infoLogging(
							StringBundler.concat(
								accountKey, ", ", emailAddress,
								", update role from ", deleteContactRoleName,
								" to ", addContactRoleName));

						addContactRoleKeys.add(addContactRole.getKey());
						deleteContactRoleKeys.add(contactRole.getKey());
					}
					else {
						_infoLogging(
							StringBundler.concat(
								accountKey, ", ", emailAddress, ", delete ",
								deleteContactRoleName, " Role"));

						deleteContactRoleKeys.add(contactRole.getKey());
					}
				}
				else {
					addContactRoleKeys.add(contactRole.getKey());
				}
			}

			if ((addContactRole != null) &&
				!addContactRoleKeys.contains(addContactRole.getKey())) {

				_infoLogging(
					StringBundler.concat(
						accountKey, ", ", emailAddress, ", add ",
						addContactRoleName, " Role"));

				addContactRoleKeys.add(addContactRole.getKey());
			}

			if (!addContactRoleKeys.isEmpty()) {
				_accountWebService.assignContactRolesByEmailAddress(
					StringPool.BLANK, StringPool.BLANK, accountKey,
					emailAddress, addContactRoleKeys.toArray(new String[0]));
			}

			if (!deleteContactRoleKeys.isEmpty()) {
				_accountWebService.unassignContactRolesByEmailAddress(
					StringPool.BLANK, StringPool.BLANK, accountKey,
					emailAddress, deleteContactRoleKeys.toArray(new String[0]));
			}
		}
		else {
			if (addContactRole != null) {
				_infoLogging(
					StringBundler.concat(
						accountKey, ", ", emailAddress,
						", add new Contact and ", addContactRoleName, " Role"));

				_contactIdentityProvider.createContact(
					emailAddress, firstName, StringPool.BLANK, lastName);

				_accountWebService.assignContactRolesByEmailAddress(
					StringPool.BLANK, StringPool.BLANK, accountKey,
					emailAddress, new String[] {addContactRole.getKey()});
			}
		}

		if (checkEmailDomain) {
			_checkEmailDomain(accountKey, emailAddress);
		}
	}

	@Override
	protected void doUpgrade() {
	}

	private void _checkEmailDomain(String accountKey, String emailAddress)
		throws Exception {

		String emailDomain = emailAddress.substring(
			emailAddress.indexOf("@") + 1);

		FilterQuery filterQuery = new FilterQuery();

		filterQuery.addLambdaEquals(true, "customerAccountKeys", accountKey);

		List<Contact> contacts = _contactWebService.search(
			StringPool.BLANK, filterQuery, 1, 1000, StringPool.BLANK);

		for (Contact contact : contacts) {
			String contactEmailAddress = contact.getEmailAddress();

			String contactEmailDomain = contactEmailAddress.substring(
				contactEmailAddress.indexOf("@") + 1);

			if (!emailDomain.equals(contactEmailDomain)) {
				_infoLogging(
					accountKey +
						", there are contacts with different email domains");

				break;
			}
		}
	}

	private void _infoLogging(String logString) {
		if (_log.isInfoEnabled()) {
			_log.info(logString);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeAccountContactRole.class);

	@Reference
	private AccountWebService _accountWebService;

	@Reference(target = "(provider=okta)")
	private ContactIdentityProvider _contactIdentityProvider;

	@Reference
	private ContactRoleWebService _contactRoleWebService;

	@Reference
	private ContactWebService _contactWebService;

}