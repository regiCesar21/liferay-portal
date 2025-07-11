/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_13_1;

import com.liferay.osb.asah.common.repository.executor.QueryExecutor;
import com.liferay.osb.asah.common.spring.resource.ResourceUtil;
import com.liferay.osb.asah.upgrade.UpgradeStep;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Rachael Koestartyo
 */
@Component
public class EventUpgradeStep implements UpgradeStep {

	@Override
	public void upgrade(String version) throws Exception {
		_queryExecutor.queryExecute(
			ResourceUtil.readResourceToString("v4_13_1/upgrade_event.sql"));

		if (_log.isInfoEnabled()) {
			_log.info("Event successfully upgraded to schema 4.13.1");
		}
	}

	private static final Log _log = LogFactory.getLog(EventUpgradeStep.class);

	@Autowired
	private QueryExecutor _queryExecutor;

}