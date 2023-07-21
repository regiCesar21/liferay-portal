/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the &quot;AppBuilderAppDeployment&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDeployment
 * @generated
 */
public class AppBuilderAppDeploymentTable
	extends BaseTable<AppBuilderAppDeploymentTable> {

	public static final AppBuilderAppDeploymentTable INSTANCE =
		new AppBuilderAppDeploymentTable();

	public final Column<AppBuilderAppDeploymentTable, Long>
		appBuilderAppDeploymentId = createColumn(
			"appBuilderAppDeploymentId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AppBuilderAppDeploymentTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppDeploymentTable, Long> appBuilderAppId =
		createColumn(
			"appBuilderAppId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppDeploymentTable, Clob> settings =
		createColumn("settings_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppDeploymentTable, String> type =
		createColumn("type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private AppBuilderAppDeploymentTable() {
		super("AppBuilderAppDeployment", AppBuilderAppDeploymentTable::new);
	}

}