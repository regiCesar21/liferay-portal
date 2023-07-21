/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.saml.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Marta Medio
 */
@ExtendedObjectClassDefinition(
	generateUI = false, scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.osb.saml.internal.configuration.OSBSamlConfiguration"
)
public interface OSBSamlConfiguration {

	@Meta.AD(
		deflt = "false", id = "saml.saas.production.environment",
		required = false
	)
	public boolean productionEnvironment();

	@Meta.AD(id = "saml.saas.pre.shared.key", required = false)
	public String preSharedKey();

	@Meta.AD(id = "saml.saas.target.instance.import.url", required = false)
	public String targetInstanceImportURL();

}