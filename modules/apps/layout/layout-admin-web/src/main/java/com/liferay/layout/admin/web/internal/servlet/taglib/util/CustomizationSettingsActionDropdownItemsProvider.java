/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Diego Hu
 * @author Bárbara Cabrera
 */
public class CustomizationSettingsActionDropdownItemsProvider {

	public CustomizationSettingsActionDropdownItemsProvider(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_layoutTypePortlet = themeDisplay.getLayoutTypePortlet();

		_resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			_httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.ACTION_PHASE);

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "toggleCustomizedViewMessage");

				liferayPortletURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/layout_admin/toggle_customized_view");

				String customizedViewURL = HttpUtil.addParameter(
					liferayPortletURL.toString(), "customized_view",
					!_layoutTypePortlet.isCustomizedView());

				dropdownItem.putData(
					"toggleCustomizedViewMessageURL", customizedViewURL);

				dropdownItem.setLabel(_getCustomizedViewMessage());
			}
		).add(
			_layoutTypePortlet::isCustomizedView,
			dropdownItem -> {
				dropdownItem.putData("action", "resetCustomizationView");

				liferayPortletURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/layout_admin/reset_customization_view");

				dropdownItem.putData(
					"resetCustomizationViewURL", liferayPortletURL.toString());
				dropdownItem.setLabel(
					LanguageUtil.get(
						_resourceBundle, "reset-my-customizations"));
			}
		).build();
	}

	private String _getCustomizedViewMessage() {
		if (!_layoutTypePortlet.isCustomizedView()) {
			return LanguageUtil.get(_resourceBundle, "view-my-customized-page");
		}
		else if (_layoutTypePortlet.isDefaultUpdated()) {
			return LanguageUtil.get(
				_resourceBundle,
				"the-defaults-for-the-current-page-have-been-updated-click-" +
					"here-to-see-them");
		}

		return LanguageUtil.get(
			_resourceBundle, "view-page-without-my-customizations");
	}

	private final HttpServletRequest _httpServletRequest;
	private final LayoutTypePortlet _layoutTypePortlet;
	private final ResourceBundle _resourceBundle;

}