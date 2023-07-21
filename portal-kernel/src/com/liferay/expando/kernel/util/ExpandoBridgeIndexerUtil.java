/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.search.Document;

/**
 * @author Raymond Augé
 * @deprecated As of Cavanaugh (7.4.x)
 */
@Deprecated
public class ExpandoBridgeIndexerUtil {

	public static void addAttributes(
		Document doc, ExpandoBridge expandoBridge) {

		getExpandoBridgeIndexer().addAttributes(doc, expandoBridge);
	}

	public static String encodeFieldName(String columnName, int indexType) {
		return getExpandoBridgeIndexer().encodeFieldName(columnName, indexType);
	}

	public static ExpandoBridgeIndexer getExpandoBridgeIndexer() {
		return _expandoBridgeIndexer;
	}

	public void setExpandoBridgeIndexer(
		ExpandoBridgeIndexer expandoBridgeIndexer) {

		_expandoBridgeIndexer = expandoBridgeIndexer;
	}

	private static ExpandoBridgeIndexer _expandoBridgeIndexer;

}