/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.dto.v1_0;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "Represents the log containing the workflow's activity history (e.g., transitions, assignees, etc.).",
	value = "WorkflowLog"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "WorkflowLog")
public class WorkflowLog implements Serializable {

	public static WorkflowLog toDTO(String json) {
		return ObjectMapperUtil.readValue(WorkflowLog.class, json);
	}

	public static WorkflowLog unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(WorkflowLog.class, json);
	}

	@Schema(
		description = "The user account of the person auditing the workflow."
	)
	@Valid
	public Creator getAuditPerson() {
		if (_auditPersonSupplier != null) {
			auditPerson = _auditPersonSupplier.get();

			_auditPersonSupplier = null;
		}

		return auditPerson;
	}

	public void setAuditPerson(Creator auditPerson) {
		this.auditPerson = auditPerson;

		_auditPersonSupplier = null;
	}

	@JsonIgnore
	public void setAuditPerson(
		UnsafeSupplier<Creator, Exception> auditPersonUnsafeSupplier) {

		_auditPersonSupplier = () -> {
			try {
				return auditPersonUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "The user account of the person auditing the workflow."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Creator auditPerson;

	@JsonIgnore
	private Supplier<Creator> _auditPersonSupplier;

	@Schema(description = "The log's comments.")
	public String getCommentLog() {
		if (_commentLogSupplier != null) {
			commentLog = _commentLogSupplier.get();

			_commentLogSupplier = null;
		}

		return commentLog;
	}

	public void setCommentLog(String commentLog) {
		this.commentLog = commentLog;

		_commentLogSupplier = null;
	}

	@JsonIgnore
	public void setCommentLog(
		UnsafeSupplier<String, Exception> commentLogUnsafeSupplier) {

		_commentLogSupplier = () -> {
			try {
				return commentLogUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The log's comments.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String commentLog;

	@JsonIgnore
	private Supplier<String> _commentLogSupplier;

	@Schema(description = "The log's creation date.")
	public Date getDateCreated() {
		if (_dateCreatedSupplier != null) {
			dateCreated = _dateCreatedSupplier.get();

			_dateCreatedSupplier = null;
		}

		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;

		_dateCreatedSupplier = null;
	}

	@JsonIgnore
	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		_dateCreatedSupplier = () -> {
			try {
				return dateCreatedUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The log's creation date.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateCreated;

	@JsonIgnore
	private Supplier<Date> _dateCreatedSupplier;

	@Schema(description = "The log's ID.")
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

	@GraphQLField(description = "The log's ID.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long id;

	@JsonIgnore
	private Supplier<Long> _idSupplier;

	@Schema(description = "The person assigned to the workflow.")
	@Valid
	public Creator getPerson() {
		if (_personSupplier != null) {
			person = _personSupplier.get();

			_personSupplier = null;
		}

		return person;
	}

	public void setPerson(Creator person) {
		this.person = person;

		_personSupplier = null;
	}

	@JsonIgnore
	public void setPerson(
		UnsafeSupplier<Creator, Exception> personUnsafeSupplier) {

		_personSupplier = () -> {
			try {
				return personUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The person assigned to the workflow.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Creator person;

	@JsonIgnore
	private Supplier<Creator> _personSupplier;

	@Schema(description = "The previous person assigned to the workflow.")
	@Valid
	public Creator getPreviousPerson() {
		if (_previousPersonSupplier != null) {
			previousPerson = _previousPersonSupplier.get();

			_previousPersonSupplier = null;
		}

		return previousPerson;
	}

	public void setPreviousPerson(Creator previousPerson) {
		this.previousPerson = previousPerson;

		_previousPersonSupplier = null;
	}

	@JsonIgnore
	public void setPreviousPerson(
		UnsafeSupplier<Creator, Exception> previousPersonUnsafeSupplier) {

		_previousPersonSupplier = () -> {
			try {
				return previousPersonUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The previous person assigned to the workflow.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Creator previousPerson;

	@JsonIgnore
	private Supplier<Creator> _previousPersonSupplier;

	@Schema(description = "The workflow's previous state.")
	public String getPreviousState() {
		if (_previousStateSupplier != null) {
			previousState = _previousStateSupplier.get();

			_previousStateSupplier = null;
		}

		return previousState;
	}

	public void setPreviousState(String previousState) {
		this.previousState = previousState;

		_previousStateSupplier = null;
	}

	@JsonIgnore
	public void setPreviousState(
		UnsafeSupplier<String, Exception> previousStateUnsafeSupplier) {

		_previousStateSupplier = () -> {
			try {
				return previousStateUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The workflow's previous state.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String previousState;

	@JsonIgnore
	private Supplier<String> _previousStateSupplier;

	@Schema(description = "The workflow's current state.")
	public String getState() {
		if (_stateSupplier != null) {
			state = _stateSupplier.get();

			_stateSupplier = null;
		}

		return state;
	}

	public void setState(String state) {
		this.state = state;

		_stateSupplier = null;
	}

	@JsonIgnore
	public void setState(
		UnsafeSupplier<String, Exception> stateUnsafeSupplier) {

		_stateSupplier = () -> {
			try {
				return stateUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The workflow's current state.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String state;

	@JsonIgnore
	private Supplier<String> _stateSupplier;

	@Schema(description = "The task asociated with this workflow log.")
	public Long getTaskId() {
		if (_taskIdSupplier != null) {
			taskId = _taskIdSupplier.get();

			_taskIdSupplier = null;
		}

		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;

		_taskIdSupplier = null;
	}

	@JsonIgnore
	public void setTaskId(
		UnsafeSupplier<Long, Exception> taskIdUnsafeSupplier) {

		_taskIdSupplier = () -> {
			try {
				return taskIdUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The task asociated with this workflow log.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long taskId;

	@JsonIgnore
	private Supplier<Long> _taskIdSupplier;

	@Schema(description = "The workflow log's type.")
	public String getType() {
		if (_typeSupplier != null) {
			type = _typeSupplier.get();

			_typeSupplier = null;
		}

		return type;
	}

	public void setType(String type) {
		this.type = type;

		_typeSupplier = null;
	}

	@JsonIgnore
	public void setType(UnsafeSupplier<String, Exception> typeUnsafeSupplier) {
		_typeSupplier = () -> {
			try {
				return typeUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The workflow log's type.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String type;

	@JsonIgnore
	private Supplier<String> _typeSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WorkflowLog)) {
			return false;
		}

		WorkflowLog workflowLog = (WorkflowLog)object;

		return Objects.equals(toString(), workflowLog.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		Creator auditPerson = getAuditPerson();

		if (auditPerson != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"auditPerson\": ");

			sb.append(String.valueOf(auditPerson));
		}

		String commentLog = getCommentLog();

		if (commentLog != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"commentLog\": ");

			sb.append("\"");

			sb.append(_escape(commentLog));

			sb.append("\"");
		}

		Date dateCreated = getDateCreated();

		if (dateCreated != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(dateCreated));

			sb.append("\"");
		}

		Long id = getId();

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		Creator person = getPerson();

		if (person != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"person\": ");

			sb.append(String.valueOf(person));
		}

		Creator previousPerson = getPreviousPerson();

		if (previousPerson != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"previousPerson\": ");

			sb.append(String.valueOf(previousPerson));
		}

		String previousState = getPreviousState();

		if (previousState != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"previousState\": ");

			sb.append("\"");

			sb.append(_escape(previousState));

			sb.append("\"");
		}

		String state = getState();

		if (state != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"state\": ");

			sb.append("\"");

			sb.append(_escape(state));

			sb.append("\"");
		}

		Long taskId = getTaskId();

		if (taskId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taskId\": ");

			sb.append(taskId);
		}

		String type = getType();

		if (type != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(type));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.admin.workflow.dto.v1_0.WorkflowLog",
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