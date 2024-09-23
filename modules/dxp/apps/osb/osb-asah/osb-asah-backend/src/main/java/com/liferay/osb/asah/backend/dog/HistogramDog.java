/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dog;

import com.liferay.osb.asah.backend.dog.helper.MetricHelper;
import com.liferay.osb.asah.backend.dog.helper.SearchQueryContext;
import com.liferay.osb.asah.backend.model.AppearsOnHistogramMetric;
import com.liferay.osb.asah.backend.model.AssetType;
import com.liferay.osb.asah.backend.model.HistogramMetric;
import com.liferay.osb.asah.backend.model.HistogramMetricBag;
import com.liferay.osb.asah.backend.model.IdentityType;
import com.liferay.osb.asah.backend.model.Metric;
import com.liferay.osb.asah.backend.repository.AssetMetricRepository;
import com.liferay.osb.asah.common.date.dog.TimeZoneDog;
import com.liferay.osb.asah.common.model.Interval;
import com.liferay.osb.asah.common.model.MetricType;
import com.liferay.osb.asah.common.model.TimeRange;
import com.liferay.osb.asah.common.util.SetUtil;

import java.time.Clock;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Marcellus Tavares
 */
@Component
public class HistogramDog {

	@Autowired
	public HistogramDog(List<AssetMetricRepository> assetMetricRepositories) {
		assetMetricRepositories.forEach(
			assetMetricAssetMetricRepository -> _assetMetricRepositoryMap.put(
				assetMetricAssetMetricRepository.getAssetType(),
				assetMetricAssetMetricRepository));
	}

	public HistogramMetricBag getHistogramMetricBag(
		List<HistogramMetric> histogramMetrics, boolean includePrevious,
		Interval interval, MetricType metricType, TimeRange timeRange) {

		if (histogramMetrics.isEmpty()) {
			return new HistogramMetricBag();
		}

		HistogramMetricBag histogramMetricBag =
			_metricHelper.createHistogramMetricBag(
				Clock.system(_timeZoneDog.getZoneId()), includePrevious,
				interval, metricType, timeRange);

		Map<String, Metric> metrics = _getMetrics(histogramMetricBag);

		for (HistogramMetric histogramMetric : histogramMetrics) {
			Metric metric = metrics.get(histogramMetric.getKey());

			if (metric != null) {
				metric.setValue(histogramMetric.getValue());

				continue;
			}

			LocalDateTime previousBucketLocalDateTime = LocalDateTime.parse(
				histogramMetric.getKey());

			String bucketKey = null;

			if (timeRange == TimeRange.LAST_24_HOURS) {
				bucketKey = String.valueOf(
					previousBucketLocalDateTime.plusHours(24));
			}
			else {
				bucketKey = String.valueOf(
					previousBucketLocalDateTime.plusDays(
						timeRange.getDeltaDays()));
			}

			metric = metrics.get(bucketKey);

			if (metric != null) {
				metric.setPreviousValue(histogramMetric.getValue());
			}
		}

		return histogramMetricBag;
	}

	public HistogramMetricBag getHistogramMetricBag(
		MetricType metricType, SearchQueryContext searchQueryContext) {

		return getHistogramMetricBag(
			SetUtil.of(searchQueryContext.getChannelIdAsLong()),
			IdentityType.ALL, metricType, searchQueryContext);
	}

	public HistogramMetricBag getHistogramMetricBag(
		Set<Long> channelIds, IdentityType identityType, MetricType metricType,
		SearchQueryContext searchQueryContext) {

		AssetMetricRepository assetMetricRepository =
			_assetMetricRepositoryMap.get(searchQueryContext.getAssetType());

		if (assetMetricRepository == null) {
			throw new IllegalArgumentException(
				"There is no asset metric repository for asset type " +
					searchQueryContext.getAssetType());
		}

		String assetTitle = null;

		if (searchQueryContext.getAssetType() != AssetType.CUSTOM) {
			assetTitle = searchQueryContext.getTitle();
		}

		Interval interval = searchQueryContext.getInterval();

		TimeRange timeRange = searchQueryContext.getTimeRange();

		if ((timeRange == TimeRange.LAST_24_HOURS) ||
			(timeRange == TimeRange.YESTERDAY)) {

			interval = Interval.HOUR;
		}

		return getHistogramMetricBag(
			assetMetricRepository.getHistogramMetrics(
				searchQueryContext.getAssetId(), assetTitle, channelIds,
				identityType, true, interval, metricType, timeRange),
			searchQueryContext.isIncludePrevious(), interval, metricType,
			timeRange);
	}

	public Map<String, HistogramMetricBag> getTopAppearsOnHistogramMetricBag(
		Set<Long> channelIds, IdentityType identityType, MetricType metricType,
		SearchQueryContext searchQueryContext, int size) {

		AssetMetricRepository assetMetricRepository =
			_assetMetricRepositoryMap.get(searchQueryContext.getAssetType());

		if (assetMetricRepository == null) {
			throw new IllegalArgumentException(
				"There is no asset metric repository for asset type " +
					searchQueryContext.getAssetType());
		}

		String assetTitle = null;

		if (searchQueryContext.getAssetType() != AssetType.CUSTOM) {
			assetTitle = searchQueryContext.getTitle();
		}

		Interval interval = searchQueryContext.getInterval();

		TimeRange timeRange = searchQueryContext.getTimeRange();

		if ((timeRange == TimeRange.LAST_24_HOURS) ||
			(timeRange == TimeRange.YESTERDAY)) {

			interval = Interval.HOUR;
		}

		List<AppearsOnHistogramMetric> appearsOnHistogramMetrics =
			assetMetricRepository.getTopAppearsOnHistogramMetrics(
				searchQueryContext.getAssetId(), assetTitle, channelIds,
				identityType, interval, metricType, size, timeRange);

		Map<String, List<HistogramMetric>> histogramMetrics =
			new LinkedHashMap<>();

		for (AppearsOnHistogramMetric appearsOnHistogramMetric :
				appearsOnHistogramMetrics) {

			histogramMetrics.putIfAbsent(
				String.join(
					"#", appearsOnHistogramMetric.getCanonicalUrl(),
					appearsOnHistogramMetric.getPageTitle()),
				new ArrayList<>());

			Collections.addAll(
				histogramMetrics.get(
					String.join(
						"#", appearsOnHistogramMetric.getCanonicalUrl(),
						appearsOnHistogramMetric.getPageTitle())),
				appearsOnHistogramMetric);
		}

		Map<String, HistogramMetricBag> histogramMetricBags =
			new LinkedHashMap<>();

		for (Map.Entry<String, List<HistogramMetric>> entry :
				histogramMetrics.entrySet()) {

			histogramMetricBags.put(
				entry.getKey(),
				getHistogramMetricBag(
					entry.getValue(), searchQueryContext.isIncludePrevious(),
					interval, metricType, timeRange));
		}

		return histogramMetricBags;
	}

	private Map<String, Metric> _getMetrics(
		HistogramMetricBag histogramMetricBag) {

		List<HistogramMetric> histogramMetrics =
			histogramMetricBag.getMetrics();

		Stream<HistogramMetric> histogramMetricsStream =
			histogramMetrics.stream();

		return histogramMetricsStream.collect(
			Collectors.toMap(HistogramMetric::getKey, Function.identity()));
	}

	private final Map<AssetType, AssetMetricRepository>
		_assetMetricRepositoryMap = new HashMap<>();

	@Autowired
	private MetricHelper _metricHelper;

	@Autowired
	private TimeZoneDog _timeZoneDog;

}