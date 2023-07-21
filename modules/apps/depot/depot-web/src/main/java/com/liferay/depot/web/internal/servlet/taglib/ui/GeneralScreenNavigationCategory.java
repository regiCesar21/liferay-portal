/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.servlet.taglib.ui;

import com.liferay.depot.web.internal.constants.DepotScreenNavigationEntryConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = ScreenNavigationCategory.class)
public class GeneralScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return DepotScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "general");
	}

	@Override
	public String getScreenNavigationKey() {
		return DepotScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_DEPOT;
	}

}