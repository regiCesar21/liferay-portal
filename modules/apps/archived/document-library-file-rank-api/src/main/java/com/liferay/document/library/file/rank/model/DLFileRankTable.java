/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.file.rank.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DLFileRank&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileRank
 * @generated
 */
public class DLFileRankTable extends BaseTable<DLFileRankTable> {

	public static final DLFileRankTable INSTANCE = new DLFileRankTable();

	public final Column<DLFileRankTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DLFileRankTable, Long> fileRankId = createColumn(
		"fileRankId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DLFileRankTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFileRankTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFileRankTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFileRankTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DLFileRankTable, Long> fileEntryId = createColumn(
		"fileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFileRankTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private DLFileRankTable() {
		super("DLFileRank", DLFileRankTable::new);
	}

}