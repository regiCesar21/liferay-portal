/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.wedeploy.auth.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;WeDeployAuth_WeDeployAuthApp&quot; database table.
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthApp
 * @generated
 */
public class WeDeployAuthAppTable extends BaseTable<WeDeployAuthAppTable> {

	public static final WeDeployAuthAppTable INSTANCE =
		new WeDeployAuthAppTable();

	public final Column<WeDeployAuthAppTable, Long> weDeployAuthAppId =
		createColumn(
			"weDeployAuthAppId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<WeDeployAuthAppTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WeDeployAuthAppTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WeDeployAuthAppTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WeDeployAuthAppTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WeDeployAuthAppTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WeDeployAuthAppTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WeDeployAuthAppTable, String> redirectURI =
		createColumn(
			"redirectURI", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WeDeployAuthAppTable, String> clientId = createColumn(
		"clientId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WeDeployAuthAppTable, String> clientSecret =
		createColumn(
			"clientSecret", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private WeDeployAuthAppTable() {
		super("WeDeployAuth_WeDeployAuthApp", WeDeployAuthAppTable::new);
	}

}