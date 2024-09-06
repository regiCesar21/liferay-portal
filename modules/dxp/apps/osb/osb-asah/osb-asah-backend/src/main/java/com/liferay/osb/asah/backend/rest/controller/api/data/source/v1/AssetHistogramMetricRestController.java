/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.api.data.source.v1;

import com.liferay.osb.asah.backend.dog.HistogramDog;
import com.liferay.osb.asah.backend.dog.helper.SearchQueryContext;
import com.liferay.osb.asah.backend.dto.AssetHistogramMetricDTO;
import com.liferay.osb.asah.backend.model.AssetType;
import com.liferay.osb.asah.backend.model.BlogMetricType;
import com.liferay.osb.asah.backend.model.DocumentLibraryMetricType;
import com.liferay.osb.asah.backend.model.IdentityType;
import com.liferay.osb.asah.backend.model.JournalMetricType;
import com.liferay.osb.asah.common.model.TimeRange;
import com.liferay.osb.asah.common.spring.http.exception.OSBAsahException;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rachael Koestartyo
 */
@RequestMapping("/api/1.0/asset-histogram-metric/{assetType}")
@RestController(
	"com.liferay.osb.asah.backend.rest.controller.api.data.source.v1.AssetHistogramMetricRestController"
)
public class AssetHistogramMetricRestController {

	@GetMapping
	public AssetHistogramMetricDTO getAssetHistogramMetricDTO(
		@RequestParam String assetId,
		@PathVariable("assetType") String assetTypeString,
		@RequestParam Set<Long> channelIds, @RequestParam String identityType,
		@RequestParam(defaultValue = "30") int rangeKey) {

		AssetType assetType = AssetType.of(assetTypeString);

		SearchQueryContext searchQueryContext = new SearchQueryContext(
			assetType);

		searchQueryContext.setAssetId(assetId);
		searchQueryContext.setInterval("D");
		searchQueryContext.setTimeRange(TimeRange.of(rangeKey));

		Set<AssetHistogramMetricDTO> assetHistogramMetricDTOs =
			new LinkedHashSet<>();

		if (assetType == AssetType.BLOG) {
			assetHistogramMetricDTOs.add(
				new AssetHistogramMetricDTO(
					_histogramDog.getHistogramMetricBag(
						channelIds, IdentityType.valueOf(identityType),
						BlogMetricType.COMMENTS, searchQueryContext),
					BlogMetricType.COMMENTS.getName()));
			assetHistogramMetricDTOs.add(
				new AssetHistogramMetricDTO(
					_histogramDog.getHistogramMetricBag(
						channelIds, IdentityType.valueOf(identityType),
						BlogMetricType.VIEWS, searchQueryContext),
					BlogMetricType.VIEWS.getName()));
		}
		else if (assetType == AssetType.DOCUMENT) {
			assetHistogramMetricDTOs.add(
				new AssetHistogramMetricDTO(
					_histogramDog.getHistogramMetricBag(
						channelIds, IdentityType.valueOf(identityType),
						DocumentLibraryMetricType.COMMENTS, searchQueryContext),
					DocumentLibraryMetricType.COMMENTS.getName()));
			assetHistogramMetricDTOs.add(
				new AssetHistogramMetricDTO(
					_histogramDog.getHistogramMetricBag(
						channelIds, IdentityType.valueOf(identityType),
						DocumentLibraryMetricType.DOWNLOADS,
						searchQueryContext),
					DocumentLibraryMetricType.DOWNLOADS.getName()));
			assetHistogramMetricDTOs.add(
				new AssetHistogramMetricDTO(
					_histogramDog.getHistogramMetricBag(
						channelIds, IdentityType.valueOf(identityType),
						DocumentLibraryMetricType.PREVIEWS, searchQueryContext),
					DocumentLibraryMetricType.PREVIEWS.getName()));
		}
		else if (assetType == AssetType.JOURNAL) {
			assetHistogramMetricDTOs.add(
				new AssetHistogramMetricDTO(
					_histogramDog.getHistogramMetricBag(
						channelIds, IdentityType.valueOf(identityType),
						JournalMetricType.VIEWS, searchQueryContext),
					JournalMetricType.VIEWS.getName()));
		}
		else {
			throw new OSBAsahException(
				HttpStatus.BAD_REQUEST,
				"Unsupported asset type: " + assetTypeString);
		}

		return new AssetHistogramMetricDTO(assetHistogramMetricDTOs);
	}

	@Autowired
	private HistogramDog _histogramDog;

}