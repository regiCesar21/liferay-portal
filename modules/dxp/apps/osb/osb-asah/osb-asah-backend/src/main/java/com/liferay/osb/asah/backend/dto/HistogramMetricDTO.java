/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dto;

import com.liferay.osb.asah.backend.model.HistogramMetric;

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

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	private String _key;

}