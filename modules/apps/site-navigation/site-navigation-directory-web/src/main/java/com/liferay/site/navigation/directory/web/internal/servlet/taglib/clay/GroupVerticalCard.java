/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.directory.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class GroupVerticalCard implements VerticalCard {

	public GroupVerticalCard(Group group, RenderRequest renderRequest) {
		_group = group;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getAspectRatioCssClasses() {
		return "aspect-ratio-item-center-middle " +
			"aspect-ratio-item-vertical-fluid";
	}

	@Override
	public String getHref() {
		if (_group.getGroupId() != _themeDisplay.getScopeGroupId()) {
			return _group.getDisplayURL(_themeDisplay);
		}

		return null;
	}

	@Override
	public String getIcon() {
		return "sites";
	}

	@Override
	public String getImageSrc() {
		return _group.getLogoURL(_themeDisplay, false);
	}

	@Override
	public String getSubtitle() {
		return _group.getDescription(_themeDisplay.getLocale());
	}

	@Override
	public String getTitle() {
		try {
			return HtmlUtil.escape(
				_group.getDescriptiveName(_themeDisplay.getLocale()));
		}
		catch (Exception exception) {
		}

		return HtmlUtil.escape(_group.getName(_themeDisplay.getLocale()));
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final Group _group;
	private final ThemeDisplay _themeDisplay;

}