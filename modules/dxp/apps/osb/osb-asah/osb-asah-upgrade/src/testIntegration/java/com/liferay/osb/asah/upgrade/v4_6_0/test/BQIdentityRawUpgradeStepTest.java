/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_6_0.test;

import com.liferay.osb.asah.common.repository.BQIdentityRepository;
import com.liferay.osb.asah.test.util.annotation.BQSQLResource;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;
import com.liferay.osb.asah.upgrade.OSBAsahUpgradeSpringTestContext;
import com.liferay.osb.asah.upgrade.v4_6_0.BQIdentityRawUpgradeStep;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Rachael Koestartyo
 */
public class BQIdentityRawUpgradeStepTest
	implements OSBAsahTestExecutionListenersContext,
			   OSBAsahUpgradeSpringTestContext {

	@BQSQLResource(resourcePath = "bq_identity_raw_upgrade_step_test.sql")
	@Test
	public void testUpgrade() {
		_bqIdentityRawUpgradeStep.upgrade("");

		Assertions.assertNull(_bqIdentityRepository.getBQIndividualId("1"));
		Assertions.assertNull(_bqIdentityRepository.getBQIndividualId("2"));
		Assertions.assertNotNull(_bqIdentityRepository.getBQIndividualId("3"));
	}

	@Autowired
	private BQIdentityRawUpgradeStep _bqIdentityRawUpgradeStep;

	@Autowired
	private BQIdentityRepository _bqIdentityRepository;

}