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
@GraphQLName("AppWorkflowTask")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AppWorkflowTask")
public class AppWorkflowTask implements Serializable {

	public static AppWorkflowTask toDTO(String json) {
		return ObjectMapperUtil.readValue(AppWorkflowTask.class, json);
	}

	public static AppWorkflowTask unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(AppWorkflowTask.class, json);
	}

	@Schema
	@Valid
	public AppWorkflowDataLayoutLink[] getAppWorkflowDataLayoutLinks() {
		if (_appWorkflowDataLayoutLinksSupplier != null) {
			appWorkflowDataLayoutLinks =
				_appWorkflowDataLayoutLinksSupplier.get();

			_appWorkflowDataLayoutLinksSupplier = null;
		}

		return appWorkflowDataLayoutLinks;
	}

	public void setAppWorkflowDataLayoutLinks(
		AppWorkflowDataLayoutLink[] appWorkflowDataLayoutLinks) {

		this.appWorkflowDataLayoutLinks = appWorkflowDataLayoutLinks;

		_appWorkflowDataLayoutLinksSupplier = null;
	}

	@JsonIgnore
	public void setAppWorkflowDataLayoutLinks(
		UnsafeSupplier<AppWorkflowDataLayoutLink[], Exception>
			appWorkflowDataLayoutLinksUnsafeSupplier) {

		_appWorkflowDataLayoutLinksSupplier = () -> {
			try {
				return appWorkflowDataLayoutLinksUnsafeSupplier.get();
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
	protected AppWorkflowDataLayoutLink[] appWorkflowDataLayoutLinks;

	@JsonIgnore
	private Supplier<AppWorkflowDataLayoutLink[]>
		_appWorkflowDataLayoutLinksSupplier;

	@Schema
	@Valid
	public AppWorkflowRoleAssignment[] getAppWorkflowRoleAssignments() {
		if (_appWorkflowRoleAssignmentsSupplier != null) {
			appWorkflowRoleAssignments =
				_appWorkflowRoleAssignmentsSupplier.get();

			_appWorkflowRoleAssignmentsSupplier = null;
		}

		return appWorkflowRoleAssignments;
	}

	public void setAppWorkflowRoleAssignments(
		AppWorkflowRoleAssignment[] appWorkflowRoleAssignments) {

		this.appWorkflowRoleAssignments = appWorkflowRoleAssignments;

		_appWorkflowRoleAssignmentsSupplier = null;
	}

	@JsonIgnore
	public void setAppWorkflowRoleAssignments(
		UnsafeSupplier<AppWorkflowRoleAssignment[], Exception>
			appWorkflowRoleAssignmentsUnsafeSupplier) {

		_appWorkflowRoleAssignmentsSupplier = () -> {
			try {
				return appWorkflowRoleAssignmentsUnsafeSupplier.get();
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
	protected AppWorkflowRoleAssignment[] appWorkflowRoleAssignments;

	@JsonIgnore
	private Supplier<AppWorkflowRoleAssignment[]>
		_appWorkflowRoleAssignmentsSupplier;

	@Schema
	@Valid
	public AppWorkflowTransition[] getAppWorkflowTransitions() {
		if (_appWorkflowTransitionsSupplier != null) {
			appWorkflowTransitions = _appWorkflowTransitionsSupplier.get();

			_appWorkflowTransitionsSupplier = null;
		}

		return appWorkflowTransitions;
	}

	public void setAppWorkflowTransitions(
		AppWorkflowTransition[] appWorkflowTransitions) {

		this.appWorkflowTransitions = appWorkflowTransitions;

		_appWorkflowTransitionsSupplier = null;
	}

	@JsonIgnore
	public void setAppWorkflowTransitions(
		UnsafeSupplier<AppWorkflowTransition[], Exception>
			appWorkflowTransitionsUnsafeSupplier) {

		_appWorkflowTransitionsSupplier = () -> {
			try {
				return appWorkflowTransitionsUnsafeSupplier.get();
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
	protected AppWorkflowTransition[] appWorkflowTransitions;

	@JsonIgnore
	private Supplier<AppWorkflowTransition[]> _appWorkflowTransitionsSupplier;

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

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppWorkflowTask)) {
			return false;
		}

		AppWorkflowTask appWorkflowTask = (AppWorkflowTask)object;

		return Objects.equals(toString(), appWorkflowTask.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		AppWorkflowDataLayoutLink[] appWorkflowDataLayoutLinks =
			getAppWorkflowDataLayoutLinks();

		if (appWorkflowDataLayoutLinks != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowDataLayoutLinks\": ");

			sb.append("[");

			for (int i = 0; i < appWorkflowDataLayoutLinks.length; i++) {
				sb.append(String.valueOf(appWorkflowDataLayoutLinks[i]));

				if ((i + 1) < appWorkflowDataLayoutLinks.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		AppWorkflowRoleAssignment[] appWorkflowRoleAssignments =
			getAppWorkflowRoleAssignments();

		if (appWorkflowRoleAssignments != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowRoleAssignments\": ");

			sb.append("[");

			for (int i = 0; i < appWorkflowRoleAssignments.length; i++) {
				sb.append(String.valueOf(appWorkflowRoleAssignments[i]));

				if ((i + 1) < appWorkflowRoleAssignments.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		AppWorkflowTransition[] appWorkflowTransitions =
			getAppWorkflowTransitions();

		if (appWorkflowTransitions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowTransitions\": ");

			sb.append("[");

			for (int i = 0; i < appWorkflowTransitions.length; i++) {
				sb.append(String.valueOf(appWorkflowTransitions[i]));

				if ((i + 1) < appWorkflowTransitions.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
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

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowTask",
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