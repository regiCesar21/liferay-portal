/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.model.StyleBookEntry;

import java.util.Map;

import javax.portlet.RenderRequest;

/**
 * @author Víctor Galán
 */
public class SelectStylebookLayoutVerticalCard implements VerticalCard {

	public SelectStylebookLayoutVerticalCard(
		StyleBookEntry styleBookEntry, RenderRequest renderRequest) {

		_styleBookEntry = styleBookEntry;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public Map<String, String> getData() {
		return HashMapBuilder.put(
			"name", _styleBookEntry.getName()
		).put(
			"styleBookEntryId",
			String.valueOf(_styleBookEntry.getStyleBookEntryId())
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
		return _styleBookEntry.getImagePreviewURL(_themeDisplay);
	}

	@Override
	public String getTitle() {
		return _styleBookEntry.getName();
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final StyleBookEntry _styleBookEntry;
	private final ThemeDisplay _themeDisplay;

}