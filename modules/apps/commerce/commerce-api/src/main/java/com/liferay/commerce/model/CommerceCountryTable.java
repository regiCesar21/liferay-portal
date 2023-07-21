/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceCountry&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountry
 * @generated
 */
public class CommerceCountryTable extends BaseTable<CommerceCountryTable> {

	public static final CommerceCountryTable INSTANCE =
		new CommerceCountryTable();

	public final Column<CommerceCountryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceCountryTable, Long> commerceCountryId =
		createColumn(
			"commerceCountryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommerceCountryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceCountryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceCountryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceCountryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceCountryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceCountryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceCountryTable, Boolean> billingAllowed =
		createColumn(
			"billingAllowed", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<CommerceCountryTable, Boolean> shippingAllowed =
		createColumn(
			"shippingAllowed", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<CommerceCountryTable, String> twoLettersISOCode =
		createColumn(
			"twoLettersISOCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceCountryTable, String> threeLettersISOCode =
		createColumn(
			"threeLettersISOCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceCountryTable, Integer> numericISOCode =
		createColumn(
			"numericISOCode", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<CommerceCountryTable, Boolean> subjectToVAT =
		createColumn(
			"subjectToVAT", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommerceCountryTable, Double> priority = createColumn(
		"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommerceCountryTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommerceCountryTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<CommerceCountryTable, Boolean> channelFilterEnabled =
		createColumn(
			"channelFilterEnabled", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);

	private CommerceCountryTable() {
		super("CommerceCountry", CommerceCountryTable::new);
	}

}