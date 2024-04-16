/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.account.entry.details.web.internal.display.context;

import com.liferay.osb.customer.account.entry.details.web.internal.display.context.util.AccountEntryDetailsRequestHelper;
import com.liferay.osb.customer.admin.constants.AccountEntryConstants;
import com.liferay.osb.customer.admin.constants.AccountEnvironmentConstants;
import com.liferay.osb.customer.admin.constants.ProductEntryConstants;
import com.liferay.osb.customer.admin.model.AccountEntry;
import com.liferay.osb.customer.admin.model.AccountEntryLanguage;
import com.liferay.osb.customer.admin.model.AccountEnvironment;
import com.liferay.osb.customer.admin.model.ProductEntry;
import com.liferay.osb.customer.admin.service.AccountEntryLanguageLocalService;
import com.liferay.osb.customer.admin.service.AccountEntryLocalServiceUtil;
import com.liferay.osb.customer.admin.service.AccountEnvironmentLocalServiceUtil;
import com.liferay.osb.customer.admin.service.ProductEntryLocalServiceUtil;
import com.liferay.osb.customer.admin.service.permission.AccountEnvironmentPermission;
import com.liferay.osb.customer.constants.OSBActionKeys;
import com.liferay.osb.customer.constants.OSBCustomerConstants;
import com.liferay.osb.customer.github.model.Collaborator;
import com.liferay.osb.customer.github.service.CollaboratorLocalServiceUtil;
import com.liferay.osb.customer.koroneiki.constants.ProductConstants;
import com.liferay.osb.customer.koroneiki.constants.TeamRoleConstants;
import com.liferay.osb.customer.koroneiki.util.AccountReader;
import com.liferay.osb.customer.koroneiki.web.service.AuditEntryWebService;
import com.liferay.osb.customer.koroneiki.web.service.ContactRoleWebService;
import com.liferay.osb.customer.koroneiki.web.service.ContactWebService;
import com.liferay.osb.customer.koroneiki.web.service.ProductPurchaseViewWebService;
import com.liferay.osb.customer.koroneiki.web.service.ProductPurchaseWebService;
import com.liferay.osb.customer.koroneiki.web.service.TeamWebService;
import com.liferay.osb.customer.ticket.service.TicketAttachmentLocalService;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.AuditEntry;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Contact;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ContactRole;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchaseView;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Team;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.TeamRole;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import javax.portlet.ActionRequest;
import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Amos Fong
 */
public class AccountEntryViewDisplayContext {

	public AccountEntryViewDisplayContext(
			PortletRequest portletRequest, MimeResponse mimeResponse,
			Account account, AccountReader accountReader,
			AccountEntryLanguageLocalService accountEntryLanguageLocalService,
			AuditEntryWebService auditEntryWebService,
			ContactRoleWebService contactRoleWebService,
			ContactWebService contactWebService,
			ProductPurchaseViewWebService productPurchaseViewWebService,
			ProductPurchaseWebService productPurchaseWebService,
			TeamWebService teamWebService,
			TicketAttachmentLocalService ticketAttachmentLocalService)
		throws Exception {

		_portletRequest = portletRequest;
		_mimeResponse = mimeResponse;
		_account = account;
		_accountReader = accountReader;
		_accountEntryLanguageLocalService = accountEntryLanguageLocalService;
		_auditEntryWebService = auditEntryWebService;
		_contactRoleWebService = contactRoleWebService;
		_contactWebService = contactWebService;
		_productPurchaseViewWebService = productPurchaseViewWebService;
		_productPurchaseWebService = productPurchaseWebService;
		_teamWebService = teamWebService;
		_ticketAttachmentLocalService = ticketAttachmentLocalService;

		_accountEntry = AccountEntryLocalServiceUtil.fetchKoroneikiAccountEntry(
			_account.getKey());

		_accountEntryDetailsRequestHelper =
			new AccountEntryDetailsRequestHelper(portletRequest);

		_request = _accountEntryDetailsRequestHelper.getRequest();
		_user = _accountEntryDetailsRequestHelper.getUser();

		boolean isPartnerManagedSupportWorker = false;
		String partnerManagedSupportName = StringPool.BLANK;
		String partnerName = StringPool.BLANK;

		List<Team> teams = _teamWebService.getAssignedTeams(_account.getKey());

		for (Team team : teams) {
			TeamRole[] teamRoles = team.getTeamRoles();

			if (teamRoles == null) {
				continue;
			}

			for (TeamRole teamRole : teamRoles) {
				String name = teamRole.getName();

				if (name.equals(TeamRoleConstants.NAME_FIRST_LINE_SUPPORT)) {
					partnerManagedSupportName = team.getName();

					List<ContactRole> contactRoles =
						_contactRoleWebService.getTeamContactRoles(
							team.getKey(), _user.getUuid(), 1, 100);

					if (!contactRoles.isEmpty()) {
						isPartnerManagedSupportWorker = true;
					}
				}
				else if (name.equals(TeamRoleConstants.NAME_PARTNER)) {
					partnerName = team.getName();
				}
			}
		}

		_isPartnerManagedSupportWorker = isPartnerManagedSupportWorker;
		_partnerManagedSupportName = partnerManagedSupportName;
		_partnerName = partnerName;
	}

	public Account getAccount() {
		return _account;
	}

	public AccountEntry getAccountEntry() {
		return _accountEntry;
	}

	public String getAccountEnvironmentAddURL(AccountEntry accountEntry)
		throws PortletException {

		PortletURL portletURL = _mimeResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "editAccountEnvironment");
		portletURL.setParameter(
			"redirect", _accountEntryDetailsRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"accountEntryId", String.valueOf(accountEntry.getAccountEntryId()));

		return portletURL.toString();
	}

	public JSONArray getAccountEnvironmentsJSONArray() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<AccountEnvironment> accountEnvironments =
			AccountEnvironmentLocalServiceUtil.getAccountEnvironments(
				_accountEntry.getAccountEntryId());

		for (AccountEnvironment accountEnvironment : accountEnvironments) {
			JSONObject jsonObject = getDisplayJSONObject(accountEnvironment);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public List<List<AuditEntry>> getAuditEntrySets() throws Exception {
		List<List<AuditEntry>> auditEntrySets = new ArrayList<>();

		List<AuditEntry> auditEntries =
			_auditEntryWebService.getAccountAuditEntries(
				_account.getKey(), 1, 1000);

		long auditSetId = 0;

		List<AuditEntry> auditEntrySet = null;

		for (AuditEntry auditEntry : auditEntries) {
			if (auditEntry.getAuditSetId() != auditSetId) {
				auditSetId = auditEntry.getAuditSetId();

				auditEntrySet = new ArrayList<>();

				auditEntrySets.add(auditEntrySet);
			}

			auditEntrySet.add(auditEntry);
		}

		return auditEntrySets;
	}

	public String getCollaboratorAddURL() throws PortletException {
		PortletURL portletURL = _mimeResponse.createActionURL();

		portletURL.setParameter(ActionRequest.ACTION_NAME, "addCollaborator");
		portletURL.setParameter(
			"accountEntryId",
			String.valueOf(_accountEntry.getAccountEntryId()));

		return portletURL.toString();
	}

	public JSONArray getCollaboratorsJSONArray() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Collaborator> collaborators =
			CollaboratorLocalServiceUtil.getCollaborators(
				_accountEntry.getAccountEntryId());

		for (Collaborator collaborator : collaborators) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("collaboratorId", collaborator.getCollaboratorId());
			jsonObject.put("createDate", collaborator.getCreateDate());
			jsonObject.put(
				"deleteCollaboratorURL",
				getCollaboratorDeleteURL(collaborator));
			jsonObject.put("emailAddress", collaborator.getEmailAddress());
			jsonObject.put("fullName", collaborator.getFullName());
			jsonObject.put("gitHubUserName", collaborator.getGitHubUserName());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public List<Contact> getCustomerContacts() throws Exception {
		return _contactWebService.getAccountCustomerContacts(
			_account.getKey(), 1, 1000);
	}

	public JSONObject getEnvCommerceJSONObject(
			Set<ListType> envCommerceVersions)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Set<ListType> envLFRVersions = new HashSet<>();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ListType listType : envCommerceVersions) {
			List<ListType> envLFRListTypes =
				AccountEnvironmentConstants.getEnvListTypes(
					listType.getListTypeId(),
					ProductEntryConstants.
						LIST_TYPE_DIGITAL_ENTERPRISE_ALL_VERSIONS);

			envLFRVersions.addAll(envLFRListTypes);

			JSONObject envCommerceVersionJSONObject =
				JSONFactoryUtil.createJSONObject();

			JSONObject envCommerceVersionEnvironmentsJSONObject =
				JSONFactoryUtil.createJSONObject();

			envCommerceVersionEnvironmentsJSONObject.put(
				"envLFR", toJSONArray(envLFRListTypes));

			envCommerceVersionJSONObject.put(
				String.valueOf(listType.getListTypeId()),
				envCommerceVersionEnvironmentsJSONObject);

			jsonArray.put(envCommerceVersionJSONObject);
		}

		jsonObject.put("envCommerceVersions", jsonArray);

		jsonObject.put(
			"envLFRVersions",
			getEnvLFRVersionsJSONArray(envLFRVersions, "commerce"));

		return jsonObject;
	}

	public JSONObject getEnvironmentConfigurationJSONObject() throws Exception {
		JSONArray productsJSONArray = JSONFactoryUtil.createJSONArray();

		Set<ListType> envCommerceVersions = new HashSet<>();
		Set<ListType> envLFRVersions = new HashSet<>();
		Set<Integer> enterpriseSearchEnvironments = new HashSet<>();

		StringBundler sb = new StringBundler(5);

		sb.append("accountKey eq '");
		sb.append(_account.getKey());
		sb.append("' and status eq '");
		sb.append(String.valueOf(WorkflowConstants.STATUS_APPROVED));
		sb.append("'");

		List<ProductPurchaseView> productPurchaseViews =
			_productPurchaseViewWebService.getProductPurchaseViews(
				StringPool.BLANK, sb.toString(), 1, 1000, StringPool.BLANK);

		for (ProductPurchaseView productPurchaseView : productPurchaseViews) {
			Product product = productPurchaseView.getProduct();

			ProductEntry productEntry =
				ProductEntryLocalServiceUtil.getProductEntryByKoroneikiKey(
					product.getKey());

			if (productEntry.isEnterpriseSearch()) {
				enterpriseSearchEnvironments.add(productEntry.getEnvironment());
			}
		}

		for (ProductPurchaseView productPurchaseView : productPurchaseViews) {
			Product product = productPurchaseView.getProduct();

			ProductEntry productEntry =
				ProductEntryLocalServiceUtil.getProductEntryByKoroneikiKey(
					product.getKey());

			if (!productEntry.isAccountEnvironments()) {
				continue;
			}

			productsJSONArray.put(
				getProductJSONObject(
					productEntry, enterpriseSearchEnvironments));

			if (productEntry.isCommerce()) {
				envCommerceVersions.addAll(
					getCurrentVersionsListTypes(productEntry));
			}
			else {
				envLFRVersions.addAll(
					getCurrentVersionsListTypes(productEntry));
			}
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (!envCommerceVersions.isEmpty()) {
			jsonObject.put(
				"envCommerce", getEnvCommerceJSONObject(envCommerceVersions));
		}

		jsonObject.put(
			"envLFRVersions", getEnvLFRVersionsJSONArray(envLFRVersions));
		jsonObject.put("products", productsJSONArray);

		return jsonObject;
	}

	public List<Contact> getLiferayContacts() throws Exception {
		return _contactWebService.getAccountWorkerContacts(
			_account.getKey(), 1, 1000);
	}

	public String getPartnerManagedSupportName() {
		return _partnerManagedSupportName;
	}

	public String getPartnerName() {
		return _partnerName;
	}

	public SearchContainer getProductPurchaseViewsSearchContainer()
		throws Exception {

		SearchContainer searchContainer = new SearchContainer(
			_portletRequest, _mimeResponse.createRenderURL(),
			Collections.emptyList(), "no-offerings-were-found");

		StringBundler sb = new StringBundler(4);

		sb.append("accountKey eq '");
		sb.append(_account.getKey());
		sb.append("'");

		if (!isAdminOrSubscriptionServices() &&
			!isPartnerManagedSupportWorker()) {

			sb.append(" and state eq 'active'");
		}

		List<ProductPurchaseView> productPurchaseViews =
			_productPurchaseViewWebService.getProductPurchaseViews(
				StringPool.BLANK, sb.toString(), 1, 1000, StringPool.BLANK);

		List<ProductPurchaseViewDisplay> productPurchaseViewDisplays =
			new ArrayList<>();

		for (ProductPurchaseView productPurchaseView : productPurchaseViews) {
			productPurchaseViewDisplays.add(
				new ProductPurchaseViewDisplay(_request, productPurchaseView));
		}

		searchContainer.setResults(productPurchaseViewDisplays);

		searchContainer.setTotal(productPurchaseViewDisplays.size());

		return searchContainer;
	}

	public String getState() throws Exception {
		return _accountReader.getState(_account.getKey());
	}

	public String getSupportLanguage() {
		if (_accountEntry == null) {
			return StringPool.DASH;
		}

		List<AccountEntryLanguage> accountEntryLanguages =
			_accountEntryLanguageLocalService.getAccountEntryLanguages(
				_accountEntry.getAccountEntryId());

		if (accountEntryLanguages.isEmpty()) {
			return StringPool.DASH;
		}

		AccountEntryLanguage accountEntryLanguage = accountEntryLanguages.get(
			0);

		String supportLanguage = AccountEntryConstants.getLanguageLabel(
			accountEntryLanguage.getLanguageId());

		return LanguageUtil.get(_request, supportLanguage);
	}

	public String getSupportLevel() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("accountKey eq '");
		sb.append(_account.getKey());
		sb.append("' and state eq 'active'");

		List<ProductPurchase> productPurchases =
			_productPurchaseWebService.search(sb.toString(), 1, 1000);

		ProductPurchase productPurchase = _accountReader.getSLAProductPurchase(
			productPurchases);

		if (productPurchase != null) {
			Product product = productPurchase.getProduct();

			return product.getName();
		}

		return StringPool.BLANK;
	}

	public SearchContainer getTicketAttachmentsSearchContainer()
		throws Exception {

		SearchContainer searchContainer = new SearchContainer(
			_portletRequest, _mimeResponse.createRenderURL(),
			Collections.emptyList(), "no-attachments-were-found");

		searchContainer.setResults(
			_ticketAttachmentLocalService.getTicketAttachments(
				_accountEntry.getAccountEntryId()));
		searchContainer.setTotal(
			_ticketAttachmentLocalService.getTicketAttachmentsCount(
				_accountEntry.getAccountEntryId()));

		return searchContainer;
	}

	public boolean hasOnlyLiferaySaas() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("accountKey eq '");
		sb.append(_account.getKey());
		sb.append("' and (state eq 'active' or state eq 'unactivated')");

		List<ProductPurchase> productPurchases =
			_productPurchaseWebService.search(sb.toString(), 1, 1000);

		for (ProductPurchase productPurchase : productPurchases) {
			Product product = productPurchase.getProduct();

			String name = product.getName();

			if (ArrayUtil.contains(ProductConstants.NAMES_PARTNERSHIP, name) ||
				name.contains(ProductConstants.NAME_PREFIX_DXP) ||
				name.contains(ProductConstants.NAME_PREFIX_LIFERAY_PAAS) ||
				name.contains(ProductConstants.NAME_PREFIX_PORTAL)) {

				return false;
			}
		}

		return true;
	}

	public boolean isAdminOrSubscriptionServices() {
		if (RoleLocalServiceUtil.hasUserRole(
				_user.getUserId(),
				OSBCustomerConstants.ROLE_ADMINISTRATOR_ID)) {

			return true;
		}

		if (OrganizationLocalServiceUtil.hasUserOrganization(
				_user.getUserId(),
				OSBCustomerConstants.ORGANIZATION_DATA_ACCESS_EU_ID) ||
			OrganizationLocalServiceUtil.hasUserOrganization(
				_user.getUserId(),
				OSBCustomerConstants.ORGANIZATION_DATA_ACCESS_US_ID) ||
			OrganizationLocalServiceUtil.hasUserOrganization(
				_user.getUserId(),
				OSBCustomerConstants.
					ORGANIZATION_DIVISION_SUBSCRIPTION_SERVICES_ID)) {

			return true;
		}

		return false;
	}

	public boolean isPartnerManagedSupportWorker() {
		return _isPartnerManagedSupportWorker;
	}

	protected String getAccountEnvironmentDeleteURL(
		AccountEnvironment accountEnvironment) {

		PortletURL portletURL = _mimeResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "deleteAccountEnvironment");
		portletURL.setParameter(
			"redirect", _accountEntryDetailsRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"accountEnvironmentId",
			String.valueOf(accountEnvironment.getAccountEnvironmentId()));

		return portletURL.toString();
	}

	protected String getAccountEnvironmentEditURL(
			AccountEnvironment accountEnvironment)
		throws PortletException {

		PortletURL portletURL = _mimeResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "editAccountEnvironment");
		portletURL.setParameter(
			"redirect", _accountEntryDetailsRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"accountEnvironmentId",
			String.valueOf(accountEnvironment.getAccountEnvironmentId()));

		return portletURL.toString();
	}

	protected String getCollaboratorDeleteURL(Collaborator collaborator) {
		PortletURL portletURL = _mimeResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "deleteCollaborator");
		portletURL.setParameter(
			"redirect", _accountEntryDetailsRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"collaboratorId", String.valueOf(collaborator.getCollaboratorId()));

		return portletURL.toString();
	}

	protected List<ListType> getCurrentVersionsListTypes(
		ProductEntry productEntry) {

		List<ListType> productVersionListTypes = new ArrayList<>();

		IntStream intStream = Arrays.stream(
			ProductEntryConstants.LIST_TYPES_DEPRECATED);

		LongStream longStream = intStream.asLongStream();

		long[] deprecatedTypes = longStream.toArray();

		for (ListType listType : productEntry.getAllVersionsListTypes()) {
			if (ArrayUtil.contains(deprecatedTypes, listType.getListTypeId()) ||
				(listType.getListTypeId() ==
					(long)ProductEntryConstants.COMMERCE_VERSION_1_0) ||
				(listType.getListTypeId() ==
					(long)ProductEntryConstants.PORTAL_VERSION_OTHER)) {

				continue;
			}

			productVersionListTypes.add(listType);
		}

		return productVersionListTypes;
	}

	protected JSONObject getDisplayJSONObject(
			AccountEnvironment accountEnvironment)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"accountEnvironmentId",
			accountEnvironment.getAccountEnvironmentId());

		if (AccountEnvironmentPermission.contains(
				_accountEntryDetailsRequestHelper.getPermissionChecker(),
				_accountEntry.getAccountEntryId(), OSBActionKeys.DELETE)) {

			jsonObject.put(
				"deleteAccountEnvironmentURL",
				getAccountEnvironmentDeleteURL(accountEnvironment));
		}

		if (AccountEnvironmentPermission.contains(
				_accountEntryDetailsRequestHelper.getPermissionChecker(),
				_accountEntry.getAccountEntryId(), OSBActionKeys.UPDATE)) {

			jsonObject.put(
				"editAccountEnvironmentURL",
				getAccountEnvironmentEditURL(accountEnvironment));
		}

		jsonObject.put(
			"envASLabel",
			LanguageUtil.get(_request, accountEnvironment.getEnvASLabel()));
		jsonObject.put(
			"envBrowserLabel",
			LanguageUtil.get(
				_request, accountEnvironment.getEnvBrowserLabel()));
		jsonObject.put(
			"envCommerceLabel",
			LanguageUtil.get(
				_request, accountEnvironment.getEnvCommerceLabel()));
		jsonObject.put(
			"envCSLabel",
			LanguageUtil.get(_request, accountEnvironment.getEnvCSLabel()));
		jsonObject.put(
			"envDBLabel",
			LanguageUtil.get(_request, accountEnvironment.getEnvDBLabel()));
		jsonObject.put(
			"envJVMLabel",
			LanguageUtil.get(_request, accountEnvironment.getEnvJVMLabel()));
		jsonObject.put(
			"envLFRLabel",
			LanguageUtil.get(_request, accountEnvironment.getEnvLFRLabel()));
		jsonObject.put(
			"envSearchLabels",
			toEnvSearchJSONArray(accountEnvironment.getEnvSearchLabels()));

		String envOSLabel = LanguageUtil.get(
			_request, accountEnvironment.getEnvOSLabel());

		if (Validator.isNotNull(accountEnvironment.getEnvOSCustom())) {
			envOSLabel += " - " + accountEnvironment.getEnvOSCustom();
		}

		jsonObject.put("envOSLabel", envOSLabel);

		jsonObject.put("name", accountEnvironment.getName());

		ProductEntry productEntry =
			ProductEntryLocalServiceUtil.getProductEntry(
				accountEnvironment.getProductEntryId());

		jsonObject.put("productEntryDisplayName", productEntry.getName());
		jsonObject.put("productEntryId", productEntry.getProductEntryId());

		return jsonObject;
	}

	protected JSONObject getEnvLFRJSONObject(
			ListType listType, String... sublistType)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		long listTypeId = listType.getListTypeId();

		jsonObject.put("envLFR", toJSONArray(Arrays.asList(listType)));

		List<ListType> envASListTypes =
			AccountEnvironmentConstants.getEnvListTypes(
				listTypeId, AccountEnvironmentConstants.LIST_TYPE_ENV_AS,
				sublistType);

		jsonObject.put("envAS", toJSONArray(envASListTypes));

		List<ListType> envBrowserListTypes =
			AccountEnvironmentConstants.getEnvListTypes(
				listTypeId, AccountEnvironmentConstants.LIST_TYPE_ENV_BROWSER,
				sublistType);

		jsonObject.put("envBrowser", toJSONArray(envBrowserListTypes));

		if (ProductEntryConstants.isPortalVersion6_2(listTypeId) ||
			ProductEntryConstants.isDigitalEnterpriseVersion7_0(listTypeId) ||
			ProductEntryConstants.isDigitalEnterpriseVersion7_1(listTypeId) ||
			ProductEntryConstants.isDigitalEnterpriseVersion7_2(listTypeId) ||
			ProductEntryConstants.isDigitalEnterpriseVersion7_3(listTypeId) ||
			ProductEntryConstants.isDigitalEnterpriseVersion7_4(listTypeId)) {

			List<ListType> envCSListTypes =
				AccountEnvironmentConstants.getEnvListTypes(
					listTypeId, AccountEnvironmentConstants.LIST_TYPE_ENV_CS,
					sublistType);

			jsonObject.put("envCS", toJSONArray(envCSListTypes));
		}

		List<ListType> envDBListTypes =
			AccountEnvironmentConstants.getEnvListTypes(
				listTypeId, AccountEnvironmentConstants.LIST_TYPE_ENV_DB,
				sublistType);

		jsonObject.put("envDB", toJSONArray(envDBListTypes));

		List<ListType> envJVMListTypes =
			AccountEnvironmentConstants.getEnvListTypes(
				listTypeId, AccountEnvironmentConstants.LIST_TYPE_ENV_JVM,
				sublistType);

		jsonObject.put("envJVM", toJSONArray(envJVMListTypes));

		List<ListType> envOSListTypes =
			AccountEnvironmentConstants.getEnvListTypes(
				listTypeId, AccountEnvironmentConstants.LIST_TYPE_ENV_OS,
				sublistType);

		jsonObject.put("envOS", toJSONArray(envOSListTypes));

		if (ProductEntryConstants.isDigitalEnterpriseVersion7_0(listTypeId) ||
			ProductEntryConstants.isDigitalEnterpriseVersion7_1(listTypeId) ||
			ProductEntryConstants.isDigitalEnterpriseVersion7_2(listTypeId) ||
			ProductEntryConstants.isDigitalEnterpriseVersion7_3(listTypeId) ||
			ProductEntryConstants.isDigitalEnterpriseVersion7_4(listTypeId)) {

			JSONArray envSearchJSONArray = JSONFactoryUtil.createJSONArray();

			JSONObject envSearchEnterpriseJSONObject =
				JSONFactoryUtil.createJSONObject();

			List<ListType> envSearchEnterpriseListTypes =
				AccountEnvironmentConstants.getEnvListTypes(
					listTypeId,
					AccountEnvironmentConstants.LIST_TYPE_ENV_SEARCH,
					ArrayUtil.append(new String[] {"enterprise"}, sublistType));

			envSearchEnterpriseJSONObject.put(
				"enterprise", toJSONArray(envSearchEnterpriseListTypes));

			envSearchJSONArray.put(envSearchEnterpriseJSONObject);

			JSONObject envSearchStandardJSONObject =
				JSONFactoryUtil.createJSONObject();

			List<ListType> envSearchStandardListTypes =
				AccountEnvironmentConstants.getEnvListTypes(
					listTypeId,
					AccountEnvironmentConstants.LIST_TYPE_ENV_SEARCH,
					ArrayUtil.append(new String[] {"standard"}, sublistType));

			envSearchStandardJSONObject.put(
				"standard", toJSONArray(envSearchStandardListTypes));

			envSearchJSONArray.put(envSearchStandardJSONObject);

			jsonObject.put("envSearch", envSearchJSONArray);
		}

		return jsonObject;
	}

	protected JSONArray getEnvLFRVersionsJSONArray(
			Set<ListType> envLFRVersions, String... sublistType)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ListType listType : envLFRVersions) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put(
				String.valueOf(listType.getListTypeId()),
				getEnvLFRJSONObject(listType, sublistType));

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	protected JSONObject getProductJSONObject(
			ProductEntry productEntry,
			Set<Integer> enterpriseSearchEnvironments)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("displayName", productEntry.getName());

		if (productEntry.isCommerce() || productEntry.isDXP()) {
			if (enterpriseSearchEnvironments.contains(
					productEntry.getEnvironment())) {

				jsonObject.put("enterpriseSearch", true);
			}
			else {
				jsonObject.put("enterpriseSearch", false);
			}
		}

		jsonObject.put("productEntryId", productEntry.getProductEntryId());

		if (productEntry.isCommerce()) {
			jsonObject.put(
				"envCommerce",
				toJSONArray(getCurrentVersionsListTypes(productEntry)));
		}
		else {
			jsonObject.put(
				"envLFR",
				toJSONArray(getCurrentVersionsListTypes(productEntry)));
		}

		return jsonObject;
	}

	protected JSONArray toEnvSearchJSONArray(List<String> envSearches) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String envSearch : envSearches) {
			jsonArray.put(LanguageUtil.get(_request, envSearch));
		}

		return jsonArray;
	}

	protected JSONArray toJSONArray(List<ListType> listTypes) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ListType listType : listTypes) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put(
				"name", LanguageUtil.get(_request, listType.getName()));
			jsonObject.put("value", listType.getListTypeId());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private final Account _account;
	private final AccountEntry _accountEntry;
	private final AccountEntryDetailsRequestHelper
		_accountEntryDetailsRequestHelper;
	private final AccountEntryLanguageLocalService
		_accountEntryLanguageLocalService;
	private final AccountReader _accountReader;
	private final AuditEntryWebService _auditEntryWebService;
	private final ContactRoleWebService _contactRoleWebService;
	private final ContactWebService _contactWebService;
	private final boolean _isPartnerManagedSupportWorker;
	private final MimeResponse _mimeResponse;
	private final String _partnerManagedSupportName;
	private final String _partnerName;
	private final PortletRequest _portletRequest;
	private final ProductPurchaseViewWebService _productPurchaseViewWebService;
	private final ProductPurchaseWebService _productPurchaseWebService;
	private final HttpServletRequest _request;
	private final TeamWebService _teamWebService;
	private final TicketAttachmentLocalService _ticketAttachmentLocalService;
	private final User _user;

}