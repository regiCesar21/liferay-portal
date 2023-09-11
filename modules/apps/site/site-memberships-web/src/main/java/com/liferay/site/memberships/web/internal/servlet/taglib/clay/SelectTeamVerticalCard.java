/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.memberships.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Christopher Kian
 */
public class SelectTeamVerticalCard implements VerticalCard {

	public SelectTeamVerticalCard(Team team) {
		_team = team;
	}

	@Override
	public Map<String, String> getData() {
		return HashMapBuilder.put(
			"id", String.valueOf(_team.getTeamId())
		).build();
	}

	@Override
	public String getElementClasses() {
		return "selector-button";
	}

	@Override
	public String getIcon() {
		return "users";
	}

	@Override
	public String getTitle() {
		return _team.getName();
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final Team _team;

}