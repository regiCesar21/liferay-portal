/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.timebased.otp.internal.upgrade;

import com.liferay.multi.factor.authentication.timebased.otp.internal.upgrade.v1_1_0.MFATimeBasedOTPLastValidTOTPUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Manuele Castro
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class MultiFactorAuthenticationTimebasedOtpUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.1.0", new MFATimeBasedOTPLastValidTOTPUpgradeProcess());
	}

}