/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.html.preview.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;HtmlPreviewEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see HtmlPreviewEntry
 * @generated
 */
public class HtmlPreviewEntryTable extends BaseTable<HtmlPreviewEntryTable> {

	public static final HtmlPreviewEntryTable INSTANCE =
		new HtmlPreviewEntryTable();

	public final Column<HtmlPreviewEntryTable, Long> htmlPreviewEntryId =
		createColumn(
			"htmlPreviewEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<HtmlPreviewEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<HtmlPreviewEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<HtmlPreviewEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<HtmlPreviewEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<HtmlPreviewEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<HtmlPreviewEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<HtmlPreviewEntryTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<HtmlPreviewEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<HtmlPreviewEntryTable, Long> fileEntryId = createColumn(
		"fileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private HtmlPreviewEntryTable() {
		super("HtmlPreviewEntry", HtmlPreviewEntryTable::new);
	}

}