/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Company&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Company
 * @generated
 */
public class CompanyTable extends BaseTable<CompanyTable> {

	public static final CompanyTable INSTANCE = new CompanyTable();

	public final Column<CompanyTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CompanyTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CompanyTable, Long> accountId = createColumn(
		"accountId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CompanyTable, String> webId = createColumn(
		"webId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CompanyTable, String> mx = createColumn(
		"mx", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CompanyTable, String> homeURL = createColumn(
		"homeURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CompanyTable, Long> logoId = createColumn(
		"logoId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CompanyTable, Boolean> system = createColumn(
		"system_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CompanyTable, Integer> maxUsers = createColumn(
		"maxUsers", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CompanyTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private CompanyTable() {
		super("Company", CompanyTable::new);
	}

}