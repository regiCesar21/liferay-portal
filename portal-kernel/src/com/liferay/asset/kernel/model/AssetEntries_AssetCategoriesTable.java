/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AssetEntries_AssetCategories&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategory
 * @see AssetEntry
 * @generated
 */
public class AssetEntries_AssetCategoriesTable
	extends BaseTable<AssetEntries_AssetCategoriesTable> {

	public static final AssetEntries_AssetCategoriesTable INSTANCE =
		new AssetEntries_AssetCategoriesTable();

	public final Column<AssetEntries_AssetCategoriesTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntries_AssetCategoriesTable, Long> categoryId =
		createColumn(
			"categoryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntries_AssetCategoriesTable, Long> entryId =
		createColumn("entryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntries_AssetCategoriesTable, Long>
		ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetEntries_AssetCategoriesTable, Boolean>
		ctChangeType = createColumn(
			"ctChangeType", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private AssetEntries_AssetCategoriesTable() {
		super(
			"AssetEntries_AssetCategories",
			AssetEntries_AssetCategoriesTable::new);
	}

}