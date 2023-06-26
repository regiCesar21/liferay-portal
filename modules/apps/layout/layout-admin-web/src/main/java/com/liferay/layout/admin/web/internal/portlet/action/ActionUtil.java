/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.layout.set.prototype.helper.LayoutSetPrototypeHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.ThemeSetting;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.service.ThemeLocalServiceUtil;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.ThemeFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.ThemeSettingImpl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ActionUtil {

	public static void addFriendlyURLWarningSessionMessages(
			Layout layout, PortletRequest portletRequest,
			LayoutSetPrototypeHelper layoutSetPrototypeHelper)
		throws PortalException {

		Group group = layout.getGroup();
		LayoutSet layoutSet = layout.getLayoutSet();

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-174417")) ||
			(!group.isLayoutSetPrototype() &&
			 !layoutSet.isLayoutSetPrototypeLinkActive())) {

			return;
		}

		List<Layout> layouts =
			layoutSetPrototypeHelper.getDuplicatedFriendlyURLLayouts(layout);

		if (layouts.isEmpty()) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String key = "the-page-was-saved-with-a-conflicting-friendly-url";

		if (group.isLayoutSetPrototype()) {
			key =
				"the-site-template-page-was-saved-with-a-conflicting-" +
					"friendly-url";
		}

		MultiSessionMessages.add(
			portletRequest, "layoutSetPrototypeFriendlyURLWarning",
			LanguageUtil.get(themeDisplay.getLocale(), key));
	}

	public static void deleteThemeSettingsProperties(
		UnicodeProperties typeSettingsUnicodeProperties, String device) {

		String keyPrefix = ThemeSettingImpl.namespaceProperty(device);

		Set<String> keys = typeSettingsUnicodeProperties.keySet();

		keys.removeIf(key -> key.startsWith(keyPrefix));
	}

	public static String getColorSchemeId(
			long companyId, String themeId, String colorSchemeId)
		throws Exception {

		Theme theme = ThemeLocalServiceUtil.getTheme(companyId, themeId);

		if (!theme.hasColorSchemes()) {
			colorSchemeId = StringPool.BLANK;
		}

		if (Validator.isNull(colorSchemeId)) {
			ColorScheme colorScheme = ThemeLocalServiceUtil.getColorScheme(
				companyId, themeId, colorSchemeId);

			colorSchemeId = colorScheme.getColorSchemeId();
		}

		return colorSchemeId;
	}

	public static void updateLookAndFeel(
			ActionRequest actionRequest, long companyId, long liveGroupId,
			long stagingGroupId, boolean privateLayout, long layoutId,
			UnicodeProperties typeSettingsUnicodeProperties)
		throws Exception {

		long groupId = liveGroupId;

		if (stagingGroupId > 0) {
			groupId = stagingGroupId;
		}

		String[] devices = StringUtil.split(
			ParamUtil.getString(actionRequest, "devices"));

		for (String device : devices) {
			String deviceThemeId = ParamUtil.getString(
				actionRequest, device + "ThemeId");
			String deviceColorSchemeId = ParamUtil.getString(
				actionRequest, device + "ColorSchemeId");
			String deviceCss = ParamUtil.getString(
				actionRequest, device + "Css");

			boolean deviceInheritLookAndFeel = ParamUtil.getBoolean(
				actionRequest, device + "InheritLookAndFeel");

			if (deviceInheritLookAndFeel) {
				deviceThemeId = ThemeFactoryUtil.getDefaultRegularThemeId(
					companyId);
				deviceColorSchemeId = StringPool.BLANK;

				deleteThemeSettingsProperties(
					typeSettingsUnicodeProperties, device);
			}
			else if (Validator.isNotNull(deviceThemeId)) {
				deviceColorSchemeId = getColorSchemeId(
					companyId, deviceThemeId, deviceColorSchemeId);

				updateThemeSettingsProperties(
					actionRequest, companyId, groupId, layoutId, privateLayout,
					typeSettingsUnicodeProperties, device, deviceThemeId, true);
			}

			LayoutServiceUtil.updateLayout(
				groupId, privateLayout, layoutId,
				typeSettingsUnicodeProperties.toString());

			LayoutServiceUtil.updateLookAndFeel(
				groupId, privateLayout, layoutId, deviceThemeId,
				deviceColorSchemeId, deviceCss);
		}
	}

	public static void updateThemeSettingsProperties(
			ActionRequest actionRequest, long companyId, long groupId,
			long layoutId, boolean privateLayout,
			UnicodeProperties typeSettingsUnicodeProperties, String device,
			String deviceThemeId, boolean layout)
		throws Exception {

		Theme theme = ThemeLocalServiceUtil.getTheme(companyId, deviceThemeId);

		deleteThemeSettingsProperties(typeSettingsUnicodeProperties, device);

		Map<String, ThemeSetting> themeSettings =
			theme.getConfigurableSettings();

		if (themeSettings.isEmpty()) {
			return;
		}

		setThemeSettingProperties(
			actionRequest, groupId, layoutId, privateLayout,
			typeSettingsUnicodeProperties, themeSettings, device, layout);
	}

	protected static void setThemeSettingProperties(
			ActionRequest actionRequest, long groupId, long layoutId,
			boolean privateLayout,
			UnicodeProperties typeSettingsUnicodeProperties,
			Map<String, ThemeSetting> themeSettings, String device,
			boolean isLayout)
		throws PortalException {

		Layout layout = null;

		if (isLayout) {
			layout = LayoutLocalServiceUtil.getLayout(
				groupId, privateLayout, layoutId);
		}

		for (Map.Entry<String, ThemeSetting> entry : themeSettings.entrySet()) {
			String key = entry.getKey();
			ThemeSetting themeSetting = entry.getValue();

			String property = StringBundler.concat(
				device, "ThemeSettingsProperties--", key,
				StringPool.DOUBLE_DASH);

			String value = ParamUtil.getString(
				actionRequest, property, themeSetting.getValue());

			if ((isLayout &&
				 !Objects.equals(
					 value,
					 layout.getDefaultThemeSetting(key, device, false))) ||
				(!isLayout && !value.equals(themeSetting.getValue()))) {

				typeSettingsUnicodeProperties.setProperty(
					ThemeSettingImpl.namespaceProperty(device, key), value);
			}
		}
	}

}