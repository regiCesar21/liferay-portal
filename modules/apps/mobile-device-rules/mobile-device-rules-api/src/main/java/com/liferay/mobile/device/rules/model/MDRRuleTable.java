/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mobile.device.rules.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;MDRRule&quot; database table.
 *
 * @author Edward C. Han
 * @see MDRRule
 * @generated
 */
public class MDRRuleTable extends BaseTable<MDRRuleTable> {

	public static final MDRRuleTable INSTANCE = new MDRRuleTable();

	public final Column<MDRRuleTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<MDRRuleTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MDRRuleTable, Long> ruleId = createColumn(
		"ruleId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MDRRuleTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MDRRuleTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MDRRuleTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MDRRuleTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MDRRuleTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MDRRuleTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MDRRuleTable, Long> ruleGroupId = createColumn(
		"ruleGroupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MDRRuleTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MDRRuleTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MDRRuleTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MDRRuleTable, Clob> typeSettings = createColumn(
		"typeSettings", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<MDRRuleTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private MDRRuleTable() {
		super("MDRRule", MDRRuleTable::new);
	}

}