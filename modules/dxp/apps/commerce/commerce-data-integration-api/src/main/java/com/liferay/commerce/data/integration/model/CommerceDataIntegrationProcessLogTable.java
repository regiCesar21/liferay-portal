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
 * The table class for the &quot;CDataIntegrationProcessLog&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcessLog
 * @generated
 */
public class CommerceDataIntegrationProcessLogTable
	extends BaseTable<CommerceDataIntegrationProcessLogTable> {

	public static final CommerceDataIntegrationProcessLogTable INSTANCE =
		new CommerceDataIntegrationProcessLogTable();

	public final Column<CommerceDataIntegrationProcessLogTable, Long>
		commerceDataIntegrationProcessLogId = createColumn(
			"CDataIntegrationProcessLogId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceDataIntegrationProcessLogTable, Long>
		companyId = createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, String>
		userName = createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Date>
		createDate = createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Long>
		CDataIntegrationProcessId = createColumn(
			"CDataIntegrationProcessId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Clob> error =
		createColumn("error", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Clob> output =
		createColumn("output_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Date>
		startDate = createColumn(
			"startDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Date> endDate =
		createColumn(
			"endDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDataIntegrationProcessLogTable, Integer>
		status = createColumn(
			"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private CommerceDataIntegrationProcessLogTable() {
		super(
			"CDataIntegrationProcessLog",
			CommerceDataIntegrationProcessLogTable::new);
	}

}