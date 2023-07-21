/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.admin.web.internal.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.admin.web.internal.constants.SiteAdminPortletKeys;
import com.liferay.site.admin.web.internal.display.context.AddGroupDisplayContext;

import java.io.IOException;

import java.util.Dictionary;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SiteAdminPortletKeys.SITE_ADMIN,
		"mvc.command.name=/site_admin/add_group"
	},
	service = MVCRenderCommand.class
)
public class AddGroupMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		boolean disablePrivateLayouts = false;

		try {
			Configuration configuration = _configurationAdmin.getConfiguration(
				"com.liferay.layout.internal.configuration." +
					"FFDisablePrivateLayoutsConfiguration",
				StringPool.QUESTION);

			if (configuration != null) {
				Dictionary<String, Object> properties =
					configuration.getProperties();

				if ((properties != null) &&
					GetterUtil.getBoolean(properties.get("enabled"))) {

					disablePrivateLayouts = true;
				}
			}
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}
		}

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			new AddGroupDisplayContext(
				disablePrivateLayouts,
				_portal.getHttpServletRequest(renderRequest), renderResponse));

		return "/add_group.jsp";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddGroupMVCRenderCommand.class);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private Portal _portal;

}