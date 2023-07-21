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
 * The table class for the &quot;CommerceAccountOrganizationRel&quot; database table.
 *
 * @author Marco Leo
 * @see CommerceAccountOrganizationRel
 * @generated
 */
public class CommerceAccountOrganizationRelTable
	extends BaseTable<CommerceAccountOrganizationRelTable> {

	public static final CommerceAccountOrganizationRelTable INSTANCE =
		new CommerceAccountOrganizationRelTable();

	public final Column<CommerceAccountOrganizationRelTable, Long>
		commerceAccountId = createColumn(
			"commerceAccountId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommerceAccountOrganizationRelTable, Long>
		organizationId = createColumn(
			"organizationId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommerceAccountOrganizationRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountOrganizationRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountOrganizationRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountOrganizationRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceAccountOrganizationRelTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private CommerceAccountOrganizationRelTable() {
		super(
			"CommerceAccountOrganizationRel",
			CommerceAccountOrganizationRelTable::new);
	}

}