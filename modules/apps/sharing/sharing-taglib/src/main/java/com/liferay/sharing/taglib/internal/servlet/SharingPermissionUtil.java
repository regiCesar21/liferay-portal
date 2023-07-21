/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.taglib.internal.servlet;

import com.liferay.sharing.security.permission.SharingPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = {})
public class SharingPermissionUtil {

	public static final SharingPermission getSharingPermission() {
		return _sharingPermission;
	}

	@Reference(unbind = "-")
	protected void setSharingPermission(SharingPermission sharingPermission) {
		_sharingPermission = sharingPermission;
	}

	private static SharingPermission _sharingPermission;

}