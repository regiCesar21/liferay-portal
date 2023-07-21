/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mail.reader.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Mail_Folder&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Folder
 * @generated
 */
public class FolderTable extends BaseTable<FolderTable> {

	public static final FolderTable INSTANCE = new FolderTable();

	public final Column<FolderTable, Long> folderId = createColumn(
		"folderId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<FolderTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FolderTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FolderTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FolderTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FolderTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FolderTable, Long> accountId = createColumn(
		"accountId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FolderTable, String> fullName = createColumn(
		"fullName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FolderTable, String> displayName = createColumn(
		"displayName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FolderTable, Integer> remoteMessageCount = createColumn(
		"remoteMessageCount", Integer.class, Types.INTEGER,
		Column.FLAG_DEFAULT);

	private FolderTable() {
		super("Mail_Folder", FolderTable::new);
	}

}