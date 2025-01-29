/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_11_0.test;

import com.liferay.osb.asah.common.entity.DataSource;
import com.liferay.osb.asah.common.repository.DataSourceRepository;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;
import com.liferay.osb.asah.upgrade.OSBAsahUpgradeSpringTestContext;
import com.liferay.osb.asah.upgrade.v4_11_0.DataSourcesUpgradeStep;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Rachael Koestartyo
 */
public class DataSourcesUpgradeStepTest
	implements OSBAsahTestExecutionListenersContext,
			   OSBAsahUpgradeSpringTestContext {

	@Test
	public void testUpgrade() throws Exception {
		DataSource dataSource = new DataSource("Liferay Italy");

		dataSource.setCredentialType("Token Authentication");
		dataSource.setId(123L);
		dataSource.setIsNew(Boolean.TRUE);
		dataSource.setProviderType("LIFERAY");
		dataSource.setState("CREDENTIALS_VALID");
		dataSource.setStatus("ACTIVE");
		dataSource.setURL("");

		dataSource = _dataSourceRepository.save(dataSource);

		Assertions.assertNull(dataSource.getFaroBackendSecuritySignature());
		Assertions.assertEquals("CREDENTIALS_VALID", dataSource.getState());
		Assertions.assertEquals("ACTIVE", dataSource.getStatus());

		_dataSourcesUpgradeStep.upgrade("");

		Optional<DataSource> dataSourceOptional =
			_dataSourceRepository.findById(123L);

		Assertions.assertTrue(dataSourceOptional.isPresent());

		DataSource updatedDataSource = dataSourceOptional.get();

		Assertions.assertNotNull(
			updatedDataSource.getFaroBackendSecuritySignature());
		Assertions.assertEquals("DISCONNECTED", updatedDataSource.getState());
		Assertions.assertEquals("INACTIVE", updatedDataSource.getStatus());
	}

	@Autowired
	private DataSourceRepository _dataSourceRepository;

	@Autowired
	private DataSourcesUpgradeStep _dataSourcesUpgradeStep;

}