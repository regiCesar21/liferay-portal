/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v3_2_0;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Danny Situ
 */
public class FriendlyURLEntryUpgradeProcess extends UpgradeProcess {

	public FriendlyURLEntryUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		GroupLocalService groupLocalService) {

		_classNameLocalService = classNameLocalService;
		_groupLocalService = groupLocalService;
	}

	@Override
	public void doUpgrade() throws Exception {
		if (!hasTable("FriendlyURLEntry")) {
			return;
		}

		long assetCategoryClassNameId = _classNameLocalService.getClassNameId(
			AssetCategory.class);
		long cProductClassNameId = _classNameLocalService.getClassNameId(
			CProduct.class);

		try (PreparedStatement ps1 = connection.prepareStatement(
				StringBundler.concat(
					"select * from FriendlyURLEntry where classNameId in (",
					String.valueOf(assetCategoryClassNameId), ",",
					String.valueOf(cProductClassNameId), ")"));
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update FriendlyURLEntry set groupId = ? where " +
						"friendlyURLEntryId = ?");
			PreparedStatement ps3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update FriendlyURLEntryLocalization set groupId = ? " +
						"where friendlyURLEntryId = ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long groupId = rs.getLong("groupId");

				List<Long> groupIds = _groupLocalService.getGroupIds(
					companyId, true);

				if (groupIds.contains(groupId)) {
					continue;
				}

				groupIds = _groupLocalService.getGroupIds(companyId, false);

				if (groupIds.contains(groupId)) {
					continue;
				}

				Group group = _groupLocalService.getCompanyGroup(companyId);

				if (groupId == group.getGroupId()) {
					continue;
				}

				long friendlyURLEntryId = rs.getLong("friendlyURLEntryId");

				ps2.setLong(1, group.getGroupId());
				ps2.setLong(2, friendlyURLEntryId);

				ps2.execute();

				ps3.setLong(1, group.getGroupId());
				ps3.setLong(2, friendlyURLEntryId);

				ps3.execute();
			}
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final GroupLocalService _groupLocalService;

}