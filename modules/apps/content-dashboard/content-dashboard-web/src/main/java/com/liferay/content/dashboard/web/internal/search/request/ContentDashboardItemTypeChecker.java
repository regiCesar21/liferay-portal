/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.search.request;

import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;

import java.util.List;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina Goonzález
 */
public class ContentDashboardItemTypeChecker extends EmptyOnClickRowChecker {

	public ContentDashboardItemTypeChecker(
		List<? extends ContentDashboardItemType>
			checkedContentDashboardItemTypes,
		RenderResponse renderResponse) {

		super(renderResponse);

		_checkedContentDashboardItemTypes = checkedContentDashboardItemTypes;
	}

	@Override
	public String getAllRowsCheckBox() {
		return null;
	}

	@Override
	public String getAllRowsCheckBox(HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public boolean isChecked(Object object) {
		ContentDashboardItemType contentDashboardItemType =
			(ContentDashboardItemType)object;

		return _checkedContentDashboardItemTypes.contains(
			contentDashboardItemType);
	}

	private final List<? extends ContentDashboardItemType>
		_checkedContentDashboardItemTypes;

}