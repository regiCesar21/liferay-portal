/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.exporter;

import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public interface PortletConfigurationExporter {

	public Map<String, Object> getPortletConfiguration(
		long plid, String portletId);

	public String getPortletName();

}