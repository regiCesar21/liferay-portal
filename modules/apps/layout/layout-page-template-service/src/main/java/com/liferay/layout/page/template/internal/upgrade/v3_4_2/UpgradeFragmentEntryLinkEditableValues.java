/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v3_4_2;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Rubén Pulido
 */
public class UpgradeFragmentEntryLinkEditableValues extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select fragmentEntryLinkId,editableValues,rendererKey from " +
					"FragmentEntryLink where rendererKey = " +
						"'BASIC_COMPONENT-separator'");
			PreparedStatement ps2 = connection.prepareStatement(
				"update FragmentEntryLink set editableValues = ? where " +
					"fragmentEntryLinkId = ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				JSONObject editablesJSONObject =
					JSONFactoryUtil.createJSONObject(
						rs.getString("editableValues"));

				JSONObject configurationJSONObject =
					editablesJSONObject.getJSONObject(
						"com.liferay.fragment.entry.processor.freemarker." +
							"FreeMarkerFragmentEntryProcessor");

				if (configurationJSONObject == null) {
					continue;
				}

				if (configurationJSONObject.has("verticalSpace")) {
					configurationJSONObject.put(
						"bottomSpacing",
						configurationJSONObject.remove("verticalSpace"));
				}

				ps2.setString(1, editablesJSONObject.toString());
				ps2.setLong(2, rs.getLong("fragmentEntryLinkId"));

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

}