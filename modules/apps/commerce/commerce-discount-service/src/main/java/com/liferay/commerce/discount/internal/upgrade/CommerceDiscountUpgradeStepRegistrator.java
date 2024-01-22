/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.internal.upgrade;

import com.liferay.commerce.discount.internal.upgrade.v2_0_0.CommerceDiscountCommerceAccountGroupRelUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_0_0.CommerceDiscountRelUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_0_0.CommerceDiscountRuleUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_0_0.CommerceDiscountUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_0_0.CommerceDiscountUsageEntryUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_1_0.CommerceDiscountExternalReferenceCodeUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_2_0.CommerceDiscountAccountRelUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_2_0.CommerceDiscountRuleNameUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceDiscountUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("Commerce discount upgrade step registrator started");
		}

		registry.register(
			"1.0.0", "2.0.0",
			new CommerceDiscountCommerceAccountGroupRelUpgradeProcess(),
			new CommerceDiscountRelUpgradeProcess(),
			new CommerceDiscountRuleUpgradeProcess(),
			new CommerceDiscountUpgradeProcess(),
			new CommerceDiscountUsageEntryUpgradeProcess());

		registry.register(
			"2.0.0", "2.1.0",
			new CommerceDiscountExternalReferenceCodeUpgradeProcess());

		registry.register(
			"2.1.0", "2.2.0", new CommerceDiscountAccountRelUpgradeProcess(),
			new com.liferay.commerce.discount.internal.upgrade.v2_2_0.
				CommerceDiscountCommerceAccountGroupRelUpgradeProcess(),
			new CommerceDiscountRuleNameUpgradeProcess(),
			new com.liferay.commerce.discount.internal.upgrade.v2_2_0.
				CommerceDiscountUpgradeProcess());

		registry.register(
			"2.2.0", "2.3.0",
			new com.liferay.commerce.discount.internal.upgrade.v2_3_0.
				CommerceDiscountUpgradeProcess());

		registry.register(
			"2.3.0", "2.4.0",
			new com.liferay.commerce.discount.internal.upgrade.v2_4_0.
				CommerceDiscountUpgradeProcess());

		registry.register(
			"2.4.0", "2.4.1",
			new com.liferay.commerce.discount.internal.upgrade.v2_4_1.
				CommerceDiscountUpgradeProcess());

		registry.register("2.4.1", "2.4.2", new DummyUpgradeStep());

		if (_log.isInfoEnabled()) {
			_log.info("Commerce discount upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountUpgradeStepRegistrator.class);

}