/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.data.set.view.list;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.constants.ClayDataSetConstants;
import com.liferay.petra.string.StringPool;

/**
 * @author Alessio Antonio Rendina
 */
public abstract class BaseListClayDataSetDisplayView
	implements ClayDataSetDisplayView {

	@Override
	public String getContentRenderer() {
		return ClayDataSetConstants.LIST;
	}

	public abstract String getDescription();

	public String getImage() {
		return StringPool.BLANK;
	}

	@Override
	public String getLabel() {
		return ClayDataSetConstants.LIST;
	}

	@Override
	public String getName() {
		return ClayDataSetConstants.LIST;
	}

	public String getSticker() {
		return StringPool.BLANK;
	}

	public String getSymbol() {
		return StringPool.BLANK;
	}

	@Override
	public String getThumbnail() {
		return ClayDataSetConstants.LIST;
	}

	public abstract String getTitle();

}