/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.data.set.filter;

import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Marco Leo
 */
public abstract class BaseRadioClayDataSetFilter implements ClayDataSetFilter {

	public String getOperator() {
		return "eq";
	}

	public abstract List<RadioClayDataSetFilterItem>
		getRadioClayDataSetFilterItems(Locale locale);

	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	@Override
	public String getType() {
		return "radio";
	}

}