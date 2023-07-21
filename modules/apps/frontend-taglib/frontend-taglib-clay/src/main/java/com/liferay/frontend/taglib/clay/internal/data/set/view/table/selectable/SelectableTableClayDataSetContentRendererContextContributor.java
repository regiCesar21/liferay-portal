/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.internal.data.set.view.table.selectable;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetContentRendererContextContributor;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.constants.ClayDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.view.table.selectable.BaseSelectableTableClayDataSetDisplayView;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "clay.data.set.content.renderer.name=" + ClayDataSetConstants.SELECTABLE_TABLE,
	service = ClayDataSetContentRendererContextContributor.class
)
public class SelectableTableClayDataSetContentRendererContextContributor
	implements ClayDataSetContentRendererContextContributor {

	@Override
	public Map<String, Object> getContentRendererContext(
		ClayDataSetDisplayView clayDataSetDisplayView, Locale locale) {

		if (clayDataSetDisplayView instanceof
				BaseSelectableTableClayDataSetDisplayView) {

			return _serialize(
				(BaseSelectableTableClayDataSetDisplayView)
					clayDataSetDisplayView,
				locale);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseSelectableTableClayDataSetDisplayView
			baseSelectableTableClayDataSetDisplayView,
		Locale locale) {

		return HashMapBuilder.<String, Object>put(
			"schema",
			JSONUtil.put(
				"firstColumnLabel",
				baseSelectableTableClayDataSetDisplayView.getFirstColumnLabel(
					locale)
			).put(
				"firstColumnName",
				baseSelectableTableClayDataSetDisplayView.getFirstColumnName()
			)
		).build();
	}

}