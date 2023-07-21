/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect;

import java.util.Collection;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Thuong Dinh
 */
@ProviderType
public interface OpenIdConnectProviderRegistry<S, T> {

	public OpenIdConnectProvider<S, T> findOpenIdConnectProvider(
			long companyId, String name)
		throws OpenIdConnectServiceException.ProviderException;

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public default OpenIdConnectProvider<S, T> findOpenIdConnectProvider(
			String name)
		throws OpenIdConnectServiceException.ProviderException {

		throw new UnsupportedOperationException();
	}

	public OpenIdConnectProvider<S, T> getOpenIdConnectProvider(
		long companyId, String name);

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public default OpenIdConnectProvider<S, T> getOpenIdConnectProvider(
		String name) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public default Collection<String> getOpenIdConnectProviderNames() {
		throw new UnsupportedOperationException();
	}

	public Collection<String> getOpenIdConnectProviderNames(long companyId);

}