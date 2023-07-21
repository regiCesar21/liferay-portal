/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v1_1_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class AccountEntryTable {

	public static final String TABLE_NAME = "AccountEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"externalReferenceCode", Types.VARCHAR},
		{"accountEntryId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"parentAccountEntryId", Types.BIGINT}, {"name", Types.VARCHAR},
		{"description", Types.VARCHAR}, {"domains", Types.VARCHAR},
		{"logoId", Types.BIGINT}, {"taxIdNumber", Types.VARCHAR},
		{"type_", Types.VARCHAR}, {"status", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("accountEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("parentAccountEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("domains", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("logoId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("taxIdNumber", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE =
"create table AccountEntry (mvccVersion LONG default 0 not null,externalReferenceCode VARCHAR(75) null,accountEntryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentAccountEntryId LONG,name VARCHAR(100) null,description STRING null,domains STRING null,logoId LONG,taxIdNumber VARCHAR(75) null,type_ VARCHAR(75) null,status INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table AccountEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_48CB043 on AccountEntry (companyId, status)"
	};

}