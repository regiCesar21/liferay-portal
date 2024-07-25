/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.zendesk.synchronizer;

import com.liferay.osb.customer.admin.constants.AccountEntryConstants;
import com.liferay.osb.customer.admin.constants.ExternalIdMapperConstants;
import com.liferay.osb.customer.admin.model.AccountEntry;
import com.liferay.osb.customer.admin.model.ProductEntry;
import com.liferay.osb.customer.admin.service.AccountEntryLocalService;
import com.liferay.osb.customer.admin.service.ExternalIdMapperLocalService;
import com.liferay.osb.customer.admin.service.ProductEntryLocalService;
import com.liferay.osb.customer.identity.management.provider.UserIdentityProvider;
import com.liferay.osb.customer.koroneiki.constants.ContactRoleConstants;
import com.liferay.osb.customer.koroneiki.constants.ProductPurchaseConstants;
import com.liferay.osb.customer.koroneiki.constants.TeamRoleConstants;
import com.liferay.osb.customer.koroneiki.util.AccountReader;
import com.liferay.osb.customer.koroneiki.web.service.AccountWebService;
import com.liferay.osb.customer.koroneiki.web.service.ContactRoleWebService;
import com.liferay.osb.customer.koroneiki.web.service.ContactWebService;
import com.liferay.osb.customer.koroneiki.web.service.ExternalLinkWebService;
import com.liferay.osb.customer.koroneiki.web.service.ProductPurchaseWebService;
import com.liferay.osb.customer.zendesk.connector.constants.ZendeskTagConstants;
import com.liferay.osb.customer.zendesk.constants.ZendeskLocales;
import com.liferay.osb.customer.zendesk.constants.ZendeskTicketConstants;
import com.liferay.osb.customer.zendesk.model.ZendeskOrganizationMembership;
import com.liferay.osb.customer.zendesk.model.ZendeskTicket;
import com.liferay.osb.customer.zendesk.model.ZendeskUser;
import com.liferay.osb.customer.zendesk.synchronizer.configuration.ZendeskSynchronizerConfigurationValues;
import com.liferay.osb.customer.zendesk.synchronizer.util.AddressUtil;
import com.liferay.osb.customer.zendesk.util.ZendeskMapperUtil;
import com.liferay.osb.customer.zendesk.web.service.ZendeskOrganizationMembershipWebService;
import com.liferay.osb.customer.zendesk.web.service.ZendeskOrganizationWebService;
import com.liferay.osb.customer.zendesk.web.service.ZendeskTicketWebService;
import com.liferay.osb.customer.zendesk.web.service.ZendeskUserWebService;
import com.liferay.osb.customer.zendesk.web.service.search.Query;
import com.liferay.osb.customer.zendesk.web.service.search.QueryFactory;
import com.liferay.osb.customer.zendesk.web.service.search.SearchHits;
import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkDomain;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Contact;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ContactRole;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.PostalAddress;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Team;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.TeamRole;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(immediate = true, service = AccountSynchronizer.class)
public class AccountSynchronizer {

	public void addCustomers(Account account, AccountEntry accountEntry)
		throws Exception {

		if (!ZendeskSynchronizerThreadLocal.isEnabled()) {
			return;
		}

		List<Contact> contacts = _contactWebService.getAccountCustomerContacts(
			account.getKey(), 1, 1000);

		for (Contact contact : contacts) {
			User user = _userIdentityProvider.fetchUserByEmailAddress(
				contact.getEmailAddress());

			if (user == null) {
				continue;
			}

			for (ContactRole contactRole : contact.getContactRoles()) {
				String contactRoleName = contactRole.getName();

				if (!accountEntry.isActiveTicketSupport() &&
					!contactRoleName.equals(
						ContactRoleConstants.NAME_SUPPORT_CLOSED_WATCHER)) {

					continue;
				}

				if (ArrayUtil.contains(
						ContactRoleConstants.SUPPORT_CONTACT_ROLES,
						contactRoleName) ||
					ArrayUtil.contains(
						ContactRoleConstants.PARTNER_CONTACT_ROLES,
						contactRoleName)) {

					_customerSynchronizer.add(user, account, accountEntry);

					break;
				}
			}
		}
	}

	public void addFirstLineSupport(Account account, AccountEntry accountEntry)
		throws Exception {

		if (account.getAssignedTeams() == null) {
			return;
		}

		for (Team team : account.getAssignedTeams()) {
			if (team.getTeamRoles() == null) {
				continue;
			}

			for (TeamRole teamRole : team.getTeamRoles()) {
				String name = teamRole.getName();

				if (name.equals(TeamRoleConstants.NAME_FIRST_LINE_SUPPORT)) {
					addFirstLineSupport(accountEntry, team);
				}
			}
		}
	}

	public void addFirstLineSupport(AccountEntry accountEntry, Team team)
		throws Exception {

		if (!ZendeskSynchronizerThreadLocal.isEnabled()) {
			return;
		}

		List<Contact> contacts = _contactWebService.getTeamContacts(
			team.getKey(), 1, 1000);

		for (Contact contact : contacts) {
			User user = _userIdentityProvider.fetchUserByEmailAddress(
				contact.getEmailAddress());

			if (user == null) {
				continue;
			}

			long zendeskOrganizationId =
				_zendeskMapperUtil.fetchZendeskOrganizationId(
					accountEntry.getAccountEntryId());

			long zendeskUserId = _userSynchronizer.update(user, null);

			addOrganizationMemberships(
				zendeskUserId, new long[] {zendeskOrganizationId});
		}
	}

	public void reassignTickets(AccountEntry accountEntry, Team team, User user)
		throws Exception {

		if (!ZendeskSynchronizerThreadLocal.isEnabled()) {
			return;
		}

		long zendeskOrganizationId =
			_zendeskMapperUtil.fetchZendeskOrganizationId(
				accountEntry.getAccountEntryId());
		long zendeskUserId = _zendeskMapperUtil.fetchZendeskUserId(
			user.getUserId());

		if ((zendeskOrganizationId == 0) || (zendeskUserId == 0)) {
			return;
		}

		Set<String> criteria = new HashSet<>();

		criteria.add("organization:" + zendeskOrganizationId);
		criteria.add("requester:" + zendeskUserId);
		criteria.add("status<closed");

		List<ZendeskTicket> zendeskTickets =
			_zendeskTicketWebService.getZendeskTickets(criteria);

		if (zendeskTickets.isEmpty()) {
			return;
		}

		User newUser = null;

		List<Contact> contacts = _contactWebService.getTeamContacts(
			team.getKey(), 1, 1000);

		for (Contact contact : contacts) {
			String emailAddress = contact.getEmailAddress();

			if (emailAddress.equals(user.getEmailAddress())) {
				continue;
			}

			User curUser = _userIdentityProvider.fetchUserByEmailAddress(
				emailAddress);

			if (curUser == null) {
				continue;
			}

			newUser = curUser;
		}

		long newZendeskUserId = 0;

		if (newUser != null) {
			newZendeskUserId = _zendeskMapperUtil.fetchZendeskUserId(
				newUser.getUserId());
		}
		else {
			ZendeskUser zendeskUser =
				_zendeskUserWebService.getZendeskUserByEmail(
					getDefaultUserEmail(accountEntry.getAccountEntryId()));

			newZendeskUserId = zendeskUser.getZendeskUserId();

			checkAndAddOrganizationMembership(
				newZendeskUserId, zendeskOrganizationId);
		}

		for (ZendeskTicket zendeskTicket : zendeskTickets) {
			zendeskTicket.setRequesterId(newZendeskUserId);
			zendeskTicket.setZendeskOrganizationId(zendeskOrganizationId);
		}

		_zendeskTicketWebService.updateZendeskTickets(zendeskTickets);
	}

	public void reassignTickets(
			String accountKey, AccountEntry accountEntry, User user)
		throws Exception {

		if (!ZendeskSynchronizerThreadLocal.isEnabled()) {
			return;
		}

		long zendeskOrganizationId =
			_zendeskMapperUtil.fetchZendeskOrganizationId(
				accountEntry.getAccountEntryId());
		long zendeskUserId = _zendeskMapperUtil.fetchZendeskUserId(
			user.getUserId());

		if ((zendeskOrganizationId == 0) || (zendeskUserId == 0)) {
			return;
		}

		Set<String> criteria = new HashSet<>();

		criteria.add("organization:" + zendeskOrganizationId);
		criteria.add("requester:" + zendeskUserId);
		criteria.add("status<closed");

		List<ZendeskTicket> zendeskTickets =
			_zendeskTicketWebService.getZendeskTickets(criteria);

		if (zendeskTickets.isEmpty()) {
			return;
		}

		long newZendeskUserId = 0;

		Account account = _accountWebService.getAccountContactsContactRoles(
			accountKey);

		Contact[] contacts = account.getCustomerContacts();

		if (!ArrayUtil.isEmpty(contacts)) {
			for (Contact contact : contacts) {
				String emailAddress = contact.getEmailAddress();

				if (emailAddress.equals(user.getEmailAddress())) {
					continue;
				}

				ContactRole[] contactRoles = contact.getContactRoles();

				if (ArrayUtil.isEmpty(contactRoles)) {
					continue;
				}

				for (ContactRole contactRole : contactRoles) {
					String name = contactRole.getName();

					if (name.equals(
							ContactRoleConstants.NAME_SUPPORT_ADMINISTRATOR) ||
						name.equals(
							ContactRoleConstants.NAME_SUPPORT_REQUESTER)) {

						User curUser =
							_userIdentityProvider.fetchUserByEmailAddress(
								contact.getEmailAddress());

						if (curUser != null) {
							newZendeskUserId =
								_zendeskMapperUtil.fetchZendeskUserId(
									curUser.getUserId());
						}
					}
				}

				if (newZendeskUserId > 0) {
					break;
				}
			}
		}

		if (newZendeskUserId <= 0) {
			ZendeskUser zendeskUser =
				_zendeskUserWebService.getZendeskUserByEmail(
					getDefaultUserEmail(accountEntry.getAccountEntryId()));

			newZendeskUserId = zendeskUser.getZendeskUserId();

			checkAndAddOrganizationMembership(
				newZendeskUserId, zendeskOrganizationId);
		}

		for (ZendeskTicket zendeskTicket : zendeskTickets) {
			zendeskTicket.setRequesterId(newZendeskUserId);
			zendeskTicket.setZendeskOrganizationId(zendeskOrganizationId);
		}

		_zendeskTicketWebService.updateZendeskTickets(zendeskTickets);
	}

	public void remove(Account account, AccountEntry accountEntry)
		throws Exception {

		if (!ZendeskSynchronizerThreadLocal.isEnabled()) {
			return;
		}

		long zendeskOrganizationId =
			_zendeskMapperUtil.fetchZendeskOrganizationId(
				accountEntry.getAccountEntryId());

		if (zendeskOrganizationId <= 0) {
			return;
		}

		Query query = _queryFactory.createQuery();

		query.addCriterion("organization_id:" + zendeskOrganizationId);
		query.addCriterion("user:no-reply@*");
		query.setPage(1);

		SearchHits<ZendeskUser> searchHits =
			_zendeskUserWebService.getZendeskUsers(query);

		List<ZendeskUser> zendeskUsers = searchHits.getResults();

		for (ZendeskUser zendeskUser : zendeskUsers) {
			_asyncZendeskUserWebService.deleteZendeskUser(
				zendeskUser.getZendeskUserId());
		}

		removeCustomers(accountEntry, zendeskOrganizationId);

		if (hasFirstLineSupport(account)) {
			removeFirstLineSupport(account, accountEntry);
		}

		Set<String> criteria = new HashSet<>();

		criteria.add("organization:" + zendeskOrganizationId);

		List<ZendeskTicket> zendeskTickets =
			_zendeskTicketWebService.getZendeskTickets(criteria);

		if (zendeskTickets.isEmpty()) {
			_asyncZendeskOrganizationWebService.deleteZendeskOrganization(
				zendeskOrganizationId);
		}
	}

	public void removeCustomers(Account account, AccountEntry accountEntry)
		throws Exception {

		if (!ZendeskSynchronizerThreadLocal.isEnabled()) {
			return;
		}

		List<Contact> contacts = _contactWebService.getAccountCustomerContacts(
			account.getKey(), 1, 1000);

		for (Contact contact : contacts) {
			User user = _userIdentityProvider.fetchUserByEmailAddress(
				contact.getEmailAddress());

			if (user == null) {
				continue;
			}

			List<ContactRole> contactRoles =
				_contactRoleWebService.getAccountContactRoles(
					account.getKey(), user.getUuid(), 1, 1);

			boolean isSupportClosedWatcher = false;

			for (ContactRole contactRole : contactRoles) {
				String contactRoleName = contactRole.getName();

				if (contactRoleName.equals(
						ContactRoleConstants.NAME_SUPPORT_CLOSED_WATCHER)) {

					isSupportClosedWatcher = true;

					break;
				}
			}

			if (!isSupportClosedWatcher) {
				_customerSynchronizer.remove(user, accountEntry);
			}
		}
	}

	public void removeFirstLineSupport(
			Account account, AccountEntry accountEntry)
		throws Exception {

		if (account.getAssignedTeams() == null) {
			return;
		}

		for (Team team : account.getAssignedTeams()) {
			if (team.getTeamRoles() == null) {
				continue;
			}

			for (TeamRole teamRole : team.getTeamRoles()) {
				String name = teamRole.getName();

				if (name.equals(TeamRoleConstants.NAME_FIRST_LINE_SUPPORT)) {
					removeFirstLineSupport(account, accountEntry, team);
				}
			}
		}
	}

	public void removeFirstLineSupport(
			Account account, AccountEntry accountEntry, Team team)
		throws Exception {

		if (!ZendeskSynchronizerThreadLocal.isEnabled()) {
			return;
		}

		List<Contact> teamContacts = _contactWebService.getTeamContacts(
			team.getKey(), 1, 1000);

		for (Contact teamContact : teamContacts) {
			User user = _userIdentityProvider.fetchUserByEmailAddress(
				teamContact.getEmailAddress());

			if (user == null) {
				continue;
			}

			boolean isSupportContact = false;

			List<ContactRole> contactRoles =
				_contactRoleWebService.getAccountContactRoles(
					account.getKey(), user.getUuid(), 1, 1000);

			if (!contactRoles.isEmpty()) {
				for (ContactRole curContactRole : contactRoles) {
					if (ArrayUtil.contains(
							ContactRoleConstants.SUPPORT_CONTACT_ROLES,
							curContactRole.getName())) {

						isSupportContact = true;

						break;
					}
				}
			}

			if (isSupportContact) {
				continue;
			}

			long zendeskOrganizationId =
				_zendeskMapperUtil.fetchZendeskOrganizationId(
					accountEntry.getAccountEntryId());
			long zendeskUserId = _zendeskMapperUtil.fetchZendeskUserId(
				user.getUserId());

			if ((zendeskOrganizationId > 0) && (zendeskUserId > 0)) {
				removeOrganizationMemberships(
					zendeskUserId, new long[] {zendeskOrganizationId});
			}

			_userSynchronizer.update(user, null);
		}
	}

	public void solveZendeskTickets(AccountEntry accountEntry, User user)
		throws Exception {

		if (!ZendeskSynchronizerThreadLocal.isEnabled()) {
			return;
		}

		long zendeskOrganizationId =
			_zendeskMapperUtil.fetchZendeskOrganizationId(
				accountEntry.getAccountEntryId());

		Set<String> criteria = new HashSet<>();

		criteria.add("organization:" + zendeskOrganizationId);

		if (user != null) {
			long zendeskUserId = _zendeskMapperUtil.fetchZendeskUserId(
				user.getUserId());

			criteria.add("requester:" + zendeskUserId);
		}

		criteria.add("status<" + ZendeskTicketConstants.STATUS_CLOSED);

		List<ZendeskTicket> zendeskTickets =
			_zendeskTicketWebService.getZendeskTickets(criteria);

		if (zendeskTickets.isEmpty()) {
			return;
		}

		ZendeskUser zendeskUser = _zendeskUserWebService.getZendeskUserByEmail(
			getDefaultUserEmail(accountEntry.getAccountEntryId()));

		checkAndAddOrganizationMembership(
			zendeskUser.getZendeskUserId(), zendeskOrganizationId);

		for (ZendeskTicket zendeskTicket : zendeskTickets) {
			Map<Long, String> customFields = zendeskTicket.getCustomFields();

			customFields.putIfAbsent(
				ZendeskSynchronizerConfigurationValues.
					ZENDESK_TICKET_LONG_TERM_RESOLUTION_FIELD_ID,
				"n_a_customer_inactivity");

			zendeskTicket.setCustomFields(customFields);

			zendeskTicket.setRequesterId(zendeskUser.getZendeskUserId());
			zendeskTicket.setStatus("solved");
		}

		_zendeskTicketWebService.updateZendeskTickets(zendeskTickets);
	}

	public void update(Account account, AccountEntry accountEntry)
		throws Exception {

		if (!ZendeskSynchronizerThreadLocal.isEnabled()) {
			return;
		}

		List<ProductPurchase> productPurchases =
			_accountReader.getProductPurchases(account.getKey());

		boolean externalIdMappers =
			_externalIdMapperLocalService.hasExternalIdMappers(
				_classNameLocalService.getClassNameId(AccountEntry.class),
				accountEntry.getAccountEntryId(),
				ExternalIdMapperConstants.TYPE_ZENDESK);

		String address = StringPool.BLANK;
		String countryName = StringPool.BLANK;

		PostalAddress[] postalAddresses = account.getPostalAddresses();

		if (!ArrayUtil.isEmpty(postalAddresses)) {
			address = AddressUtil.convertAddressToString(postalAddresses[0]);
			countryName = postalAddresses[0].getAddressCountry();
		}

		boolean firstLineSupport = false;
		String partnerName = StringPool.BLANK;
		String partnerJiraProject = StringPool.BLANK;

		if (account.getAssignedTeams() != null) {
			for (Team team : account.getAssignedTeams()) {
				if (team.getTeamRoles() == null) {
					continue;
				}

				for (TeamRole teamRole : team.getTeamRoles()) {
					String name = teamRole.getName();

					if (name.equals(
							TeamRoleConstants.NAME_FIRST_LINE_SUPPORT)) {

						firstLineSupport = true;

						partnerName = team.getName();

						for (ExternalLink teamExternalLink :
								team.getExternalLinks()) {

							String externalLinkDomain =
								teamExternalLink.getDomain();

							if (externalLinkDomain.equals("jira")) {
								partnerJiraProject =
									teamExternalLink.getEntityId();

								break;
							}
						}

						break;
					}
				}

				if (firstLineSupport) {
					break;
				}
			}
		}

		_zendeskOrganizationWebService.createOrUpdateZendeskOrganization(
			account.getCode(), account.getKey(), countryName, address,
			String.valueOf(accountEntry.getAccountEntryId()), account.getName(),
			accountEntry.getInstructions(), String.valueOf(firstLineSupport),
			partnerJiraProject, partnerName, getSupportLevel(productPurchases),
			getStatus(productPurchases), getSupportLanguage(accountEntry),
			getSupportRegion(account), account.getTierAsString(),
			getExternalLinks(account),
			getTags(account, accountEntry, productPurchases));

		if (!externalIdMappers) {
			_asyncZendeskUserWebService.createOrUpdateZendeskUser(
				null, getDefaultUserEmail(accountEntry.getAccountEntryId()),
				ZendeskLocales.US, accountEntry.getCode(),
				accountEntry.getName(), null, null);
		}
	}

	protected void addOrganizationMemberships(
			long zendeskUserId, long[] zendeskOrganizationIds)
		throws Exception {

		if (ArrayUtil.isEmpty(zendeskOrganizationIds)) {
			return;
		}

		_asyncZendeskOrganizationMembershipWebService.
			createOrganizationMemberships(
				zendeskUserId, zendeskOrganizationIds);

		for (long zendeskOrganizationId : zendeskOrganizationIds) {
			_asyncZendeskUserWebService.
				createZendeskUserOrganizationSubscription(
					zendeskUserId, zendeskOrganizationId);
		}
	}

	protected void checkAndAddOrganizationMembership(
			long zendeskUserId, long zendeskOrganizationId)
		throws Exception {

		if (!hasOrganizationMembership(zendeskUserId, zendeskOrganizationId)) {
			addOrganizationMemberships(
				zendeskUserId, new long[] {zendeskOrganizationId});
		}
	}

	protected String getDefaultUserEmail(long accountEntryId) {
		return "no-reply@" + String.valueOf(accountEntryId) + ".com.broken";
	}

	protected List<String> getExternalLinks(Account account) {
		List<String> externalLinks = new ArrayList<>();

		for (ExternalLink externalLink : account.getExternalLinks()) {
			String domain = externalLink.getDomain();

			if (!domain.equals(ExternalLinkDomain.ZENDESK)) {
				externalLinks.add(externalLink.getUrl());
			}
		}

		return externalLinks;
	}

	protected String getStatus(List<ProductPurchase> productPurchases) {
		String state = _accountReader.getSubscriptionState(productPurchases);

		if (state.equals(ProductPurchaseConstants.STATE_ACTIVE)) {
			return "Approved";
		}
		else if (state.equals(ProductPurchaseConstants.STATE_EXPIRED)) {
			return "Expired";
		}

		return "Closed";
	}

	protected String getSupportLanguage(AccountEntry accountEntry) {
		String[] languageIds = accountEntry.getLanguageIds();

		if (ArrayUtil.isEmpty(languageIds)) {
			return StringPool.BLANK;
		}

		return AccountEntryConstants.getLanguageLabel(languageIds[0]);
	}

	protected String getSupportLevel(List<ProductPurchase> productPurchases)
		throws Exception {

		ProductPurchase productPurchase = _accountReader.getSLAProductPurchase(
			productPurchases);

		if (productPurchase != null) {
			Product product = productPurchase.getProduct();

			return StringUtil.removeSubstring(
				product.getName(), " Subscription");
		}

		return StringPool.BLANK;
	}

	protected String getSupportRegion(Account account) {
		if (account.getRegion() == Account.Region.UNITED_STATES) {
			return "US";
		}

		return account.getRegionAsString();
	}

	protected Set<String> getTags(
			Account account, AccountEntry accountEntry,
			List<ProductPurchase> productPurchases)
		throws Exception {

		if (!accountEntry.isActiveTicketSupport()) {
			return Collections.emptySet();
		}

		Set<String> tags = new HashSet<>();

		Date now = new Date();

		for (ProductPurchase productPurchase : productPurchases) {
			if ((productPurchase.getStatus() ==
					ProductPurchase.Status.CANCELLED) ||
				(!productPurchase.getPerpetual() &&
				 now.after(productPurchase.getEndDate()))) {

				continue;
			}

			ProductEntry productEntry =
				_productEntryLocalService.getProductEntryByKoroneikiKey(
					productPurchase.getProductKey());

			if (Validator.isNotNull(productEntry.getZendeskTag())) {
				tags.add(productEntry.getZendeskTag());
			}
		}

		Map<String, String> properties = account.getProperties();

		if (properties.containsKey("gsOpportunity")) {
			tags.add(ZendeskTagConstants.GS_OPPORTUNITY);
		}

		if (properties.containsKey("premiumService")) {
			tags.add(ZendeskTagConstants.PREMIUM_SERVICE);
		}

		if (properties.containsKey("projectSolution")) {
			tags.add(_toZendeskTag(properties.get("projectSolution")));
		}

		return tags;
	}

	protected boolean hasFirstLineSupport(Account account) {
		if (account.getAssignedTeams() == null) {
			return false;
		}

		for (Team team : account.getAssignedTeams()) {
			if (team.getTeamRoles() == null) {
				continue;
			}

			for (TeamRole teamRole : team.getTeamRoles()) {
				String name = teamRole.getName();

				if (name.equals(TeamRoleConstants.NAME_FIRST_LINE_SUPPORT)) {
					return true;
				}
			}
		}

		return false;
	}

	protected boolean hasOrganizationMembership(
			long zendeskUserId, long zendeskOrganizationId)
		throws Exception {

		List<ZendeskOrganizationMembership> zendeskOrganizationMemberships =
			_zendeskOrganizationMembershipWebService.
				getZendeskUserOrganizationMemberships(zendeskUserId);

		for (ZendeskOrganizationMembership zendeskOrganizationMembership :
				zendeskOrganizationMemberships) {

			if (zendeskOrganizationId ==
					zendeskOrganizationMembership.getZendeskOrganizationId()) {

				return true;
			}
		}

		return false;
	}

	protected void removeCustomers(
			AccountEntry accountEntry, long zendeskOrganizationId)
		throws Exception {

		Query query = _queryFactory.createQuery();

		query.addCriterion("organization_id:" + zendeskOrganizationId);
		query.setPage(1);

		SearchHits<ZendeskUser> searchHits =
			_zendeskUserWebService.getZendeskUsers(query);

		List<ZendeskUser> zendeskUsers = searchHits.getResults();

		for (ZendeskUser zendeskUser : zendeskUsers) {
			String emailAddress = zendeskUser.getEmail();

			if (emailAddress.equals(
					getDefaultUserEmail(accountEntry.getAccountEntryId()))) {

				continue;
			}

			User user = _userIdentityProvider.fetchUserByEmailAddress(
				emailAddress);

			if (user == null) {
				continue;
			}

			_customerSynchronizer.remove(user, accountEntry);
		}
	}

	protected void removeOrganizationMemberships(
			long zendeskUserId, long[] zendeskOrganizationIds)
		throws Exception {

		if (ArrayUtil.isEmpty(zendeskOrganizationIds)) {
			return;
		}

		_asyncZendeskOrganizationMembershipWebService.
			deleteOrganizationMemberships(
				zendeskUserId, zendeskOrganizationIds);
	}

	private String _toZendeskTag(String tag) {
		return StringUtil.replace(
			StringUtil.toLowerCase(tag), CharPool.SPACE, CharPool.UNDERLINE);
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountReader _accountReader;

	@Reference
	private AccountWebService _accountWebService;

	@Reference(target = "(async=true)")
	private ZendeskOrganizationMembershipWebService
		_asyncZendeskOrganizationMembershipWebService;

	@Reference(target = "(async=true)")
	private ZendeskOrganizationWebService _asyncZendeskOrganizationWebService;

	@Reference(target = "(async=true)")
	private ZendeskUserWebService _asyncZendeskUserWebService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ContactRoleWebService _contactRoleWebService;

	@Reference
	private ContactWebService _contactWebService;

	@Reference
	private CustomerSynchronizer _customerSynchronizer;

	@Reference
	private ExternalIdMapperLocalService _externalIdMapperLocalService;

	@Reference
	private ExternalLinkWebService _externalLinkWebService;

	@Reference
	private ProductEntryLocalService _productEntryLocalService;

	@Reference
	private ProductPurchaseWebService _productPurchaseWebService;

	@Reference
	private QueryFactory _queryFactory;

	@Reference(target = "(provider=okta)")
	private UserIdentityProvider _userIdentityProvider;

	@Reference
	private UserSynchronizer _userSynchronizer;

	@Reference
	private ZendeskMapperUtil _zendeskMapperUtil;

	@Reference
	private ZendeskOrganizationMembershipWebService
		_zendeskOrganizationMembershipWebService;

	@Reference
	private ZendeskOrganizationWebService _zendeskOrganizationWebService;

	@Reference
	private ZendeskTicketWebService _zendeskTicketWebService;

	@Reference
	private ZendeskUserWebService _zendeskUserWebService;

}