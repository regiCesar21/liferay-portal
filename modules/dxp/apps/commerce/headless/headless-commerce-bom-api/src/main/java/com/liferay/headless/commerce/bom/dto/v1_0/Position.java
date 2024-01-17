/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.bom.dto.v1_0;

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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
@GraphQLName("Position")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Position")
public class Position implements Serializable {

	public static Position toDTO(String json) {
		return ObjectMapperUtil.readValue(Position.class, json);
	}

	public static Position unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Position.class, json);
	}

	@Schema(example = "33.54")
	public Double getX() {
		if (_xSupplier != null) {
			x = _xSupplier.get();

			_xSupplier = null;
		}

		return x;
	}

	public void setX(Double x) {
		this.x = x;

		_xSupplier = null;
	}

	@JsonIgnore
	public void setX(UnsafeSupplier<Double, Exception> xUnsafeSupplier) {
		_xSupplier = () -> {
			try {
				return xUnsafeSupplier.get();
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
	protected Double x;

	@JsonIgnore
	private Supplier<Double> _xSupplier;

	@Schema(example = "33.54")
	public Double getY() {
		if (_ySupplier != null) {
			y = _ySupplier.get();

			_ySupplier = null;
		}

		return y;
	}

	public void setY(Double y) {
		this.y = y;

		_ySupplier = null;
	}

	@JsonIgnore
	public void setY(UnsafeSupplier<Double, Exception> yUnsafeSupplier) {
		_ySupplier = () -> {
			try {
				return yUnsafeSupplier.get();
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
	protected Double y;

	@JsonIgnore
	private Supplier<Double> _ySupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Position)) {
			return false;
		}

		Position position = (Position)object;

		return Objects.equals(toString(), position.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Double x = getX();

		if (x != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"x\": ");

			sb.append(x);
		}

		Double y = getY();

		if (y != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"y\": ");

			sb.append(y);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.commerce.bom.dto.v1_0.Position",
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