/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dto;

import com.liferay.osb.asah.backend.model.HistogramMetric;

import java.util.Objects;

/**
 * @author Rachael Koestartyo
 */
public class HistogramMetricDTO extends MetricDTO {

	public HistogramMetricDTO(HistogramMetric histogramMetric) {
		_key = histogramMetric.getKey();

		setPreviousValue(histogramMetric.getPreviousValue());
		setPreviousValueKey(histogramMetric.getPreviousValueKey());
		setValue(histogramMetric.getValue());
		setValueKey(histogramMetric.getValueKey());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof HistogramMetricDTO)) {
			return false;
		}

		HistogramMetricDTO histogramMetricDTO = (HistogramMetricDTO)obj;

		if (super.equals(histogramMetricDTO) &&
			Objects.equals(_key, histogramMetricDTO._key)) {

			return true;
		}

		return false;
	}

	public String getKey() {
		return _key;
	}

	@Override
	public int hashCode() {
		return super.hashCode() + Objects.hash(_key);
	}

	public void setKey(String key) {
		_key = key;
	}

	private String _key;

}