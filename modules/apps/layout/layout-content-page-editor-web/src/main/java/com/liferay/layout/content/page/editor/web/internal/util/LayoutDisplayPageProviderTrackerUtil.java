/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = {})
public class LayoutDisplayPageProviderTrackerUtil {

	public static LayoutDisplayPageProvider<?> getLayoutDisplayPageProvider(
		String className) {

		return _layoutDisplayPageProviderTracker.
			getLayoutDisplayPageProviderByClassName(className);
	}

	@Reference(unbind = "-")
	protected void setLayoutDisplayPageProviderTracker(
		LayoutDisplayPageProviderTracker layoutDisplayPageProviderTracker) {

		_layoutDisplayPageProviderTracker = layoutDisplayPageProviderTracker;
	}

	private static LayoutDisplayPageProviderTracker
		_layoutDisplayPageProviderTracker;

}