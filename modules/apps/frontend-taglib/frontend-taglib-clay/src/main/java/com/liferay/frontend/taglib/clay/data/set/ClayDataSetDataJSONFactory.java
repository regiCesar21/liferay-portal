/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.data.set;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public interface ClayDataSetDataJSONFactory {

	public String create(
			long groupId, String tableName, List<Object> items,
			HttpServletRequest httpServletRequest)
		throws Exception;

	public String create(
			long groupId, String tableName, List<Object> items, int itemsCount,
			HttpServletRequest httpServletRequest)
		throws Exception;

}