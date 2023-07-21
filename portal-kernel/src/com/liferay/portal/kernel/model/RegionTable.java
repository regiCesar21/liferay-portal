/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Region&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Region
 * @generated
 */
public class RegionTable extends BaseTable<RegionTable> {

	public static final RegionTable INSTANCE = new RegionTable();

	public final Column<RegionTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RegionTable, Long> regionId = createColumn(
		"regionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RegionTable, Long> countryId = createColumn(
		"countryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RegionTable, String> regionCode = createColumn(
		"regionCode", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RegionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RegionTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private RegionTable() {
		super("Region", RegionTable::new);
	}

}