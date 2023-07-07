/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.upgrade.v7_3_x.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class GroupTable {

	public static final String TABLE_NAME = "Group_";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"creatorUserId", Types.BIGINT},
		{"modifiedDate", Types.TIMESTAMP}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"parentGroupId", Types.BIGINT},
		{"liveGroupId", Types.BIGINT}, {"treePath", Types.VARCHAR},
		{"groupKey", Types.VARCHAR}, {"name", Types.VARCHAR},
		{"description", Types.VARCHAR}, {"type_", Types.INTEGER},
		{"typeSettings", Types.CLOB}, {"manualMembership", Types.BOOLEAN},
		{"membershipRestriction", Types.INTEGER},
		{"friendlyURL", Types.VARCHAR}, {"site", Types.BOOLEAN},
		{"remoteStagingGroupCount", Types.INTEGER},
		{"inheritContent", Types.BOOLEAN}, {"active_", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("creatorUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentGroupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("liveGroupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("treePath", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("groupKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("typeSettings", Types.CLOB);

TABLE_COLUMNS_MAP.put("manualMembership", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("membershipRestriction", Types.INTEGER);

TABLE_COLUMNS_MAP.put("friendlyURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("site", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("remoteStagingGroupCount", Types.INTEGER);

TABLE_COLUMNS_MAP.put("inheritContent", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE =
"create table Group_ (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,groupId LONG not null,companyId LONG,creatorUserId LONG,modifiedDate DATE null,classNameId LONG,classPK LONG,parentGroupId LONG,liveGroupId LONG,treePath STRING null,groupKey VARCHAR(150) null,name STRING null,description STRING null,type_ INTEGER,typeSettings TEXT null,manualMembership BOOLEAN,membershipRestriction INTEGER,friendlyURL VARCHAR(255) null,site BOOLEAN,remoteStagingGroupCount INTEGER,inheritContent BOOLEAN,active_ BOOLEAN,primary key (groupId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table Group_";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_EB3A63D9 on Group_ (classNameId, classPK, ctCollectionId)",
		"create index IX_BD3CB13A on Group_ (classNameId, groupId, companyId, parentGroupId)",
		"create index IX_8B5402E5 on Group_ (companyId, active_, ctCollectionId)",
		"create unique index IX_504CABF5 on Group_ (companyId, classNameId, classPK, ctCollectionId)",
		"create index IX_2442742A on Group_ (companyId, classNameId, ctCollectionId)",
		"create index IX_B7EBDBB2 on Group_ (companyId, classNameId, parentGroupId, ctCollectionId)",
		"create index IX_A67A0AA5 on Group_ (companyId, classNameId, site, ctCollectionId)",
		"create index IX_286EE120 on Group_ (companyId, ctCollectionId)",
		"create unique index IX_9A7D6AD0 on Group_ (companyId, friendlyURL[$COLUMN_LENGTH:255$], ctCollectionId)",
		"create unique index IX_BE219CF4 on Group_ (companyId, groupKey[$COLUMN_LENGTH:150$], ctCollectionId)",
		"create index IX_A20523FC on Group_ (companyId, parentGroupId, ctCollectionId)",
		"create index IX_121A14F7 on Group_ (companyId, parentGroupId, site, ctCollectionId)",
		"create index IX_162053E9 on Group_ (companyId, parentGroupId, site, inheritContent, ctCollectionId)",
		"create index IX_4108074A on Group_ (companyId, site, active_, ctCollectionId)",
		"create index IX_CFE2671B on Group_ (companyId, site, ctCollectionId)",
		"create index IX_8060F096 on Group_ (liveGroupId, ctCollectionId)",
		"create index IX_5263ACD8 on Group_ (type_, active_, ctCollectionId)",
		"create index IX_21CBD878 on Group_ (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_BFEBCBAC on Group_ (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)"
	};

}