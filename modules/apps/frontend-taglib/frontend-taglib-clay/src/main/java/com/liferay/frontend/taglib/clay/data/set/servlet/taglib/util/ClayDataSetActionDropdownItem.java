/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.data.set.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;

/**
 * @author Marco Leo
 */
public class ClayDataSetActionDropdownItem extends DropdownItem {

	public ClayDataSetActionDropdownItem(
		String href, String icon, String id, String label, String method,
		String permissionKey, String target) {

		setHref(href);
		setIcon(icon);
		setId(id);
		setLabel(label);
		setMethod(method);
		setPermissionKey(permissionKey);
		setTarget(target);
	}

	public void setId(String id) {
		putData("id", id);
	}

	public void setMethod(String method) {
		putData("method", method);
	}

	public void setPermissionKey(String permissionKey) {
		putData("permissionKey", permissionKey);
	}

}