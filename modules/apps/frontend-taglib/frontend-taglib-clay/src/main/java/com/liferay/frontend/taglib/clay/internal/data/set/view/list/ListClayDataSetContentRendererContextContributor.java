/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.internal.data.set.view.list;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetContentRendererContextContributor;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.constants.ClayDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.view.list.BaseListClayDataSetDisplayView;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = "clay.data.set.content.renderer.name=" + ClayDataSetConstants.LIST,
	service = ClayDataSetContentRendererContextContributor.class
)
public class ListClayDataSetContentRendererContextContributor
	implements ClayDataSetContentRendererContextContributor {

	@Override
	public Map<String, Object> getContentRendererContext(
		ClayDataSetDisplayView clayDataSetDisplayView, Locale locale) {

		if (clayDataSetDisplayView instanceof BaseListClayDataSetDisplayView) {
			return _serialize(
				(BaseListClayDataSetDisplayView)clayDataSetDisplayView);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseListClayDataSetDisplayView baseListClayDataSetDisplayView) {

		return HashMapBuilder.<String, Object>put(
			"schema",
			HashMapBuilder.<String, Object>put(
				"description", baseListClayDataSetDisplayView.getDescription()
			).put(
				"image", baseListClayDataSetDisplayView.getImage()
			).put(
				"sticker", baseListClayDataSetDisplayView.getSticker()
			).put(
				"symbol", baseListClayDataSetDisplayView.getSymbol()
			).put(
				"title",
				() -> {
					String title = baseListClayDataSetDisplayView.getTitle();

					if (title.contains(StringPool.PERIOD)) {
						return StringUtil.split(title, StringPool.PERIOD);
					}

					return title;
				}
			).build()
		).build();
	}

}