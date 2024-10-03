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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Ivica Cardic
 */
@Component
public class AssetHourlyViewsUpgradeStep implements UpgradeStep {

	@Override
	public void upgrade(String version) throws Exception {
		if (_environment.acceptsProfiles("prod")) {
			String projectId = ProjectIdThreadLocal.getProjectId();

			for (String view : _VIEWS) {
				_bigQuerySchemaManager.dropTable(projectId, view);
			}

			if (_log.isInfoEnabled()) {
				_log.info("Hourly Views were deleted successfully");
			}
		}
	}

	private static final String[] _VIEWS = {
		"bloghourly", "customassethourly", "documentlibraryhourly",
		"formhourly", "journalhourly", "pagehourly"
	};

	private static final Log _log = LogFactory.getLog(
		AssetHourlyViewsUpgradeStep.class);

	@Autowired
	private BigQuerySchemaManager _bigQuerySchemaManager;

	@Autowired
	private Environment _environment;

}