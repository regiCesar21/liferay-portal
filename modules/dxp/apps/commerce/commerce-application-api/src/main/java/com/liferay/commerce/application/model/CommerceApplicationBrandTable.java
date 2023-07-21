/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.application.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceApplicationBrand&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationBrand
 * @generated
 */
public class CommerceApplicationBrandTable
	extends BaseTable<CommerceApplicationBrandTable> {

	public static final CommerceApplicationBrandTable INSTANCE =
		new CommerceApplicationBrandTable();

	public final Column<CommerceApplicationBrandTable, Long>
		commerceApplicationBrandId = createColumn(
			"commerceApplicationBrandId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceApplicationBrandTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationBrandTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationBrandTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationBrandTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationBrandTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationBrandTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationBrandTable, Long> logoId =
		createColumn("logoId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private CommerceApplicationBrandTable() {
		super("CommerceApplicationBrand", CommerceApplicationBrandTable::new);
	}

}