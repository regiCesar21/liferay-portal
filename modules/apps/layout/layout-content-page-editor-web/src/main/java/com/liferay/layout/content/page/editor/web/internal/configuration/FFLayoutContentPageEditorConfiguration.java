/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Víctor Galán
 */
@ExtendedObjectClassDefinition(generateUI = false)
@Meta.OCD(
	id = "com.liferay.layout.content.page.editor.web.internal.configuration.FFLayoutContentPageEditorConfiguration"
)
public interface FFLayoutContentPageEditorConfiguration {

	@Meta.AD(deflt = "false", required = false)
	public boolean adaptiveMediaEnabled();

	@Meta.AD(deflt = "false", required = false)
	public boolean containerItemFlexEnabled();

}