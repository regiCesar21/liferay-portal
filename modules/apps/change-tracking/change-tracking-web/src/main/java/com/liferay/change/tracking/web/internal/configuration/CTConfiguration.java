/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tomas Polesovsky
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.change.tracking.web.internal.configuration.CTConfiguration",
	localization = "content/Language",
	name = "publications-portal-configuration-name"
)
public interface CTConfiguration {

	@Meta.AD(
		deflt = "com.liferay.portal.kernel.model.Group|com.liferay.portal.kernel.model.User",
		name = "root-display-class-names", required = false
	)
	public String[] rootDisplayClassNames();

	@Meta.AD(
		deflt = "com.liferay.asset.kernel.model.AssetEntry",
		name = "root-display-child-class-names", required = false
	)
	public String[] rootDisplayChildClassNames();

}