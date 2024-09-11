/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

/**
 * @author Marcos Martins
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetAppearsOnHistogramMetricDTO {

	public AssetAppearsOnHistogramMetricDTO(
		Set<AppearsOnHistogramMetricDTO> appearsOnHistogramMetricDTOs,
		String metricName) {

		_appearsOnHistogramMetricDTOs = appearsOnHistogramMetricDTOs;
		_metricName = metricName;
	}

	public AssetAppearsOnHistogramMetricDTO(
		Set<AssetAppearsOnHistogramMetricDTO>
			assetAppearsOnHistogramMetricDTOs) {

		_assetAppearsOnHistogramMetricDTOs = assetAppearsOnHistogramMetricDTOs;
	}

	@JsonProperty("appearsOnHistograms")
	public Set<AppearsOnHistogramMetricDTO> getAppearsOnHistogramMetricDTOs() {
		return _appearsOnHistogramMetricDTOs;
	}

	@JsonProperty("assetAppearsOnHistograms")
	public Set<AssetAppearsOnHistogramMetricDTO>
		getAssetAppearsOnHistogramMetricDTOs() {

		return _assetAppearsOnHistogramMetricDTOs;
	}

	public String getMetricName() {
		return _metricName;
	}

	public void setMetricName(String metricName) {
		_metricName = metricName;
	}

	private Set<AppearsOnHistogramMetricDTO> _appearsOnHistogramMetricDTOs;
	private Set<AssetAppearsOnHistogramMetricDTO>
		_assetAppearsOnHistogramMetricDTOs;
	private String _metricName;

}