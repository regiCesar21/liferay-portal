/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.configuration;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Shinn Lok
 */
public class OAuthConfigurationValues {

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static final String OAUTH_CLASS_NAME = GetterUtil.getString(
		OAuthConfigurationUtil.get("oauth.class.name"));

	public static final String OAUTH_REALM = GetterUtil.getString(
		OAuthConfigurationUtil.get("oauth.realm"));

}