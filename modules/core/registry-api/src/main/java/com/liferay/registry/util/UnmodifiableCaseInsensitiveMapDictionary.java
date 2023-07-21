/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.registry.util;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class UnmodifiableCaseInsensitiveMapDictionary<V>
	extends Dictionary<String, V> {

	public UnmodifiableCaseInsensitiveMapDictionary(Map<String, V> map) {
		if (map == null) {
			_map = Collections.emptyMap();
		}
		else {
			_map = Collections.unmodifiableMap(map);
		}

		_keysEnumeration = Collections.enumeration(_map.keySet());
		_elementsEnumeration = Collections.enumeration(_map.values());
	}

	@Override
	public Enumeration<V> elements() {
		return _elementsEnumeration;
	}

	@Override
	public V get(Object key) {
		if (!(key instanceof String)) {
			return null;
		}

		String keyString = (String)key;

		V value = _map.get(keyString);

		if (value == null) {
			value = _map.get(keyString.toLowerCase());
		}

		return value;
	}

	@Override
	public boolean isEmpty() {
		return _map.isEmpty();
	}

	@Override
	public Enumeration<String> keys() {
		return _keysEnumeration;
	}

	@Override
	public V put(String key, V value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public V remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return _map.size();
	}

	private final Enumeration<V> _elementsEnumeration;
	private final Enumeration<String> _keysEnumeration;
	private final Map<String, V> _map;

}