/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Brian Wing Shun Chan
 */
public class ExpandoBridgeFactoryUtil {

	public static ExpandoBridge getExpandoBridge(
		long companyId, String className) {

		return getExpandoBridgeFactory().getExpandoBridge(companyId, className);
	}

	public static ExpandoBridge getExpandoBridge(
		long companyId, String className, long classPK) {

		return getExpandoBridgeFactory().getExpandoBridge(
			companyId, className, classPK);
	}

	public static ExpandoBridgeFactory getExpandoBridgeFactory() {
		return _expandoBridgeFactory;
	}

	/**
	 * @param      expandoBridgeFactory
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setExpandoBridgeFactory(
		ExpandoBridgeFactory expandoBridgeFactory) {

		_expandoBridgeFactory = expandoBridgeFactory;
	}

	private static volatile ExpandoBridgeFactory _expandoBridgeFactory =
		ServiceProxyFactory.newServiceTrackedInstance(
			ExpandoBridgeFactory.class, ExpandoBridgeFactoryUtil.class,
			"_expandoBridgeFactory", true);

}