/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.data.set.filter;

import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Marco Leo
 */
public abstract class BaseAutocompleteClayDataSetFilter
	implements ClayDataSetFilter {

	public abstract String getAPIURL();

	public abstract String getItemKey();

	public abstract String getItemLabel();

	public String getPlaceholder() {
		return "search";
	}

	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	@Override
	public String getType() {
		return "autocomplete";
	}

	public boolean isMultipleSelection() {
		return true;
	}

}