/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.marketplace.web.service.internal;

import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Contact;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ContactRole;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.PostalAddress;
import com.liferay.osb.provisioning.koroneiki.constants.ContactRoleConstants;
import com.liferay.osb.provisioning.koroneiki.constants.ProductPurchaseConstants;
import com.liferay.osb.provisioning.koroneiki.reader.AccountReader;
import com.liferay.osb.provisioning.marketplace.web.service.MarketplaceWebService;
import com.liferay.osb.provisioning.marketplace.web.service.internal.configuration.MarketplaceConfiguration;
import com.liferay.petra.json.web.service.client.BaseJSONWebServiceClientImpl;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.nio.charset.StandardCharsets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.reactor.IOReactorException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(
	configurationPid = "com.liferay.osb.provisioning.marketplace.web.service.internal.configuration.MarketplaceConfiguration",
	immediate = true, service = MarketplaceWebService.class
)
public class MarketplaceWebServiceImpl
	extends BaseJSONWebServiceClientImpl implements MarketplaceWebService {

	@Override
	public void afterPropertiesSet() throws IOReactorException {
		setMaxAttempts(3);

		super.afterPropertiesSet();
	}

	public void syncAccount(Account account) throws Exception {
		List<NameValuePair> authHeaders = new ArrayList<>();

		authHeaders.add(
			new BasicNameValuePair(
				"Authorization", "Bearer " + _getAuthorizationToken()));

		JSONObject accountJSONObject = _putAccount(authHeaders, account);

		JSONArray jsonArray = _getUserAccounts(
			authHeaders, accountJSONObject.getLong("id"));

		List<Contact> addContacts = _getAddContacts(account, jsonArray);

		for (Contact contact : addContacts) {
			_assignUserToAccount(
				authHeaders, account, contact.getEmailAddress());
		}

		List<String> removeEmailAddresses = _getRemoveContacts(
			account, jsonArray);

		for (String emailAddress : removeEmailAddresses) {
			_unassignUserToAccount(authHeaders, account, emailAddress);
		}

		JSONArray postalAddressesJSONArray = _getPostalAddresses(
			authHeaders, account.getKey());

		PostalAddress[] postalAddresses = account.getPostalAddresses();

		if (!ArrayUtil.isEmpty(postalAddresses)) {
			for (PostalAddress postalAddress : postalAddresses) {
				_postPostalAddress(
					authHeaders, account.getKey(), postalAddress);
			}
		}

		for (int i = 0; i < postalAddressesJSONArray.length(); i++) {
			JSONObject jsonObject = postalAddressesJSONArray.getJSONObject(i);

			String externalReferenceCode = jsonObject.getString(
				"externalReferenceCode");

			if (!_hasAddress(postalAddresses, externalReferenceCode)) {
				_deletePostalAddress(authHeaders, jsonObject.getLong("id"));
			}
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		MarketplaceConfiguration marketplaceConfiguration =
			ConfigurableUtil.createConfigurable(
				MarketplaceConfiguration.class, properties);

		_clientId = marketplaceConfiguration.clientId();
		_clientSecret = marketplaceConfiguration.clientSecret();
		_domainName = marketplaceConfiguration.domainName();
	}

	@Override
	protected String execute(HttpRequestBase httpRequestBase)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException {

		setHostName(_domainName);
		setHostPort(Http.HTTPS_PORT);
		setProtocol(Http.HTTPS);

		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

		requestConfigBuilder.setCookieSpec(CookieSpecs.STANDARD);

		httpRequestBase.setConfig(requestConfigBuilder.build());

		return super.execute(httpRequestBase);
	}

	@Override
	protected void signRequest(HttpRequestBase httpRequestBase) {
	}

	private void _assignUserToAccount(
			List<NameValuePair> authHeaders, Account account,
			String emailAddress)
		throws Exception {

		String url = StringUtil.replace(
			_URL_API_ACCOUNT_USER_ACCOUNTS_BY_EMAIL_ADDRESS,
			new String[] {"{externalReferenceCode}", "{emailAddress}"},
			new String[] {account.getKey(), URLCodec.encodeURL(emailAddress)});

		doPost(url, Collections.emptyList(), authHeaders);
	}

	private JSONObject _createCustomFieldJSONObject(
		String name, String dataType, String data) {

		return JSONUtil.put(
			"customValue", JSONUtil.put("data", data)
		).put(
			"dataType", dataType
		).put(
			"name", name
		);
	}

	private void _deletePostalAddress(
			List<NameValuePair> authHeaders, long addressId)
		throws Exception {

		doDelete(
			StringUtil.replace(
				_URL_API_ACCOUNT_ADDRESS, "{id}", String.valueOf(addressId)),
			Collections.emptyList(), authHeaders);
	}

	private int _getAccountStatus(Account account) {
		String subscriptionState = _accountReader.getSubscriptionState(account);

		if (subscriptionState.equals(ProductPurchaseConstants.STATE_ACTIVE)) {
			return WorkflowConstants.STATUS_APPROVED;
		}
		else if (subscriptionState.equals(
					ProductPurchaseConstants.STATE_EXPIRED)) {

			return WorkflowConstants.STATUS_EXPIRED;
		}
		else if (subscriptionState.equals(
					ProductPurchaseConstants.STATE_UNACTIVATED)) {

			return WorkflowConstants.STATUS_PENDING;
		}

		return WorkflowConstants.STATUS_INACTIVE;
	}

	private List<Contact> _getAddContacts(
		Account account, JSONArray jsonArray) {

		List<Contact> addContacts = new ArrayList<>();

		Contact[] customerContacts = account.getCustomerContacts();

		for (Contact contact : customerContacts) {
			if (!_isSupportAdmin(contact)) {
				continue;
			}

			boolean isAlreadyAdded = false;

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				String emailAddress = jsonObject.getString("emailAddress");

				if (StringUtil.equalsIgnoreCase(
						emailAddress, contact.getEmailAddress())) {

					isAlreadyAdded = true;

					break;
				}
			}

			if (!isAlreadyAdded) {
				addContacts.add(contact);
			}
		}

		return addContacts;
	}

	private String _getAuthorizationToken() throws Exception {
		List<NameValuePair> parameters = Arrays.asList(
			new BasicNameValuePair("client_id", _clientId),
			new BasicNameValuePair("client_secret", _clientSecret),
			new BasicNameValuePair("grant_type", "client_credentials"),
			new BasicNameValuePair("response_type", "code"));

		List<NameValuePair> headers = Arrays.asList(
			new BasicNameValuePair(
				"Content-Type",
				ContentTypes.APPLICATION_X_WWW_FORM_URLENCODED));

		String response = doPost(_URL_OAUTH_TOKEN, parameters, headers);

		JSONObject jsonObject = _jsonFactory.createJSONObject(response);

		return jsonObject.getString("access_token");
	}

	private Country _getCountry(String addressCountry) throws Exception {
		addressCountry = StringUtil.toLowerCase(addressCountry);

		addressCountry = StringUtil.replace(
			addressCountry, CharPool.SPACE, CharPool.DASH);

		return _countryService.getCountryByName(addressCountry);
	}

	private JSONArray _getPostalAddresses(
			List<NameValuePair> authHeaders, String externalReferenceCode)
		throws Exception {

		String response = doGet(
			StringUtil.replace(
				_URL_API_ACCOUNT_BY_EXTERNAL_REFERENCE_CODE_ADDRESSES,
				"{externalReferenceCode}", externalReferenceCode),
			Collections.emptyList(), authHeaders);

		JSONObject jsonObject = _jsonFactory.createJSONObject(response);

		return jsonObject.getJSONArray("items");
	}

	private String _getRegionCode(String addressRegion, long countryId) {
		if (Validator.isNull(addressRegion) || (countryId <= 0)) {
			return StringPool.BLANK;
		}

		Region region = _regionService.fetchRegion(countryId, addressRegion);

		if (region != null) {
			return region.getRegionCode();
		}

		List<Region> regions = _regionService.getRegions(countryId);

		for (Region curRegion : regions) {
			if (StringUtil.equalsIgnoreCase(
					addressRegion, curRegion.getName())) {

				return curRegion.getRegionCode();
			}
		}

		return StringPool.BLANK;
	}

	private List<String> _getRemoveContacts(
		Account account, JSONArray jsonArray) {

		List<String> removeContacts = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			boolean remove = true;

			String emailAddress = jsonObject.getString("emailAddress");

			Contact[] customerContacts = account.getCustomerContacts();

			for (Contact contact : customerContacts) {
				if (!_isSupportAdmin(contact)) {
					continue;
				}

				if (StringUtil.equalsIgnoreCase(
						emailAddress, contact.getEmailAddress())) {

					remove = false;
				}
			}

			if (remove) {
				removeContacts.add(emailAddress);
			}
		}

		return removeContacts;
	}

	private StringEntity _getStringEntity(String json) {
		StringEntity stringEntity = new StringEntity(
			json, StandardCharsets.UTF_8);

		stringEntity.setContentType("application/json");

		return stringEntity;
	}

	private JSONArray _getUserAccounts(
			List<NameValuePair> authHeaders, long accountId)
		throws Exception {

		String response = doGet(
			StringUtil.replace(
				_URL_API_ACCOUNT_USER_ACCOUNTS, "{accountId}",
				String.valueOf(accountId)),
			Arrays.asList(new BasicNameValuePair("pageSize", "10000")),
			authHeaders);

		JSONObject jsonObject = _jsonFactory.createJSONObject(response);

		return jsonObject.getJSONArray("items");
	}

	private boolean _hasAddress(
		PostalAddress[] postalAddresses, String externalReferenceCode) {

		if (ArrayUtil.isEmpty(postalAddresses)) {
			return false;
		}

		for (PostalAddress postalAddress : postalAddresses) {
			if (externalReferenceCode.equals(
					String.valueOf(postalAddress.getId()))) {

				return true;
			}
		}

		return false;
	}

	private boolean _isSupportAdmin(Contact contact) {
		ContactRole[] contactRoles = contact.getContactRoles();

		for (ContactRole contactRole : contactRoles) {
			String name = contactRole.getName();

			if (name.equals(ContactRoleConstants.NAME_SUPPORT_ADMINISTRATOR)) {
				return true;
			}
		}

		return false;
	}

	private void _postPostalAddress(
			List<NameValuePair> authHeaders, String accountExternalReference,
			PostalAddress postalAddress)
		throws Exception {

		String url = StringUtil.replace(
			_URL_API_ACCOUNT_BY_EXTERNAL_REFERENCE_CODE_ADDRESSES,
			"{externalReferenceCode}", accountExternalReference);

		HttpPost httpPost = new HttpPost(url);

		addHeaders(httpPost, authHeaders);

		Country country = _getCountry(postalAddress.getAddressCountry());

		JSONObject jsonObject = JSONUtil.put(
			"city", postalAddress.getAddressLocality()
		).put(
			"countryISOCode", country.getA2()
		).put(
			"externalReferenceCode", postalAddress.getId()
		).put(
			"name", postalAddress.getId()
		).put(
			"regionISOCode",
			_getRegionCode(
				postalAddress.getAddressRegion(), country.getCountryId())
		).put(
			"street1", postalAddress.getStreetAddressLine1()
		).put(
			"street2", postalAddress.getStreetAddressLine2()
		).put(
			"street3", postalAddress.getStreetAddressLine3()
		).put(
			"type", _ADDRESS_TYPE_BILLING_AND_SHIPPING
		).put(
			"zip", postalAddress.getPostalCode()
		);

		httpPost.setEntity(_getStringEntity(jsonObject.toString()));

		execute(httpPost);
	}

	private JSONObject _putAccount(
			List<NameValuePair> authHeaders, Account account)
		throws Exception {

		String url = StringUtil.replace(
			_URL_API_ACCOUNT_BY_EXTERNAL_REFERENCE_CODE,
			"{externalReferenceCode}", account.getKey());

		HttpPut httpPut = new HttpPut(url);

		addHeaders(httpPut, authHeaders);

		JSONObject jsonObject = JSONUtil.put(
			"name", account.getName()
		).put(
			"status", _getAccountStatus(account)
		).put(
			"type", "business"
		);

		DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		jsonArray.put(
			_createCustomFieldJSONObject(
				"Contact Email", ExpandoColumnConstants.DATA_TYPE_TEXT,
				account.getContactEmailAddress())
		).put(
			_createCustomFieldJSONObject(
				"Contact Phone", ExpandoColumnConstants.DATA_TYPE_TEXT,
				account.getPhoneNumber())
		).put(
			_createCustomFieldJSONObject(
				"Create Date", StringPool.BLANK,
				dateFormat.format(account.getDateCreated()))
		).put(
			_createCustomFieldJSONObject(
				"Homepage Url", ExpandoColumnConstants.DATA_TYPE_TEXT,
				account.getWebsite())
		);

		jsonObject.put("customFields", jsonArray);

		httpPut.setEntity(_getStringEntity(jsonObject.toString()));

		String response = execute(httpPut);

		return _jsonFactory.createJSONObject(response);
	}

	private void _unassignUserToAccount(
			List<NameValuePair> authHeaders, Account account,
			String emailAddress)
		throws Exception {

		String url = StringUtil.replace(
			_URL_API_ACCOUNT_USER_ACCOUNTS_BY_EMAIL_ADDRESS,
			new String[] {"{externalReferenceCode}", "{emailAddress}"},
			new String[] {account.getKey(), URLCodec.encodeURL(emailAddress)});

		doDelete(url, Collections.emptyList(), authHeaders);
	}

	private static final int _ADDRESS_TYPE_BILLING_AND_SHIPPING = 2;

	private static final String _URL_API_ACCOUNT_ADDRESS =
		"/o/headless-commerce-admin-account/v1.0/accountAddresses/{id}";

	private static final String _URL_API_ACCOUNT_BY_EXTERNAL_REFERENCE_CODE =
		"/o/headless-admin-user/v1.0/accounts/by-external-reference-code" +
			"/{externalReferenceCode}";

	private static final String
		_URL_API_ACCOUNT_BY_EXTERNAL_REFERENCE_CODE_ADDRESSES =
			"/o/headless-commerce-admin-account/v1.0/accounts" +
				"/by-externalReferenceCode/{externalReferenceCode}" +
					"/accountAddresses";

	private static final String _URL_API_ACCOUNT_USER_ACCOUNTS =
		"/o/headless-admin-user/v1.0/accounts/{accountId}/user-accounts";

	private static final String
		_URL_API_ACCOUNT_USER_ACCOUNTS_BY_EMAIL_ADDRESS =
			"/o/headless-admin-user/v1.0/accounts/by-external-reference-code" +
				"/{externalReferenceCode}/user-accounts/by-email-address" +
					"/{emailAddress}";

	private static final String _URL_OAUTH_TOKEN = "/o/oauth2/token";

	@Reference
	private AccountReader _accountReader;

	private String _clientId;
	private String _clientSecret;

	@Reference
	private CountryService _countryService;

	private String _domainName;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private RegionService _regionService;

}