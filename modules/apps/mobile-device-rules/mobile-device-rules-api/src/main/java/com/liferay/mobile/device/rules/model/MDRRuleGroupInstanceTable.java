/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mobile.device.rules.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;MDRRuleGroupInstance&quot; database table.
 *
 * @author Edward C. Han
 * @see MDRRuleGroupInstance
 * @generated
 */
public class MDRRuleGroupInstanceTable
	extends BaseTable<MDRRuleGroupInstanceTable> {

	public static final MDRRuleGroupInstanceTable INSTANCE =
		new MDRRuleGroupInstanceTable();

	public final Column<MDRRuleGroupInstanceTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<MDRRuleGroupInstanceTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MDRRuleGroupInstanceTable, Long> ruleGroupInstanceId =
		createColumn(
			"ruleGroupInstanceId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<MDRRuleGroupInstanceTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MDRRuleGroupInstanceTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MDRRuleGroupInstanceTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MDRRuleGroupInstanceTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MDRRuleGroupInstanceTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MDRRuleGroupInstanceTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MDRRuleGroupInstanceTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MDRRuleGroupInstanceTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MDRRuleGroupInstanceTable, Long> ruleGroupId =
		createColumn(
			"ruleGroupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MDRRuleGroupInstanceTable, Integer> priority =
		createColumn(
			"priority", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<MDRRuleGroupInstanceTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private MDRRuleGroupInstanceTable() {
		super("MDRRuleGroupInstance", MDRRuleGroupInstanceTable::new);
	}

}