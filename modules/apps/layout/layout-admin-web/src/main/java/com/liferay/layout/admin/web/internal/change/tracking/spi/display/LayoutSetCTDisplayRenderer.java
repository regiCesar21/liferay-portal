/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = CTDisplayRenderer.class)
public class LayoutSetCTDisplayRenderer
	extends BaseCTDisplayRenderer<LayoutSet> {

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest, LayoutSet layoutSet)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = layoutSet.getGroup();

		if (!_groupPermission.contains(
				themeDisplay.getPermissionChecker(), group,
				ActionKeys.UPDATE)) {

			return null;
		}

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, group, LayoutAdminPortletKeys.GROUP_PAGES, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/layout_admin/edit_layout_set");

		String currentURL = _portal.getCurrentURL(httpServletRequest);

		portletURL.setParameter("redirect", currentURL);
		portletURL.setParameter("backURL", currentURL);

		portletURL.setParameter(
			"groupId", String.valueOf(layoutSet.getGroupId()));
		portletURL.setParameter(
			"privateLayout", String.valueOf(layoutSet.isPrivateLayout()));

		return portletURL.toString();
	}

	@Override
	public Class<LayoutSet> getModelClass() {
		return LayoutSet.class;
	}

	@Override
	public String getTitle(Locale locale, LayoutSet layoutSet)
		throws PortalException {

		String title = "pages";

		Group group = layoutSet.getGroup();

		if (!group.isLayoutSetPrototype() && !group.isLayoutPrototype()) {
			if (layoutSet.isPrivateLayout()) {
				title = "private-pages";
			}
			else {
				title = "public-pages";
			}
		}

		return _language.get(locale, title);
	}

	@Override
	public String getTypeName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return _language.get(resourceBundle, "pages");
	}

	@Reference
	private GroupPermission _groupPermission;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}