/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Eudaldo Alonso
 */
@ExtendedObjectClassDefinition(
	category = "assets", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.asset.categories.configuration.AssetCategoriesCompanyConfiguration",
	localization = "content/Language", name = "categorization"
)
public interface AssetCategoriesCompanyConfiguration {

	@Meta.AD(
		deflt = "https://learn.liferay.com/dxp/7.x/en/content-authoring-and-management/tags-and-categories/defining-categories-and-vocabularies-for-content.html",
		description = "link-to-documentation-url-description",
		name = "link-to-documentation-url", required = false
	)
	public String linkToDocumentationURL();

}