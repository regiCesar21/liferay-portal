/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.display.page;

import com.liferay.info.item.InfoItemReference;

/**
 * @author Jorge Ferrer
 */
public interface LayoutDisplayPageProvider<T> {

	public String getClassName();

	public LayoutDisplayPageObjectProvider<T>
		getLayoutDisplayPageObjectProvider(InfoItemReference infoItemReference);

	public LayoutDisplayPageObjectProvider<T>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle);

	public String getURLSeparator();

}