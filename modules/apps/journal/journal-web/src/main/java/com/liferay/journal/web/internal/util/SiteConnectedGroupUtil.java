/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.util;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Adolfo Pérez
 */
public class SiteConnectedGroupUtil {

	public static long[] getCurrentAndAncestorSiteAndDepotGroupIds(long groupId)
		throws PortalException {

		return ArrayUtil.append(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId),
			ListUtil.toLongArray(
				DepotEntryServiceUtil.getGroupConnectedDepotEntries(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
				DepotEntry::getGroupId));
	}

	public static long[] getCurrentAndAncestorSiteAndDepotGroupIds(
			long groupId, boolean ddmStructuresAvailable)
		throws PortalException {

		return ArrayUtil.append(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId),
			ListUtil.toLongArray(
				DepotEntryServiceUtil.getGroupConnectedDepotEntries(
					groupId, ddmStructuresAvailable, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS),
				DepotEntry::getGroupId));
	}

}