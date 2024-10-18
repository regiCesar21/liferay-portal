/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.identity.management.internal.provider;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Contact;
import com.liferay.osb.provisioning.identity.management.provider.ContactIdentityProvider;
import com.liferay.osb.provisioning.koroneiki.web.service.ContactWebService;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jenny Chen
 */
@Component(
	immediate = true,
	property = {"host=", "protocol=https", "provider=support"},
	service = ContactIdentityProvider.class
)
public class SupportContactIdentityProvider implements ContactIdentityProvider {

	public void activateUser(String emailAddress) throws Exception {
		throw new UnsupportedOperationException();
	}

	public void addMembership(String groupId, String emailAddress)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public Contact createContact(
			String emailAddress, String firstName, String middleName,
			String lastName)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public Contact fetchContactByEmailAddress(String emailAddress, boolean sync)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public Contact fetchContactByOAuthToken(String oauthToken)
		throws Exception {

		String emailAddress = _portalCache.get(oauthToken);

		if (emailAddress == StringPool.BLANK) {
			return null;
		}

		if (emailAddress == null) {
			String response = _sendRequest(_URL_API_GET_TOKEN_USER, oauthToken);

			if (Validator.isNotNull(response)) {
				JSONObject jsonObject = _jsonFactory.createJSONObject(response);

				emailAddress = jsonObject.getString("emailAddress");
			}
		}

		if (Validator.isNotNull(emailAddress)) {
			_portalCache.put(oauthToken, emailAddress);

			return _contactWebService.fetchContactByEmailAddress(emailAddress);
		}

		_portalCache.put(oauthToken, StringPool.BLANK);

		return null;
	}

	public Contact fetchContactBySessionId(String sessionId) throws Exception {
		throw new UnsupportedOperationException();
	}

	public Contact fetchContactByUuid(String uuid) throws Exception {
		throw new UnsupportedOperationException();
	}

	public Integer fetchContactStatusByEmailAddress(String emailAddress)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public List<Contact> getGroupContacts(String groupId) throws Exception {
		throw new UnsupportedOperationException();
	}

	public void removeMembership(String groupId, String emailAddress)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	public Contact syncContact(Contact contact) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		_host = String.valueOf(properties.get("host"));
		_protocol = String.valueOf(properties.get("protocol"));

		_portalCache = (PortalCache<String, String>)_multiVMPool.getPortalCache(
			SupportContactIdentityProvider.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_multiVMPool.removePortalCache(
			SupportContactIdentityProvider.class.getName());
	}

	private String _sendRequest(String endpoint, String oauthToken)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader("Authorization", "Bearer " + oauthToken);
		options.addHeader("Accept", "application/json");

		StringBundler sb = new StringBundler(4);

		sb.append(_protocol);
		sb.append(Http.PROTOCOL_DELIMITER);
		sb.append(_host);
		sb.append(endpoint);

		options.setLocation(sb.toString());

		String response = StringPool.BLANK;

		byte[] bytes = _http.URLtoByteArray(options);

		if (bytes != null) {
			response = new String(bytes);
		}

		return response;
	}

	private static final String _URL_API_GET_TOKEN_USER =
		"/o/headless-admin-user/v1.0/my-user-account";

	@Reference
	private ContactWebService _contactWebService;

	private String _host;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<String, String> _portalCache;
	private String _protocol;

}