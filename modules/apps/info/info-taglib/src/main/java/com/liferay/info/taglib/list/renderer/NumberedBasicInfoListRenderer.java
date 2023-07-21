/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.taglib.list.renderer;

import com.liferay.info.taglib.internal.list.renderer.BasicListInfoListStyle;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Pavel Savinov
 */
public interface NumberedBasicInfoListRenderer<T>
	extends BasicInfoListRenderer<T> {

	@Override
	public default String getLabel(Locale locale) {
		Bundle bundle = FrameworkUtil.getBundle(
			NumberedBasicInfoListRenderer.class);

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.
				getResourceBundleLoaderByBundleSymbolicName(
					bundle.getSymbolicName());

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		return LanguageUtil.get(resourceBundle, "numbered-list");
	}

	@Override
	public default String getListStyle() {
		return BasicListInfoListStyle.NUMBERED.getKey();
	}

}