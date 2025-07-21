/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.model;

import com.liferay.osb.asah.backend.model.util.MetricUtil;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Rachael Koestartyo
 */
public class ObjectEntryMetric implements AssetMetric {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ObjectEntryMetric)) {
			return false;
		}

		ObjectEntryMetric objectEntryMetric = (ObjectEntryMetric)obj;

		if (Objects.equals(_assetId, objectEntryMetric._assetId) &&
			Objects.equals(_assetMetrics, objectEntryMetric._assetMetrics) &&
			Objects.equals(_assetTitle, objectEntryMetric._assetTitle) &&
			Objects.equals(_canonicalUrls, objectEntryMetric._canonicalUrls) &&
			Objects.equals(_dataSourceId, objectEntryMetric._dataSourceId) &&
			Objects.equals(
				_downloadsMetric, objectEntryMetric._downloadsMetric) &&
			Objects.equals(
				_externalReferenceCode,
				objectEntryMetric._externalReferenceCode) &&
			Objects.equals(
				_impressionsMetric, objectEntryMetric._impressionsMetric) &&
			Objects.equals(_urls, objectEntryMetric._urls) &&
			Objects.equals(_viewsMetric, objectEntryMetric._viewsMetric)) {

			return true;
		}

		return false;
	}

	@Override
	public String getAssetId() {
		return _assetId;
	}

	@Override
	public List<AssetMetric> getAssetMetrics() {
		return _assetMetrics;
	}

	@Override
	public String getAssetTitle() {
		return _assetTitle;
	}

	@Override
	public String getAssetType() {
		return AssetType.OBJECT_ENTRY.getValue();
	}

	@Override
	public Set<Metric> getAvailableMetrics() {
		return MetricUtil.getAvailableMetrics(this);
	}

	@Override
	public List<String> getCanonicalUrls() {
		return _canonicalUrls;
	}

	@Override
	public String getDataSourceId() {
		return _dataSourceId;
	}

	@Override
	public Metric getDefaultMetric() {
		return _impressionsMetric;
	}

	public Metric getDownloadsMetric() {
		return _downloadsMetric;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public Metric getImpresssionsMetric() {
		return _impressionsMetric;
	}

	@Override
	public List<String> getURLs() {
		return _urls;
	}

	public Metric getViewsMetric() {
		return _viewsMetric;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			_assetId, _assetMetrics, _assetTitle, _canonicalUrls, _dataSourceId,
			_downloadsMetric, _externalReferenceCode, _impressionsMetric, _urls,
			_viewsMetric);
	}

	@Override
	public void setAssetId(String assetId) {
		_assetId = assetId;
	}

	@Override
	public void setAssetMetrics(List<AssetMetric> assetMetrics) {
		_assetMetrics = assetMetrics;
	}

	@Override
	public void setAssetTitle(String assetTitle) {
		_assetTitle = assetTitle;
	}

	@Override
	public void setCanonicalUrls(List<String> canonicalUrls) {
		_canonicalUrls = canonicalUrls;
	}

	@Override
	public void setDataSourceId(String dataSourceId) {
		_dataSourceId = dataSourceId;
	}

	public void setDownloadsMetric(Metric downloadsMetric) {
		_downloadsMetric = downloadsMetric;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public void setImpressionsMetric(Metric impressionsMetric) {
		_impressionsMetric = impressionsMetric;
	}

	@Override
	public void setURLs(List<String> urls) {
		_urls = urls;
	}

	public void setViewsMetric(Metric viewsMetric) {
		_viewsMetric = viewsMetric;
	}

	private String _assetId;
	private List<AssetMetric> _assetMetrics;
	private String _assetTitle;
	private List<String> _canonicalUrls;
	private String _dataSourceId;
	private Metric _downloadsMetric = new Metric(
		ObjectEntryMetricType.DOWNLOADS);
	private String _externalReferenceCode;
	private Metric _impressionsMetric = new Metric(
		ObjectEntryMetricType.IMPRESSIONS);
	private List<String> _urls;
	private Metric _viewsMetric = new Metric(ObjectEntryMetricType.VIEWS);

}