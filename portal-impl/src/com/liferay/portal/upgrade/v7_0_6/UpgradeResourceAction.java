/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_6;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Michael Bowerman
 */
public class UpgradeResourceAction extends UpgradeProcess {

	protected void deleteDuplicateBitwiseValuesOnResource() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select name, bitwiseValue, min(resourceActionId) as " +
					"minResourceActionId from ResourceAction group by name, " +
						"bitwiseValue having count(resourceActionId) > 1");
			PreparedStatement ps2 = connection.prepareStatement(
				"select resourceActionId, actionId from ResourceAction where " +
					"name = ? and bitwiseValue = ? and resourceActionId != ?");
			ResultSet rs1 = ps1.executeQuery()) {

			while (rs1.next()) {
				String name = rs1.getString("name");
				long bitwiseValue = rs1.getLong("bitwiseValue");
				long minResourceActionId = rs1.getLong("minResourceActionId");

				ps2.setString(1, name);
				ps2.setLong(2, bitwiseValue);
				ps2.setLong(3, minResourceActionId);

				try (ResultSet rs2 = ps2.executeQuery()) {
					while (rs2.next()) {
						if (_log.isInfoEnabled()) {
							StringBundler sb = new StringBundler(7);

							sb.append("Deleting resource action ");
							sb.append(rs2.getString("actionId"));
							sb.append(" from resource ");
							sb.append(name);
							sb.append(" because its bitwise value is the ");
							sb.append("same as another resource action on ");
							sb.append("the same resource");

							_log.info(sb.toString());
						}

						try (PreparedStatement ps3 =
								connection.prepareStatement(
									"delete from ResourceAction where " +
										"resourceActionId = " +
											rs2.getLong("resourceActionId"))) {

							ps3.execute();
						}
					}
				}
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteDuplicateBitwiseValuesOnResource();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeResourceAction.class);

}