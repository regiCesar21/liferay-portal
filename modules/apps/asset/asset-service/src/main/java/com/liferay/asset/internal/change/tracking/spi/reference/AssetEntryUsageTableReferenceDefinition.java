/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.internal.change.tracking.spi.reference;

import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.asset.model.AssetEntryUsageTable;
import com.liferay.asset.service.persistence.AssetEntryUsagePersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class AssetEntryUsageTableReferenceDefinition
	implements TableReferenceDefinition<AssetEntryUsageTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetEntryUsageTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetEntryUsageTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			AssetEntryUsageTable.INSTANCE
		).singleColumnReference(
			AssetEntryUsageTable.INSTANCE.assetEntryId,
			AssetEntryTable.INSTANCE.entryId
		).singleColumnReference(
			AssetEntryUsageTable.INSTANCE.plid, LayoutTable.INSTANCE.plid
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetEntryUsagePersistence;
	}

	@Override
	public AssetEntryUsageTable getTable() {
		return AssetEntryUsageTable.INSTANCE;
	}

	@Reference
	private AssetEntryUsagePersistence _assetEntryUsagePersistence;

}