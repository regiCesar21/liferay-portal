/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.data.set.view.table;

/**
 * @author Marco Leo
 */
public interface ClayTableSchemaBuilder {

	public void addClayTableSchemaField(
		ClayTableSchemaField clayTableSchemaField);

	public ClayTableSchemaField addClayTableSchemaField(String fieldName);

	public ClayTableSchemaField addClayTableSchemaField(
		String fieldName, String label);

	public ClayTableSchema build();

	public void removeClayTableSchemaField(String fieldName);

	public void setClayTableSchema(ClayTableSchema clayTableSchema);

}