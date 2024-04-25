/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.remote.app.support.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Bruno Basto
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.remote.app.support.web.internal.configuration.RemoteAppConfiguration",
	localization = "content/Language", name = "remote-app-configuration-name"
)
public @interface RemoteAppConfiguration {

	@Meta.AD(
		deflt = "false", description = "enable-post-message-description",
		name = "enable-post-message-name", required = false
	)
	public boolean enablePostMessage();

}