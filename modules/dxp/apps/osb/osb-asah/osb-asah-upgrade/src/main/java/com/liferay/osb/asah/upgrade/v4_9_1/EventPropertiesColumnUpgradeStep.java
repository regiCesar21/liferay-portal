/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_9_1;

import com.liferay.osb.asah.common.repository.executor.QueryExecutor;
import com.liferay.osb.asah.upgrade.UpgradeStep;

import java.io.InputStream;

import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Marcos Martins
 */
@Component
public class EventPropertiesColumnUpgradeStep implements UpgradeStep {

	@Override
	public void upgrade(String version) throws Exception {
		_queryExecutor.queryExecute(
			_readFile("/v4_9_1/upgrade_event_properties.sql"));

		if (_log.isInfoEnabled()) {
			_log.info("Event properties were updated successfully");
		}
	}

	private String _readFile(String filePath) {
		try {
			Class<?> clazz = getClass();

			InputStream inputStream = clazz.getResourceAsStream(filePath);

			return IOUtils.toString(inputStream, Charset.defaultCharset());
		}
		catch (Exception exception) {
			throw new IllegalStateException(exception);
		}
	}

	private static final Log _log = LogFactory.getLog(
		EventPropertiesColumnUpgradeStep.class);

	@Autowired
	private QueryExecutor _queryExecutor;

}