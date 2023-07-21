/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.auth.BaseAuthTokenWhitelist;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Raymond Augé
 * @author Tomas Polesovsky
 */
public class AuthTokenWhitelistImpl extends BaseAuthTokenWhitelist {

	@Override
	public boolean isOriginCSRFWhitelisted(long companyId, String origin) {
		for (String whitelistedOrigin : _getOriginCSRFWhitelist()) {
			if (origin.startsWith(whitelistedOrigin)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isPortletCSRFWhitelisted(
		HttpServletRequest httpServletRequest, Portlet portlet) {

		Set<String> portletCSRFWhitelist = _getPortletCSRFWhitelist();

		return portletCSRFWhitelist.contains(portlet.getRootPortletId());
	}

	@Override
	public boolean isPortletInvocationWhitelisted(
		HttpServletRequest httpServletRequest, Portlet portlet) {

		Set<String> portletInvocationWhitelist =
			_getPortletInvocationWhitelist();

		return portletInvocationWhitelist.contains(portlet.getPortletId());
	}

	@Override
	public boolean isPortletURLCSRFWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		String rootPortletId = PortletIdCodec.decodePortletName(
			liferayPortletURL.getPortletId());

		Set<String> portletCSRFWhitelist = _getPortletCSRFWhitelist();

		return portletCSRFWhitelist.contains(rootPortletId);
	}

	@Override
	public boolean isPortletURLPortletInvocationWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		Set<String> portletInvocationWhitelist =
			_getPortletInvocationWhitelist();

		return portletInvocationWhitelist.contains(
			liferayPortletURL.getPortletId());
	}

	@Override
	public boolean isValidSharedSecret(String sharedSecret) {
		if (Validator.isNull(sharedSecret)) {
			return false;
		}

		if (Validator.isNull(PropsValues.AUTH_TOKEN_SHARED_SECRET)) {
			return false;
		}

		return sharedSecret.equals(
			DigesterUtil.digest(PropsValues.AUTH_TOKEN_SHARED_SECRET));
	}

	private Set<String> _getOriginCSRFWhitelist() {
		Set<String> originCSRFWhitelist = _originCSRFWhitelist;

		if (originCSRFWhitelist != null) {
			return originCSRFWhitelist;
		}

		synchronized (this) {
			if (_originCSRFWhitelist == null) {
				originCSRFWhitelist = Collections.newSetFromMap(
					new ConcurrentHashMap<>());

				registerPortalProperty(PropsKeys.AUTH_TOKEN_IGNORE_ORIGINS);

				trackWhitelistServices(
					PropsKeys.AUTH_TOKEN_IGNORE_ORIGINS, originCSRFWhitelist);

				_originCSRFWhitelist = originCSRFWhitelist;
			}
			else {
				originCSRFWhitelist = _originCSRFWhitelist;
			}
		}

		return originCSRFWhitelist;
	}

	private Set<String> _getPortletCSRFWhitelist() {
		Set<String> portletCSRFWhitelist = _portletCSRFWhitelist;

		if (portletCSRFWhitelist != null) {
			return portletCSRFWhitelist;
		}

		synchronized (this) {
			if (_portletCSRFWhitelist == null) {
				portletCSRFWhitelist = Collections.newSetFromMap(
					new ConcurrentHashMap<>());

				registerPortalProperty(PropsKeys.AUTH_TOKEN_IGNORE_PORTLETS);

				trackWhitelistServices(
					PropsKeys.AUTH_TOKEN_IGNORE_PORTLETS, portletCSRFWhitelist);

				_portletCSRFWhitelist = portletCSRFWhitelist;
			}
			else {
				portletCSRFWhitelist = _portletCSRFWhitelist;
			}
		}

		return portletCSRFWhitelist;
	}

	private Set<String> _getPortletInvocationWhitelist() {
		Set<String> portletInvocationWhitelist = _portletInvocationWhitelist;

		if (portletInvocationWhitelist != null) {
			return portletInvocationWhitelist;
		}

		synchronized (this) {
			if (_portletInvocationWhitelist == null) {
				portletInvocationWhitelist = Collections.newSetFromMap(
					new ConcurrentHashMap<>());

				registerPortalProperty(
					PropsKeys.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST);

				trackWhitelistServices(
					PropsKeys.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST,
					portletInvocationWhitelist);

				_portletInvocationWhitelist = portletInvocationWhitelist;
			}
			else {
				portletInvocationWhitelist = _portletInvocationWhitelist;
			}
		}

		return portletInvocationWhitelist;
	}

	private volatile Set<String> _originCSRFWhitelist;
	private volatile Set<String> _portletCSRFWhitelist;
	private volatile Set<String> _portletInvocationWhitelist;

}