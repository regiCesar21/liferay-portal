/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.DBTypeToSQLMap;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Samuel Ziemer
 */
public class UpgradeUser extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(7);

		sb.append("update Group_ set active_ = [$FALSE$] where groupId in ");
		sb.append("(select Group_.groupId from Group_ inner join User_ on ");
		sb.append("Group_.companyId = User_.companyId and Group_.classPK = ");
		sb.append("User_.userId where Group_.classNameId = (select ");
		sb.append("classNameId from ClassName_ where value = ");
		sb.append("'com.liferay.portal.kernel.model.User') and User_.status ");
		sb.append("= 5)");

		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(sb.toString());

		sb = new StringBundler(6);

		sb.append("update Group_ inner join User_ on Group_.companyId = ");
		sb.append("User_.companyId and Group_.classPK = User_.userId set ");
		sb.append("active_ = [$FALSE$] where Group_.classNameId = (select ");
		sb.append("classNameId from ClassName_ where value = '");
		sb.append("com.liferay.portal.kernel.model.User') and User_.status = ");
		sb.append("5");

		String sql = sb.toString();

		dbTypeToSQLMap.add(DBType.MARIADB, sql);
		dbTypeToSQLMap.add(DBType.MYSQL, sql);

		runSQL(dbTypeToSQLMap);
	}

}