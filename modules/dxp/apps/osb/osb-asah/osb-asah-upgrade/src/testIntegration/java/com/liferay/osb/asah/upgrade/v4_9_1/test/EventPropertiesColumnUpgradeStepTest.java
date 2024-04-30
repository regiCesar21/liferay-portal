/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_9_1.test;

import com.liferay.osb.asah.common.repository.executor.BigQueryQueryExecutor;
import com.liferay.osb.asah.test.util.annotation.BQSQLResource;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;
import com.liferay.osb.asah.upgrade.OSBAsahUpgradeSpringTestContext;
import com.liferay.osb.asah.upgrade.v4_9_1.EventPropertiesColumnUpgradeStep;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Marcos Martins
 */
public class EventPropertiesColumnUpgradeStepTest
	implements OSBAsahTestExecutionListenersContext,
			   OSBAsahUpgradeSpringTestContext {

	@BQSQLResource(resourcePath = "bq_event_upgrade_step_test.sql")
	@EnabledIfEnvironmentVariable(
		matches = ".*", named = "GOOGLE_APPLICATION_CREDENTIALS"
	)
	@Test
	public void testUpgrade() throws Exception {
		_eventPropertiesColumnUpgradeStep.upgrade("");

		Optional<List<Map<String, Object>>> optionalList =
			_bigQueryQueryExecutor.queryForObject(
				row -> (List<Map<String, Object>>)row.get("properties"),
				_dslContext.select(
					DSL.field("properties")
				).from(
					"BQEvent"
				).limit(
					1
				));

		List<Map<String, Object>> list = optionalList.get();

		Map<String, Object> map = list.get(0);

		Assertions.assertEquals("webContentResourcePk", map.get("name"));
		Assertions.assertEquals("b73ihsy9", map.get("value"));
	}

	@Autowired
	private BigQueryQueryExecutor _bigQueryQueryExecutor;

	@Autowired
	private DSLContext _dslContext;

	@Autowired
	private EventPropertiesColumnUpgradeStep _eventPropertiesColumnUpgradeStep;

}