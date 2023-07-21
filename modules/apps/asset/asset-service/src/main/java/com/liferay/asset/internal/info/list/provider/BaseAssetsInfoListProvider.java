/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.internal.info.list.provider;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.pagination.Pagination;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Optional;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
public abstract class BaseAssetsInfoListProvider {

	protected AssetEntryQuery getAssetEntryQuery(
		InfoListProviderContext infoListProviderContext, String orderByCol,
		String orderByType, Pagination pagination) {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		Company company = infoListProviderContext.getCompany();

		long[] availableClassNameIds =
			AssetRendererFactoryRegistryUtil.getClassNameIds(
				company.getCompanyId(), true);

		availableClassNameIds = ArrayUtil.filter(
			availableClassNameIds,
			availableClassNameId -> {
				Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
					portal.getClassName(availableClassNameId));

				if (indexer == null) {
					return false;
				}

				return true;
			});

		assetEntryQuery.setClassNameIds(availableClassNameIds);

		assetEntryQuery.setEnablePermissions(true);

		Optional<Group> groupOptional =
			infoListProviderContext.getGroupOptional();

		if (groupOptional.isPresent()) {
			Group group = groupOptional.get();

			assetEntryQuery.setGroupIds(new long[] {group.getGroupId()});
		}

		if (pagination != null) {
			assetEntryQuery.setStart(pagination.getStart());
			assetEntryQuery.setEnd(pagination.getEnd());
		}

		assetEntryQuery.setOrderByCol1(orderByCol);
		assetEntryQuery.setOrderByType1(orderByType);

		assetEntryQuery.setOrderByCol2(Field.CREATE_DATE);
		assetEntryQuery.setOrderByType2("DESC");

		return assetEntryQuery;
	}

	@Reference
	protected Portal portal;

}