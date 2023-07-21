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
public class RatingsStatsTable {

	public static final String TABLE_NAME = "RatingsStats";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"statsId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"totalEntries", Types.INTEGER}, {"totalScore", Types.DOUBLE},
		{"averageScore", Types.DOUBLE}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statsId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("totalEntries", Types.INTEGER);

TABLE_COLUMNS_MAP.put("totalScore", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("averageScore", Types.DOUBLE);

}
	public static final String TABLE_SQL_CREATE =
"create table RatingsStats (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,statsId LONG not null,companyId LONG,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,totalEntries INTEGER,totalScore DOUBLE,averageScore DOUBLE,primary key (statsId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table RatingsStats";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_C286E0E2 on RatingsStats (classNameId, classPK, ctCollectionId)",
		"create index IX_5EC6007D on RatingsStats (classNameId, createDate)",
		"create index IX_11A5584A on RatingsStats (classNameId, modifiedDate)"
	};

}