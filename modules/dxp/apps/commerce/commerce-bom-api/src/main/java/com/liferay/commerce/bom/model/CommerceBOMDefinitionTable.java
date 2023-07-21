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
 * The table class for the &quot;CommerceBOMDefinition&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceBOMDefinition
 * @generated
 */
public class CommerceBOMDefinitionTable
	extends BaseTable<CommerceBOMDefinitionTable> {

	public static final CommerceBOMDefinitionTable INSTANCE =
		new CommerceBOMDefinitionTable();

	public final Column<CommerceBOMDefinitionTable, Long>
		commerceBOMDefinitionId = createColumn(
			"commerceBOMDefinitionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceBOMDefinitionTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMDefinitionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMDefinitionTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMDefinitionTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMDefinitionTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMDefinitionTable, Long> commerceBOMFolderId =
		createColumn(
			"commerceBOMFolderId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceBOMDefinitionTable, Long>
		CPAttachmentFileEntryId = createColumn(
			"CPAttachmentFileEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceBOMDefinitionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMDefinitionTable, String> friendlyUrl =
		createColumn(
			"friendlyUrl", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CommerceBOMDefinitionTable() {
		super("CommerceBOMDefinition", CommerceBOMDefinitionTable::new);
	}

}