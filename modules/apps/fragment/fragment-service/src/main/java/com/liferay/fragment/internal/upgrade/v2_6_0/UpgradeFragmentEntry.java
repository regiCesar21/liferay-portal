/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v2_6_0;

import com.liferay.fragment.internal.upgrade.v2_6_0.util.FragmentEntryTable;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Statement;

/**
 * @author Rubén Pulido
 */
public class UpgradeFragmentEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();

		upgradeFragmentEntryCounter();
		upgradeFragmentEntryHeadIdAndHeadStatusApproved();
		upgradeFragmentEntryHeadIdAndHeadStatusDraft();
	}

	protected void upgradeFragmentEntryCounter() throws Exception {
		runSQL(
			StringBundler.concat(
				"insert into Counter (name, currentId) select '",
				FragmentEntry.class.getName(),
				"', max(fragmentEntryId) from FragmentEntry"));
	}

	protected void upgradeFragmentEntryHeadIdAndHeadStatusApproved()
		throws Exception {

		try (Statement s = connection.createStatement()) {
			StringBundler sb = new StringBundler(3);

			sb.append("update FragmentEntry set headId = -1 * fragmentEntryId");
			sb.append(", head = [$TRUE$] where status = ");
			sb.append(WorkflowConstants.STATUS_APPROVED);

			s.execute(SQLTransformer.transform(sb.toString()));
		}
	}

	protected void upgradeFragmentEntryHeadIdAndHeadStatusDraft()
		throws Exception {

		try (Statement s = connection.createStatement()) {
			StringBundler sb = new StringBundler(3);

			sb.append("update FragmentEntry set headId = fragmentEntryId, ");
			sb.append("head = [$FALSE$] where status != ");
			sb.append(WorkflowConstants.STATUS_APPROVED);

			s.execute(SQLTransformer.transform(sb.toString()));
		}
	}

	protected void upgradeSchema() throws Exception {
		alter(
			FragmentEntryTable.class, new AlterTableAddColumn("headId", "LONG"),
			new AlterTableAddColumn("head", "BOOLEAN"));
	}

}