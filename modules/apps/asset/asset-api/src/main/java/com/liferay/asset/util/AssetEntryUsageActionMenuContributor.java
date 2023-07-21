/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.util;

import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author     Pavel Savinov
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             com.liferay.layout.util.LayoutClassedModelUsageActionMenuContributor}
 */
@Deprecated
public interface AssetEntryUsageActionMenuContributor {

	public List<DropdownItem> getAssetEntryUsageActionMenu(
		AssetEntryUsage assetEntryUsage, HttpServletRequest httpServletRequest);

}