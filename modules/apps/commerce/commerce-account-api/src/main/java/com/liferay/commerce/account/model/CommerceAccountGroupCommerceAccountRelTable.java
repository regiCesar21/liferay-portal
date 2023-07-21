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
 * The table class for the &quot;CAccountGroupCAccountRel&quot; database table.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupCommerceAccountRel
 * @generated
 */
public class CommerceAccountGroupCommerceAccountRelTable
	extends BaseTable<CommerceAccountGroupCommerceAccountRelTable> {

	public static final CommerceAccountGroupCommerceAccountRelTable INSTANCE =
		new CommerceAccountGroupCommerceAccountRelTable();

	public final Column<CommerceAccountGroupCommerceAccountRelTable, String>
		externalReferenceCode = createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupCommerceAccountRelTable, Long>
		commerceAccountGroupCommerceAccountRelId = createColumn(
			"CAccountGroupCAccountRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceAccountGroupCommerceAccountRelTable, Long>
		companyId = createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupCommerceAccountRelTable, Long>
		userId = createColumn(
			"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupCommerceAccountRelTable, String>
		userName = createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupCommerceAccountRelTable, Date>
		createDate = createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupCommerceAccountRelTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupCommerceAccountRelTable, Long>
		commerceAccountGroupId = createColumn(
			"commerceAccountGroupId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupCommerceAccountRelTable, Long>
		commerceAccountId = createColumn(
			"commerceAccountId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private CommerceAccountGroupCommerceAccountRelTable() {
		super(
			"CAccountGroupCAccountRel",
			CommerceAccountGroupCommerceAccountRelTable::new);
	}

}