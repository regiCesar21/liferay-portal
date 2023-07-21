/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Jorge Ferrer
 */
public class GenericUtil {

	public static Class<?> getGenericClass(Class<?> clazz) {
		Type[] genericInterfaceTypes = clazz.getGenericInterfaces();

		for (Type genericInterfaceType : genericInterfaceTypes) {
			if (genericInterfaceType instanceof ParameterizedType) {
				ParameterizedType parameterizedType =
					(ParameterizedType)genericInterfaceType;

				return (Class<?>)parameterizedType.getActualTypeArguments()[0];
			}
		}

		Class<?> superClass = clazz.getSuperclass();

		if (superClass != null) {
			return getGenericClass(superClass);
		}

		return Object.class;
	}

	public static Class<?> getGenericClass(Object object) {
		Class<?> clazz = object.getClass();

		return getGenericClass(clazz);
	}

	public static String getGenericClassName(Object object) {
		Class<?> clazz = getGenericClass(object);

		return clazz.getName();
	}

}