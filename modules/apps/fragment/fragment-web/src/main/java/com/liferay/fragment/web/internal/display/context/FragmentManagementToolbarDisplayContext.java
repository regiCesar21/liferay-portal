/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public abstract class FragmentManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public FragmentManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<Object> searchContainer,
		FragmentDisplayContext fragmentDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);

		this.fragmentDisplayContext = fragmentDisplayContext;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public abstract Map<String, Object> getComponentContext() throws Exception;

	@Override
	public String getComponentId() {
		return "fragmentEntriesManagementToolbar" +
			fragmentDisplayContext.getFragmentCollectionId();
	}

	@Override
	public String getDefaultEventHandler() {
		return "FRAGMENT_ENTRIES_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		searchActionURL.setParameter(
			"fragmentCollectionId",
			String.valueOf(fragmentDisplayContext.getFragmentCollectionId()));

		return searchActionURL.toString();
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"name", "modified-date"};
	}

	protected final FragmentDisplayContext fragmentDisplayContext;

}