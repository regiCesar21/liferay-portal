/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.util.TrashWebKeys;

import javax.portlet.PortletConfig;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	property = "javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
	service = ConfigurationAction.class
)
public class IGDisplayConfigurationAction
	extends ValidateRootFolderConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/image_gallery_display/configuration.jsp";
	}

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletRequest.setAttribute(
			TrashWebKeys.TRASH_HELPER, _trashHelper);

		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Reference
	private TrashHelper _trashHelper;

}