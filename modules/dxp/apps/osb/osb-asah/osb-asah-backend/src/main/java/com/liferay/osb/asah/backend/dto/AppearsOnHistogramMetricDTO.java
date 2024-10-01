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
			histogramMetric -> {
				_histogramMetricDTOs.add(
					new HistogramMetricDTO(histogramMetric));

				_totalValue += histogramMetric.getValue();
			});

		if (_totalValue == 0) {
			_histogramMetricDTOs.clear();
		}

		_total = histogramMetricBag.getTotal();
	}

	public AppearsOnHistogramMetricDTO(
		HistogramMetricBag histogramMetricBag, String key) {

		this(histogramMetricBag);

		String[] keyParts = StringUtils.split(key, "#");

		_canonicalUrl = keyParts[0];
		_pageTitle = keyParts[1];
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

	public double getTotalValue() {
		return _totalValue;
	}

	public void setHistogramMetricDTOs(
		Set<HistogramMetricDTO> histogramMetricDTOs) {

		_histogramMetricDTOs = histogramMetricDTOs;
	}

	public void setTotal(Long total) {
		_total = total;
	}

	public void setTotalValue(double totalValue) {
		_totalValue = totalValue;
	}

	private String _canonicalUrl;
	private Set<HistogramMetricDTO> _histogramMetricDTOs;
	private String _pageTitle;
	private Long _total;
	private double _totalValue;

}