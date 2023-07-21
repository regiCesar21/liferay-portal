/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.taglib.servlet.taglib;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.configuration.SharingConfiguration;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.taglib.internal.servlet.SharingConfigurationFactoryUtil;
import com.liferay.taglib.util.IncludeTag;

/**
 * @author Alejandro Tardín
 */
public abstract class BaseSharingTag extends IncludeTag {

	@Override
	public int doStartTag() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SharingConfigurationFactory sharingConfigurationFactory =
			SharingConfigurationFactoryUtil.getSharingConfigurationFactory();

		SharingConfiguration sharingConfiguration =
			sharingConfigurationFactory.getGroupSharingConfiguration(
				themeDisplay.getSiteGroup());

		if (!sharingConfiguration.isEnabled()) {
			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	}

}