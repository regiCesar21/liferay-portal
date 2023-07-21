/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet;

import com.liferay.layout.content.page.editor.web.internal.portlet.constants.LayoutContentPageEditorWebPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.fragment.entry.processor.portlet.alias=test",
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.scopeable=true", "javax.portlet.display-name=Test",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.name=" + LayoutContentPageEditorWebPortletKeys.LAYOUT_CONTENT_PAGE_EDITOR_WEB_TEST_PORTLET
	},
	service = Portlet.class
)
public class LayoutContentPageEditorWebTestPortlet extends MVCPortlet {
}