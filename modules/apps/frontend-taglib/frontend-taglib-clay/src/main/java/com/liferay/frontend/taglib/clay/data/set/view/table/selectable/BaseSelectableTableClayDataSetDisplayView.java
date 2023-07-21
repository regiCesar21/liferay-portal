/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.data.set.view.table.selectable;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.constants.ClayDataSetConstants;
import com.liferay.petra.string.StringPool;

import java.util.Locale;

/**
 * @author Alessio Antonio Rendina
 */
public abstract class BaseSelectableTableClayDataSetDisplayView
	implements ClayDataSetDisplayView {

	@Override
	public String getContentRenderer() {
		return ClayDataSetConstants.SELECTABLE_TABLE;
	}

	public abstract String getFirstColumnLabel(Locale locale);

	public abstract String getFirstColumnName();

	@Override
	public String getLabel() {
		return ClayDataSetConstants.SELECTABLE_TABLE;
	}

	@Override
	public String getName() {
		return ClayDataSetConstants.SELECTABLE_TABLE;
	}

	@Override
	public String getThumbnail() {
		return StringPool.BLANK;
	}

}