/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.internal.jaxrs.context.provider;

import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.events.ThemeServicePreAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = ThemeDisplayContextProvider.class)
@Provider
public class ThemeDisplayContextProvider
	implements ContextProvider<ThemeDisplay> {

	@Override
	public ThemeDisplay createContext(Message message) {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)message.getContextualProperty("HTTP.REQUEST");

		try {
			return _getThemeDisplay(httpServletRequest);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return null;
	}

	private ThemeDisplay _getThemeDisplay(HttpServletRequest httpServletRequest)
		throws Exception {

		ServicePreAction servicePreAction = new ServicePreAction();

		HttpServletResponse httpServletResponse =
			new DummyHttpServletResponse();

		servicePreAction.servicePre(
			httpServletRequest, httpServletResponse, false);

		ThemeServicePreAction themeServicePreAction =
			new ThemeServicePreAction();

		themeServicePreAction.run(httpServletRequest, httpServletResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long plid = ParamUtil.getLong(httpServletRequest, "plid");

		if (plid > 0) {
			Layout layout = _layoutLocalService.fetchLayout(plid);

			themeDisplay.setLayout(layout);
			themeDisplay.setPlid(layout.getPlid());

			long groupId = ParamUtil.getLong(httpServletRequest, "groupId");

			themeDisplay.setScopeGroupId(groupId);
			themeDisplay.setSiteGroupId(groupId);
		}

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletId = ParamUtil.getString(httpServletRequest, "portletId");

		portletDisplay.setId(portletId);

		return themeDisplay;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeDisplayContextProvider.class);

	@Reference
	private LayoutLocalService _layoutLocalService;

}