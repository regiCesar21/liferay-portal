/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sync.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;SyncDLFileVersionDiff&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SyncDLFileVersionDiff
 * @generated
 */
public class SyncDLFileVersionDiffTable
	extends BaseTable<SyncDLFileVersionDiffTable> {

	public static final SyncDLFileVersionDiffTable INSTANCE =
		new SyncDLFileVersionDiffTable();

	public final Column<SyncDLFileVersionDiffTable, Long>
		syncDLFileVersionDiffId = createColumn(
			"syncDLFileVersionDiffId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<SyncDLFileVersionDiffTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SyncDLFileVersionDiffTable, Long> fileEntryId =
		createColumn(
			"fileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SyncDLFileVersionDiffTable, Long> sourceFileVersionId =
		createColumn(
			"sourceFileVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<SyncDLFileVersionDiffTable, Long> targetFileVersionId =
		createColumn(
			"targetFileVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<SyncDLFileVersionDiffTable, Long> dataFileEntryId =
		createColumn(
			"dataFileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SyncDLFileVersionDiffTable, Long> size = createColumn(
		"size_", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SyncDLFileVersionDiffTable, Date> expirationDate =
		createColumn(
			"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private SyncDLFileVersionDiffTable() {
		super("SyncDLFileVersionDiff", SyncDLFileVersionDiffTable::new);
	}

}