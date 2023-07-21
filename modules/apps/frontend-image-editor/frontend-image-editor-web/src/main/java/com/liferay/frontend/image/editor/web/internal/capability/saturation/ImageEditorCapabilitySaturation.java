/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.image.editor.web.internal.capability.saturation;

import com.liferay.frontend.image.editor.capability.BaseImageEditorCapability;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

/**
 * @author Bruno Basto
 */
public class ImageEditorCapabilitySaturation extends BaseImageEditorCapability {

	public ImageEditorCapabilitySaturation(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return ResourceBundleUtil.getString(resourceBundle, "saturation");
	}

	@Override
	public String getName() {
		return "saturation";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	private final ServletContext _servletContext;

}