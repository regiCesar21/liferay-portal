/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.marketplace.web.service.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Amos Fong
 */
@ExtendedObjectClassDefinition(generateUI = false)
@Meta.OCD(
	id = "com.liferay.osb.provisioning.marketplace.web.service.internal.configuration.MarketplaceConfiguration"
)
public interface MarketplaceConfiguration {

	@Meta.AD(deflt = "localhost", required = false)
	public String domainName();

	@Meta.AD(required = false)
	public String clientId();

	@Meta.AD(required = false)
	public String clientSecret();

}