/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.internal.data.set.view.timeline;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetContentRendererContextContributor;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.constants.ClayDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.view.timeline.BaseTimelineClayDataSetDisplayView;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = "clay.data.set.content.renderer.name=" + ClayDataSetConstants.TIMELINE,
	service = ClayDataSetContentRendererContextContributor.class
)
public class TimelineClayDataSetContentRendererContextContributor
	implements ClayDataSetContentRendererContextContributor {

	@Override
	public Map<String, Object> getContentRendererContext(
		ClayDataSetDisplayView clayDataSetDisplayView, Locale locale) {

		if (clayDataSetDisplayView instanceof
				BaseTimelineClayDataSetDisplayView) {

			return _serialize(
				(BaseTimelineClayDataSetDisplayView)clayDataSetDisplayView);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseTimelineClayDataSetDisplayView baseTimelineClayDataSetDisplayView) {

		return HashMapBuilder.<String, Object>put(
			"schema",
			JSONUtil.put(
				"date", baseTimelineClayDataSetDisplayView.getDate()
			).put(
				"description",
				baseTimelineClayDataSetDisplayView.getDescription()
			).put(
				"title", baseTimelineClayDataSetDisplayView.getTitle()
			)
		).build();
	}

}