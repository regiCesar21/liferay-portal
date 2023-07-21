/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.internal.upgrade.v2_2_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceDiscountRuleTable {

	public static final String TABLE_NAME = "CommerceDiscountRule";

	public static final Object[][] TABLE_COLUMNS = {
		{"commerceDiscountRuleId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"name", Types.VARCHAR}, {"commerceDiscountId", Types.BIGINT},
		{"type_", Types.VARCHAR}, {"typeSettings", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("commerceDiscountRuleId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("commerceDiscountId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("typeSettings", Types.CLOB);

}
	public static final String TABLE_SQL_CREATE =
"create table CommerceDiscountRule (commerceDiscountRuleId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,commerceDiscountId LONG,type_ VARCHAR(75) null,typeSettings TEXT null)";

	public static final String TABLE_SQL_DROP =
"drop table CommerceDiscountRule";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_CB9E6769 on CommerceDiscountRule (commerceDiscountId)"
	};

}