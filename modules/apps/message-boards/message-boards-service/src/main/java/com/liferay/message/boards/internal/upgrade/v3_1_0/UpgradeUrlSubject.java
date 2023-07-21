/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.upgrade.v3_1_0;

import com.liferay.message.boards.internal.upgrade.v3_1_0.util.MBMessageTable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Javier Gamarra
 */
public class UpgradeUrlSubject extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("MBMessage", "urlSubject")) {
			alter(
				MBMessageTable.class,
				new AlterTableAddColumn("urlSubject", "VARCHAR(255) null"));
		}

		runSQL(
			"create index IX_TEMP on MBMessage (subject[$COLUMN_LENGTH:75$]," +
				"messageId)");

		_populateUrlSubject();

		runSQL("drop index IX_TEMP on MBMessage");
	}

	private String _getURLSubject(long id, String subject) {
		if (subject == null) {
			return String.valueOf(id);
		}

		subject = StringUtil.toLowerCase(subject.trim());

		if (Validator.isNull(subject) || Validator.isNumber(subject) ||
			subject.equals("rss")) {

			subject = String.valueOf(id);
		}
		else {
			subject = FriendlyURLNormalizerUtil.normalizeWithPeriodsAndSlashes(
				subject);
		}

		return subject.substring(0, Math.min(subject.length(), 254));
	}

	private void _populateUrlSubject() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select messageId, subject from MBMessage order by subject, " +
					"messageId asc");
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update MBMessage set urlSubject = ? where messageId = " +
						"?"))) {

			int count = 0;
			String curURLSubject = null;
			String previousURLSubject = null;

			while (rs.next()) {
				long messageId = rs.getLong(1);
				String subject = rs.getString(2);

				curURLSubject = _getURLSubject(messageId, subject);

				String suffix = null;

				if (StringUtil.equals(previousURLSubject, curURLSubject)) {
					count++;
					suffix = StringPool.DASH + count;
				}
				else {
					count = 0;
					previousURLSubject = curURLSubject;
					suffix = StringPool.BLANK;
				}

				ps2.setString(1, curURLSubject + suffix);
				ps2.setLong(2, messageId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

}