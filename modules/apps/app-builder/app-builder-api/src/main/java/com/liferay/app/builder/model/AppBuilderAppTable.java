/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AppBuilderApp&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderApp
 * @generated
 */
public class AppBuilderAppTable extends BaseTable<AppBuilderAppTable> {

	public static final AppBuilderAppTable INSTANCE = new AppBuilderAppTable();

	public final Column<AppBuilderAppTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Long> appBuilderAppId =
		createColumn(
			"appBuilderAppId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AppBuilderAppTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Long> ddlRecordSetId = createColumn(
		"ddlRecordSetId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Long> ddmStructureId = createColumn(
		"ddmStructureId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Long> ddmStructureLayoutId =
		createColumn(
			"ddmStructureLayoutId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Long> deDataListViewId =
		createColumn(
			"deDataListViewId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, String> scope = createColumn(
		"scope", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private AppBuilderAppTable() {
		super("AppBuilderApp", AppBuilderAppTable::new);
	}

}