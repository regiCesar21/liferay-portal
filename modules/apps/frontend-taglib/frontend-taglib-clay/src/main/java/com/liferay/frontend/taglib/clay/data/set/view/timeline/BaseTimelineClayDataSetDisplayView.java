/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.data.set.view.timeline;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.constants.ClayDataSetConstants;

/**
 * @author Marco Leo
 */
public abstract class BaseTimelineClayDataSetDisplayView
	implements ClayDataSetDisplayView {

	@Override
	public String getContentRenderer() {
		return ClayDataSetConstants.TIMELINE;
	}

	public abstract String getDate();

	public abstract String getDescription();

	@Override
	public String getLabel() {
		return ClayDataSetConstants.TIMELINE;
	}

	@Override
	public String getName() {
		return ClayDataSetConstants.TIMELINE;
	}

	@Override
	public String getThumbnail() {
		return ClayDataSetConstants.TIMELINE;
	}

	public abstract String getTitle();

}