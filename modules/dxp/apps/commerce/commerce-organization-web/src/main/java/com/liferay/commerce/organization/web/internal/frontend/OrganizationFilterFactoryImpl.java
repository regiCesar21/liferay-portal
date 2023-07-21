/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.organization.web.internal.frontend;

import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.FilterFactory;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceOrganizationClayTableDataSetDisplayView.NAME,
	service = FilterFactory.class
)
public class OrganizationFilterFactoryImpl implements FilterFactory {

	@Override
	public Filter create(HttpServletRequest httpServletRequest) {
		OrganizationFilterImpl organizationFilterImpl =
			new OrganizationFilterImpl();

		organizationFilterImpl.setOrganizationId(
			ParamUtil.getLong(httpServletRequest, "organizationId"));
		organizationFilterImpl.setUserId(
			ParamUtil.getLong(httpServletRequest, "userId"));

		organizationFilterImpl.setKeywords(
			ParamUtil.getString(httpServletRequest, "q"));

		return organizationFilterImpl;
	}

}