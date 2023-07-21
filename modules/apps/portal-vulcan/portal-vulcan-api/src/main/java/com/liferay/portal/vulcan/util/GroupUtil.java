/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.util;

import com.liferay.portal.kernel.model.Group;

/**
 * @author Javier Gamarra
 */
public class GroupUtil {

	public static String getAssetLibraryKey(Group group) {
		if (_isDepot(group)) {
			return group.getGroupKey();
		}

		return null;
	}

	public static Long getSiteId(Group group) {
		if (_isDepot(group)) {
			return null;
		}

		return group.getGroupId();
	}

	private static boolean _isDepot(Group group) {
		if (group.isDepot()) {
			return true;
		}

		return false;
	}

}