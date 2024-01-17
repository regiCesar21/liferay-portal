/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.workflow.rest.dto.v1_0;

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
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
@GraphQLName("AppWorkflowDataRecordLink")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AppWorkflowDataRecordLink")
public class AppWorkflowDataRecordLink implements Serializable {

	public static AppWorkflowDataRecordLink toDTO(String json) {
		return ObjectMapperUtil.readValue(
			AppWorkflowDataRecordLink.class, json);
	}

	public static AppWorkflowDataRecordLink unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			AppWorkflowDataRecordLink.class, json);
	}

	@Schema
	@Valid
	public AppWorkflow getAppWorkflow() {
		if (_appWorkflowSupplier != null) {
			appWorkflow = _appWorkflowSupplier.get();

			_appWorkflowSupplier = null;
		}

		return appWorkflow;
	}

	public void setAppWorkflow(AppWorkflow appWorkflow) {
		this.appWorkflow = appWorkflow;

		_appWorkflowSupplier = null;
	}

	@JsonIgnore
	public void setAppWorkflow(
		UnsafeSupplier<AppWorkflow, Exception> appWorkflowUnsafeSupplier) {

		_appWorkflowSupplier = () -> {
			try {
				return appWorkflowUnsafeSupplier.get();
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
	protected AppWorkflow appWorkflow;

	@JsonIgnore
	private Supplier<AppWorkflow> _appWorkflowSupplier;

	@Schema
	public Long getDataRecordId() {
		if (_dataRecordIdSupplier != null) {
			dataRecordId = _dataRecordIdSupplier.get();

			_dataRecordIdSupplier = null;
		}

		return dataRecordId;
	}

	public void setDataRecordId(Long dataRecordId) {
		this.dataRecordId = dataRecordId;

		_dataRecordIdSupplier = null;
	}

	@JsonIgnore
	public void setDataRecordId(
		UnsafeSupplier<Long, Exception> dataRecordIdUnsafeSupplier) {

		_dataRecordIdSupplier = () -> {
			try {
				return dataRecordIdUnsafeSupplier.get();
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
	protected Long dataRecordId;

	@JsonIgnore
	private Supplier<Long> _dataRecordIdSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppWorkflowDataRecordLink)) {
			return false;
		}

		AppWorkflowDataRecordLink appWorkflowDataRecordLink =
			(AppWorkflowDataRecordLink)object;

		return Objects.equals(toString(), appWorkflowDataRecordLink.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		AppWorkflow appWorkflow = getAppWorkflow();

		if (appWorkflow != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflow\": ");

			sb.append(String.valueOf(appWorkflow));
		}

		Long dataRecordId = getDataRecordId();

		if (dataRecordId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataRecordId\": ");

			sb.append(dataRecordId);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowDataRecordLink",
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