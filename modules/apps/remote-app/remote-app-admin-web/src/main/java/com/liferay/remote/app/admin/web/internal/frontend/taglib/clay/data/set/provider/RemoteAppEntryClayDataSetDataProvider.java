/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.remote.app.admin.web.internal.frontend.taglib.clay.data.set.provider;

import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.remote.app.admin.web.internal.constants.RemoteAppAdminConstants;
import com.liferay.remote.app.admin.web.internal.frontend.taglib.clay.data.set.RemoteAppClayDataSetEntry;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = "clay.data.provider.key=" + RemoteAppAdminConstants.REMOTE_APP_ENTRY_DATA_SET_DISPLAY,
	service = ClayDataSetDataProvider.class
)
public class RemoteAppEntryClayDataSetDataProvider
	implements ClayDataSetDataProvider<RemoteAppClayDataSetEntry> {

	@Override
	public List<RemoteAppClayDataSetEntry> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<RemoteAppEntry> remoteAppEntries =
			_remoteAppEntryLocalService.searchRemoteAppEntries(
				themeDisplay.getCompanyId(), filter.getKeywords(),
				pagination.getStartPosition(), pagination.getEndPosition(),
				sort);

		Stream<RemoteAppEntry> stream = remoteAppEntries.stream();

		return stream.map(
			remoteAppEntry -> new RemoteAppClayDataSetEntry(
				remoteAppEntry, themeDisplay.getLocale())
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _remoteAppEntryLocalService.searchRemoteAppEntriesCount(
			themeDisplay.getCompanyId(), filter.getKeywords());
	}

	@Reference
	private RemoteAppEntryLocalService _remoteAppEntryLocalService;

}