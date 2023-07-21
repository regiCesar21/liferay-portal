/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.change.tracking.store;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Shuyang Zhou
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public class CTStoreFactoryUtil {

	public static Store createCTStore(Store store, String storeType) {
		return _ctStoreFactory.createCTStore(store, storeType);
	}

	private static volatile CTStoreFactory _ctStoreFactory =
		ServiceProxyFactory.newServiceTrackedInstance(
			CTStoreFactory.class, CTStoreFactoryUtil.class, "_ctStoreFactory",
			true);

}