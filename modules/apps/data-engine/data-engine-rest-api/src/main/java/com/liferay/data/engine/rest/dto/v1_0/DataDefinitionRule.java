/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
@GraphQLName("DataDefinitionRule")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "DataDefinitionRule")
public class DataDefinitionRule implements Serializable {

	public static DataDefinitionRule toDTO(String json) {
		return ObjectMapperUtil.readValue(DataDefinitionRule.class, json);
	}

	public static DataDefinitionRule unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(DataDefinitionRule.class, json);
	}

	@Schema
	public String[] getDataDefinitionFieldNames() {
		if (_dataDefinitionFieldNamesSupplier != null) {
			dataDefinitionFieldNames = _dataDefinitionFieldNamesSupplier.get();

			_dataDefinitionFieldNamesSupplier = null;
		}

		return dataDefinitionFieldNames;
	}

	public void setDataDefinitionFieldNames(String[] dataDefinitionFieldNames) {
		this.dataDefinitionFieldNames = dataDefinitionFieldNames;

		_dataDefinitionFieldNamesSupplier = null;
	}

	@JsonIgnore
	public void setDataDefinitionFieldNames(
		UnsafeSupplier<String[], Exception>
			dataDefinitionFieldNamesUnsafeSupplier) {

		_dataDefinitionFieldNamesSupplier = () -> {
			try {
				return dataDefinitionFieldNamesUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] dataDefinitionFieldNames;

	@JsonIgnore
	private Supplier<String[]> _dataDefinitionFieldNamesSupplier;

	@Schema
	@Valid
	public Map<String, Object> getDataDefinitionRuleParameters() {
		if (_dataDefinitionRuleParametersSupplier != null) {
			dataDefinitionRuleParameters =
				_dataDefinitionRuleParametersSupplier.get();

			_dataDefinitionRuleParametersSupplier = null;
		}

		return dataDefinitionRuleParameters;
	}

	public void setDataDefinitionRuleParameters(
		Map<String, Object> dataDefinitionRuleParameters) {

		this.dataDefinitionRuleParameters = dataDefinitionRuleParameters;

		_dataDefinitionRuleParametersSupplier = null;
	}

	@JsonIgnore
	public void setDataDefinitionRuleParameters(
		UnsafeSupplier<Map<String, Object>, Exception>
			dataDefinitionRuleParametersUnsafeSupplier) {

		_dataDefinitionRuleParametersSupplier = () -> {
			try {
				return dataDefinitionRuleParametersUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Map<String, Object> dataDefinitionRuleParameters;

	@JsonIgnore
	private Supplier<Map<String, Object>> _dataDefinitionRuleParametersSupplier;

	@Schema
	public String getName() {
		if (_nameSupplier != null) {
			name = _nameSupplier.get();

			_nameSupplier = null;
		}

		return name;
	}

	public void setName(String name) {
		this.name = name;

		_nameSupplier = null;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		_nameSupplier = () -> {
			try {
				return nameUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String name;

	@JsonIgnore
	private Supplier<String> _nameSupplier;

	@Schema
	public String getRuleType() {
		if (_ruleTypeSupplier != null) {
			ruleType = _ruleTypeSupplier.get();

			_ruleTypeSupplier = null;
		}

		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;

		_ruleTypeSupplier = null;
	}

	@JsonIgnore
	public void setRuleType(
		UnsafeSupplier<String, Exception> ruleTypeUnsafeSupplier) {

		_ruleTypeSupplier = () -> {
			try {
				return ruleTypeUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String ruleType;

	@JsonIgnore
	private Supplier<String> _ruleTypeSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DataDefinitionRule)) {
			return false;
		}

		DataDefinitionRule dataDefinitionRule = (DataDefinitionRule)object;

		return Objects.equals(toString(), dataDefinitionRule.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		String[] dataDefinitionFieldNames = getDataDefinitionFieldNames();

		if (dataDefinitionFieldNames != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataDefinitionFieldNames\": ");

			sb.append("[");

			for (int i = 0; i < dataDefinitionFieldNames.length; i++) {
				sb.append("\"");

				sb.append(_escape(dataDefinitionFieldNames[i]));

				sb.append("\"");

				if ((i + 1) < dataDefinitionFieldNames.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		Map<String, Object> dataDefinitionRuleParameters =
			getDataDefinitionRuleParameters();

		if (dataDefinitionRuleParameters != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataDefinitionRuleParameters\": ");

			sb.append(_toJSON(dataDefinitionRuleParameters));
		}

		String name = getName();

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(name));

			sb.append("\"");
		}

		String ruleType = getRuleType();

		if (ruleType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ruleType\": ");

			sb.append("\"");

			sb.append(_escape(ruleType));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.data.engine.rest.dto.v1_0.DataDefinitionRule",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}