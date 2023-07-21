/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.nio.intraband.proxy;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public class StubMapImpl<T>
	extends ConcurrentHashMap<String, T> implements StubMap<T> {

	public StubMapImpl(StubHolder.StubCreator<T> stubCreator) {
		_stubCreator = stubCreator;
	}

	@Override
	public T get(Object key) {
		String portletId = String.valueOf(key);

		StubHolder<T> stubHolder = _stubHolders.get(portletId);

		if (stubHolder != null) {
			return stubHolder.getStub();
		}

		T originalValue = super.get(key);

		if (originalValue == null) {
			return null;
		}

		return originalValue;
	}

	@Override
	public boolean removeStubHolder(String portletId, T stub) {
		return _stubHolders.remove(portletId, stub);
	}

	private final StubHolder.StubCreator<T> _stubCreator;
	private final ConcurrentMap<String, StubHolder<T>> _stubHolders =
		new ConcurrentHashMap<>();

}