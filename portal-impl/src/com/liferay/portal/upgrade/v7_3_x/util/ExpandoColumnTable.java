/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_3_x.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class ExpandoColumnTable {

	public static final String TABLE_NAME = "ExpandoColumn";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"columnId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"modifiedDate", Types.TIMESTAMP}, {"tableId", Types.BIGINT},
		{"name", Types.VARCHAR}, {"type_", Types.INTEGER},
		{"defaultData", Types.CLOB}, {"typeSettings", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("columnId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("tableId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("defaultData", Types.CLOB);

TABLE_COLUMNS_MAP.put("typeSettings", Types.CLOB);

}
	public static final String TABLE_SQL_CREATE =
"create table ExpandoColumn (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,columnId LONG not null,companyId LONG,modifiedDate DATE null,tableId LONG,name VARCHAR(75) null,type_ INTEGER,defaultData TEXT null,typeSettings TEXT null,primary key (columnId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table ExpandoColumn";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_8B26D246 on ExpandoColumn (tableId, ctCollectionId)",
		"create unique index IX_4A7D3605 on ExpandoColumn (tableId, name[$COLUMN_LENGTH:75$], ctCollectionId)"
	};

}