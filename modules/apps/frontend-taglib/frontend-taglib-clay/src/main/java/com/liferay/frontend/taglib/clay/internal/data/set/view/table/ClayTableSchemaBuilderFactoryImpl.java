/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.internal.data.set.view.table;

import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(service = ClayTableSchemaBuilderFactory.class)
public class ClayTableSchemaBuilderFactoryImpl
	implements ClayTableSchemaBuilderFactory {

	@Override
	public ClayTableSchemaBuilder create() {
		return new ClayTableSchemaBuilderImpl();
	}

}