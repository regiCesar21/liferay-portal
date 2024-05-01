/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.graphql.schema.test;

import com.liferay.osb.asah.backend.OSBAsahBackendSpringTestContext;
import com.liferay.osb.asah.backend.dog.PagePathDog;
import com.liferay.osb.asah.backend.dog.helper.SearchQueryContext;
import com.liferay.osb.asah.backend.dto.SegmentPageViewsDTO;
import com.liferay.osb.asah.backend.graphql.schema.SegmentPageViewsDataFetcher;
import com.liferay.osb.asah.backend.model.AdjacentPageViewsMetric;
import com.liferay.osb.asah.common.model.TimeRange;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import graphql.schema.DataFetchingEnvironment;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * @author Ivica Cardic
 */
public class SegmentPageViewsDataFetcherTest
	implements OSBAsahBackendSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@Test
	public void testGet() {
		Set<AdjacentPageViewsMetric> adjacentPageViewsMetrics = new HashSet<>();

		adjacentPageViewsMetrics.add(
			new AdjacentPageViewsMetric(
				"direct", Boolean.TRUE, Boolean.TRUE, "direct",
				BigDecimal.valueOf(5)));
		adjacentPageViewsMetrics.add(
			new AdjacentPageViewsMetric(
				"others", Boolean.TRUE, Boolean.TRUE, "others",
				BigDecimal.valueOf(7)));
		adjacentPageViewsMetrics.add(
			new AdjacentPageViewsMetric(
				"others", Boolean.TRUE, Boolean.FALSE, "others",
				BigDecimal.valueOf(90)));
		adjacentPageViewsMetrics.add(
			new AdjacentPageViewsMetric(
				"url-1", Boolean.FALSE, Boolean.TRUE, "url 1",
				BigDecimal.valueOf(10)));
		adjacentPageViewsMetrics.add(
			new AdjacentPageViewsMetric(
				"url-2", Boolean.FALSE, Boolean.TRUE, "url 2",
				BigDecimal.valueOf(100)));
		adjacentPageViewsMetrics.add(
			new AdjacentPageViewsMetric(
				"url-3", Boolean.FALSE, Boolean.TRUE, "url 3",
				BigDecimal.valueOf(1000)));
		adjacentPageViewsMetrics.add(
			new AdjacentPageViewsMetric(
				"url-4", Boolean.FALSE, Boolean.FALSE, "url 4",
				BigDecimal.valueOf(100)));
		adjacentPageViewsMetrics.add(
			new AdjacentPageViewsMetric(
				"url-5", Boolean.FALSE, Boolean.FALSE, "url 5",
				BigDecimal.valueOf(300)));
		adjacentPageViewsMetrics.add(
			new AdjacentPageViewsMetric(
				"url-6", Boolean.FALSE, Boolean.FALSE, "url 6",
				BigDecimal.valueOf(200)));

		Mockito.when(
			_dataFetchingEnvironment.getArgument(Mockito.eq("segmentIds"))
		).thenReturn(
			Arrays.asList("1", "2")
		);

		Mockito.when(
			_pagePathDog.getAdjacentPagesViewsMetric(
				ArgumentMatchers.any(), ArgumentMatchers.any(),
				ArgumentMatchers.eq(1L), ArgumentMatchers.any(),
				ArgumentMatchers.any())
		).thenReturn(
			adjacentPageViewsMetrics
		);

		Mockito.when(
			_pagePathDog.getAdjacentPagesViewsMetric(
				ArgumentMatchers.any(), ArgumentMatchers.any(),
				ArgumentMatchers.eq(2L), ArgumentMatchers.any(),
				ArgumentMatchers.any())
		).thenReturn(
			Collections.emptySet()
		);

		List<SegmentPageViewsDTO> segmentPageViewsDTOs =
			_segmentPageViewsDataFetcher.get(
				_dataFetchingEnvironment,
				new SearchQueryContext() {
					{
						setCanonicalUrl("http://www.liferay.com");
						setChannelId(1L);
						setTimeRange(TimeRange.LAST_30_DAYS);
						setTitle("Liferay");
					}
				});

		long[] expectedViews = {1122, 0};

		for (int i = 0; i < segmentPageViewsDTOs.size(); i++) {
			SegmentPageViewsDTO segmentPageViewsDTO = segmentPageViewsDTOs.get(
				i);

			Assertions.assertEquals(
				expectedViews[i], segmentPageViewsDTO.getViews());
		}
	}

	@Mock
	private DataFetchingEnvironment _dataFetchingEnvironment;

	@MockBean
	private PagePathDog _pagePathDog;

	@Autowired
	private SegmentPageViewsDataFetcher _segmentPageViewsDataFetcher;

}