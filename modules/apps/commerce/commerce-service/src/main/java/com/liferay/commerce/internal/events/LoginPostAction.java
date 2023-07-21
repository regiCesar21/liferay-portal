/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.events;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true, property = "key=login.events.post",
	service = LifecycleAction.class
)
public class LoginPostAction extends Action {

	@Override
	public void run(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			Cookie[] cookies = httpServletRequest.getCookies();

			if (cookies == null) {
				return;
			}

			for (Cookie cookie : cookies) {
				String name = cookie.getName();

				if (name.startsWith(
						CommerceOrder.class.getName() + StringPool.POUND)) {

					HttpServletRequest originalHttpServletRequest =
						_portal.getOriginalServletRequest(httpServletRequest);

					HttpSession httpSession =
						originalHttpServletRequest.getSession();

					httpSession.setAttribute(name, cookie.getValue());

					break;
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LoginPostAction.class);

	@Reference
	private Portal _portal;

}