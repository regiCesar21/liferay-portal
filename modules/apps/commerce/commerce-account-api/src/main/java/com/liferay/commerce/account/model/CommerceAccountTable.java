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
 * The table class for the &quot;CommerceAccount&quot; database table.
 *
 * @author Marco Leo
 * @see CommerceAccount
 * @generated
 */
public class CommerceAccountTable extends BaseTable<CommerceAccountTable> {

	public static final CommerceAccountTable INSTANCE =
		new CommerceAccountTable();

	public final Column<CommerceAccountTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, Long> commerceAccountId =
		createColumn(
			"commerceAccountId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommerceAccountTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, Long> parentCommerceAccountId =
		createColumn(
			"parentCommerceAccountId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, Long> logoId = createColumn(
		"logoId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, String> email = createColumn(
		"email", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, String> taxId = createColumn(
		"taxId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, Date> displayDate = createColumn(
		"displayDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, Long> defaultBillingAddressId =
		createColumn(
			"defaultBillingAddressId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, Long> defaultShippingAddressId =
		createColumn(
			"defaultShippingAddressId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, Date> expirationDate =
		createColumn(
			"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, Long> statusByUserId =
		createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceAccountTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private CommerceAccountTable() {
		super("CommerceAccount", CommerceAccountTable::new);
	}

}