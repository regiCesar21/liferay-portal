/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AccountGroupAccountEntryRel&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupAccountEntryRel
 * @generated
 */
public class AccountGroupAccountEntryRelTable
	extends BaseTable<AccountGroupAccountEntryRelTable> {

	public static final AccountGroupAccountEntryRelTable INSTANCE =
		new AccountGroupAccountEntryRelTable();

	public final Column<AccountGroupAccountEntryRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AccountGroupAccountEntryRelTable, Long>
		AccountGroupAccountEntryRelId = createColumn(
			"AccountGroupAccountEntryRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AccountGroupAccountEntryRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountGroupAccountEntryRelTable, Long> accountGroupId =
		createColumn(
			"accountGroupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountGroupAccountEntryRelTable, Long> accountEntryId =
		createColumn(
			"accountEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AccountGroupAccountEntryRelTable() {
		super(
			"AccountGroupAccountEntryRel",
			AccountGroupAccountEntryRelTable::new);
	}

}