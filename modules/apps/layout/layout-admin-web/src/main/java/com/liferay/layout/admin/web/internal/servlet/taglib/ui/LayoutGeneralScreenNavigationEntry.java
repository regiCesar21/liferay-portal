/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.servlet.taglib.ui;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.layout.admin.constants.LayoutScreenNavigationEntryConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategory;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategoryUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntryUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ScreenNavigationEntry.class)
public class LayoutGeneralScreenNavigationEntry
	implements ScreenNavigationEntry<Layout> {

	@Override
	public String getCategoryKey() {
		return LayoutScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL;
	}

	@Override
	public String getEntryKey() {
		return LayoutScreenNavigationEntryConstants.ENTRY_KEY_GENERAL;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), getEntryKey());
	}

	@Override
	public String getScreenNavigationKey() {
		return LayoutScreenNavigationEntryConstants.
			SCREEN_NAVIGATION_KEY_LAYOUT;
	}

	@Override
	public boolean isVisible(User user, Layout layout) {
		List<FormNavigatorCategory> formNavigatorCategories =
			FormNavigatorCategoryUtil.getFormNavigatorCategories(
				FormNavigatorConstants.FORM_NAVIGATOR_ID_LAYOUT);

		Stream<FormNavigatorCategory> formNavigatorCategoryStream =
			formNavigatorCategories.stream();

		return formNavigatorCategoryStream.anyMatch(
			category -> ListUtil.isNotEmpty(
				FormNavigatorEntryUtil.getFormNavigatorEntries(
					FormNavigatorConstants.FORM_NAVIGATOR_ID_LAYOUT,
					category.getKey(), user, layout)));
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/layout/screen/navigation/entries/general.jsp");
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return new AggregateResourceBundle(
			resourceBundle, _portal.getResourceBundle(locale));
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

}