/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.util;

import com.liferay.site.util.RecentGroupManager;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(immediate = true, service = {})
public class RecentGroupManagerUtil {

	public static RecentGroupManager getRecentGroupManager() {
		return _recentGroupManager;
	}

	@Reference(unbind = "-")
	protected void setServletContext(RecentGroupManager recentGroupManager) {
		_recentGroupManager = recentGroupManager;
	}

	private static RecentGroupManager _recentGroupManager;

}