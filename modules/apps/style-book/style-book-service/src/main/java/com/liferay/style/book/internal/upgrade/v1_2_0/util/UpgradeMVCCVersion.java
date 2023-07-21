/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.internal.upgrade.v1_2_0.util;

/**
 * @author Víctor Galán
 */
public class UpgradeMVCCVersion
	extends com.liferay.portal.kernel.upgrade.UpgradeMVCCVersion {

	@Override
	protected String[] getModuleTableNames() {
		return new String[] {"StyleBookEntryVersion"};
	}

}