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
	description = "Represents a task to be executed in a workflow.",
	value = "WorkflowTask"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "WorkflowTask")
public class WorkflowTask implements Serializable {

	public static WorkflowTask toDTO(String json) {
		return ObjectMapperUtil.readValue(WorkflowTask.class, json);
	}

	public static WorkflowTask unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(WorkflowTask.class, json);
	}

	@Schema(description = "A flag that indicates whether the task is complete.")
	public Boolean getCompleted() {
		if (_completedSupplier != null) {
			completed = _completedSupplier.get();

			_completedSupplier = null;
		}

		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;

		_completedSupplier = null;
	}

	@JsonIgnore
	public void setCompleted(
		UnsafeSupplier<Boolean, Exception> completedUnsafeSupplier) {

		_completedSupplier = () -> {
			try {
				return completedUnsafeSupplier.get();
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
		description = "A flag that indicates whether the task is complete."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Boolean completed;

	@JsonIgnore
	private Supplier<Boolean> _completedSupplier;

	@Schema(description = "The task's completion date.")
	public Date getDateCompleted() {
		if (_dateCompletedSupplier != null) {
			dateCompleted = _dateCompletedSupplier.get();

			_dateCompletedSupplier = null;
		}

		return dateCompleted;
	}

	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;

		_dateCompletedSupplier = null;
	}

	@JsonIgnore
	public void setDateCompleted(
		UnsafeSupplier<Date, Exception> dateCompletedUnsafeSupplier) {

		_dateCompletedSupplier = () -> {
			try {
				return dateCompletedUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The task's completion date.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateCompleted;

	@JsonIgnore
	private Supplier<Date> _dateCompletedSupplier;

	@Schema(description = "The task's creation date.")
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

	@GraphQLField(description = "The task's creation date.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateCreated;

	@JsonIgnore
	private Supplier<Date> _dateCreatedSupplier;

	@Schema(description = "The name of the task's workflow definition.")
	public String getDefinitionName() {
		if (_definitionNameSupplier != null) {
			definitionName = _definitionNameSupplier.get();

			_definitionNameSupplier = null;
		}

		return definitionName;
	}

	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;

		_definitionNameSupplier = null;
	}

	@JsonIgnore
	public void setDefinitionName(
		UnsafeSupplier<String, Exception> definitionNameUnsafeSupplier) {

		_definitionNameSupplier = () -> {
			try {
				return definitionNameUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The name of the task's workflow definition.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String definitionName;

	@JsonIgnore
	private Supplier<String> _definitionNameSupplier;

	@Schema(description = "The task's description.")
	public String getDescription() {
		if (_descriptionSupplier != null) {
			description = _descriptionSupplier.get();

			_descriptionSupplier = null;
		}

		return description;
	}

	public void setDescription(String description) {
		this.description = description;

		_descriptionSupplier = null;
	}

	@JsonIgnore
	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		_descriptionSupplier = () -> {
			try {
				return descriptionUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The task's description.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String description;

	@JsonIgnore
	private Supplier<String> _descriptionSupplier;

	@Schema(description = "The date the task should be completed by.")
	public Date getDueDate() {
		if (_dueDateSupplier != null) {
			dueDate = _dueDateSupplier.get();

			_dueDateSupplier = null;
		}

		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;

		_dueDateSupplier = null;
	}

	@JsonIgnore
	public void setDueDate(
		UnsafeSupplier<Date, Exception> dueDateUnsafeSupplier) {

		_dueDateSupplier = () -> {
			try {
				return dueDateUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The date the task should be completed by.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dueDate;

	@JsonIgnore
	private Supplier<Date> _dueDateSupplier;

	@Schema(description = "The task's ID.")
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

	@GraphQLField(description = "The task's ID.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long id;

	@JsonIgnore
	private Supplier<Long> _idSupplier;

	@Schema(description = "The task's name.")
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

	@GraphQLField(description = "The task's name.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String name;

	@JsonIgnore
	private Supplier<String> _nameSupplier;

	@Schema(
		description = "The object/asset that the task's workflow is managing."
	)
	@Valid
	public ObjectReviewed getObjectReviewed() {
		if (_objectReviewedSupplier != null) {
			objectReviewed = _objectReviewedSupplier.get();

			_objectReviewedSupplier = null;
		}

		return objectReviewed;
	}

	public void setObjectReviewed(ObjectReviewed objectReviewed) {
		this.objectReviewed = objectReviewed;

		_objectReviewedSupplier = null;
	}

	@JsonIgnore
	public void setObjectReviewed(
		UnsafeSupplier<ObjectReviewed, Exception>
			objectReviewedUnsafeSupplier) {

		_objectReviewedSupplier = () -> {
			try {
				return objectReviewedUnsafeSupplier.get();
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
		description = "The object/asset that the task's workflow is managing."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected ObjectReviewed objectReviewed;

	@JsonIgnore
	private Supplier<ObjectReviewed> _objectReviewedSupplier;

	@Schema(
		description = "A list of transitions to be launched by the task's workflow."
	)
	public String[] getTransitions() {
		if (_transitionsSupplier != null) {
			transitions = _transitionsSupplier.get();

			_transitionsSupplier = null;
		}

		return transitions;
	}

	public void setTransitions(String[] transitions) {
		this.transitions = transitions;

		_transitionsSupplier = null;
	}

	@JsonIgnore
	public void setTransitions(
		UnsafeSupplier<String[], Exception> transitionsUnsafeSupplier) {

		_transitionsSupplier = () -> {
			try {
				return transitionsUnsafeSupplier.get();
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
		description = "A list of transitions to be launched by the task's workflow."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String[] transitions;

	@JsonIgnore
	private Supplier<String[]> _transitionsSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WorkflowTask)) {
			return false;
		}

		WorkflowTask workflowTask = (WorkflowTask)object;

		return Objects.equals(toString(), workflowTask.toString());
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

		Boolean completed = getCompleted();

		if (completed != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"completed\": ");

			sb.append(completed);
		}

		Date dateCompleted = getDateCompleted();

		if (dateCompleted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCompleted\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(dateCompleted));

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

		String definitionName = getDefinitionName();

		if (definitionName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"definitionName\": ");

			sb.append("\"");

			sb.append(_escape(definitionName));

			sb.append("\"");
		}

		String description = getDescription();

		if (description != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(description));

			sb.append("\"");
		}

		Date dueDate = getDueDate();

		if (dueDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dueDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(dueDate));

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

		ObjectReviewed objectReviewed = getObjectReviewed();

		if (objectReviewed != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectReviewed\": ");

			sb.append(String.valueOf(objectReviewed));
		}

		String[] transitions = getTransitions();

		if (transitions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"transitions\": ");

			sb.append("[");

			for (int i = 0; i < transitions.length; i++) {
				sb.append("\"");

				sb.append(_escape(transitions[i]));

				sb.append("\"");

				if ((i + 1) < transitions.length) {
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
		defaultValue = "com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTask",
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