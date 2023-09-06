/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.distributed.messaging.internal.subscribing;

import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkDomain;
import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkEntityName;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.provisioning.customer.model.AccountEntry;
import com.liferay.osb.provisioning.customer.web.service.AccountEntryWebService;
import com.liferay.osb.provisioning.distributed.messaging.internal.configuration.DistributedMessagingConfiguration;
import com.liferay.osb.provisioning.koroneiki.web.service.AccountWebService;
import com.liferay.osb.provisioning.koroneiki.web.service.ContactRoleWebService;
import com.liferay.osb.provisioning.koroneiki.web.service.ContactWebService;
import com.liferay.osb.provisioning.koroneiki.web.service.TeamWebService;
import com.liferay.osb.provisioning.util.CustomerPortalRelease;
import com.liferay.osb.provisioning.zendesk.constants.ZendeskTicketConstants;
import com.liferay.osb.provisioning.zendesk.model.ZendeskOrganization;
import com.liferay.osb.provisioning.zendesk.model.ZendeskTicket;
import com.liferay.osb.provisioning.zendesk.web.service.ZendeskOrganizationWebService;
import com.liferay.osb.provisioning.zendesk.web.service.ZendeskTicketWebService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jenny Chen
 */
@Component(
	configurationPid = "com.liferay.osb.provisioning.distributed.messaging.internal.configuration.DistributedMessagingConfiguration",
	immediate = true, property = "topic.pattern=ebenezer-support-case-entries",
	service = SalesforceCasesMessageSubscriber.class
)
public class SalesforceCasesMessageSubscriber extends BaseMessageSubscriber {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_distributedMessagingConfiguration =
			ConfigurableUtil.createConfigurable(
				DistributedMessagingConfiguration.class, properties);
	}

	protected JSONArray convertToJSONArray(String majorCase) {
		if (Validator.isNull(majorCase)) {
			return null;
		}

		String[] majorCases = StringUtil.split(
			majorCase, StringPool.NEW_LINE + StringPool.NEW_LINE);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String curMajorCase : majorCases) {
			JSONObject jsonObject = convertToJSONObject(curMajorCase);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	protected JSONObject convertToJSONObject(String majorCase) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String[] majorCases = StringUtil.split(
			majorCase, StringPool.COMMA + StringPool.NEW_LINE);

		for (String curMajorCase : majorCases) {
			String[] pairs = StringUtil.split(
				curMajorCase, StringPool.COLON + StringPool.SPACE);

			jsonObject.put(String.valueOf(pairs[0]), String.valueOf(pairs[1]));
		}

		return jsonObject;
	}

	protected List<String> convertToList(JSONObject jsonObject) {
		List<String> stringList = new ArrayList<>();

		Iterator<String> jsonKeys = jsonObject.keys();

		while (jsonKeys.hasNext()) {
			String jsonKey = jsonKeys.next();

			stringList.add(
				StringBundler.concat(
					jsonKey, StringPool.COLON, StringPool.SPACE,
					jsonObject.getString(jsonKey)));
		}

		return stringList;
	}

	@Override
	protected void doParse(JSONObject jsonObject) throws Exception {
		String accountKey = _getAccountKey(jsonObject.getString("projectKey"));

		if (Validator.isNull(accountKey)) {
			return;
		}

		long accountEntryId = _getAccountEntryId(accountKey);

		if (accountEntryId <= 0) {
			return;
		}

		_updateZendesk(accountEntryId, jsonObject);
	}

	protected String parseMajorCase(JSONObject jsonObject) {
		List<String> stringList = convertToList(jsonObject);

		if (stringList.isEmpty()) {
			return null;
		}

		return StringUtil.merge(
			stringList, StringPool.COMMA + StringPool.NEW_LINE);
	}

	protected String parseMajorCases(JSONObject jsonObject, String majorCases) {
		if (Validator.isNull(majorCases)) {
			return parseMajorCase(jsonObject);
		}

		JSONArray jsonArray = convertToJSONArray(majorCases);

		String caseNumber = jsonObject.getString("caseNumber");

		if (jsonArray != null) {
			if (jsonArray.length() == 1) {
				JSONObject latestJSONObject = jsonArray.getJSONObject(0);

				String latestCaseNumber = latestJSONObject.getString(
					"caseNumber");

				if (latestCaseNumber.equals(caseNumber)) {
					return parseMajorCase(jsonObject);
				}

				return StringBundler.concat(
					parseMajorCase(jsonObject), StringPool.NEW_LINE,
					StringPool.NEW_LINE, parseMajorCase(latestJSONObject));
			}
			else if (jsonArray.length() == 2) {
				JSONObject latestJSONObject = jsonArray.getJSONObject(0);

				String latestCaseNumber = latestJSONObject.getString(
					"caseNumber");

				if (latestCaseNumber.equals(caseNumber)) {
					return StringBundler.concat(
						parseMajorCase(jsonObject), StringPool.NEW_LINE,
						StringPool.NEW_LINE,
						parseMajorCase(jsonArray.getJSONObject(1)));
				}

				return StringBundler.concat(
					parseMajorCase(jsonObject), StringPool.NEW_LINE,
					StringPool.NEW_LINE, parseMajorCase(latestJSONObject));
			}
		}

		return StringPool.BLANK;
	}

	private long _getAccountEntryId(String accountKey) throws Exception {
		AccountEntry accountEntry = _accountEntryWebService.fetchAccountEntry(
			accountKey);

		if (accountEntry != null) {
			return accountEntry.getAccountEntryId();
		}

		return 0;
	}

	private String _getAccountKey(String salesforceProjectKey)
		throws Exception {

		List<Account> accounts = _accountWebService.getAccounts(
			ExternalLinkDomain.SALESFORCE,
			ExternalLinkEntityName.SALESFORCE_PROJECT, salesforceProjectKey, 1,
			1);

		if (!accounts.isEmpty()) {
			Account account = accounts.get(0);

			return account.getKey();
		}

		return null;
	}

	private void _updateZendesk(long accountEntryId, JSONObject jsonObject)
		throws Exception {

		ZendeskOrganization zendeskOrganization =
			_zendeskOrganizationWebService.getZendeskOrganization(
				String.valueOf(accountEntryId));

		if (zendeskOrganization == null) {
			return;
		}

		String majorCases = parseMajorCases(
			jsonObject, zendeskOrganization.getMajorCases());

		zendeskOrganization.setMajorCases(majorCases);

		_zendeskOrganizationWebService.updateZendeskOrganization(
			zendeskOrganization);

		Set<String> criteria = new HashSet<>();

		criteria.add(
			"organization:" + zendeskOrganization.getZendeskOrganizationId());
		criteria.add("status<" + ZendeskTicketConstants.STATUS_CLOSED);

		List<ZendeskTicket> zendeskTickets =
			_zendeskTicketWebService.getZendeskTickets(criteria);

		for (ZendeskTicket zendeskTicket : zendeskTickets) {
			Map<Long, String> customFields = zendeskTicket.getCustomFields();

			customFields.put(
				_distributedMessagingConfiguration.
					zendeskTicketCustomFieldMajorCasesId(),
				majorCases);

			zendeskTicket.setCustomFields(customFields);
		}

		_zendeskTicketWebService.updateZendeskTickets(zendeskTickets);
	}

	@Reference
	private AccountEntryWebService _accountEntryWebService;

	@Reference
	private AccountWebService _accountWebService;

	@Reference
	private ContactRoleWebService _contactRoleWebService;

	@Reference
	private ContactWebService _contactWebService;

	@Reference
	private CustomerPortalRelease _customerPortalRelease;

	private volatile DistributedMessagingConfiguration
		_distributedMessagingConfiguration;

	@Reference
	private TeamWebService _teamWebService;

	@Reference
	private ZendeskOrganizationWebService _zendeskOrganizationWebService;

	@Reference
	private ZendeskTicketWebService _zendeskTicketWebService;

}