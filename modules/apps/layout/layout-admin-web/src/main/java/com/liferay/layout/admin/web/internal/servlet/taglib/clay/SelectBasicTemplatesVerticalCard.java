/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SelectBasicTemplatesVerticalCard implements VerticalCard {

	public SelectBasicTemplatesVerticalCard(
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_layoutPageTemplateEntry = layoutPageTemplateEntry;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public Map<String, String> getData() {
		Map<String, String> data = new HashMap<>();

		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		try {
			PortletURL addLayoutURL = _renderResponse.createRenderURL();

			addLayoutURL.setParameter(
				"mvcRenderCommandName", "/layout_admin/add_layout");
			addLayoutURL.setParameter("backURL", redirect);

			long selPlid = ParamUtil.getLong(_httpServletRequest, "selPlid");

			addLayoutURL.setParameter("selPlid", String.valueOf(selPlid));

			boolean privateLayout = ParamUtil.getBoolean(
				_httpServletRequest, "privateLayout");

			addLayoutURL.setParameter(
				"privateLayout", String.valueOf(privateLayout));

			addLayoutURL.setParameter("type", LayoutConstants.TYPE_CONTENT);
			addLayoutURL.setParameter(
				"masterLayoutPlid",
				String.valueOf(_layoutPageTemplateEntry.getPlid()));
			addLayoutURL.setWindowState(LiferayWindowState.POP_UP);

			data.put("add-layout-url", addLayoutURL.toString());
		}
		catch (Exception exception) {
		}

		return data;
	}

	@Override
	public String getElementClasses() {
		return "add-layout-action-option card-interactive " +
			"card-interactive-primary";
	}

	@Override
	public String getIcon() {
		return "page";
	}

	@Override
	public String getImageSrc() {
		return _layoutPageTemplateEntry.getImagePreviewURL(_themeDisplay);
	}

	@Override
	public String getTitle() {
		return _layoutPageTemplateEntry.getName();
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final HttpServletRequest _httpServletRequest;
	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}