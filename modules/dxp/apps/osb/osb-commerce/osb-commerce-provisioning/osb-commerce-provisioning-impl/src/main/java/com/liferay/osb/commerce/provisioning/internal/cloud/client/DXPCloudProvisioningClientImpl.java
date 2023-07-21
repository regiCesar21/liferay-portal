/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.commerce.provisioning.internal.cloud.client;

import com.fasterxml.jackson.core.type.TypeReference;

import com.liferay.osb.commerce.provisioning.internal.cloud.client.dto.PortalInstance;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;

import org.apache.http.client.utils.URIBuilder;

/**
 * @author Ivica Cardic
 */
public class DXPCloudProvisioningClientImpl
	extends BaseClientImpl implements DXPCloudProvisioningClient {

	public DXPCloudProvisioningClientImpl(
		String dxpCloudAPIURL, String password, String userName) {

		_dxpCloudAPIURL = dxpCloudAPIURL;
		_password = password;
		_userName = userName;
	}

	@Override
	public void deletePortalInstance(String portalInstanceId) {
		executeDelete(
			getBasicAuthorizationHeader(_password, _userName),
			_getProvisioningPortalInstancesURI(portalInstanceId));
	}

	@Override
	public PortalInstance getPortalInstance(String portalInstanceId) {
		return executeGet(
			getBasicAuthorizationHeader(_password, _userName),
			_getProvisioningPortalInstancesURI(portalInstanceId),
			PortalInstance.class);
	}

	@Override
	public List<PortalInstance> getPortalInstances() {
		return executeGet(
			getBasicAuthorizationHeader(_password, _userName),
			new TypeReference<List<PortalInstance>>() {
			},
			_getProvisioningPortalInstancesURI());
	}

	@Override
	public PortalInstance postPortalInstance(PortalInstance portalInstance) {
		try {
			URIBuilder uriBuilder = new URIBuilder(
				_getProvisioningPortalInstancesURI());

			URI uri = uriBuilder.build();

			return executePost(
				getBasicAuthorizationHeader(_password, _userName),
				portalInstance, uri.toString(), PortalInstance.class);
		}
		catch (URISyntaxException uriSyntaxException) {
			throw new SystemException(uriSyntaxException);
		}
	}

	@Override
	public PortalInstance updatePortalInstance(
		String domain, String portalInstanceId) {

		return executeUpdate(
			getBasicAuthorizationHeader(_password, _userName),
			HashMapBuilder.put(
				"domain", domain
			).build(),
			PortalInstance.class,
			_getProvisioningPortalInstancesURI(portalInstanceId));
	}

	private String _getProvisioningPortalInstancesURI() {
		return _dxpCloudAPIURL + _PROVISIONING_SAAS_PORTAL_INSTANCES_PATH;
	}

	private String _getProvisioningPortalInstancesURI(String portalInstanceId) {
		return StringBundler.concat(
			_dxpCloudAPIURL, _PROVISIONING_SAAS_PORTAL_INSTANCES_PATH, "/",
			portalInstanceId);
	}

	private static final String _PROVISIONING_SAAS_PORTAL_INSTANCES_PATH =
		"/provisioning/saas/portal-instances";

	private final String _dxpCloudAPIURL;
	private final String _password;
	private final String _userName;

}