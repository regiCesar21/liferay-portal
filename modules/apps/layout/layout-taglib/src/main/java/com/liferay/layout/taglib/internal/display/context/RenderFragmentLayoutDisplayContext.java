/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.taglib.internal.display.context;

import com.liferay.layout.adaptive.media.LayoutAdaptiveMediaProcessor;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rubén Pulido
 */
public class RenderFragmentLayoutDisplayContext {

	public RenderFragmentLayoutDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String processAMImages(String content) {
		LayoutAdaptiveMediaProcessor layoutAdaptiveMediaProcessor =
			ServletContextUtil.getLayoutAdaptiveMediaProcessor();

		return layoutAdaptiveMediaProcessor.processAdaptiveMediaContent(
			content);
	}

	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final ThemeDisplay _themeDisplay;

}