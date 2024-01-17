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
@GraphQLName("AppWorkflow")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AppWorkflow")
public class AppWorkflow implements Serializable {

	public static AppWorkflow toDTO(String json) {
		return ObjectMapperUtil.readValue(AppWorkflow.class, json);
	}

	public static AppWorkflow unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(AppWorkflow.class, json);
	}

	@Schema
	public Long getAppId() {
		if (_appIdSupplier != null) {
			appId = _appIdSupplier.get();

			_appIdSupplier = null;
		}

		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;

		_appIdSupplier = null;
	}

	@JsonIgnore
	public void setAppId(UnsafeSupplier<Long, Exception> appIdUnsafeSupplier) {
		_appIdSupplier = () -> {
			try {
				return appIdUnsafeSupplier.get();
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
	protected Long appId;

	@JsonIgnore
	private Supplier<Long> _appIdSupplier;

	@Schema
	public String getAppVersion() {
		if (_appVersionSupplier != null) {
			appVersion = _appVersionSupplier.get();

			_appVersionSupplier = null;
		}

		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;

		_appVersionSupplier = null;
	}

	@JsonIgnore
	public void setAppVersion(
		UnsafeSupplier<String, Exception> appVersionUnsafeSupplier) {

		_appVersionSupplier = () -> {
			try {
				return appVersionUnsafeSupplier.get();
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
	protected String appVersion;

	@JsonIgnore
	private Supplier<String> _appVersionSupplier;

	@Schema
	public Long getAppWorkflowDefinitionId() {
		if (_appWorkflowDefinitionIdSupplier != null) {
			appWorkflowDefinitionId = _appWorkflowDefinitionIdSupplier.get();

			_appWorkflowDefinitionIdSupplier = null;
		}

		return appWorkflowDefinitionId;
	}

	public void setAppWorkflowDefinitionId(Long appWorkflowDefinitionId) {
		this.appWorkflowDefinitionId = appWorkflowDefinitionId;

		_appWorkflowDefinitionIdSupplier = null;
	}

	@JsonIgnore
	public void setAppWorkflowDefinitionId(
		UnsafeSupplier<Long, Exception> appWorkflowDefinitionIdUnsafeSupplier) {

		_appWorkflowDefinitionIdSupplier = () -> {
			try {
				return appWorkflowDefinitionIdUnsafeSupplier.get();
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
	protected Long appWorkflowDefinitionId;

	@JsonIgnore
	private Supplier<Long> _appWorkflowDefinitionIdSupplier;

	@Schema
	@Valid
	public AppWorkflowState[] getAppWorkflowStates() {
		if (_appWorkflowStatesSupplier != null) {
			appWorkflowStates = _appWorkflowStatesSupplier.get();

			_appWorkflowStatesSupplier = null;
		}

		return appWorkflowStates;
	}

	public void setAppWorkflowStates(AppWorkflowState[] appWorkflowStates) {
		this.appWorkflowStates = appWorkflowStates;

		_appWorkflowStatesSupplier = null;
	}

	@JsonIgnore
	public void setAppWorkflowStates(
		UnsafeSupplier<AppWorkflowState[], Exception>
			appWorkflowStatesUnsafeSupplier) {

		_appWorkflowStatesSupplier = () -> {
			try {
				return appWorkflowStatesUnsafeSupplier.get();
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
	protected AppWorkflowState[] appWorkflowStates;

	@JsonIgnore
	private Supplier<AppWorkflowState[]> _appWorkflowStatesSupplier;

	@Schema
	@Valid
	public AppWorkflowTask[] getAppWorkflowTasks() {
		if (_appWorkflowTasksSupplier != null) {
			appWorkflowTasks = _appWorkflowTasksSupplier.get();

			_appWorkflowTasksSupplier = null;
		}

		return appWorkflowTasks;
	}

	public void setAppWorkflowTasks(AppWorkflowTask[] appWorkflowTasks) {
		this.appWorkflowTasks = appWorkflowTasks;

		_appWorkflowTasksSupplier = null;
	}

	@JsonIgnore
	public void setAppWorkflowTasks(
		UnsafeSupplier<AppWorkflowTask[], Exception>
			appWorkflowTasksUnsafeSupplier) {

		_appWorkflowTasksSupplier = () -> {
			try {
				return appWorkflowTasksUnsafeSupplier.get();
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
	protected AppWorkflowTask[] appWorkflowTasks;

	@JsonIgnore
	private Supplier<AppWorkflowTask[]> _appWorkflowTasksSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppWorkflow)) {
			return false;
		}

		AppWorkflow appWorkflow = (AppWorkflow)object;

		return Objects.equals(toString(), appWorkflow.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Long appId = getAppId();

		if (appId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appId\": ");

			sb.append(appId);
		}

		String appVersion = getAppVersion();

		if (appVersion != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appVersion\": ");

			sb.append("\"");

			sb.append(_escape(appVersion));

			sb.append("\"");
		}

		Long appWorkflowDefinitionId = getAppWorkflowDefinitionId();

		if (appWorkflowDefinitionId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowDefinitionId\": ");

			sb.append(appWorkflowDefinitionId);
		}

		AppWorkflowState[] appWorkflowStates = getAppWorkflowStates();

		if (appWorkflowStates != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowStates\": ");

			sb.append("[");

			for (int i = 0; i < appWorkflowStates.length; i++) {
				sb.append(String.valueOf(appWorkflowStates[i]));

				if ((i + 1) < appWorkflowStates.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		AppWorkflowTask[] appWorkflowTasks = getAppWorkflowTasks();

		if (appWorkflowTasks != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowTasks\": ");

			sb.append("[");

			for (int i = 0; i < appWorkflowTasks.length; i++) {
				sb.append(String.valueOf(appWorkflowTasks[i]));

				if ((i + 1) < appWorkflowTasks.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflow",
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