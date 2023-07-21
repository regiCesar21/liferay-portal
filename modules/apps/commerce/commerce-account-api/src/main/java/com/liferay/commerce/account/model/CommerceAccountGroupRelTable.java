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
 * The table class for the &quot;CommerceAccountGroupRel&quot; database table.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupRel
 * @generated
 */
public class CommerceAccountGroupRelTable
	extends BaseTable<CommerceAccountGroupRelTable> {

	public static final CommerceAccountGroupRelTable INSTANCE =
		new CommerceAccountGroupRelTable();

	public final Column<CommerceAccountGroupRelTable, Long>
		commerceAccountGroupRelId = createColumn(
			"commerceAccountGroupRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceAccountGroupRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupRelTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupRelTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupRelTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountGroupRelTable, Long>
		commerceAccountGroupId = createColumn(
			"commerceAccountGroupId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private CommerceAccountGroupRelTable() {
		super("CommerceAccountGroupRel", CommerceAccountGroupRelTable::new);
	}

}