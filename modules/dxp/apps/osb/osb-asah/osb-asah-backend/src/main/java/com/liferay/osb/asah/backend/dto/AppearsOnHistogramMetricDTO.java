/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.osb.asah.backend.model.HistogramMetric;
import com.liferay.osb.asah.backend.model.HistogramMetricBag;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Marcos Martins
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppearsOnHistogramMetricDTO {

	public AppearsOnHistogramMetricDTO(HistogramMetricBag histogramMetricBag) {
		_histogramMetricDTOs = new LinkedHashSet<>();

		List<HistogramMetric> histogramMetrics =
			histogramMetricBag.getMetrics();

		Stream<HistogramMetric> stream = histogramMetrics.stream();

		stream.forEach(
			histogramMetric -> _histogramMetricDTOs.add(
				new HistogramMetricDTO(histogramMetric)));

		_total = histogramMetricBag.getTotal();
	}

	public AppearsOnHistogramMetricDTO(
		HistogramMetricBag histogramMetricBag, String key) {

		this(histogramMetricBag);

		String[] keyParts = StringUtils.split(key, "#");

		_canonicalUrl = keyParts[_CANONICAL_URL_KEY_PART_INDEX];

		_pageTitle = keyParts[_PAGE_TITLE_KEY_PART_INDEX];
	}

	public String getCanonicalUrl() {
		return _canonicalUrl;
	}

	@JsonProperty("metrics")
	public Set<HistogramMetricDTO> getHistogramMetricDTOs() {
		return _histogramMetricDTOs;
	}

	public String getPageTitle() {
		return _pageTitle;
	}

	public Long getTotal() {
		return _total;
	}

	public void setHistogramMetricDTOs(
		Set<HistogramMetricDTO> histogramMetricDTOs) {

		_histogramMetricDTOs = histogramMetricDTOs;
	}

	public void setTotal(Long total) {
		_total = total;
	}

	private static final int _CANONICAL_URL_KEY_PART_INDEX = 0;

	private static final int _PAGE_TITLE_KEY_PART_INDEX = 1;

	private String _canonicalUrl;
	private Set<HistogramMetricDTO> _histogramMetricDTOs;
	private String _pageTitle;
	private Long _total;

}