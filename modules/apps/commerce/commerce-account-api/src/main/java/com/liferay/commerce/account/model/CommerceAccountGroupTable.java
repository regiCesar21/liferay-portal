/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.account.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceAccountGroup&quot; database table.
 *
 * @author Marco Leo
 * @see CommerceAccountGroup
 * @generated
 */
public class CommerceAccountGroupTable
	extends BaseTable<CommerceAccountGroupTable> {

	public static final CommerceAccountGroupTable INSTANCE =
		new CommerceAccountGroupTable();

	public final Column<CommerceAccountGroupTable, String>
		externalReferenceCode = createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupTable, Long>
		commerceAccountGroupId = createColumn(
			"commerceAccountGroupId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceAccountGroupTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupTable, Boolean> system =
		createColumn(
			"system_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private CommerceAccountGroupTable() {
		super("CommerceAccountGroup", CommerceAccountGroupTable::new);
	}

}