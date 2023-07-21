/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.model.StyleBookEntry;

import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.RenderRequest;

/**
 * @author Víctor Galán
 */
public class DefaultStylebookLayoutVerticalCard implements VerticalCard {

	public DefaultStylebookLayoutVerticalCard(
		String name, StyleBookEntry styleBookEntry,
		RenderRequest renderRequest) {

		_name = name;
		_styleBookEntry = styleBookEntry;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", _themeDisplay.getLocale(), getClass());
	}

	@Override
	public Map<String, String> getData() {
		return HashMapBuilder.put(
			"name", _name
		).put(
			"styleBookEntryId", "0"
		).build();
	}

	@Override
	public String getElementClasses() {
		return "select-master-layout-option card-interactive " +
			"card-interactive-primary";
	}

	@Override
	public String getIcon() {
		return "magic";
	}

	@Override
	public String getImageSrc() {
		if (_styleBookEntry != null) {
			return _styleBookEntry.getImagePreviewURL(_themeDisplay);
		}

		return null;
	}

	@Override
	public String getSubtitle() {
		if (_styleBookEntry != null) {
			return _styleBookEntry.getName();
		}

		return LanguageUtil.get(_resourceBundle, "provided-by-theme");
	}

	@Override
	public String getTitle() {
		return _name;
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final String _name;
	private final ResourceBundle _resourceBundle;
	private final StyleBookEntry _styleBookEntry;
	private final ThemeDisplay _themeDisplay;

}