/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_11_0;

import com.liferay.osb.asah.common.entity.DataSource;
import com.liferay.osb.asah.common.repository.DataSourceRepository;
import com.liferay.osb.asah.upgrade.UpgradeStep;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jooq.tools.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Rachael Koestartyo
 */
@Component
public class DataSourcesUpgradeStep implements UpgradeStep {

	@Override
	public void upgrade(String version) {
		List<DataSource> dataSources = new ArrayList<>();

		for (DataSource dataSource : _dataSourceRepository.findAll()) {
			if (StringUtils.isEmpty(
					dataSource.getFaroBackendSecuritySignature())) {

				dataSource.setFaroBackendSecuritySignature(
					String.valueOf(UUID.randomUUID()));
				dataSource.setState("DISCONNECTED");
				dataSource.setStatus("INACTIVE");

				dataSources.add(dataSource);
			}
		}

		if (!dataSources.isEmpty()) {
			_dataSourceRepository.saveAll(dataSources);

			if (_log.isInfoEnabled()) {
				_log.info("Updated " + dataSources.size() + " data sources.");
			}
		}
	}

	private static final Log _log = LogFactory.getLog(
		DataSourcesUpgradeStep.class);

	@Autowired
	private DataSourceRepository _dataSourceRepository;

}