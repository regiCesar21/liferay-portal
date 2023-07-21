/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.index.IndexInformation;

import java.util.Objects;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Adam Brandizzi
 */
public class SearchAdminDisplayBuilder {

	public SearchAdminDisplayBuilder(
		Language language, Portal portal, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_language = language;
		_portal = portal;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public SearchAdminDisplayContext build() {
		SearchAdminDisplayContext searchAdminDisplayContext =
			new SearchAdminDisplayContext();

		NavigationItemList navigationItemList = new NavigationItemList();
		String selectedTab = getSelectedTab();

		addNavigationItemList(navigationItemList, "connections", selectedTab);

		addNavigationItemList(navigationItemList, "index-actions", selectedTab);

		if (isIndexInformationAvailable()) {
			addNavigationItemList(
				navigationItemList, "field-mappings", selectedTab);
		}

		searchAdminDisplayContext.setNavigationItemList(navigationItemList);
		searchAdminDisplayContext.setSelectedTab(selectedTab);

		return searchAdminDisplayContext;
	}

	public void setIndexInformation(IndexInformation indexInformation) {
		_indexInformation = indexInformation;
	}

	protected void addNavigationItemList(
		NavigationItemList navigationItemList, String label,
		String selectedTab) {

		navigationItemList.add(
			navigationItem -> {
				navigationItem.setActive(selectedTab.equals(label));
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "tabs1", label);
				navigationItem.setLabel(
					_language.get(
						_portal.getHttpServletRequest(_renderRequest), label));
			});
	}

	protected String getSelectedTab() {
		String selectedTab = ParamUtil.getString(
			_renderRequest, "tabs1", "connections");

		if (!Objects.equals(selectedTab, "field-mappings") &&
			!Objects.equals(selectedTab, "index-actions") &&
			!Objects.equals(selectedTab, "connections")) {

			return "connections";
		}

		if (Objects.equals(selectedTab, "field-mappings") &&
			!isIndexInformationAvailable()) {

			return "connections";
		}

		return selectedTab;
	}

	protected boolean isIndexInformationAvailable() {
		if (_indexInformation != null) {
			return true;
		}

		return false;
	}

	private IndexInformation _indexInformation;
	private final Language _language;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}