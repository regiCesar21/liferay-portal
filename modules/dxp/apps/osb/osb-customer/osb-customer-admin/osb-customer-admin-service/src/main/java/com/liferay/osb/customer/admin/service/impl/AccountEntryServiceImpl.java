/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.admin.service.impl;

import com.liferay.osb.customer.admin.constants.AccountEntryConstants;
import com.liferay.osb.customer.admin.constants.WorkflowConstants;
import com.liferay.osb.customer.admin.exception.NoSuchAccountEntryException;
import com.liferay.osb.customer.admin.model.AccountEntry;
import com.liferay.osb.customer.admin.model.ProductEntry;
import com.liferay.osb.customer.admin.model.impl.AccountEntryImpl;
import com.liferay.osb.customer.admin.model.impl.legacy.OfferingEntryImpl;
import com.liferay.osb.customer.admin.model.legacy.OfferingEntry;
import com.liferay.osb.customer.admin.service.base.AccountEntryServiceBaseImpl;
import com.liferay.osb.customer.admin.service.permission.AccountEntryPermission;
import com.liferay.osb.customer.constants.OSBActionKeys;
import com.liferay.osb.customer.constants.OSBCustomerConstants;
import com.liferay.osb.customer.koroneiki.util.AccountReader;
import com.liferay.osb.customer.koroneiki.web.service.AccountWebService;
import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkDomain;
import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkEntityName;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Amos Fong
 */
@JSONWebService(mode = JSONWebServiceMode.MANUAL)
public class AccountEntryServiceImpl extends AccountEntryServiceBaseImpl {

	public AccountEntry deleteAccountEntry(long accountEntryId)
		throws PortalException {

		AccountEntryPermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.DELETE);

		return accountEntryLocalService.deleteAccountEntry(
			getUserId(), accountEntryId);
	}

	@JSONWebService
	public AccountEntry fetchCorpProjectAccountEntry(String corpProjectUuid)
		throws Exception {

		validateJSONWebServicePermissions();

		List<Account> accounts = _accountWebService.getAccounts(
			ExternalLinkDomain.WEB, ExternalLinkEntityName.WEB_CORP_PROJECT,
			corpProjectUuid, 1, 1);

		if (accounts.isEmpty()) {
			return null;
		}

		return translate(accounts.get(0));
	}

	@JSONWebService
	public AccountEntry fetchKoroneikiAccountEntry(String koroneikiAccountKey)
		throws PortalException {

		validateJSONWebServicePermissions();

		return accountEntryLocalService.fetchKoroneikiAccountEntry(
			koroneikiAccountKey);
	}

	@JSONWebService
	public List<AccountEntry> getAccountEntries(
			String userUuid, long[] productEntryIds)
		throws Exception {

		validateJSONWebServicePermissions();

		StringBundler sb = new StringBundler();

		sb.append("customerContactUuids/any(s:s eq '");
		sb.append(userUuid);
		sb.append("') and productKeys/any(s:");

		for (int i = 0; i < productEntryIds.length; i++) {
			ProductEntry productEntry =
				productEntryLocalService.getProductEntry(productEntryIds[i]);

			sb.append("s eq '");
			sb.append(productEntry.getKoroneikiProductKey());
			sb.append("'");

			if ((i + 1) < productEntryIds.length) {
				sb.append(" or ");
			}
		}

		sb.append(")");

		List<Account> accounts = _accountWebService.search(
			StringPool.BLANK, sb.toString(), 1, 1000, null);

		List<AccountEntry> accountEntries = new ArrayList<>();

		for (Account account : accounts) {
			accountEntries.add(translate(account));
		}

		return accountEntries;
	}

	public AccountEntry getAccountEntry(long accountEntryId)
		throws PortalException {

		AccountEntryPermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.VIEW);

		return accountEntryLocalService.getAccountEntry(accountEntryId);
	}

	@JSONWebService
	public AccountEntry getAccountEntryByCode(String code)
		throws PortalException {

		validateJSONWebServicePermissions();

		try {
			List<Account> accounts = _accountWebService.search(
				StringPool.BLANK, "code eq '" + code + "'", 1, 10, null);

			for (Account account : accounts) {
				AccountEntry accountEntry =
					accountEntryLocalService.fetchKoroneikiAccountEntry(
						account.getKey());

				if (accountEntry != null) {
					return accountEntry;
				}
			}

			throw new NoSuchAccountEntryException();
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	@JSONWebService
	public AccountEntry getCorpProjectAccountEntry(String corpProjectUuid)
		throws Exception {

		validateJSONWebServicePermissions();

		AccountEntry accountEntry = fetchCorpProjectAccountEntry(
			corpProjectUuid);

		if (accountEntry == null) {
			throw new NoSuchAccountEntryException();
		}

		return accountEntry;
	}

	public void syncToZendesk(String koroneikiAccountKey) throws Exception {
		validateJSONWebServicePermissions();

		Account account = _accountWebService.fetchAccount(koroneikiAccountKey);

		if (account != null) {
			AccountEntry accountEntry =
				accountEntryLocalService.fetchKoroneikiAccountEntry(
					koroneikiAccountKey);

			if (accountEntry != null) {
				accountEntryLocalService.updateAccountEntry(
					accountEntry.getAccountEntryId(),
					_accountReader.isActiveSupport(account.getEntitlements()),
					_accountReader.isActiveTicketSupport(
						account.getEntitlements()),
					_accountReader.getStatus(account));
			}
			else {
				List<ProductPurchase> productPurchases =
					_accountReader.getProductPurchases(koroneikiAccountKey);

				if (_accountReader.isSyncAccount(productPurchases)) {
					accountEntryLocalService.addAccountEntry(
						OSBCustomerConstants.USER_DEFAULT_USER_ID,
						account.getKey(),
						_accountReader.getDossieraAccountKey(
							account.getExternalLinks()),
						_accountReader.getCorpProjectUuid(
							account.getExternalLinks()),
						_accountReader.getCorpProjectId(
							account.getExternalLinks()),
						account.getName(), account.getCode(), null,
						_accountReader.isActiveSupport(
							account.getEntitlements()),
						_accountReader.isActiveTicketSupport(
							account.getEntitlements()),
						_accountReader.getStatus(account),
						new String[] {
							AccountEntryConstants.getLanguageId(
								account.getLanguage())
						});
				}
			}

			Message message = new Message();

			message.put("koroneikiAccountKey", koroneikiAccountKey);

			MessageBusUtil.sendMessage("liferay/zendesk_account_sync", message);
		}
	}

	public AccountEntry updateInstructions(
			long accountEntryId, String instructions)
		throws PortalException {

		AccountEntryPermission.check(
			getPermissionChecker(), accountEntryId,
			OSBActionKeys.UPDATE_ACCOUNT_INSTRUCTIONS);

		return accountEntryLocalService.updateInstructions(
			getUserId(), accountEntryId, instructions);
	}

	@JSONWebService
	public AccountEntry updateInstructions(
			String koroneikiAccountKey, String instructions)
		throws PortalException {

		validateJSONWebServicePermissions();

		AccountEntry accountEntry =
			accountEntryLocalService.getKoroneikiAccountEntry(
				koroneikiAccountKey);

		return accountEntryLocalService.updateInstructions(
			getUserId(), accountEntry.getAccountEntryId(), instructions);
	}

	@JSONWebService
	public AccountEntry updateLanguageId(
			String koroneikiAccountKey, String languageId)
		throws PortalException {

		validateJSONWebServicePermissions();

		AccountEntry accountEntry =
			accountEntryLocalService.getKoroneikiAccountEntry(
				koroneikiAccountKey);

		accountEntryLanguageLocalService.setAccountEntryLanguageIds(
			accountEntry.getAccountEntryId(), new String[] {languageId});

		Message message = new Message();

		message.put("koroneikiAccountKey", koroneikiAccountKey);

		MessageBusUtil.sendMessage(
			"liferay/zendesk_account_language_sync", message);

		return accountEntry;
	}

	protected String getExternalLinkEntityId(
		Account account, String externalLinkDomain,
		String externalLinkEntityName) {

		ExternalLink[] externalLinks = account.getExternalLinks();

		if (externalLinks != null) {
			for (ExternalLink externalLink : externalLinks) {
				String domain = externalLink.getDomain();
				String entityName = externalLink.getEntityName();

				if (domain.equals(externalLinkDomain) &&
					entityName.equals(externalLinkEntityName)) {

					return externalLink.getEntityId();
				}
			}
		}

		return null;
	}

	protected AccountEntry translate(Account account) throws Exception {
		AccountEntry accountEntry = new AccountEntryImpl();

		if (Validator.isNotNull(account.getParentAccountKey())) {
			Account parentAccount = _accountWebService.getAccount(
				account.getParentAccountKey());

			accountEntry.setDossieraAccountKey(
				getExternalLinkEntityId(
					parentAccount, ExternalLinkDomain.SALESFORCE,
					ExternalLinkEntityName.SALESFORCE_ACCOUNT));
			accountEntry.setCorpEntryName(parentAccount.getName());
		}

		accountEntry.setKoroneikiAccountKey(account.getKey());
		accountEntry.setCorpProjectUuid(
			getExternalLinkEntityId(
				account, ExternalLinkDomain.WEB,
				ExternalLinkEntityName.WEB_CORP_PROJECT));
		accountEntry.setName(account.getName());

		if (account.getStatus() == Account.Status.ACTIVE) {
			accountEntry.setStatus(WorkflowConstants.STATUS_APPROVED);
		}
		else if (account.getStatus() == Account.Status.CLOSED) {
			accountEntry.setStatus(WorkflowConstants.STATUS_CLOSED);
		}

		ProductPurchase[] productPurchases = account.getProductPurchases();

		if (productPurchases != null) {
			List<OfferingEntry> offeringEntries = new ArrayList<>();

			for (ProductPurchase productPurchase : productPurchases) {
				ProductEntry productEntry =
					productEntryLocalService.getProductEntryByKoroneikiKey(
						productPurchase.getProductKey());

				OfferingEntry offeringEntry = new OfferingEntryImpl();

				offeringEntry.setSupportEndDate(productPurchase.getEndDate());
				offeringEntry.setProductEntryId(
					productEntry.getProductEntryId());
				offeringEntry.setQuantity(productPurchase.getQuantity());
				offeringEntry.setStartDate(productPurchase.getStartDate());

				offeringEntries.add(offeringEntry);
			}

			accountEntry.setOfferingEntries(offeringEntries);
		}

		return accountEntry;
	}

	protected void validateJSONWebServicePermissions() throws PortalException {
		if (!roleLocalService.hasUserRole(
				getUserId(), OSBCustomerConstants.ROLE_OSB_ADMINISTRATOR_ID)) {

			throw new PrincipalException();
		}
	}

	@ServiceReference(type = AccountReader.class)
	private AccountReader _accountReader;

	@ServiceReference(type = AccountWebService.class)
	private AccountWebService _accountWebService;

}