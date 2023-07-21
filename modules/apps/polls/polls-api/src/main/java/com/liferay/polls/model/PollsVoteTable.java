/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.polls.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;PollsVote&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see PollsVote
 * @generated
 */
public class PollsVoteTable extends BaseTable<PollsVoteTable> {

	public static final PollsVoteTable INSTANCE = new PollsVoteTable();

	public final Column<PollsVoteTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<PollsVoteTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Long> voteId = createColumn(
		"voteId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<PollsVoteTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Long> questionId = createColumn(
		"questionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Long> choiceId = createColumn(
		"choiceId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<PollsVoteTable, Date> voteDate = createColumn(
		"voteDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private PollsVoteTable() {
		super("PollsVote", PollsVoteTable::new);
	}

}