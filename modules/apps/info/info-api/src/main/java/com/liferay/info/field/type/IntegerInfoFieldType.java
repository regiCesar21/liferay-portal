/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

/**
 * @author     Alejandro Tardín
 * @deprecated As of Athanasius (7.3.x)
 */
@Deprecated
public class IntegerInfoFieldType implements InfoFieldType {

	public static final IntegerInfoFieldType INSTANCE =
		new IntegerInfoFieldType();

	@Override
	public String getName() {
		return "integer";
	}

	private IntegerInfoFieldType() {
	}

}