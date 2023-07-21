/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.extender.internal;

import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Matthew Tambara
 */
public class CompatResourceBundleLoader implements ResourceBundleLoader {

	public CompatResourceBundleLoader(
		com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader
			resourceBundleLoader) {

		_resourceBundleLoader = resourceBundleLoader;
	}

	@Override
	public ResourceBundle loadResourceBundle(Locale locale) {
		return _resourceBundleLoader.loadResourceBundle(locale);
	}

	private final com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader
		_resourceBundleLoader;

}