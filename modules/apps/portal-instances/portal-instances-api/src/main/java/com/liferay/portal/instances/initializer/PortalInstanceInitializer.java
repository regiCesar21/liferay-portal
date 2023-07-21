/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.initializer;

import com.liferay.portal.instances.exception.InitializationException;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Ivica Cardic
 */
@ProviderType
public interface PortalInstanceInitializer {

	public String getKey();

	public void initialize(long companyId) throws InitializationException;

	public void initialize(
			long companyId, HttpServletRequest httpServletRequest,
			Map<String, String> payload)
		throws InitializationException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #initialize(long)}
	 */
	@Deprecated
	public void initialize(String webId, String virtualHostname, String mx)
		throws InitializationException;

	public boolean isActive();

}