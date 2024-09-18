/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.osb.asah.backend.model.Metric;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Marcos Martins
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceMetricDTO {

	public DeviceMetricDTO(Set<DeviceMetricDTO> deviceMetricDTOs) {
		_deviceMetricDTOs = deviceMetricDTOs;
	}

	public DeviceMetricDTO(String metricName, List<Metric> metrics) {
		_metricDTOs = new LinkedHashSet<>();

		for (Metric metric : metrics) {
			_metricDTOs.add(new MetricDTO(metric));
		}

		_metricName = metricName;
	}

	@JsonProperty("deviceMetrics")
	public Set<DeviceMetricDTO> getDeviceMetricDTOs() {
		return _deviceMetricDTOs;
	}

	@JsonProperty("metrics")
	public Set<MetricDTO> getMetricDTOs() {
		return _metricDTOs;
	}

	public String getMetricName() {
		return _metricName;
	}

	private Set<DeviceMetricDTO> _deviceMetricDTOs;
	private Set<MetricDTO> _metricDTOs;
	private String _metricName;

}