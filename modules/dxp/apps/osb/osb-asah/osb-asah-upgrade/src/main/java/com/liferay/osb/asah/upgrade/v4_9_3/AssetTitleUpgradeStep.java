/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_9_3;

import com.liferay.osb.asah.common.repository.executor.QueryExecutor;
import com.liferay.osb.asah.common.spring.resource.ResourceUtil;
import com.liferay.osb.asah.upgrade.UpgradeStep;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Marcos Martins
 */
@Component
public class AssetTitleUpgradeStep implements UpgradeStep {

	@Override
	public void upgrade(String version) throws Exception {
		_queryExecutor.queryExecute(
			ResourceUtil.readResourceToString(
				"v4_9_3/upgrade_asset_titles.sql"));

		if (_log.isInfoEnabled()) {
			_log.info("Asset titles were updated successfully");
		}
	}

	private static final Log _log = LogFactory.getLog(
		AssetTitleUpgradeStep.class);

	@Autowired
	private QueryExecutor _queryExecutor;

}