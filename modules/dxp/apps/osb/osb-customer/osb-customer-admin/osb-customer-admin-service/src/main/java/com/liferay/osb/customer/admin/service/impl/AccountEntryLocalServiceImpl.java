/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.admin.service.impl;

import com.liferay.osb.customer.admin.constants.AuditEntryConstants;
import com.liferay.osb.customer.admin.constants.VisibilityConstants;
import com.liferay.osb.customer.admin.exception.AccountEntryKoroneikiAccountKeyException;
import com.liferay.osb.customer.admin.model.AccountAttachment;
import com.liferay.osb.customer.admin.model.AccountEntry;
import com.liferay.osb.customer.admin.model.AccountEnvironment;
import com.liferay.osb.customer.admin.service.base.AccountEntryLocalServiceBaseImpl;
import com.liferay.osb.customer.constants.OSBCustomerConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 * @author Amos Fong
 */
public class AccountEntryLocalServiceImpl
	extends AccountEntryLocalServiceBaseImpl {

	public AccountEntry addAccountEntry(
			long userId, String koroneikiAccountKey, String dossieraAccountKey,
			String corpProjectUuid, long corpProjectId, String name,
			String code, String instructions, boolean activeSupport,
			boolean activeTicketSupport, int status, String[] languageIds)
		throws PortalException {

		validate(0, koroneikiAccountKey);

		return doAddAccountEntry(
			userId, koroneikiAccountKey, dossieraAccountKey, corpProjectUuid,
			corpProjectId, name, code, instructions, activeSupport,
			activeTicketSupport, status, languageIds);
	}

	@Override
	public AccountEntry deleteAccountEntry(long userId, long accountEntryId)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		AccountEntry accountEntry = accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		accountEntryPersistence.remove(accountEntry);

		// Account attachments

		List<AccountAttachment> accountAttachments =
			accountAttachmentPersistence.findByAccountEntryId(accountEntryId);

		for (AccountAttachment accountAttachment : accountAttachments) {
			accountAttachmentLocalService.deleteAccountAttachment(
				accountAttachment);
		}

		// Account environments

		List<AccountEnvironment> accountEnvironments =
			accountEnvironmentPersistence.findByAccountEntryId(accountEntryId);

		for (AccountEnvironment accountEnvironment : accountEnvironments) {
			accountEnvironmentLocalService.deleteAccountEnvironment(
				accountEnvironment.getAccountEnvironmentId());
		}

		// Account languages

		accountEntryLanguagePersistence.removeByAccountEntryId(accountEntryId);

		// External ids

		long classNameId = classNameLocalService.getClassNameId(
			AccountEntry.class.getName());

		externalIdMapperPersistence.removeByC_C(classNameId, accountEntryId);

		// Audit entry

		auditEntryLocalService.addAuditEntry(
			userId, user.getFullName(), new Date(), classNameId, accountEntryId,
			0, classNameId, accountEntryId, AuditEntryConstants.ACTION_DELETE,
			AuditEntryConstants.FIELD_NOT_APPLICABLE, VisibilityConstants.ADMIN,
			accountEntry.getName(),
			String.valueOf(accountEntry.getAccountEntryId()), StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK);

		return accountEntry;
	}

	public AccountEntry deleteAccountEntry(String koroneikiAccountKey)
		throws PortalException {

		AccountEntry accountEntry =
			accountEntryPersistence.findByKoroneikiAccountKey(
				koroneikiAccountKey);

		return deleteAccountEntry(
			OSBCustomerConstants.USER_DEFAULT_USER_ID,
			accountEntry.getAccountEntryId());
	}

	public AccountEntry fetchCorpProjectAccountEntry(String corpProjectUuid) {
		return accountEntryPersistence.fetchByCorpProjectUuid(corpProjectUuid);
	}

	@Override
	public AccountEntry fetchKoroneikiAccountEntry(String koroneikiAccountKey) {
		return accountEntryPersistence.fetchByKoroneikiAccountKey(
			koroneikiAccountKey);
	}

	public List<AccountEntry> getAccountEntries(String dossieraAccountKey) {
		return accountEntryPersistence.findByDossieraAccountKey(
			dossieraAccountKey);
	}

	@Override
	public AccountEntry getAccountEntry(long accountEntryId)
		throws PortalException {

		return accountEntryPersistence.findByPrimaryKey(accountEntryId);
	}

	public List<AccountEntry> getExpiredSupportAccountEntries(
		int start, int end) {

		return accountEntryFinder.findByExpiredSupport(start, end);
	}

	@Override
	public AccountEntry getKoroneikiAccountEntry(String koroneikiAccountKey)
		throws PortalException {

		return accountEntryPersistence.findByKoroneikiAccountKey(
			koroneikiAccountKey);
	}

	public List<AccountEntry> search(
		String keywords, LinkedHashMap<String, Object> params, int start,
		int end, OrderByComparator obc) {

		return accountEntryFinder.findByKeywords(
			keywords, params, start, end, obc);
	}

	public List<AccountEntry> search(String name, String code) {
		return accountEntryPersistence.findByN_C(name, code);
	}

	public List<AccountEntry> search(
		String koroneikiAccountKey, String dossieraAccountKey, String name,
		String code, int[] statuses, String instructions,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end, OrderByComparator obc) {

		return accountEntryFinder.findByKAK_DAK_N_C_I_S(
			koroneikiAccountKey, dossieraAccountKey, name, code, statuses,
			instructions, params, andOperator, start, end, obc);
	}

	public int searchCount(
		String keywords, LinkedHashMap<String, Object> params) {

		return accountEntryFinder.countByKeywords(keywords, params);
	}

	public int searchCount(
		String koroneikiAccountKey, String dossieraAccountKey, String name,
		String code, int[] statuses, String instructions,
		LinkedHashMap<String, Object> params, boolean andOperator) {

		return accountEntryFinder.countByKAK_DAK_N_C_I_S(
			koroneikiAccountKey, dossieraAccountKey, name, code, statuses,
			instructions, params, andOperator);
	}

	public AccountEntry updateAccountEntry(
			long accountEntryId, boolean activeSupport,
			boolean activeTicketSupport, int status)
		throws PortalException {

		Date now = new Date();

		AccountEntry accountEntry = accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		AccountEntry oldAccountEntry = (AccountEntry)accountEntry.clone();

		accountEntry.setModifiedDate(now);
		accountEntry.setActiveSupport(activeSupport);
		accountEntry.setActiveTicketSupport(activeTicketSupport);
		accountEntry.setStatus(status);

		accountEntry = accountEntryPersistence.update(accountEntry);

		updateAuditEntry(
			OSBCustomerConstants.USER_DEFAULT_USER_ID, StringPool.BLANK,
			oldAccountEntry, accountEntry);

		return accountEntry;
	}

	public AccountEntry updateAccountEntry(
			long userId, long accountEntryId, String koroneikiAccountKey,
			String dossieraAccountKey)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		AccountEntry accountEntry = accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		AccountEntry oldAccountEntry = (AccountEntry)accountEntry.clone();

		validate(accountEntryId, koroneikiAccountKey);

		accountEntry.setModifiedUserId(user.getUserId());
		accountEntry.setModifiedUserName(user.getFullName());
		accountEntry.setModifiedDate(new Date());
		accountEntry.setKoroneikiAccountKey(koroneikiAccountKey);
		accountEntry.setDossieraAccountKey(dossieraAccountKey);

		accountEntry = accountEntryPersistence.update(accountEntry);

		updateAuditEntry(
			user.getUserId(), user.getFullName(), oldAccountEntry,
			accountEntry);

		return accountEntry;
	}

	public AccountEntry updateAccountEntry(
			long userId, long accountEntryId, String koroneikiAccountKey,
			String dossieraAccountKey, String corpProjectUuid,
			long corpProjectId, String name, String code, String instructions,
			boolean activeSupport, boolean activeTicketSupport, int status,
			String[] languageIds)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		AccountEntry accountEntry = accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		AccountEntry oldAccountEntry = (AccountEntry)accountEntry.clone();

		validate(accountEntryId, koroneikiAccountKey);

		accountEntry.setModifiedUserId(user.getUserId());
		accountEntry.setModifiedUserName(user.getFullName());
		accountEntry.setModifiedDate(new Date());
		accountEntry.setKoroneikiAccountKey(koroneikiAccountKey);
		accountEntry.setDossieraAccountKey(dossieraAccountKey);
		accountEntry.setCorpProjectUuid(corpProjectUuid);
		accountEntry.setCorpProjectId(corpProjectId);
		accountEntry.setName(name);
		accountEntry.setCode(code);
		accountEntry.setInstructions(instructions);
		accountEntry.setActiveSupport(activeSupport);
		accountEntry.setActiveTicketSupport(activeTicketSupport);
		accountEntry.setStatus(status);

		if (ArrayUtil.isNotEmpty(languageIds)) {
			accountEntryLanguageLocalService.setAccountEntryLanguageIds(
				accountEntryId, languageIds);
		}

		accountEntry = accountEntryPersistence.update(accountEntry);

		updateAuditEntry(
			user.getUserId(), user.getFullName(), oldAccountEntry,
			accountEntry);

		return accountEntry;
	}

	public AccountEntry updateInstructions(
			long userId, long accountEntryId, String instructions)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		AccountEntry accountEntry = accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		AccountEntry oldAccountEntry = (AccountEntry)accountEntry.clone();

		accountEntry.setInstructions(instructions);

		accountEntry = accountEntryPersistence.update(accountEntry);

		updateAuditEntry(
			user.getUserId(), user.getFullName(), oldAccountEntry,
			accountEntry);

		return accountEntry;
	}

	public void updateLastZendeskAuditDate(
			long userId, long accountEntryId, String auditLabel,
			String auditValue)
		throws PortalException {

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		AccountEntry accountEntry = accountEntryPersistence.findByPrimaryKey(
			accountEntryId);

		accountEntry.setLastZendeskAuditDate(now);

		accountEntryPersistence.update(accountEntry);

		if (Validator.isNotNull(auditLabel) ||
			Validator.isNotNull(auditValue)) {

			long classNameId = classNameLocalService.getClassNameId(
				AccountEntry.class.getName());

			auditEntryLocalService.addAuditEntry(
				userId, user.getFullName(), now, classNameId, accountEntryId, 0,
				classNameId, accountEntryId, AuditEntryConstants.ACTION_AUDIT,
				AuditEntryConstants.FIELD_NOT_APPLICABLE,
				VisibilityConstants.ADMIN, auditLabel, auditValue,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, true,
				false);
		}
	}

	protected AccountEntry doAddAccountEntry(
			long userId, String koroneikiAccountKey, String dossieraAccountKey,
			String corpProjectUuid, long corpProjectId, String name,
			String code, String instructions, boolean activeSupport,
			boolean activeTicketSupport, int status, String[] languageIds)
		throws PortalException {

		// Account entry

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		long accountEntryId = counterLocalService.increment();

		AccountEntry accountEntry = accountEntryPersistence.create(
			accountEntryId);

		accountEntry.setCompanyId(OSBCustomerConstants.COMPANY_ID);
		accountEntry.setUserId(user.getUserId());
		accountEntry.setUserName(user.getFullName());
		accountEntry.setCreateDate(now);
		accountEntry.setModifiedUserId(user.getUserId());
		accountEntry.setModifiedUserName(user.getFullName());
		accountEntry.setModifiedDate(now);
		accountEntry.setKoroneikiAccountKey(koroneikiAccountKey);
		accountEntry.setDossieraAccountKey(dossieraAccountKey);
		accountEntry.setCorpProjectUuid(corpProjectUuid);
		accountEntry.setCorpProjectId(corpProjectId);
		accountEntry.setName(name);
		accountEntry.setCode(code);
		accountEntry.setInstructions(instructions);
		accountEntry.setActiveSupport(activeSupport);
		accountEntry.setActiveTicketSupport(activeTicketSupport);
		accountEntry.setStatus(status);

		// Languages

		if (ArrayUtil.isNotEmpty(languageIds)) {
			accountEntryLanguageLocalService.setAccountEntryLanguageIds(
				accountEntryId, languageIds);
		}

		return accountEntryPersistence.update(accountEntry);
	}

	protected String formatLanguageIds(String[] languageIds) {
		List<String> formattedLanguageIds = new ArrayList<>(languageIds.length);

		for (String languageId : languageIds) {
			Locale locale = LocaleUtil.fromLanguageId(languageId);

			formattedLanguageIds.add(locale.getDisplayName());
		}

		return StringUtil.merge(formattedLanguageIds);
	}

	protected void updateAuditEntry(
			long userId, String userName, AccountEntry oldAccountEntry,
			AccountEntry accountEntry)
		throws PortalException {

		long classPK = accountEntry.getAccountEntryId();
		Date createDate = accountEntry.getModifiedDate();
		long classNameId = classNameLocalService.getClassNameId(
			AccountEntry.class.getName());
		long auditSetId = auditEntryLocalService.getNextAuditSetId(
			AccountEntry.class.getName(), classPK);
		int auditAction = AuditEntryConstants.ACTION_UPDATE;

		String oldKoroneikiAccountKey =
			oldAccountEntry.getKoroneikiAccountKey();

		if (!oldKoroneikiAccountKey.equals(
				accountEntry.getKoroneikiAccountKey())) {

			auditEntryLocalService.addAuditEntry(
				userId, userName, createDate, classNameId, classPK, auditSetId,
				classNameId, classPK, auditAction,
				AuditEntryConstants.FIELD_KORONEIKI_ACCOUNT_KEY,
				VisibilityConstants.LIFERAY_INC, StringPool.BLANK,
				oldKoroneikiAccountKey, StringPool.BLANK,
				accountEntry.getKoroneikiAccountKey(), StringPool.BLANK);
		}

		String oldDossieraAccountKey = oldAccountEntry.getDossieraAccountKey();

		if (!oldDossieraAccountKey.equals(
				accountEntry.getDossieraAccountKey())) {

			auditEntryLocalService.addAuditEntry(
				userId, userName, createDate, classNameId, classPK, auditSetId,
				classNameId, classPK, auditAction,
				AuditEntryConstants.FIELD_DOSSIERA_ACCOUNT_KEY,
				VisibilityConstants.LIFERAY_INC, StringPool.BLANK,
				oldDossieraAccountKey, StringPool.BLANK,
				accountEntry.getDossieraAccountKey(), StringPool.BLANK);
		}

		String oldInstructions = oldAccountEntry.getInstructions();

		if (!oldInstructions.equals(accountEntry.getInstructions())) {
			auditEntryLocalService.addAuditEntry(
				userId, userName, createDate, classNameId, classPK, auditSetId,
				classNameId, classPK, auditAction,
				AuditEntryConstants.FIELD_INSTRUCTIONS,
				VisibilityConstants.LIFERAY_INC, StringPool.BLANK,
				oldInstructions, StringPool.BLANK,
				accountEntry.getInstructions(), StringPool.BLANK);
		}

		if (oldAccountEntry.getStatus() != accountEntry.getStatus()) {
			auditEntryLocalService.addAuditEntry(
				userId, userName, createDate, classNameId, classPK, auditSetId,
				classNameId, classPK, auditAction,
				AuditEntryConstants.FIELD_STATUS,
				VisibilityConstants.LIFERAY_INC,
				oldAccountEntry.getStatusLabel(),
				String.valueOf(oldAccountEntry.getStatus()),
				accountEntry.getStatusLabel(),
				String.valueOf(accountEntry.getStatus()), StringPool.BLANK);
		}

		String[] oldLanguageIds = oldAccountEntry.getLanguageIds();
		String[] languageIds = accountEntry.getLanguageIds();

		Arrays.sort(oldLanguageIds);
		Arrays.sort(languageIds);

		if (!Arrays.equals(oldLanguageIds, languageIds)) {
			String oldLanguageIdsString = formatLanguageIds(oldLanguageIds);
			String languageIdsString = formatLanguageIds(languageIds);

			auditEntryLocalService.addAuditEntry(
				userId, userName, createDate, classNameId, classPK, auditSetId,
				classNameId, classPK, auditAction,
				AuditEntryConstants.FIELD_LANGUAGES,
				VisibilityConstants.LIFERAY_INC, oldLanguageIdsString,
				StringUtil.merge(oldLanguageIds), languageIdsString,
				StringUtil.merge(languageIds), StringPool.BLANK);
		}
	}

	protected void validate(long accountEntryId, String koroneikiAccountKey)
		throws PortalException {

		if (Validator.isNull(koroneikiAccountKey)) {
			throw new AccountEntryKoroneikiAccountKeyException();
		}
	}

}