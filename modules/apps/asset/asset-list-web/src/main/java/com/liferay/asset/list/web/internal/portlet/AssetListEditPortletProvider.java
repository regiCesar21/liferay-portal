/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.web.internal.portlet;

import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.EditPortletProvider;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.asset.list.model.AssetListEntry",
	service = EditPortletProvider.class
)
public class AssetListEditPortletProvider
	extends BasePortletProvider implements EditPortletProvider {

	@Override
	public String getPortletName() {
		return AssetListPortletKeys.ASSET_LIST;
	}

	@Override
	public PortletURL getPortletURL(HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = super.getPortletURL(httpServletRequest);

		portletURL.setParameter("mvcPath", "/edit_asset_list_entry.jsp");

		return portletURL;
	}

}