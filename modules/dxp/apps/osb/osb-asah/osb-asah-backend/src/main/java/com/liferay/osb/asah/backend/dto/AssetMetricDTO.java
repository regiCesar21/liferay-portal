/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dto;

import com.liferay.osb.asah.backend.model.AssetMetric;
import com.liferay.osb.asah.backend.model.Metric;
import com.liferay.osb.asah.common.model.MetricType;
import com.liferay.osb.asah.common.util.SetUtil;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Marcos Martins
 */
public class AssetMetricDTO {

	public AssetMetricDTO(
		AssetMetric assetMetric, Set<MetricType> metricTypes) {

		_assetId = assetMetric.getAssetId();
		_assetTitle = assetMetric.getAssetTitle();
		_assetType = assetMetric.getAssetType();
		_dataSourceId = assetMetric.getDataSourceId();
		_defaultMetric = assetMetric.getDefaultMetric();

		for (MetricType metricType : metricTypes) {
			for (Metric metric : assetMetric.getAvailableMetrics()) {
				if (metricType.equals(metric.getMetricType())) {
					_selectedMetrics.add(metric);
				}
			}
		}

		_urls = assetMetric.getURLs();
	}

	public AssetMetricDTO(
		Set<AssetMetric> assetMetrics, Set<MetricType> selectedMetricsType) {

		SetUtil.map(
			assetMetrics,
			assetMetric -> new AssetMetricDTO(
				assetMetric, selectedMetricsType));
	}

	public String getAssetId() {
		return _assetId;
	}

	public Set<AssetMetricDTO> getAssetMetricDTOs() {
		return _assetMetricDTOs;
	}

	public String getAssetTitle() {
		return _assetTitle;
	}

	public String getAssetType() {
		return _assetType;
	}

	public String getDataSourceId() {
		return _dataSourceId;
	}

	public Metric getDefaultMetric() {
		return _defaultMetric;
	}

	public Set<Metric> getSelectedMetrics() {
		return _selectedMetrics;
	}

	public List<String> getUrls() {
		return _urls;
	}

	public void setAssetId(String assetId) {
		_assetId = assetId;
	}

	public void setAssetMetricDTOs(Set<AssetMetricDTO> assetMetricDTOs) {
		_assetMetricDTOs = assetMetricDTOs;
	}

	public void setAssetTitle(String assetTitle) {
		_assetTitle = assetTitle;
	}

	public void setAssetType(String assetType) {
		_assetType = assetType;
	}

	public void setDataSourceId(String dataSourceId) {
		_dataSourceId = dataSourceId;
	}

	public void setDefaultMetric(Metric defaultMetric) {
		_defaultMetric = defaultMetric;
	}

	public void setSelectedMetrics(Set<Metric> selectedMetrics) {
		_selectedMetrics = selectedMetrics;
	}

	public void setUrls(List<String> urls) {
		_urls = urls;
	}

	private String _assetId;
	private Set<AssetMetricDTO> _assetMetricDTOs = new LinkedHashSet<>();
	private String _assetTitle;
	private String _assetType;
	private String _dataSourceId;
	private Metric _defaultMetric;
	private Set<Metric> _selectedMetrics = new LinkedHashSet<>();
	private List<String> _urls;

}