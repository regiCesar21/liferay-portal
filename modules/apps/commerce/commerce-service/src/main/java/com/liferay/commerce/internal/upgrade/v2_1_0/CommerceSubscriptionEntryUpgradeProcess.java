/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v2_1_0;

import com.liferay.commerce.internal.upgrade.base.BaseCommerceServiceUpgradeProcess;
import com.liferay.commerce.model.impl.CommerceSubscriptionEntryModelImpl;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.List;

/**
 * @author Ethan Bustad
 */
public class CommerceSubscriptionEntryUpgradeProcess
	extends BaseCommerceServiceUpgradeProcess {

	public CommerceSubscriptionEntryUpgradeProcess(
		CPDefinitionLocalService cpDefinitionLocalService,
		CPInstanceLocalService cpInstanceLocalService) {

		_cpDefinitionLocalService = cpDefinitionLocalService;
		_cpInstanceLocalService = cpInstanceLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		addColumn(
			CommerceSubscriptionEntryModelImpl.class,
			CommerceSubscriptionEntryModelImpl.TABLE_NAME, "CPInstanceUUID",
			"VARCHAR(75)");
		addColumn(
			CommerceSubscriptionEntryModelImpl.class,
			CommerceSubscriptionEntryModelImpl.TABLE_NAME, "CProductId",
			"LONG");

		_addIndexes(CommerceSubscriptionEntryModelImpl.TABLE_NAME);

		try (PreparedStatement ps = connection.prepareStatement(
				"update CommerceSubscriptionEntry set CProductId = ?," +
					"CPInstanceUUID = ? where CPInstanceId = ?");
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(
				"select distinct CPInstanceId from " +
					"CommerceSubscriptionEntry")) {

			while (rs.next()) {
				long cpInstanceId = rs.getLong("CPInstanceId");

				CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
					cpInstanceId);

				CPDefinition cpDefinition =
					_cpDefinitionLocalService.getCPDefinition(
						cpInstance.getCPDefinitionId());

				ps.setLong(1, cpDefinition.getCProductId());

				ps.setString(2, cpInstance.getCPInstanceUuid());

				ps.setLong(3, cpInstanceId);

				ps.execute();
			}
		}

		runSQL(
			"alter table CommerceSubscriptionEntry drop column CPInstanceId");
	}

	private void _addIndexes(String tableName) throws Exception {
		Class<?> clazz = getClass();

		List<ObjectValuePair<String, IndexMetadata>> indexesSQL = getIndexesSQL(
			clazz.getClassLoader(), tableName);

		for (ObjectValuePair<String, IndexMetadata> indexSQL : indexesSQL) {
			IndexMetadata indexMetadata = indexSQL.getValue();

			if (_log.isInfoEnabled()) {
				_log.info(
					String.format(
						"Adding index %s to table %s",
						indexMetadata.getIndexName(), tableName));
			}

			if (!hasIndex(tableName, indexMetadata.getIndexName())) {
				runSQL(indexMetadata.getCreateSQL(null));
			}
			else if (_log.isInfoEnabled()) {
				_log.info(
					String.format(
						"Index %s already exists on table %s",
						indexMetadata.getIndexName(), tableName));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceSubscriptionEntryUpgradeProcess.class);

	private final CPDefinitionLocalService _cpDefinitionLocalService;
	private final CPInstanceLocalService _cpInstanceLocalService;

}