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
 * The table class for the &quot;CommerceApplicationModel&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationModel
 * @generated
 */
public class CommerceApplicationModelTable
	extends BaseTable<CommerceApplicationModelTable> {

	public static final CommerceApplicationModelTable INSTANCE =
		new CommerceApplicationModelTable();

	public final Column<CommerceApplicationModelTable, Long>
		commerceApplicationModelId = createColumn(
			"commerceApplicationModelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceApplicationModelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationModelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationModelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationModelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationModelTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationModelTable, Long>
		commerceApplicationBrandId = createColumn(
			"commerceApplicationBrandId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationModelTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationModelTable, String> year =
		createColumn("year", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CommerceApplicationModelTable() {
		super("CommerceApplicationModel", CommerceApplicationModelTable::new);
	}

}