/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.item.action;

import java.util.Locale;

/**
 * @author David Arques
 */
public interface ContentDashboardItemAction {

	public String getIcon();

	public String getLabel(Locale locale);

	public String getName();

	public Type getType();

	public String getURL();

	public String getURL(Locale locale);

	public enum Type {

		DELETE, EDIT, VIEW, VIEW_IN_PANEL

	}

}