/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dto;

import com.liferay.osb.asah.backend.model.Metric;

/**
 * @author Rachael Koestartyo
 */
public class MetricDTO {

	public MetricDTO() {
	}

	public MetricDTO(Metric metric) {
		_previousValue = metric.getPreviousValue();
		_previousValueKey = metric.getPreviousValueKey();
		_value = metric.getValue();
		_valueKey = metric.getValueKey();
	}

	public Double getPreviousValue() {
		return _previousValue;
	}

	public String getPreviousValueKey() {
		return _previousValueKey;
	}

	public Double getValue() {
		return _value;
	}

	public String getValueKey() {
		return _valueKey;
	}

	public void setPreviousValue(Double previousValue) {
		if (previousValue != null) {
			_previousValue = previousValue;
		}
	}

	public void setPreviousValueKey(String previousValueKey) {
		_previousValueKey = previousValueKey;
	}

	public void setValue(Double value) {
		if (value != null) {
			_value = value;
		}
	}

	public void setValueKey(String valueKey) {
		_valueKey = valueKey;
	}

	private Double _previousValue;
	private String _previousValueKey;
	private Double _value = 0D;
	private String _valueKey;

}