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
 * The table class for the &quot;CAModelCProductRel&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationModelCProductRel
 * @generated
 */
public class CommerceApplicationModelCProductRelTable
	extends BaseTable<CommerceApplicationModelCProductRelTable> {

	public static final CommerceApplicationModelCProductRelTable INSTANCE =
		new CommerceApplicationModelCProductRelTable();

	public final Column<CommerceApplicationModelCProductRelTable, Long>
		commerceApplicationModelCProductRelId = createColumn(
			"CAModelCProductRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceApplicationModelCProductRelTable, Long>
		companyId = createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationModelCProductRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationModelCProductRelTable, String>
		userName = createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationModelCProductRelTable, Date>
		createDate = createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationModelCProductRelTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationModelCProductRelTable, Long>
		commerceApplicationModelId = createColumn(
			"commerceApplicationModelId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceApplicationModelCProductRelTable, Long>
		CProductId = createColumn(
			"CProductId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private CommerceApplicationModelCProductRelTable() {
		super(
			"CAModelCProductRel",
			CommerceApplicationModelCProductRelTable::new);
	}

}