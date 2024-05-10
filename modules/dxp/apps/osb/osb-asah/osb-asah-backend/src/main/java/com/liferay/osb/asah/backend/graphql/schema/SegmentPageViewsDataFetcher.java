/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.graphql.schema;

import com.liferay.osb.asah.backend.dog.PagePathDog;
import com.liferay.osb.asah.backend.dog.helper.SearchQueryContext;
import com.liferay.osb.asah.backend.dto.SegmentPageViewsDTO;
import com.liferay.osb.asah.backend.graphql.annotation.GraphQLTypeWiring;
import com.liferay.osb.asah.backend.model.AdjacentPageViewsMetric;

import graphql.schema.DataFetchingEnvironment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Ivica Cardic
 */
@Component
@GraphQLTypeWiring(fieldName = "segmentPageViews", typeName = "QueryType")
public class SegmentPageViewsDataFetcher
	extends BaseDataFetcher<List<SegmentPageViewsDTO>> {

	@Override
	public List<SegmentPageViewsDTO> get(
		DataFetchingEnvironment dataFetchingEnvironment,
		SearchQueryContext searchQueryContext) {

		List<SegmentPageViewsDTO> segmentPageViewsDTOs = new ArrayList<>();

		List<Long> segmentIds = _getSegmentIds(dataFetchingEnvironment);

		Set<AdjacentPageViewsMetric> adjacentPagesViewsMetrics =
			_pagePathDog.getPreviousAdjacentPagesViewsMetric(
				searchQueryContext.getCanonicalUrl(),
				searchQueryContext.getChannelIdAsLong(), segmentIds,
				searchQueryContext.getTimeRange(),
				searchQueryContext.getTitle());

		for (Long segmentId : segmentIds) {
			Set<AdjacentPageViewsMetric> segmentAdjacentPagesViewsMetrics =
				adjacentPagesViewsMetrics.stream(
				).filter(
					adjacentPageViewsMetric -> Objects.equals(
						adjacentPageViewsMetric.getSegmentId(), segmentId)
				).collect(
					Collectors.toSet()
				);

			segmentPageViewsDTOs.add(
				new SegmentPageViewsDTO(
					String.valueOf(segmentId),
					_getViews(segmentAdjacentPagesViewsMetrics)));
		}

		return segmentPageViewsDTOs;
	}

	private List<Long> _getSegmentIds(
		DataFetchingEnvironment dataFetchingEnvironment) {

		List<String> segmentIds = dataFetchingEnvironment.getArgument(
			"segmentIds");

		if (!CollectionUtils.isEmpty(segmentIds)) {
			return segmentIds.stream(
			).map(
				Long::valueOf
			).collect(
				Collectors.toList()
			);
		}

		return Collections.emptyList();
	}

	private long _getViews(
		Set<AdjacentPageViewsMetric> adjacentPagesViewsMetrics) {

		long views = 0;

		for (AdjacentPageViewsMetric adjacentPageViewsMetric :
				adjacentPagesViewsMetrics) {

			if (adjacentPageViewsMetric.isPrevious()) {
				views += adjacentPageViewsMetric.getViewsAsLong();
			}
		}

		return views;
	}

	@Autowired
	private PagePathDog _pagePathDog;

}