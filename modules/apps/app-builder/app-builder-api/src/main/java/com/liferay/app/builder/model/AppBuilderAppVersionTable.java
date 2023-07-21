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
 * The table class for the &quot;AppBuilderAppVersion&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppVersion
 * @generated
 */
public class AppBuilderAppVersionTable
	extends BaseTable<AppBuilderAppVersionTable> {

	public static final AppBuilderAppVersionTable INSTANCE =
		new AppBuilderAppVersionTable();

	public final Column<AppBuilderAppVersionTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppVersionTable, Long>
		appBuilderAppVersionId = createColumn(
			"appBuilderAppVersionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AppBuilderAppVersionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppVersionTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppVersionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppVersionTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppVersionTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppVersionTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppVersionTable, Long> appBuilderAppId =
		createColumn(
			"appBuilderAppId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppVersionTable, Long> ddlRecordSetId =
		createColumn(
			"ddlRecordSetId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppVersionTable, Long> ddmStructureId =
		createColumn(
			"ddmStructureId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppVersionTable, Long> ddmStructureLayoutId =
		createColumn(
			"ddmStructureLayoutId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppVersionTable, String> version =
		createColumn(
			"version", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private AppBuilderAppVersionTable() {
		super("AppBuilderAppVersion", AppBuilderAppVersionTable::new);
	}

}