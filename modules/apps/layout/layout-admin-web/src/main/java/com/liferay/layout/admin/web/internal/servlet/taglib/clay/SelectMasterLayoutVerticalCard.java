/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class SelectMasterLayoutVerticalCard implements VerticalCard {

	public SelectMasterLayoutVerticalCard(
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		RenderRequest renderRequest) {

		_layoutPageTemplateEntry = layoutPageTemplateEntry;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public Map<String, String> getData() {
		return HashMapBuilder.put(
			"name", _layoutPageTemplateEntry.getName()
		).put(
			"plid", String.valueOf(_layoutPageTemplateEntry.getPlid())
		).build();
	}

	@Override
	public String getElementClasses() {
		return "select-master-layout-option card-interactive " +
			"card-interactive-primary";
	}

	@Override
	public String getIcon() {
		return "page";
	}

	@Override
	public String getImageSrc() {
		return _layoutPageTemplateEntry.getImagePreviewURL(_themeDisplay);
	}

	@Override
	public String getTitle() {
		return _layoutPageTemplateEntry.getName();
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final ThemeDisplay _themeDisplay;

}