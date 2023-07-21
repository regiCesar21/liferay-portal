/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;OAuth_OAuthUser&quot; database table.
 *
 * @author Ivica Cardic
 * @see OAuthUser
 * @generated
 */
public class OAuthUserTable extends BaseTable<OAuthUserTable> {

	public static final OAuthUserTable INSTANCE = new OAuthUserTable();

	public final Column<OAuthUserTable, Long> oAuthUserId = createColumn(
		"oAuthUserId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<OAuthUserTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuthUserTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuthUserTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthUserTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<OAuthUserTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<OAuthUserTable, Long> oAuthApplicationId = createColumn(
		"oAuthApplicationId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<OAuthUserTable, String> accessToken = createColumn(
		"accessToken", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<OAuthUserTable, String> accessSecret = createColumn(
		"accessSecret", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private OAuthUserTable() {
		super("OAuth_OAuthUser", OAuthUserTable::new);
	}

}