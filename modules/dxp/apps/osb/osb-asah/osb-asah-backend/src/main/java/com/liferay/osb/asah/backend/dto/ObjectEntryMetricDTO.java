/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dto;

import com.liferay.osb.asah.backend.model.Metric;
import com.liferay.osb.asah.backend.model.ObjectEntryMetric;
import com.liferay.osb.asah.common.model.MetricType;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Rachael Koestartyo
 */
public class ObjectEntryMetricDTO {

	public ObjectEntryMetricDTO(
		ObjectEntryMetric objectEntryMetric, Set<MetricType> metricTypes) {

		_assetId = objectEntryMetric.getAssetId();
		_assetTitle = objectEntryMetric.getAssetTitle();
		_assetType = objectEntryMetric.getAssetType();
		_dataSourceId = objectEntryMetric.getDataSourceId();
		_defaultMetric = objectEntryMetric.getDefaultMetric();
		_externalReferenceCode = objectEntryMetric.getExternalReferenceCode();

		for (MetricType metricType : metricTypes) {
			for (Metric metric : objectEntryMetric.getAvailableMetrics()) {
				if (metricType.equals(metric.getMetricType())) {
					_selectedMetrics.add(metric);
				}
			}
		}

		_urls = objectEntryMetric.getURLs();
	}

	public String getAssetId() {
		return _assetId;
	}

	public Set<ObjectEntryMetricDTO> getAssetMetricDTOs() {
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

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
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

	public void setAssetMetricDTOs(Set<ObjectEntryMetricDTO> assetMetricDTOs) {
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

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public void setSelectedMetrics(Set<Metric> selectedMetrics) {
		_selectedMetrics = selectedMetrics;
	}

	public void setUrls(List<String> urls) {
		_urls = urls;
	}

	private String _assetId;
	private Set<ObjectEntryMetricDTO> _assetMetricDTOs = new LinkedHashSet<>();
	private String _assetTitle;
	private String _assetType;
	private String _dataSourceId;
	private Metric _defaultMetric;
	private String _externalReferenceCode;
	private Set<Metric> _selectedMetrics = new LinkedHashSet<>();
	private List<String> _urls;

}