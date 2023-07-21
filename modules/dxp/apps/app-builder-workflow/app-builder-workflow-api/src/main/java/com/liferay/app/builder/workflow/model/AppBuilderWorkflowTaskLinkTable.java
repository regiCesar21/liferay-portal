/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.workflow.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AppBuilderWorkflowTaskLink&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderWorkflowTaskLink
 * @generated
 */
public class AppBuilderWorkflowTaskLinkTable
	extends BaseTable<AppBuilderWorkflowTaskLinkTable> {

	public static final AppBuilderWorkflowTaskLinkTable INSTANCE =
		new AppBuilderWorkflowTaskLinkTable();

	public final Column<AppBuilderWorkflowTaskLinkTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AppBuilderWorkflowTaskLinkTable, Long>
		appBuilderWorkflowTaskLinkId = createColumn(
			"appBuilderWorkflowTaskLinkId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AppBuilderWorkflowTaskLinkTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderWorkflowTaskLinkTable, Long> appBuilderAppId =
		createColumn(
			"appBuilderAppId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderWorkflowTaskLinkTable, Long>
		appBuilderAppVersionId = createColumn(
			"appBuilderAppVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<AppBuilderWorkflowTaskLinkTable, Long>
		ddmStructureLayoutId = createColumn(
			"ddmStructureLayoutId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<AppBuilderWorkflowTaskLinkTable, Boolean> readOnly =
		createColumn(
			"readOnly", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<AppBuilderWorkflowTaskLinkTable, String>
		workflowTaskName = createColumn(
			"workflowTaskName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private AppBuilderWorkflowTaskLinkTable() {
		super(
			"AppBuilderWorkflowTaskLink", AppBuilderWorkflowTaskLinkTable::new);
	}

}