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
 * The table class for the &quot;WeDeployAuth_WeDeployAuthToken&quot; database table.
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthToken
 * @generated
 */
public class WeDeployAuthTokenTable extends BaseTable<WeDeployAuthTokenTable> {

	public static final WeDeployAuthTokenTable INSTANCE =
		new WeDeployAuthTokenTable();

	public final Column<WeDeployAuthTokenTable, Long> weDeployAuthTokenId =
		createColumn(
			"weDeployAuthTokenId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<WeDeployAuthTokenTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WeDeployAuthTokenTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WeDeployAuthTokenTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WeDeployAuthTokenTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WeDeployAuthTokenTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WeDeployAuthTokenTable, String> clientId = createColumn(
		"clientId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WeDeployAuthTokenTable, String> token = createColumn(
		"token", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WeDeployAuthTokenTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private WeDeployAuthTokenTable() {
		super("WeDeployAuth_WeDeployAuthToken", WeDeployAuthTokenTable::new);
	}

}