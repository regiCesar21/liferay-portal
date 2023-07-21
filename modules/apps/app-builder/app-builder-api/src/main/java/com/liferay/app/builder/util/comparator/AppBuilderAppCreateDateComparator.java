/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.util.comparator;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Gabriel Albuquerque
 */
public class AppBuilderAppCreateDateComparator
	extends OrderByComparator<AppBuilderApp> {

	public static final String ORDER_BY_ASC = "AppBuilderApp.createDate ASC";

	public static final String ORDER_BY_DESC = "AppBuilderApp.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public AppBuilderAppCreateDateComparator() {
		this(false);
	}

	public AppBuilderAppCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		AppBuilderApp appBuilderApp1, AppBuilderApp appBuilderApp2) {

		int value = DateUtil.compareTo(
			appBuilderApp1.getCreateDate(), appBuilderApp2.getCreateDate());

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}