/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.image.editor.web.internal.capability.contrast;

import com.liferay.frontend.image.editor.capability.BaseImageEditorCapability;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

/**
 * @author Bruno Basto
 */
public class ImageEditorCapabilityContrast extends BaseImageEditorCapability {

	public ImageEditorCapabilityContrast(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return ResourceBundleUtil.getString(resourceBundle, "contrast");
	}

	@Override
	public String getName() {
		return "contrast";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	private final ServletContext _servletContext;

}