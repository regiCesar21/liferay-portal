/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.web.internal.servlet.taglib.definition;

import com.liferay.data.engine.taglib.servlet.taglib.definition.DataLayoutBuilderDefinition;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, property = "content.type=app-builder",
	service = DataLayoutBuilderDefinition.class
)
public class AppBuilderDataLayoutBuilderDefinition
	implements DataLayoutBuilderDefinition {

	@Override
	public boolean allowFieldSets() {
		return true;
	}

	@Override
	public boolean allowMultiplePages() {
		return false;
	}

	@Override
	public boolean allowNestedFields() {
		return false;
	}

	@Override
	public boolean allowRules() {
		return true;
	}

	@Override
	public boolean allowSuccessPage() {
		return false;
	}

	@Override
	public String[] getUnimplementedProperties() {
		return new String[] {"allowGuestUsers"};
	}

}