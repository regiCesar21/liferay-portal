/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Account_&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Account
 * @generated
 */
public class AccountTable extends BaseTable<AccountTable> {

	public static final AccountTable INSTANCE = new AccountTable();

	public final Column<AccountTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AccountTable, Long> accountId = createColumn(
		"accountId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AccountTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AccountTable, Long> parentAccountId = createColumn(
		"parentAccountId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> legalName = createColumn(
		"legalName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> legalId = createColumn(
		"legalId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> legalType = createColumn(
		"legalType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> sicCode = createColumn(
		"sicCode", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> tickerSymbol = createColumn(
		"tickerSymbol", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> industry = createColumn(
		"industry", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountTable, String> size = createColumn(
		"size_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private AccountTable() {
		super("Account_", AccountTable::new);
	}

}