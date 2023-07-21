/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.app.manager.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.marketplace.app.manager.web.internal.constants.BundleStateConstants;
import com.liferay.marketplace.app.manager.web.internal.util.AppDisplay;
import com.liferay.marketplace.app.manager.web.internal.util.AppDisplayFactoryUtil;
import com.liferay.marketplace.app.manager.web.internal.util.BundleManagerUtil;
import com.liferay.marketplace.app.manager.web.internal.util.comparator.AppDisplayComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class ViewAppsManagerManagementToolbarDisplayContext
	extends BaseAppManagerManagementToolbarDisplayContext {

	public ViewAppsManagerManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse);

		_searchContainer = _createSearchContainer(liferayPortletRequest);
	}

	@Override
	public String getClearResultsURL() {
		PortletURL removeLabelURL = getPortletURL();

		removeLabelURL.setParameter("category", (String)null);
		removeLabelURL.setParameter("state", (String)null);

		return removeLabelURL.toString();
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(getCategoryDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "categories"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(getStatusDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "status"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "order-by"));
			}
		).build();
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		String category = getCategory();
		String state = getState();

		return LabelItemListBuilder.add(
			() -> !category.equals("all-categories"),
			labelItem -> {
				PortletURL removeLabelURL = getPortletURL();

				removeLabelURL.setParameter("category", (String)null);

				labelItem.putData("removeLabelURL", removeLabelURL.toString());

				labelItem.setCloseable(true);

				String label = String.format(
					"%s: %s", LanguageUtil.get(httpServletRequest, "category"),
					LanguageUtil.get(httpServletRequest, category));

				labelItem.setLabel(label);
			}
		).add(
			() -> !state.equals("all-statuses"),
			labelItem -> {
				PortletURL removeLabelURL = getPortletURL();

				removeLabelURL.setParameter("state", (String)null);

				labelItem.putData("removeLabelURL", removeLabelURL.toString());

				labelItem.setCloseable(true);

				String label = String.format(
					"%s: %s", LanguageUtil.get(httpServletRequest, "state"),
					LanguageUtil.get(httpServletRequest, state));

				labelItem.setLabel(label);
			}
		).build();
	}

	@Override
	public int getItemsTotal() {
		return _searchContainer.getTotal();
	}

	@Override
	public PortletURL getPortletURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("category", getCategory());
		portletURL.setParameter("state", getState());
		portletURL.setParameter("orderByType", getOrderByType());

		if (_searchContainer != null) {
			portletURL.setParameter(
				_searchContainer.getCurParam(),
				String.valueOf(_searchContainer.getCur()));
			portletURL.setParameter(
				_searchContainer.getDeltaParam(),
				String.valueOf(_searchContainer.getDelta()));
		}

		return portletURL;
	}

	@Override
	public SearchContainer<Object> getSearchContainer() {
		return _searchContainer;
	}

	private SearchContainer<Object> _createSearchContainer(
		LiferayPortletRequest liferayPortletRequest) {

		SearchContainer<Object> searchContainer = new SearchContainer(
			liferayPortletRequest, getPortletURL(), null, "no-apps-were-found");

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByType(getOrderByType());

		String category = getCategory();

		if (category.equals("all-categories")) {
			category = StringPool.BLANK;
		}

		List<AppDisplay> appDisplays = AppDisplayFactoryUtil.getAppDisplays(
			BundleManagerUtil.getBundles(), category,
			BundleStateConstants.getState(getState()),
			liferayPortletRequest.getLocale());

		appDisplays = ListUtil.sort(
			appDisplays, new AppDisplayComparator(getOrderByType()));

		int end = searchContainer.getEnd();

		if (end > appDisplays.size()) {
			end = appDisplays.size();
		}

		List<Object> results = new ArrayList<>(appDisplays);

		searchContainer.setResults(
			results.subList(searchContainer.getStart(), end));

		searchContainer.setTotal(appDisplays.size());

		return searchContainer;
	}

	private final SearchContainer<Object> _searchContainer;

}