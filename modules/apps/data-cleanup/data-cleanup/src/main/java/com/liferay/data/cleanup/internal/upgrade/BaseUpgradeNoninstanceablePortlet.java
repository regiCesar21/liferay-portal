/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alberto Chaparro
 */
public abstract class BaseUpgradeNoninstanceablePortlet extends UpgradeProcess {

	protected void removePortlet(
			String bundleSymbolicName, String[] oldPortletIds,
			String[] portletIds)
		throws Exception {

		if (ArrayUtil.getLength(oldPortletIds) > 0) {
			try (PreparedStatement ps = connection.prepareStatement(
					"select portletId from Portlet where portletId = ?")) {

				ps.setString(1, oldPortletIds[0]);

				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						portletIds = oldPortletIds;
					}
				}
			}
		}

		for (String portletId : portletIds) {
			LayoutTypeSettingsUtil.removePortletId(connection, portletId);

			runSQL("delete from Portlet where portletId = '" + portletId + "'");

			runSQL(
				"delete from PortletPreferences where portletId = '" +
					portletId + "'");

			runSQL(
				"delete from ResourcePermission where name = '" + portletId +
					"'");
		}

		runSQL(
			"delete from Release_ where servletContextName = '" +
				bundleSymbolicName + "'");
	}

}