/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.dto.v1_0;

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
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
@GraphQLName(description = "https://www.schema.org/Process", value = "Process")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Process")
public class Process implements Serializable {

	public static Process toDTO(String json) {
		return ObjectMapperUtil.readValue(Process.class, json);
	}

	public static Process unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Process.class, json);
	}

	@Schema
	public Long getId() {
		if (_idSupplier != null) {
			id = _idSupplier.get();

			_idSupplier = null;
		}

		return id;
	}

	public void setId(Long id) {
		this.id = id;

		_idSupplier = null;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		_idSupplier = () -> {
			try {
				return idUnsafeSupplier.get();
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
	protected Long id;

	@JsonIgnore
	private Supplier<Long> _idSupplier;

	@Schema
	public Long getInstanceCount() {
		if (_instanceCountSupplier != null) {
			instanceCount = _instanceCountSupplier.get();

			_instanceCountSupplier = null;
		}

		return instanceCount;
	}

	public void setInstanceCount(Long instanceCount) {
		this.instanceCount = instanceCount;

		_instanceCountSupplier = null;
	}

	@JsonIgnore
	public void setInstanceCount(
		UnsafeSupplier<Long, Exception> instanceCountUnsafeSupplier) {

		_instanceCountSupplier = () -> {
			try {
				return instanceCountUnsafeSupplier.get();
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
	protected Long instanceCount;

	@JsonIgnore
	private Supplier<Long> _instanceCountSupplier;

	@Schema
	public Long getOnTimeInstanceCount() {
		if (_onTimeInstanceCountSupplier != null) {
			onTimeInstanceCount = _onTimeInstanceCountSupplier.get();

			_onTimeInstanceCountSupplier = null;
		}

		return onTimeInstanceCount;
	}

	public void setOnTimeInstanceCount(Long onTimeInstanceCount) {
		this.onTimeInstanceCount = onTimeInstanceCount;

		_onTimeInstanceCountSupplier = null;
	}

	@JsonIgnore
	public void setOnTimeInstanceCount(
		UnsafeSupplier<Long, Exception> onTimeInstanceCountUnsafeSupplier) {

		_onTimeInstanceCountSupplier = () -> {
			try {
				return onTimeInstanceCountUnsafeSupplier.get();
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
	protected Long onTimeInstanceCount;

	@JsonIgnore
	private Supplier<Long> _onTimeInstanceCountSupplier;

	@Schema
	public Long getOverdueInstanceCount() {
		if (_overdueInstanceCountSupplier != null) {
			overdueInstanceCount = _overdueInstanceCountSupplier.get();

			_overdueInstanceCountSupplier = null;
		}

		return overdueInstanceCount;
	}

	public void setOverdueInstanceCount(Long overdueInstanceCount) {
		this.overdueInstanceCount = overdueInstanceCount;

		_overdueInstanceCountSupplier = null;
	}

	@JsonIgnore
	public void setOverdueInstanceCount(
		UnsafeSupplier<Long, Exception> overdueInstanceCountUnsafeSupplier) {

		_overdueInstanceCountSupplier = () -> {
			try {
				return overdueInstanceCountUnsafeSupplier.get();
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
	protected Long overdueInstanceCount;

	@JsonIgnore
	private Supplier<Long> _overdueInstanceCountSupplier;

	@Schema
	public String getTitle() {
		if (_titleSupplier != null) {
			title = _titleSupplier.get();

			_titleSupplier = null;
		}

		return title;
	}

	public void setTitle(String title) {
		this.title = title;

		_titleSupplier = null;
	}

	@JsonIgnore
	public void setTitle(
		UnsafeSupplier<String, Exception> titleUnsafeSupplier) {

		_titleSupplier = () -> {
			try {
				return titleUnsafeSupplier.get();
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
	protected String title;

	@JsonIgnore
	private Supplier<String> _titleSupplier;

	@Schema
	public Long getUntrackedInstanceCount() {
		if (_untrackedInstanceCountSupplier != null) {
			untrackedInstanceCount = _untrackedInstanceCountSupplier.get();

			_untrackedInstanceCountSupplier = null;
		}

		return untrackedInstanceCount;
	}

	public void setUntrackedInstanceCount(Long untrackedInstanceCount) {
		this.untrackedInstanceCount = untrackedInstanceCount;

		_untrackedInstanceCountSupplier = null;
	}

	@JsonIgnore
	public void setUntrackedInstanceCount(
		UnsafeSupplier<Long, Exception> untrackedInstanceCountUnsafeSupplier) {

		_untrackedInstanceCountSupplier = () -> {
			try {
				return untrackedInstanceCountUnsafeSupplier.get();
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
	protected Long untrackedInstanceCount;

	@JsonIgnore
	private Supplier<Long> _untrackedInstanceCountSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Process)) {
			return false;
		}

		Process process = (Process)object;

		return Objects.equals(toString(), process.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Long id = getId();

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		Long instanceCount = getInstanceCount();

		if (instanceCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"instanceCount\": ");

			sb.append(instanceCount);
		}

		Long onTimeInstanceCount = getOnTimeInstanceCount();

		if (onTimeInstanceCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"onTimeInstanceCount\": ");

			sb.append(onTimeInstanceCount);
		}

		Long overdueInstanceCount = getOverdueInstanceCount();

		if (overdueInstanceCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"overdueInstanceCount\": ");

			sb.append(overdueInstanceCount);
		}

		String title = getTitle();

		if (title != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(title));

			sb.append("\"");
		}

		Long untrackedInstanceCount = getUntrackedInstanceCount();

		if (untrackedInstanceCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"untrackedInstanceCount\": ");

			sb.append(untrackedInstanceCount);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.portal.workflow.metrics.rest.dto.v1_0.Process",
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