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

/**
 * @author Rachael Koestartyo
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetHistogramMetricDTO {

	public AssetHistogramMetricDTO(HistogramMetricBag histogramMetricBag) {
		List<HistogramMetric> histogramMetrics =
			histogramMetricBag.getMetrics();

		Stream<HistogramMetric> stream = histogramMetrics.stream();

		stream.forEach(
			histogramMetric -> {
				_histogramMetricDTOs.add(
					new HistogramMetricDTO(histogramMetric));

				_totalValue += histogramMetric.getValue();
			});

		_total = histogramMetricBag.getTotal();
	}

	public AssetHistogramMetricDTO(
		HistogramMetricBag histogramMetricBag, String metricName) {

		this(histogramMetricBag);

		_metricName = metricName;
	}

	public AssetHistogramMetricDTO(
		Set<AssetHistogramMetricDTO> assetHistogramMetricDTOs) {

		_assetHistogramMetricDTOs = assetHistogramMetricDTOs;
	}

	@JsonProperty("histograms")
	public Set<AssetHistogramMetricDTO> getAssetHistogramMetricDTOs() {
		return _assetHistogramMetricDTOs;
	}

	@JsonProperty("metrics")
	public Set<HistogramMetricDTO> getHistogramMetricDTOs() {
		return _histogramMetricDTOs;
	}

	public String getMetricName() {
		return _metricName;
	}

	public Long getTotal() {
		return _total;
	}

	public double getTotalValue() {
		return _totalValue;
	}

	public void setAssetHistogramMetricDTOs(
		Set<AssetHistogramMetricDTO> assetHistogramMetricDTOs) {

		_assetHistogramMetricDTOs = assetHistogramMetricDTOs;
	}

	public void setHistogramMetricDTOs(
		Set<HistogramMetricDTO> histogramMetricDTOs) {

		_histogramMetricDTOs = histogramMetricDTOs;
	}

	public void setMetricName(String metricName) {
		_metricName = metricName;
	}

	public void setTotal(Long total) {
		_total = total;
	}

	public void setTotalValue(double totalValue) {
		_totalValue = totalValue;
	}

	private Set<AssetHistogramMetricDTO> _assetHistogramMetricDTOs =
		new LinkedHashSet<>();
	private Set<HistogramMetricDTO> _histogramMetricDTOs =
		new LinkedHashSet<>();
	private String _metricName;
	private Long _total;
	private double _totalValue;

}