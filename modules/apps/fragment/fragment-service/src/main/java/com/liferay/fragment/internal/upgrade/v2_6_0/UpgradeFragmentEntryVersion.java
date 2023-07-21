/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v2_6_0;

import com.liferay.fragment.internal.upgrade.v2_6_0.util.FragmentEntryVersionTable;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Statement;

/**
 * @author Rubén Pulido
 */
public class UpgradeFragmentEntryVersion extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(FragmentEntryVersionTable.TABLE_SQL_CREATE);

		insertIntoFragmentEntryVersion();

		upgradeFragmentEntryVersionCounter();
	}

	protected void insertIntoFragmentEntryVersion() throws Exception {
		try (Statement s = connection.createStatement()) {
			StringBundler sb = new StringBundler(17);

			sb.append("insert into FragmentEntryVersion(");
			sb.append("fragmentEntryVersionId, version, uuid_, ");
			sb.append("fragmentEntryId, groupId, companyId, userId, ");
			sb.append("userName, createDate, modifiedDate, ");
			sb.append("fragmentCollectionId, fragmentEntryKey, name, css, ");
			sb.append("html, js, cacheable, configuration, ");
			sb.append("previewFileEntryId, readOnly, type_, lastPublishDate, ");
			sb.append("status, statusByUserId, statusByUserName, statusDate) ");
			sb.append("select fragmentEntryId as fragmentEntryVersionId, 1 ");
			sb.append("as version, uuid_, fragmentEntryId, groupId, ");
			sb.append("companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, fragmentCollectionId, fragmentEntryKey, ");
			sb.append("name, css, html, js, cacheable, configuration, ");
			sb.append("previewFileEntryId, readOnly, type_, lastPublishDate, ");
			sb.append("status, statusByUserId, statusByUserName, statusDate ");
			sb.append("from FragmentEntry where status = ");
			sb.append(WorkflowConstants.STATUS_APPROVED);

			s.execute(sb.toString());
		}
	}

	protected void upgradeFragmentEntryVersionCounter() throws Exception {
		runSQL(
			StringBundler.concat(
				"insert into Counter (name, currentId) select '",
				FragmentEntryVersion.class.getName(),
				"', max(fragmentEntryVersionId) from FragmentEntryVersion"));
	}

}