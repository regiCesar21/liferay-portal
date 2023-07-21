/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.messaging.proxy;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Shuyang Zhou
 */
public class ProxyModeThreadLocal {

	public static boolean isForceSync() {
		return _forceSync.get();
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #setWithSafeClosable(boolean)}
	 */
	@Deprecated
	public static void setForceSync(boolean forceSync) {
		_forceSync.set(forceSync);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #setWithSafeCloseable(boolean)}
	 */
	@Deprecated
	public static SafeClosable setWithSafeClosable(boolean forceSync) {
		return _forceSync.setWithSafeClosable(forceSync);
	}

	public static SafeCloseable setWithSafeCloseable(boolean forceSync) {
		return _forceSync.setWithSafeCloseable(forceSync);
	}

	private static final CentralizedThreadLocal<Boolean> _forceSync =
		new CentralizedThreadLocal<>(
			ProxyModeThreadLocal.class + "_forceSync", () -> Boolean.TRUE);

}