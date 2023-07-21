/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.message.boards.model.MBStatsUserTable;
import com.liferay.message.boards.service.persistence.MBStatsUserPersistence;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class MBStatsUserTableReferenceDefinition
	implements TableReferenceDefinition<MBStatsUserTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<MBStatsUserTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<MBStatsUserTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			MBStatsUserTable.INSTANCE
		).singleColumnReference(
			MBStatsUserTable.INSTANCE.userId, UserTable.INSTANCE.userId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _mbStatsUserPersistence;
	}

	@Override
	public MBStatsUserTable getTable() {
		return MBStatsUserTable.INSTANCE;
	}

	@Reference
	private MBStatsUserPersistence _mbStatsUserPersistence;

}