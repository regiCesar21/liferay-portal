/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.item.selector.web.internal.layout.list.retriever;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.info.pagination.Pagination;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.list.retriever.ClassedModelListObjectReference;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverContext;
import com.liferay.portal.kernel.dao.orm.QueryUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = LayoutListRetriever.class)
public class AssetEntryListLayoutListRetriever
	implements LayoutListRetriever
		<InfoListItemSelectorReturnType, ClassedModelListObjectReference> {

	@Override
	public List<Object> getList(
		ClassedModelListObjectReference classedModelListObjectReference,
		LayoutListRetrieverContext layoutListRetrieverContext) {

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.fetchAssetListEntry(
				classedModelListObjectReference.getClassPK());

		if (assetListEntry == null) {
			return Collections.emptyList();
		}

		Optional<long[]> segmentsEntryIdsOptional =
			layoutListRetrieverContext.getSegmentsEntryIdsOptional();

		long[] segmentsEntryIds = segmentsEntryIdsOptional.orElse(
			new long[] {0});

		Optional<Pagination> paginationOptional =
			layoutListRetrieverContext.getPaginationOptional();

		Pagination pagination = paginationOptional.orElse(
			Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		List<AssetEntry> assetEntries =
			_assetListAssetEntryProvider.getAssetEntries(
				assetListEntry, segmentsEntryIds, pagination.getStart(),
				pagination.getEnd());

		if (Objects.equals(
				AssetEntry.class.getName(),
				assetListEntry.getAssetEntryType())) {

			return Collections.unmodifiableList(assetEntries);
		}

		return _toAssetObjects(assetEntries);
	}

	@Override
	public int getListCount(
		ClassedModelListObjectReference classedModelListObjectReference,
		LayoutListRetrieverContext layoutListRetrieverContext) {

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.fetchAssetListEntry(
				classedModelListObjectReference.getClassPK());

		if (assetListEntry == null) {
			return 0;
		}

		Optional<long[]> segmentsEntryIdsOptional =
			layoutListRetrieverContext.getSegmentsEntryIdsOptional();

		long[] segmentsEntryIds = segmentsEntryIdsOptional.orElse(
			new long[] {0});

		return _assetListAssetEntryProvider.getAssetEntriesCount(
			assetListEntry, segmentsEntryIds);
	}

	private List<Object> _toAssetObjects(List<AssetEntry> assetEntries) {
		List<Object> assetObjects = new ArrayList<>(assetEntries.size());

		for (AssetEntry assetEntry : assetEntries) {
			AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

			assetObjects.add(assetRenderer.getAssetObject());
		}

		return assetObjects;
	}

	@Reference
	private AssetListAssetEntryProvider _assetListAssetEntryProvider;

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

}