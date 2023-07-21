/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v1_0_0;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.service.AssetEntryUsageLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.Date;
import java.sql.PreparedStatement;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeLayoutClassedModelUsage extends UpgradeProcess {

	public UpgradeLayoutClassedModelUsage(
		AssetEntryLocalService assetEntryLocalService,
		AssetEntryUsageLocalService assetEntryUsageLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
		_assetEntryUsageLocalService = assetEntryUsageLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSchema();

		_upgradeLayoutClassedModelUsage();
	}

	private void _upgradeLayoutClassedModelUsage() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("insert into LayoutClassedModelUsage (mvccVersion, uuid_, ");
		sb.append("layoutClassedModelUsageId, groupId, createDate, ");
		sb.append("modifiedDate, classNameId, classPK, containerKey, ");
		sb.append("containerType, plid, type_ ) values (?, ?, ?, ?, ?, ?, ?, ");
		sb.append("?, ?, ?, ?, ?)");

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb.toString())) {

			List<AssetEntryUsage> assetEntryUsages =
				_assetEntryUsageLocalService.getAssetEntryUsages(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (AssetEntryUsage assetEntryUsage : assetEntryUsages) {
				AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
					assetEntryUsage.getAssetEntryId());

				if ((assetEntry == null) || (assetEntryUsage.getPlid() <= 0)) {
					continue;
				}

				ps.setLong(1, 0);
				ps.setString(2, PortalUUIDUtil.generate());
				ps.setLong(3, increment());
				ps.setLong(4, assetEntryUsage.getGroupId());
				ps.setDate(5, new Date(System.currentTimeMillis()));
				ps.setDate(6, new Date(System.currentTimeMillis()));
				ps.setLong(7, assetEntry.getClassNameId());
				ps.setLong(8, assetEntry.getClassPK());
				ps.setString(9, assetEntryUsage.getContainerKey());
				ps.setLong(10, assetEntryUsage.getContainerType());
				ps.setLong(11, assetEntryUsage.getPlid());
				ps.setLong(12, assetEntryUsage.getType());

				ps.addBatch();
			}

			ps.executeBatch();
		}
	}

	private void _upgradeSchema() throws Exception {
		String template = StringUtil.read(
			UpgradeLayoutClassedModelUsage.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false);
	}

	private final AssetEntryLocalService _assetEntryLocalService;
	private final AssetEntryUsageLocalService _assetEntryUsageLocalService;

}