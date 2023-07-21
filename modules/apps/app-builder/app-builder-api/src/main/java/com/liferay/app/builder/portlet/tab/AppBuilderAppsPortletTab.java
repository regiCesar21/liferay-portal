/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.portlet.tab;

import com.liferay.portal.kernel.model.User;

import java.util.Locale;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Inácio Nery
 */
@ProviderType
public interface AppBuilderAppsPortletTab {

	public void deleteApp(long appBuilderAppId, User user) throws Exception;

	public String getEditEntryPoint();

	public String getLabel(Locale locale);

	public String getListEntryPoint();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getListEntryPoint()}
	 */
	@Deprecated
	public default String getPluginEntryPoint() {
		return getListEntryPoint();
	}

}