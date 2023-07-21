/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.chat.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Chat_Entry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Entry
 * @generated
 */
public class EntryTable extends BaseTable<EntryTable> {

	public static final EntryTable INSTANCE = new EntryTable();

	public final Column<EntryTable, Long> entryId = createColumn(
		"entryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<EntryTable, Long> createDate = createColumn(
		"createDate", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<EntryTable, Long> fromUserId = createColumn(
		"fromUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<EntryTable, Long> toUserId = createColumn(
		"toUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<EntryTable, String> content = createColumn(
		"content", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<EntryTable, Integer> flag = createColumn(
		"flag", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private EntryTable() {
		super("Chat_Entry", EntryTable::new);
	}

}