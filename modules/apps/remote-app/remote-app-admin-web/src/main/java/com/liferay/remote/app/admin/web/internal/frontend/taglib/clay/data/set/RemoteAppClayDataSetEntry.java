/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.remote.app.admin.web.internal.frontend.taglib.clay.data.set;

import com.liferay.remote.app.model.RemoteAppEntry;

import java.util.Locale;

/**
 * @author Bruno Basto
 */
public class RemoteAppClayDataSetEntry {

	public RemoteAppClayDataSetEntry(
		RemoteAppEntry remoteAppEntry, Locale locale) {

		_remoteAppEntry = remoteAppEntry;
		_locale = locale;
	}

	public String getName() {
		return _remoteAppEntry.getName(_locale);
	}

	public long getRemoteAppEntryId() {
		return _remoteAppEntry.getRemoteAppEntryId();
	}

	public String getURL() {
		return _remoteAppEntry.getUrl();
	}

	private final Locale _locale;
	private final RemoteAppEntry _remoteAppEntry;

}