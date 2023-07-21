/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.item.type;

import com.liferay.info.item.InfoItemReference;

import java.util.Date;
import java.util.Locale;

/**
 * @author Cristina González
 */
public interface ContentDashboardItemType<T> {

	public String getFullLabel(Locale locale);

	public InfoItemReference getInfoItemReference();

	public String getLabel(Locale locale);

	public Date getModifiedDate();

	public long getUserId();

	public String toJSONString(Locale locale);

}