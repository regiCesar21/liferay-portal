/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.info.display.url.provider.InfoEditURLProvider;
import com.liferay.info.display.url.provider.InfoEditURLProviderTracker;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = {})
public class InfoEditURLProviderUtil {

	public static String getURLEdit(
			String className, Object object,
			HttpServletRequest httpServletRequest)
		throws Exception {

		InfoEditURLProvider<Object> infoEditURLProvider =
			(InfoEditURLProvider<Object>)
				_infoEditURLProviderTracker.getInfoEditURLProvider(className);

		if (infoEditURLProvider == null) {
			return null;
		}

		return infoEditURLProvider.getURL(object, httpServletRequest);
	}

	@Reference(unbind = "-")
	protected void setInfoEditURLProviderTracker(
		InfoEditURLProviderTracker infoEditURLProviderTracker) {

		_infoEditURLProviderTracker = infoEditURLProviderTracker;
	}

	private static InfoEditURLProviderTracker _infoEditURLProviderTracker;

}