/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.client;

import com.liferay.analytics.model.IdentityContextMessage;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Eduardo García
 */
@ProviderType
public interface IdentityClient {

	public String getUserId(IdentityContextMessage identityContextMessage)
		throws Exception;

}