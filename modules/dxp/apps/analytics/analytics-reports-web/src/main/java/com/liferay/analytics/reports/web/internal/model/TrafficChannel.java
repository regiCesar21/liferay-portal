/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.portal.kernel.json.JSONObject;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author David Arques
 */
public interface TrafficChannel {

	public String getHelpMessageKey();

	public String getName();

	public long getTrafficAmount();

	public double getTrafficShare();

	public JSONObject toJSONObject(
		Locale locale, ResourceBundle resourceBundle);

}