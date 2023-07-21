/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.display.context;

import com.liferay.analytics.reports.web.internal.util.AnalyticsReportsUtil;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

/**
 * @author David Arques
 * @author Sarai Díaz
 */
public class AnalyticsReportsDisplayContext<T> {

	public AnalyticsReportsDisplayContext(
		LayoutDisplayPageObjectProvider<T> layoutDisplayPageObjectProvider,
		RenderRequest renderRequest, RenderResponse renderResponse,
		ThemeDisplay themeDisplay) {

		_layoutDisplayPageObjectProvider = layoutDisplayPageObjectProvider;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_themeDisplay = themeDisplay;
	}

	public Map<String, Object> getData() {
		if (_data != null) {
			return _data;
		}

		_data = Collections.singletonMap(
			"context",
			Collections.singletonMap(
				"analyticsReportsDataURL",
				String.valueOf(
					_getResourceURL("/analytics_reports/get_data"))));

		return _data;
	}

	public String getHideAnalyticsReportsPanelURL() {
		PortletURL portletURL = _renderResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "/analytics_reports/hide_panel");

		String redirect = ParamUtil.getString(_renderRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}
		else {
			portletURL.setParameter(
				"redirect",
				_themeDisplay.getLayoutFriendlyURL(_themeDisplay.getLayout()));
		}

		return String.valueOf(portletURL);
	}

	public String getLiferayAnalyticsURL() {
		return PrefsPropsUtil.getString(
			_themeDisplay.getCompanyId(), "liferayAnalyticsURL");
	}

	public boolean isAnalyticsSynced() {
		long groupId = ParamUtil.getLong(
			_renderRequest, "groupId", _themeDisplay.getScopeGroupId());

		return AnalyticsReportsUtil.isAnalyticsSynced(
			_themeDisplay.getCompanyId(), groupId);
	}

	private ResourceURL _getResourceURL(String resourceID) {
		ResourceURL resourceURL = _renderResponse.createResourceURL();

		resourceURL.setParameter(
			"classNameId",
			String.valueOf(_layoutDisplayPageObjectProvider.getClassNameId()));
		resourceURL.setParameter(
			"classPK",
			String.valueOf(_layoutDisplayPageObjectProvider.getClassPK()));

		resourceURL.setResourceID(resourceID);

		return resourceURL;
	}

	private Map<String, Object> _data;
	private final LayoutDisplayPageObjectProvider<T>
		_layoutDisplayPageObjectProvider;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}