/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;BlogsStatsUser&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsStatsUser
 * @generated
 */
public class BlogsStatsUserTable extends BaseTable<BlogsStatsUserTable> {

	public static final BlogsStatsUserTable INSTANCE =
		new BlogsStatsUserTable();

	public final Column<BlogsStatsUserTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<BlogsStatsUserTable, Long> statsUserId = createColumn(
		"statsUserId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<BlogsStatsUserTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BlogsStatsUserTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BlogsStatsUserTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<BlogsStatsUserTable, Integer> entryCount = createColumn(
		"entryCount", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<BlogsStatsUserTable, Date> lastPostDate = createColumn(
		"lastPostDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<BlogsStatsUserTable, Integer> ratingsTotalEntries =
		createColumn(
			"ratingsTotalEntries", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<BlogsStatsUserTable, Double> ratingsTotalScore =
		createColumn(
			"ratingsTotalScore", Double.class, Types.DOUBLE,
			Column.FLAG_DEFAULT);
	public final Column<BlogsStatsUserTable, Double> ratingsAverageScore =
		createColumn(
			"ratingsAverageScore", Double.class, Types.DOUBLE,
			Column.FLAG_DEFAULT);

	private BlogsStatsUserTable() {
		super("BlogsStatsUser", BlogsStatsUserTable::new);
	}

}