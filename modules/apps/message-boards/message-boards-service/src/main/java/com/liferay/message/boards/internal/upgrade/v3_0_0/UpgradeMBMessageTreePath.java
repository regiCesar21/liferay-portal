/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.upgrade.v3_0_0;

import com.liferay.message.boards.internal.upgrade.v3_0_0.util.MBMessageTable;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class UpgradeMBMessageTreePath extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("MBMessage", "treePath")) {
			alter(
				MBMessageTable.class,
				new AlterTableAddColumn("treePath", "STRING null"));
		}

		_populateTreePath();
	}

	private String _calculatePath(Map<Long, Long> relations, long messageId) {
		List<String> paths = new ArrayList<>();

		paths.add(messageId + "/");

		while (relations.containsKey(messageId)) {
			messageId = relations.get(messageId);

			paths.add(messageId + "/");
		}

		paths.add("/");

		Collections.reverse(paths);

		return StringUtil.merge(paths, "");
	}

	private void _populateTreePath() throws Exception {
		runSQL(
			"update MBMessage set treePath = CONCAT('/', " +
				"CAST_TEXT(messageId), '/') where parentMessageId = 0");

		runSQL(
			"update MBMessage set treePath = CONCAT('/', " +
				"CAST_TEXT(rootMessageId), '/', CAST_TEXT(messageId), '/') " +
					"where parentMessageId = rootMessageId");

		Map<Long, Long> relations = new HashMap<>();

		try (PreparedStatement ps = connection.prepareStatement(
				"select messageId, parentMessageId from MBMessage where " +
					"parentMessageId != 0 order by createDate desc");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				relations.put(rs.getLong(1), rs.getLong(2));
			}
		}

		try (PreparedStatement ps1 = connection.prepareStatement(
				"select messageId from MBMessage where treePath is null or " +
					"treePath = ''");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update MBMessage set treePath = ? where messageId = ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long messageId = rs.getLong(1);

				ps2.setString(1, _calculatePath(relations, messageId));
				ps2.setLong(2, messageId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

}