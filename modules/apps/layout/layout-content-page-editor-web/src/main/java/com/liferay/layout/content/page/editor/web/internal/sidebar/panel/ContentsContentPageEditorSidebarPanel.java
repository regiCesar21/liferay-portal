/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.sidebar.panel;

import com.liferay.layout.content.page.editor.sidebar.panel.ContentPageEditorSidebarPanel;
import com.liferay.layout.security.permission.resource.LayoutContentModelResourcePermission;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, property = "service.ranking:Integer=400",
	service = ContentPageEditorSidebarPanel.class
)
public class ContentsContentPageEditorSidebarPanel
	implements ContentPageEditorSidebarPanel {

	@Override
	public String getIcon() {
		return "list-ul";
	}

	@Override
	public String getId() {
		return "contents";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "contents");
	}

	@Override
	public boolean isVisible(
		PermissionChecker permissionChecker, long plid, int layoutType) {

		try {
			if (_layoutPermission.contains(
					permissionChecker, plid, ActionKeys.UPDATE) ||
				_layoutPermission.contains(
					permissionChecker, plid,
					ActionKeys.UPDATE_LAYOUT_CONTENT) ||
				_modelResourcePermission.contains(
					permissionChecker, plid, ActionKeys.UPDATE)) {

				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentsContentPageEditorSidebarPanel.class);

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private LayoutContentModelResourcePermission _modelResourcePermission;

}