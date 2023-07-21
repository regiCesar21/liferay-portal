/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.admin.web.internal.util.SiteInitializerItem;

import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SelectSiteInitializerVerticalCard implements VerticalCard {

	public SelectSiteInitializerVerticalCard(
		SiteInitializerItem siteInitializerItem, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_siteInitializerItem = siteInitializerItem;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	@Override
	public Map<String, String> getData() {
		return HashMapBuilder.put(
			"add-site-url",
			() -> {
				PortletURL addSiteURL = _renderResponse.createRenderURL();

				long parentGroupId = ParamUtil.getLong(
					_httpServletRequest, "parentGroupId");

				addSiteURL.setParameter(
					"mvcRenderCommandName", "/site_admin/add_group");
				addSiteURL.setParameter(
					"backURL",
					ParamUtil.getString(_httpServletRequest, "redirect"));
				addSiteURL.setParameter(
					"creationType", _siteInitializerItem.getType());
				addSiteURL.setParameter(
					"layoutSetPrototypeId",
					String.valueOf(
						_siteInitializerItem.getLayoutSetPrototypeId()));
				addSiteURL.setParameter(
					"parentGroupId", String.valueOf(parentGroupId));
				addSiteURL.setParameter(
					"siteInitializerKey",
					_siteInitializerItem.getSiteInitializerKey());

				addSiteURL.setWindowState(LiferayWindowState.POP_UP);

				return addSiteURL.toString();
			}
		).put(
			"layout-set-prototype-id",
			String.valueOf(_siteInitializerItem.getLayoutSetPrototypeId())
		).build();
	}

	@Override
	public String getElementClasses() {
		return "add-site-action-option card-interactive " +
			"card-interactive-secondary";
	}

	@Override
	public String getIcon() {
		return "site-template";
	}

	@Override
	public String getImageSrc() {
		if (_siteInitializerItem.isCreationTypeSiteTemplate()) {
			return null;
		}

		if (Validator.isNull(_siteInitializerItem.getIcon())) {
			return null;
		}

		return _siteInitializerItem.getIcon();
	}

	@Override
	public String getTitle() {
		return _siteInitializerItem.getName();
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;
	private final SiteInitializerItem _siteInitializerItem;

}