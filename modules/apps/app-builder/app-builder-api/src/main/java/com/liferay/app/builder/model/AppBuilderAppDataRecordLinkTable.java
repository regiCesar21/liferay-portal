/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AppBuilderAppDataRecordLink&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDataRecordLink
 * @generated
 */
public class AppBuilderAppDataRecordLinkTable
	extends BaseTable<AppBuilderAppDataRecordLinkTable> {

	public static final AppBuilderAppDataRecordLinkTable INSTANCE =
		new AppBuilderAppDataRecordLinkTable();

	public final Column<AppBuilderAppDataRecordLinkTable, Long>
		appBuilderAppDataRecordLinkId = createColumn(
			"appBuilderAppDataRecordLinkId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AppBuilderAppDataRecordLinkTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppDataRecordLinkTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppDataRecordLinkTable, Long>
		appBuilderAppId = createColumn(
			"appBuilderAppId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppDataRecordLinkTable, Long>
		appBuilderAppVersionId = createColumn(
			"appBuilderAppVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppDataRecordLinkTable, Long> ddlRecordId =
		createColumn(
			"ddlRecordId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AppBuilderAppDataRecordLinkTable() {
		super(
			"AppBuilderAppDataRecordLink",
			AppBuilderAppDataRecordLinkTable::new);
	}

}