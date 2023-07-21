/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.item.selector;

import com.liferay.content.dashboard.web.internal.constants.ContentDashboardPortletKeys;
import com.liferay.content.dashboard.web.internal.display.context.ContentDashboardItemTypeItemSelectorViewDisplayContext;
import com.liferay.content.dashboard.web.internal.display.context.ContentDashboardItemTypeItemSelectorViewManagementToolbarDisplayContext;
import com.liferay.content.dashboard.web.internal.item.selector.criteria.content.dashboard.type.criterion.ContentDashboardItemTypeItemSelectorCriterion;
import com.liferay.content.dashboard.web.internal.item.selector.provider.ContentDashboardItemTypeItemSelectorProvider;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ItemSelectorView.class)
public class ContentDashboardItemTypeItemSelectorView
	implements ItemSelectorView<ContentDashboardItemTypeItemSelectorCriterion> {

	@Override
	public Class<ContentDashboardItemTypeItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return ContentDashboardItemTypeItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return ResourceBundleUtil.getString(resourceBundle, "subtype");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			ContentDashboardItemTypeItemSelectorCriterion
				contentDashboardItemTypeItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/view_content_dashboard_item_types.jsp");

		PortletRequest portletRequest =
			(PortletRequest)servletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse =
			(PortletResponse)servletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		SearchContainer<? extends ContentDashboardItemType> searchContainer =
			_contentDashboardItemTypeItemSelectorProvider.getSearchContainer(
				portletRequest, portletResponse, portletURL);

		String portletNamespace = _portal.getPortletNamespace(
			ContentDashboardPortletKeys.CONTENT_DASHBOARD_ADMIN);

		servletRequest.setAttribute(
			ContentDashboardItemTypeItemSelectorViewDisplayContext.class.
				getName(),
			new ContentDashboardItemTypeItemSelectorViewDisplayContext(
				portletNamespace + "selectedContentDashboardItemTypeItem",
				searchContainer));

		servletRequest.setAttribute(
			ContentDashboardItemTypeItemSelectorViewManagementToolbarDisplayContext.class.
				getName(),
			new ContentDashboardItemTypeItemSelectorViewManagementToolbarDisplayContext(
				_portal.getHttpServletRequest(portletRequest),
				_portal.getLiferayPortletRequest(portletRequest),
				_portal.getLiferayPortletResponse(portletResponse),
				searchContainer));

		requestDispatcher.include(servletRequest, servletResponse);
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new UUIDItemSelectorReturnType());

	@Reference
	private ContentDashboardItemTypeItemSelectorProvider
		_contentDashboardItemTypeItemSelectorProvider;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.content.dashboard.web)"
	)
	private ServletContext _servletContext;

	@Reference
	private UserLocalService _userLocalService;

}