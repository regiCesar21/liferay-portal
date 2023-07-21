/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alicia García
 */
@Component(
	configurationPid = "com.liferay.document.library.web.internal.configuration.FFDocumentLibraryDDMEditorConfiguration",
	immediate = true,
	service = FFDocumentLibraryDDMEditorConfigurationUtil.class
)
public class FFDocumentLibraryDDMEditorConfigurationUtil {

	public static boolean useDataEngineEditor() {
		return _ffDocumentLibraryDDMEditorConfiguration.useDataEngineEditor();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffDocumentLibraryDDMEditorConfiguration =
			ConfigurableUtil.createConfigurable(
				FFDocumentLibraryDDMEditorConfiguration.class, properties);
	}

	private static volatile FFDocumentLibraryDDMEditorConfiguration
		_ffDocumentLibraryDDMEditorConfiguration;

}