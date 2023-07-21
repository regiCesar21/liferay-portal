/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.data.integration.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CDataIntegrationProcess&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcess
 * @generated
 */
public class CommerceDataIntegrationProcessTable
	extends BaseTable<CommerceDataIntegrationProcessTable> {

	public static final CommerceDataIntegrationProcessTable INSTANCE =
		new CommerceDataIntegrationProcessTable();

	public final Column<CommerceDataIntegrationProcessTable, Long>
		commerceDataIntegrationProcessId = createColumn(
			"CDataIntegrationProcessId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceDataIntegrationProcessTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessTable, String> type =
		createColumn("type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessTable, Clob>
		typeSettings = createColumn(
			"typeSettings", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessTable, Boolean> system =
		createColumn(
			"system_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessTable, Boolean> active =
		createColumn(
			"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessTable, String>
		cronExpression = createColumn(
			"cronExpression", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessTable, Date> startDate =
		createColumn(
			"startDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessTable, Date> endDate =
		createColumn(
			"endDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private CommerceDataIntegrationProcessTable() {
		super(
			"CDataIntegrationProcess",
			CommerceDataIntegrationProcessTable::new);
	}

}