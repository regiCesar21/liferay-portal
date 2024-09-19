/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dto;

import com.liferay.osb.asah.backend.model.Metric;

import java.util.Objects;

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MetricDTO)) {
			return false;
		}

		MetricDTO metricDTO = (MetricDTO)obj;

		if (Objects.equals(_previousValue, metricDTO._previousValue) &&
			Objects.equals(_previousValueKey, metricDTO._previousValueKey) &&
			Objects.equals(_value, metricDTO._value) &&
			Objects.equals(_valueKey, metricDTO._valueKey)) {

			return true;
		}

		return false;
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

	@Override
	public int hashCode() {
		return Objects.hash(
			_previousValue, _previousValueKey, _value, _valueKey);
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