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
 * The table class for the &quot;CommerceAccountUserRel&quot; database table.
 *
 * @author Marco Leo
 * @see CommerceAccountUserRel
 * @generated
 */
public class CommerceAccountUserRelTable
	extends BaseTable<CommerceAccountUserRelTable> {

	public static final CommerceAccountUserRelTable INSTANCE =
		new CommerceAccountUserRelTable();

	public final Column<CommerceAccountUserRelTable, Long> commerceAccountId =
		createColumn(
			"commerceAccountId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommerceAccountUserRelTable, Long>
		commerceAccountUserId = createColumn(
			"commerceAccountUserId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceAccountUserRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountUserRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountUserRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountUserRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountUserRelTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private CommerceAccountUserRelTable() {
		super("CommerceAccountUserRel", CommerceAccountUserRelTable::new);
	}

}