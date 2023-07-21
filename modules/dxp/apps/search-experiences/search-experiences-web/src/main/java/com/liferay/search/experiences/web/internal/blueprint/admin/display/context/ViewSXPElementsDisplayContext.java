/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.web.internal.blueprint.admin.display.context;

import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.search.experiences.constants.SXPActionKeys;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.web.internal.display.context.helper.SXPRequestHelper;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kevin Tan
 */
public class ViewSXPElementsDisplayContext {

	public ViewSXPElementsDisplayContext(
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<SXPElement> sxpElementModelResourcePermission) {

		_sxpElementModelResourcePermission = sxpElementModelResourcePermission;

		_sxpRequestHelper = new SXPRequestHelper(httpServletRequest);
	}

	public String getAPIURL() {
		return "/o/search-experiences-rest/v1.0/sxp-elements";
	}

	public PortletURL getPortletURL() throws PortletException {
		return PortletURLUtil.clone(
			PortletURLUtil.getCurrent(
				_sxpRequestHelper.getLiferayPortletRequest(),
				_sxpRequestHelper.getLiferayPortletResponse()),
			_sxpRequestHelper.getLiferayPortletResponse());
	}

	public boolean hasAddSXPElementPermission() {
		PortletResourcePermission portletResourcePermission =
			_sxpElementModelResourcePermission.getPortletResourcePermission();

		return portletResourcePermission.contains(
			_sxpRequestHelper.getPermissionChecker(), null,
			SXPActionKeys.ADD_SXP_ELEMENT);
	}

	private final ModelResourcePermission<SXPElement>
		_sxpElementModelResourcePermission;
	private final SXPRequestHelper _sxpRequestHelper;

}