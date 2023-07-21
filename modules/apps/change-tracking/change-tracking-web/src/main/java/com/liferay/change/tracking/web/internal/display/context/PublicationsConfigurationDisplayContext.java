/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.display.context;

import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Máté Thurzó
 * @author Samuel Trong Tran
 */
public class PublicationsConfigurationDisplayContext {

	public PublicationsConfigurationDisplayContext(
		CTPreferencesLocalService ctPreferencesLocalService,
		HttpServletRequest httpServletRequest, Language language,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CTPreferences ctPreferences =
			ctPreferencesLocalService.fetchCTPreferences(
				themeDisplay.getCompanyId(), 0);

		if (ctPreferences != null) {
			_publicationsEnabled = true;
		}
		else {
			_publicationsEnabled = false;
		}

		_language = language;
		_renderResponse = renderResponse;
	}

	public String getActionURL() {
		PortletURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/change_tracking/update_global_publications_configuration");

		return actionURL.toString();
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		if (isPublicationsEnabled()) {
			_navigation = ParamUtil.getString(
				_httpServletRequest, "navigation", "global-settings");
		}
		else {
			_navigation = "global-settings";
		}

		return _navigation;
	}

	public boolean isPublicationsEnabled() {
		return _publicationsEnabled;
	}

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private String _navigation;
	private final boolean _publicationsEnabled;
	private final RenderResponse _renderResponse;

}