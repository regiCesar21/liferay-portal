/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.internal.data.set.view.cards;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetContentRendererContextContributor;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.constants.ClayDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.view.cards.BaseCardsClayDataSetDisplayView;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	property = "clay.data.set.content.renderer.name=" + ClayDataSetConstants.CARDS,
	service = ClayDataSetContentRendererContextContributor.class
)
public class CardsClayDataSetContentRendererContextContributor
	implements ClayDataSetContentRendererContextContributor {

	@Override
	public Map<String, Object> getContentRendererContext(
		ClayDataSetDisplayView clayDataSetDisplayView, Locale locale) {

		if (clayDataSetDisplayView instanceof BaseCardsClayDataSetDisplayView) {
			return _serialize(
				(BaseCardsClayDataSetDisplayView)clayDataSetDisplayView);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseCardsClayDataSetDisplayView clayCardsDataSetDisplayView) {

		return HashMapBuilder.<String, Object>put(
			"schema",
			HashMapBuilder.<String, Object>put(
				"description", clayCardsDataSetDisplayView.getDescription()
			).put(
				"href", clayCardsDataSetDisplayView.getLink()
			).put(
				"image", clayCardsDataSetDisplayView.getImage()
			).put(
				"sticker", clayCardsDataSetDisplayView.getSticker()
			).put(
				"symbol", clayCardsDataSetDisplayView.getSymbol()
			).put(
				"title", clayCardsDataSetDisplayView.getTitle()
			).build()
		).build();
	}

	@Reference
	private JSONFactory _jsonFactory;

}