/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.HorizontalCard;

/**
 * @author Adolfo Pérez
 */
public class DepotDashboardApplicationHorizontalCard implements HorizontalCard {

	public DepotDashboardApplicationHorizontalCard(
		String href, String icon, String title) {

		_href = href;
		_icon = icon;
		_title = title;
	}

	@Override
	public String getElementClasses() {
		return "card-interactive card-interactive-primary card-type-template " +
			"template-card-horizontal";
	}

	@Override
	public String getHref() {
		return _href;
	}

	@Override
	public String getIcon() {
		return _icon;
	}

	@Override
	public String getTitle() {
		return _title;
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final String _href;
	private final String _icon;
	private final String _title;

}