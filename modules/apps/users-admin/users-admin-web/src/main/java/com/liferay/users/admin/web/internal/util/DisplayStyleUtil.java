/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.web.internal.util;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Drew Brokke
 */
public class DisplayStyleUtil {

	public static String getDisplayStyle(
		PortletRequest portletRequest, String defaultDisplayStyle) {

		String displayStyle = ParamUtil.getString(
			portletRequest, "displayStyle");

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(portletRequest);

		if (Validator.isNull(displayStyle)) {
			displayStyle = portalPreferences.getValue(
				UsersAdminPortletKeys.USERS_ADMIN, "display-style",
				defaultDisplayStyle);
		}
		else {
			portalPreferences.setValue(
				UsersAdminPortletKeys.USERS_ADMIN, "display-style",
				displayStyle);

			LiferayPortletRequest liferayPortletRequest =
				PortalUtil.getLiferayPortletRequest(portletRequest);

			HttpServletRequest httpServletRequest =
				liferayPortletRequest.getOriginalHttpServletRequest();

			httpServletRequest.setAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
		}

		return displayStyle;
	}

}