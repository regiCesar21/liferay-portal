/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.bom.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceBOMFolder&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceBOMFolder
 * @generated
 */
public class CommerceBOMFolderTable extends BaseTable<CommerceBOMFolderTable> {

	public static final CommerceBOMFolderTable INSTANCE =
		new CommerceBOMFolderTable();

	public final Column<CommerceBOMFolderTable, Long> commerceBOMFolderId =
		createColumn(
			"commerceBOMFolderId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceBOMFolderTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderTable, Long>
		parentCommerceBOMFolderId = createColumn(
			"parentCommerceBOMFolderId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderTable, Long> logoId = createColumn(
		"logoId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderTable, String> treePath = createColumn(
		"treePath", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CommerceBOMFolderTable() {
		super("CommerceBOMFolder", CommerceBOMFolderTable::new);
	}

}