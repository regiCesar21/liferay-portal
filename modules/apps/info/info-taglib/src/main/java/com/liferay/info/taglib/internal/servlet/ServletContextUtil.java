/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.taglib.internal.servlet;

import com.liferay.info.item.renderer.InfoItemRendererTracker;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = {})
public class ServletContextUtil {

	public static final InfoItemRendererTracker getInfoItemRendererTracker() {
		return _infoItemRendererTracker;
	}

	public static final ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference(unbind = "-")
	protected void setInfoItemRendererTracker(
		InfoItemRendererTracker infoItemRendererTracker) {

		_infoItemRendererTracker = infoItemRendererTracker;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.info.taglib)", unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static InfoItemRendererTracker _infoItemRendererTracker;
	private static ServletContext _servletContext;

}