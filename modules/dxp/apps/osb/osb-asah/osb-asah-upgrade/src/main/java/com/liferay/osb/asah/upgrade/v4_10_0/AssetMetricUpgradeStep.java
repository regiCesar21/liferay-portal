/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_10_0;

import com.liferay.osb.asah.common.bigquery.BigQuerySchemaManager;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;
import com.liferay.osb.asah.upgrade.UpgradeStep;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Marcos Martins
 */
@Component
public class AssetMetricUpgradeStep implements UpgradeStep {

	@Override
	public void upgrade(String version) throws Exception {
		_updateHourlyAssetMetricTableFunction("blogHourly");
		_updateHourlyAssetMetricTableFunction("customAssetHourly");
		_updateHourlyAssetMetricTableFunction("documentLibraryHourly");
	}

	private void _updateHourlyAssetMetricTableFunction(String functionName) {
		_bigQuerySchemaManager.createFunction(
			functionName, ProjectIdThreadLocal.getProjectId());

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Function %s successfully created", functionName));
		}
	}

	private static final Log _log = LogFactory.getLog(
		AssetMetricUpgradeStep.class);

	@Autowired
	private BigQuerySchemaManager _bigQuerySchemaManager;

}