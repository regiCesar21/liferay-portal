/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.data.set;

import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.internal.data.set.view.table.ClayTableSchemaBuilderImpl;

import java.util.Locale;

/**
 * @author Marco Leo
 */
public interface ClayDataSetDisplayView {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getClayTableSchema(Locale)}
	 */
	@Deprecated
	public default ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilderImpl clayTableSchemaBuilderImpl =
			new ClayTableSchemaBuilderImpl();

		return clayTableSchemaBuilderImpl.build();
	}

	public default ClayTableSchema getClayTableSchema(Locale locale) {
		return getClayTableSchema();
	}

	public String getContentRenderer();

	public default String getContentRendererModuleURL() {
		return null;
	}

	public String getLabel();

	public String getName();

	public String getThumbnail();

}