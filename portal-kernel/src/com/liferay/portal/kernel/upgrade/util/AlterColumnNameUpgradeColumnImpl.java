/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.util;

/**
 * @author Michael Bowerman
 */
public class AlterColumnNameUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public AlterColumnNameUpgradeColumnImpl(String newName, String oldName) {
		super(newName);

		_oldName = oldName;
	}

	public AlterColumnNameUpgradeColumnImpl(
		String newName, String oldName, Integer oldColumnType) {

		super(newName, oldColumnType);

		_oldName = oldName;
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		return oldValue;
	}

	@Override
	public String getOldName() {
		return _oldName;
	}

	private final String _oldName;

}