/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.info.display.contributor.util;

import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class LayoutDisplayPageProviderUtil {

	public static LayoutDisplayPageObjectProvider<?>
		getLayoutDisplayPageObjectProvider(
			HttpServletRequest httpServletRequest,
			LayoutDisplayPageProviderTracker layoutDisplayPageProviderTracker,
			Portal portal) {

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			(LayoutDisplayPageObjectProvider<?>)httpServletRequest.getAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

		if (layoutDisplayPageObjectProvider != null) {
			return layoutDisplayPageObjectProvider;
		}

		String className = portal.getClassName(
			ParamUtil.getLong(httpServletRequest, "classNameId"));

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByClassName(className);

		try {
			layoutDisplayPageObjectProvider =
				layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
					new InfoItemReference(
						className,
						ParamUtil.getLong(httpServletRequest, "classPK")));

			httpServletRequest.setAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER,
				layoutDisplayPageObjectProvider);

			return layoutDisplayPageObjectProvider;
		}
		catch (Exception exception) {
			_log.error("Unable to get info display object provider", exception);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutDisplayPageProviderUtil.class);

}