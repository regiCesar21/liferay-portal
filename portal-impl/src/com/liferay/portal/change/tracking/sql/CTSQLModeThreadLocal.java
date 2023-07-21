/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.change.tracking.sql;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Preston Crary
 */
public class CTSQLModeThreadLocal {

	public static CTSQLMode getCTSQLMode() {
		return _ctSQLMode.get();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #setCTSQLModeWithSafeCloseable(CTSQLMode)}
	 */
	@Deprecated
	public static SafeClosable setCTSQLMode(CTSQLMode ctSQLMode) {
		return _ctSQLMode.setWithSafeClosable(ctSQLMode);
	}

	public static SafeCloseable setCTSQLModeWithSafeCloseable(
		CTSQLMode ctSQLMode) {

		return _ctSQLMode.setWithSafeCloseable(ctSQLMode);
	}

	public static enum CTSQLMode {

		CT_ALL, CT_ONLY, DEFAULT,

	}

	private CTSQLModeThreadLocal() {
	}

	private static final CentralizedThreadLocal<CTSQLMode> _ctSQLMode =
		new CentralizedThreadLocal<>(
			CTSQLModeThreadLocal.class + "._ctSQLMode",
			() -> CTSQLMode.DEFAULT);

}