/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.remote.app.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class RemoteAppAdminDisplayContext {

	public RemoteAppAdminDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		RemoteAppEntryLocalService remoteAppEntryLocalService) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				PortletURL addRemoteAppEntryURL =
					_renderResponse.createRenderURL();

				addRemoteAppEntryURL.setParameter(
					"mvcRenderCommandName",
					"/remote_app_admin/edit_remote_app_entry");
				addRemoteAppEntryURL.setParameter("redirect", _getRedirect());

				dropdownItem.setHref(addRemoteAppEntryURL);

				dropdownItem.setLabel(_getLabel("add-remote-web-app"));
			}
		).build();
	}

	public PortletURL getCurrentPortletURL() {
		return PortletURLUtil.getCurrent(_renderRequest, _renderResponse);
	}

	private HttpServletRequest _getHttpServletRequest() {
		return PortalUtil.getHttpServletRequest(_renderRequest);
	}

	private String _getLabel(String label) {
		return LanguageUtil.get(_getHttpServletRequest(), label);
	}

	private String _getRedirect() {
		return PortalUtil.getCurrentURL(_getHttpServletRequest());
	}

	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}