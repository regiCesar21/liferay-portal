/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.upgrade.v2_0_1;

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.view.count.ViewCountManagerUtil;
import com.liferay.redirect.model.RedirectNotFoundEntry;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alejandro Tardín
 */
public class UpgradeRedirectNotFoundEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				SQLTransformer.transform(
					"select redirectNotFoundEntryId, companyId, hits from " +
						"RedirectNotFoundEntry"));
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long redirectNotFoundEntryId = rs.getLong(
					"redirectNotFoundEntryId");
				long companyId = rs.getLong("companyId");
				int hits = rs.getInt("hits");

				ViewCountManagerUtil.incrementViewCount(
					companyId,
					PortalUtil.getClassNameId(RedirectNotFoundEntry.class),
					redirectNotFoundEntryId, hits);
			}
		}
	}

}