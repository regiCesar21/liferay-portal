/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.item.action;

import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author David Arques
 */
public class AnalyticsReportsContentDashboardItemAction
	implements ContentDashboardItemAction {

	public AnalyticsReportsContentDashboardItemAction(
		ResourceBundleLoader resourceBundleLoader, String url) {

		_resourceBundleLoader = resourceBundleLoader;
		_url = url;
	}

	@Override
	public String getIcon() {
		return "analytics";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return ResourceBundleUtil.getString(resourceBundle, "view-metrics");
	}

	@Override
	public String getName() {
		return _NAME;
	}

	@Override
	public Type getType() {
		return Type.VIEW_IN_PANEL;
	}

	@Override
	public String getURL() {
		return _url;
	}

	@Override
	public String getURL(Locale locale) {
		return _url;
	}

	private static final String _NAME = "viewMetrics";

	private final ResourceBundleLoader _resourceBundleLoader;
	private final String _url;

}