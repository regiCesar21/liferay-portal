/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.web.internal.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.web.internal.constants.RedirectPortletKeys;

import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Alejandro Tardín
 */
public class RedirectEntrySearch extends SearchContainer<RedirectEntry> {

	public RedirectEntrySearch(
		PortletRequest portletRequest, PortletResponse portletResponse,
		PortletURL iteratorURL, String searchContainerId) {

		super(portletRequest, iteratorURL, null, _EMPTY_RESULTS_MESSAGE);

		PortalPreferences preferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(portletRequest);

		String portletId = RedirectPortletKeys.REDIRECT;

		String orderByCol = ParamUtil.getString(portletRequest, "orderByCol");
		String orderByType = ParamUtil.getString(portletRequest, "orderByType");

		if (Validator.isNotNull(orderByCol)) {
			preferences.setValue(
				portletId, "redirect-entries-order-by-col", orderByCol);
		}
		else {
			orderByCol = preferences.getValue(
				portletId, "redirect-entries-order-by-col", "modified-date");
		}

		if (Validator.isNotNull(orderByType)) {
			preferences.setValue(
				portletId, "redirect-entries-order-by-type", orderByType);
		}
		else {
			orderByType = preferences.getValue(
				portletId, "redirect-entries-order-by-type", "asc");
		}

		setId(searchContainerId);
		setOrderableHeaders(_orderableHeaders);
		setOrderByCol(orderByCol);
		setOrderByType(orderByType);
		setRowChecker(new EmptyOnClickRowChecker(portletResponse));
	}

	private static final String _EMPTY_RESULTS_MESSAGE =
		"no-redirects-were-found";

	private static final Map<String, String> _orderableHeaders =
		HashMapBuilder.put(
			"destinationURL", "sourceURL"
		).build();

}