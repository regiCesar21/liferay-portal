/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_5_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DDMFormInstanceReportTable {

	public static final String TABLE_NAME = "DDMFormInstanceReport";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"formInstanceReportId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"formInstanceId", Types.BIGINT}, {"data_", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("formInstanceReportId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("formInstanceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("data_", Types.CLOB);

}
	public static final String TABLE_SQL_CREATE =
"create table DDMFormInstanceReport (mvccVersion LONG default 0 not null,formInstanceReportId LONG not null primary key,groupId LONG,companyId LONG,createDate DATE null,modifiedDate DATE null,formInstanceId LONG,data_ TEXT null)";

	public static final String TABLE_SQL_DROP =
"drop table DDMFormInstanceReport";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
	};

}