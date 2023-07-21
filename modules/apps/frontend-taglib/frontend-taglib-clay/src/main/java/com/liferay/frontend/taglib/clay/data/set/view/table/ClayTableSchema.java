/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.data.set.view.table;

import java.util.Map;

/**
 * @author Marco Leo
 */
public class ClayTableSchema {

	public Map<String, ClayTableSchemaField> getClayTableSchemaFieldsMap() {
		return _clayTableSchemaFieldsMap;
	}

	public void setClayTableSchemaFieldsMap(
		Map<String, ClayTableSchemaField> clayTableSchemaFieldsMap) {

		_clayTableSchemaFieldsMap = clayTableSchemaFieldsMap;
	}

	private Map<String, ClayTableSchemaField> _clayTableSchemaFieldsMap;

}