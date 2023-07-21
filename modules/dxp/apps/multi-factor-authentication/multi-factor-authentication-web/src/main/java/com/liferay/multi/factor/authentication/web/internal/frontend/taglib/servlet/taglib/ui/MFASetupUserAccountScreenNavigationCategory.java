/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.web.internal.frontend.taglib.servlet.taglib.ui;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.multi.factor.authentication.web.internal.constants.MFASetupUserAccountScreenNavigationConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.users.admin.constants.UserScreenNavigationEntryConstants;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marta Medio
 */
@Component(
	property = "screen.navigation.category.order:Integer=40",
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class MFASetupUserAccountScreenNavigationCategory
	implements ScreenNavigationCategory, ScreenNavigationEntry<User> {

	@Override
	public String getCategoryKey() {
		return MFASetupUserAccountScreenNavigationConstants.CATEGORY_KEY_MFA;
	}

	@Override
	public String getEntryKey() {
		return null;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass()),
			getCategoryKey());
	}

	@Override
	public String getScreenNavigationKey() {
		return UserScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_USERS;
	}

	@Override
	public boolean isVisible(User user, User context) {
		return false;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {
	}

}