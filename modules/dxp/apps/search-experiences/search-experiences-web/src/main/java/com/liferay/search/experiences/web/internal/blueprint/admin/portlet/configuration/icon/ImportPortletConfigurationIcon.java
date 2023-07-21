/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.web.internal.blueprint.admin.portlet.configuration.icon;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.search.experiences.constants.SXPActionKeys;
import com.liferay.search.experiences.constants.SXPConstants;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.web.internal.compat.petra.portlet.url.builder.PortletURLBuilder;

import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kevin Tan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SXPPortletKeys.SXP_BLUEPRINT_ADMIN, "path=-",
		"path=/sxp_blueprint_admin/view_sxp_blueprints",
		"path=/sxp_blueprint_admin/view_sxp_elements"
	},
	service = PortletConfigurationIcon.class
)
public class ImportPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getLocale(portletRequest), getClass());

		return LanguageUtil.get(resourceBundle, "import");
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return StringBundler.concat(
			"Liferay.Util.openModal({height: '320px', size: 'md', title: '",
			getMessage(portletRequest), "', url: '",
			PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					portletRequest, SXPPortletKeys.SXP_BLUEPRINT_ADMIN,
					PortletRequest.RENDER_PHASE)
			).setMVCPath(
				"/sxp_blueprint_admin/view_import.jsp"
			).setRedirect(
				() -> {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)portletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					return themeDisplay.getURLCurrent();
				}
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString(),
			"'});");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "javascript:;";
	}

	@Override
	public double getWeight() {
		return 100;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (_portletResourcePermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup(),
				SXPActionKeys.ADD_SXP_BLUEPRINT)) {

			return true;
		}

		return false;
	}

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + SXPConstants.RESOURCE_NAME + ")",
		unbind = "-"
	)
	private PortletResourcePermission _portletResourcePermission;

}