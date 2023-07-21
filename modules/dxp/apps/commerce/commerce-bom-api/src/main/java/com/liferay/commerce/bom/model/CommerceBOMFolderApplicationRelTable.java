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
 * The table class for the &quot;CBOMFolderApplicationRel&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceBOMFolderApplicationRel
 * @generated
 */
public class CommerceBOMFolderApplicationRelTable
	extends BaseTable<CommerceBOMFolderApplicationRelTable> {

	public static final CommerceBOMFolderApplicationRelTable INSTANCE =
		new CommerceBOMFolderApplicationRelTable();

	public final Column<CommerceBOMFolderApplicationRelTable, Long>
		commerceBOMFolderApplicationRelId = createColumn(
			"CBOMFolderApplicationRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceBOMFolderApplicationRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderApplicationRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderApplicationRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderApplicationRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderApplicationRelTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderApplicationRelTable, Long>
		commerceBOMFolderId = createColumn(
			"commerceBOMFolderId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderApplicationRelTable, Long>
		commerceApplicationModelId = createColumn(
			"commerceApplicationModelId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private CommerceBOMFolderApplicationRelTable() {
		super(
			"CBOMFolderApplicationRel",
			CommerceBOMFolderApplicationRelTable::new);
	}

}