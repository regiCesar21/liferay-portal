/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

/**
 * @author     Julio Camarero
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             com.liferay.portal.kernel.portlet.constants.PortletPreferencesFactoryConstants}
 */
@Deprecated
public interface PortletPreferencesFactoryConstants {

	public static final String SETTINGS_SCOPE_COMPANY = "company";

	public static final String SETTINGS_SCOPE_GROUP = "group";

	public static final String SETTINGS_SCOPE_PORTLET_INSTANCE =
		"portletInstance";

}