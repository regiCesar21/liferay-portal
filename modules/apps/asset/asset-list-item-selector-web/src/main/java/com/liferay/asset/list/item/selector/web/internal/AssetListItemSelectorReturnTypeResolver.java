/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.item.selector.web.internal;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ItemSelectorReturnTypeResolver.class)
public class AssetListItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<InfoListItemSelectorReturnType, AssetListEntry> {

	@Override
	public Class<InfoListItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return InfoListItemSelectorReturnType.class;
	}

	@Override
	public Class<AssetListEntry> getModelClass() {
		return AssetListEntry.class;
	}

	@Override
	public String getValue(
		AssetListEntry assetListEntry, ThemeDisplay themeDisplay) {

		return JSONUtil.put(
			"classNameId", _portal.getClassNameId(AssetListEntry.class)
		).put(
			"classPK", assetListEntry.getAssetListEntryId()
		).put(
			"title", assetListEntry.getTitle()
		).toString();
	}

	@Reference
	private Portal _portal;

}