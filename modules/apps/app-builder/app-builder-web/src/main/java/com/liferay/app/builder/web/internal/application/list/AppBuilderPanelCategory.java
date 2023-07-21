/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.web.internal.application.list;

import com.liferay.app.builder.web.internal.constants.AppBuilderPanelCategoryKeys;
import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Farache
 */
@Component(
	immediate = true,
	property = {
		"panel.category.key=" + PanelCategoryKeys.APPLICATIONS_MENU_APPLICATIONS,
		"panel.category.order:Integer=700"
	},
	service = PanelCategory.class
)
public class AppBuilderPanelCategory extends BasePanelCategory {

	@Override
	public String getKey() {
		return AppBuilderPanelCategoryKeys.
			APPLICATIONS_MENU_APPLICATIONS_APP_BUILDER;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "app-builder");
	}

}