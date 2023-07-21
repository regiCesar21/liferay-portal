/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.internal.upgrade.v4_0_1.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Matija Petanjek
 * @generated
 */
public class DispatchTriggerTable {

	public static final String TABLE_NAME = "DispatchTrigger";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"dispatchTriggerId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"active_", Types.BOOLEAN},
		{"cronExpression", Types.VARCHAR},
		{"dispatchTaskClusterMode", Types.INTEGER},
		{"dispatchTaskExecutorType", Types.VARCHAR},
		{"dispatchTaskSettings", Types.CLOB}, {"endDate", Types.TIMESTAMP},
		{"name", Types.VARCHAR}, {"overlapAllowed", Types.BOOLEAN},
		{"startDate", Types.TIMESTAMP}, {"system_", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("dispatchTriggerId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("cronExpression", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("dispatchTaskClusterMode", Types.INTEGER);

TABLE_COLUMNS_MAP.put("dispatchTaskExecutorType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("dispatchTaskSettings", Types.CLOB);

TABLE_COLUMNS_MAP.put("endDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("overlapAllowed", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("startDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("system_", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE =
"create table DispatchTrigger (mvccVersion LONG default 0 not null,dispatchTriggerId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,active_ BOOLEAN,cronExpression VARCHAR(75) null,dispatchTaskClusterMode INTEGER,dispatchTaskExecutorType VARCHAR(75) null,dispatchTaskSettings TEXT null,endDate DATE null,name VARCHAR(75) null,overlapAllowed BOOLEAN,startDate DATE null,system_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table DispatchTrigger";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_71D6AFE9 on DispatchTrigger (active_, dispatchTaskClusterMode)",
		"create index IX_1B108A04 on DispatchTrigger (companyId, dispatchTaskExecutorType[$COLUMN_LENGTH:75$])",
		"create unique index IX_D86DCE63 on DispatchTrigger (companyId, name[$COLUMN_LENGTH:75$])",
		"create index IX_F6ABBDDE on DispatchTrigger (companyId, userId)"
	};

}