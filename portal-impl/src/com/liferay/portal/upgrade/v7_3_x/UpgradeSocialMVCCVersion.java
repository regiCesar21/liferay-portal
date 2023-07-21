/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.portal.kernel.upgrade.UpgradeMVCCVersion;

/**
 * @author Preston Crary
 */
public class UpgradeSocialMVCCVersion extends UpgradeMVCCVersion {

	@Override
	protected String[] getExcludedTableNames() {
		return new String[] {"CompanyInfo"};
	}

	@Override
	protected String[] getModuleTableNames() {
		return new String[] {
			"SocialActivity", "SocialActivityAchievement",
			"SocialActivityCounter", "SocialActivityLimit", "SocialActivitySet",
			"SocialActivitySetting", "SocialRelation", "SocialRequest"
		};
	}

}