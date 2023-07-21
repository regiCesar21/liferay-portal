/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.util;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;
import java.util.Set;

/**
 * @author Rubén Pulido
 */
public class PaddingConverter {

	public static final Map<Integer, Integer> externalToInternalValuesMap =
		HashMapBuilder.put(
			0, 0
		).put(
			1, 3
		).put(
			2, 4
		).put(
			4, 5
		).put(
			6, 6
		).put(
			8, 7
		).put(
			10, 8
		).build();

	public static Integer convertToExternalValue(Integer value) {
		Set<Integer> externalValues = externalToInternalValuesMap.keySet();

		for (Integer externalValue : externalValues) {
			if (value.equals(externalToInternalValuesMap.get(externalValue))) {
				return externalValue;
			}
		}

		return null;
	}

	public static Integer convertToInternalValue(Integer label) {
		return externalToInternalValuesMap.get(label);
	}

}