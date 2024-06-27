/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dog;

import com.liferay.osb.asah.backend.dog.helper.SearchQueryContext;
import com.liferay.osb.asah.backend.model.AssetType;
import com.liferay.osb.asah.backend.model.Individual;
import com.liferay.osb.asah.backend.repository.AssetMetricRepository;
import com.liferay.osb.asah.common.model.MetricType;
import com.liferay.osb.asah.common.model.ReportIndividual;
import com.liferay.osb.asah.common.model.ResultBag;
import com.liferay.osb.asah.common.repository.ReportIndividualRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author André Miranda
 * @author Marcellus Tavares
 */
@Component
public class ReportIndividualDog {

	@Autowired
	public ReportIndividualDog(
		List<AssetMetricRepository> assetMetricRepositories,
		ReportIndividualRepository reportIndividualRepository) {

		assetMetricRepositories.forEach(
			assetMetricAssetMetricRepository -> _assetMetricRepositoryMap.put(
				assetMetricAssetMetricRepository.getAssetType(),
				assetMetricAssetMetricRepository));

		_reportIndividualRepository = reportIndividualRepository;
	}

	public ReportIndividual fetchReportIndividual(String id) {
		Optional<ReportIndividual> reportIndividualOptional =
			_reportIndividualRepository.findReportIndividualById(id);

		return reportIndividualOptional.orElse(null);
	}

	public ResultBag<Individual> getIndividualResultBag(
		String keywords, MetricType metricType,
		SearchQueryContext searchQueryContext, int size, int start) {

		AssetMetricRepository assetMetricRepository =
			_assetMetricRepositoryMap.get(searchQueryContext.getAssetType());

		if (assetMetricRepository == null) {
			throw new IllegalArgumentException(
				"There is no asset metric repository for asset type " +
					searchQueryContext.getAssetType());
		}

		Sort sort = Sort.by(
			Sort.Order.asc("firstName"), Sort.Order.asc("lastName"));

		return new ResultBag<>(
			assetMetricRepository.getKnownIndividuals(
				searchQueryContext.getAssetId(), searchQueryContext.getTitle(),
				searchQueryContext.getChannelIdAsLong(), metricType,
				PageRequest.of(start / size, size, sort), keywords,
				searchQueryContext.getTimeRange()),
			assetMetricRepository.getKnownIndividualsCount(
				searchQueryContext.getAssetId(), searchQueryContext.getTitle(),
				searchQueryContext.getChannelIdAsLong(), metricType, keywords,
				searchQueryContext.getTimeRange()));
	}

	public Page<ReportIndividual> searchReportIndividualPage(
		@Nullable Long channelId, int page, @Nullable String query,
		@Nullable Long segmentId, int size) {

		return PageableExecutionUtils.getPage(
			_reportIndividualRepository.searchReportIndividuals(
				channelId, PageRequest.of(page, size), query, segmentId),
			PageRequest.of(page, size),
			() -> _reportIndividualRepository.countReportIndividuals(
				channelId, query, segmentId));
	}

	private final Map<AssetType, AssetMetricRepository>
		_assetMetricRepositoryMap = new HashMap<>();
	private final ReportIndividualRepository _reportIndividualRepository;

}