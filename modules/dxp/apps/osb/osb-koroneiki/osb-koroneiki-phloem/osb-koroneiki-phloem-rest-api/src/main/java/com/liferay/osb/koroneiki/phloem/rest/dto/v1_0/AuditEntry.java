/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.koroneiki.phloem.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

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
 * @author Amos Fong
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "An audit log that records changes to objects.",
	value = "AuditEntry"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AuditEntry")
public class AuditEntry implements Serializable {

	public static AuditEntry toDTO(String json) {
		return ObjectMapperUtil.readValue(AuditEntry.class, json);
	}

	public static AuditEntry unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(AuditEntry.class, json);
	}

	@Schema(description = "The action performed on the object.")
	@Valid
	public Action getAction() {
		if (_actionSupplier != null) {
			action = _actionSupplier.get();

			_actionSupplier = null;
		}

		return action;
	}

	@JsonIgnore
	public String getActionAsString() {
		Action action = getAction();

		if (action == null) {
			return null;
		}

		return action.toString();
	}

	public void setAction(Action action) {
		this.action = action;

		_actionSupplier = null;
	}

	@JsonIgnore
	public void setAction(
		UnsafeSupplier<Action, Exception> actionUnsafeSupplier) {

		_actionSupplier = () -> {
			try {
				return actionUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The action performed on the object.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Action action;

	private Supplier<Action> _actionSupplier;

	@Schema(
		description = "The full name of the user performing the audited action."
	)
	public String getAgentName() {
		if (_agentNameSupplier != null) {
			agentName = _agentNameSupplier.get();

			_agentNameSupplier = null;
		}

		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;

		_agentNameSupplier = null;
	}

	@JsonIgnore
	public void setAgentName(
		UnsafeSupplier<String, Exception> agentNameUnsafeSupplier) {

		_agentNameSupplier = () -> {
			try {
				return agentNameUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(
		description = "The full name of the user performing the audited action."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String agentName;

	private Supplier<String> _agentNameSupplier;

	@Schema(description = "The UUID of the user performing the audited action.")
	public String getAgentUID() {
		if (_agentUIDSupplier != null) {
			agentUID = _agentUIDSupplier.get();

			_agentUIDSupplier = null;
		}

		return agentUID;
	}

	public void setAgentUID(String agentUID) {
		this.agentUID = agentUID;

		_agentUIDSupplier = null;
	}

	@JsonIgnore
	public void setAgentUID(
		UnsafeSupplier<String, Exception> agentUIDUnsafeSupplier) {

		_agentUIDSupplier = () -> {
			try {
				return agentUIDUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(
		description = "The UUID of the user performing the audited action."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String agentUID;

	private Supplier<String> _agentUIDSupplier;

	@Schema(description = "The id of related audit entries.")
	public Long getAuditSetId() {
		if (_auditSetIdSupplier != null) {
			auditSetId = _auditSetIdSupplier.get();

			_auditSetIdSupplier = null;
		}

		return auditSetId;
	}

	public void setAuditSetId(Long auditSetId) {
		this.auditSetId = auditSetId;

		_auditSetIdSupplier = null;
	}

	@JsonIgnore
	public void setAuditSetId(
		UnsafeSupplier<Long, Exception> auditSetIdUnsafeSupplier) {

		_auditSetIdSupplier = () -> {
			try {
				return auditSetIdUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The id of related audit entries.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long auditSetId;

	private Supplier<Long> _auditSetIdSupplier;

	@Schema(description = "The audit entry's creation date.")
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
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The audit entry's creation date.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateCreated;

	private Supplier<Date> _dateCreatedSupplier;

	@Schema(description = "Additional information describing what occurred.")
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
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(
		description = "Additional information describing what occurred."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String description;

	private Supplier<String> _descriptionSupplier;

	@Schema(description = "The field of the audited object.")
	public String getField() {
		if (_fieldSupplier != null) {
			field = _fieldSupplier.get();

			_fieldSupplier = null;
		}

		return field;
	}

	public void setField(String field) {
		this.field = field;

		_fieldSupplier = null;
	}

	@JsonIgnore
	public void setField(
		UnsafeSupplier<String, Exception> fieldUnsafeSupplier) {

		_fieldSupplier = () -> {
			try {
				return fieldUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The field of the audited object.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String field;

	private Supplier<String> _fieldSupplier;

	@Schema(description = "The class name of the audited object.")
	public String getFieldClassLabel() {
		if (_fieldClassLabelSupplier != null) {
			fieldClassLabel = _fieldClassLabelSupplier.get();

			_fieldClassLabelSupplier = null;
		}

		return fieldClassLabel;
	}

	public void setFieldClassLabel(String fieldClassLabel) {
		this.fieldClassLabel = fieldClassLabel;

		_fieldClassLabelSupplier = null;
	}

	@JsonIgnore
	public void setFieldClassLabel(
		UnsafeSupplier<String, Exception> fieldClassLabelUnsafeSupplier) {

		_fieldClassLabelSupplier = () -> {
			try {
				return fieldClassLabelUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The class name of the audited object.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String fieldClassLabel;

	private Supplier<String> _fieldClassLabelSupplier;

	@Schema(description = "The primary key of the audited object.")
	public Long getFieldClassPK() {
		if (_fieldClassPKSupplier != null) {
			fieldClassPK = _fieldClassPKSupplier.get();

			_fieldClassPKSupplier = null;
		}

		return fieldClassPK;
	}

	public void setFieldClassPK(Long fieldClassPK) {
		this.fieldClassPK = fieldClassPK;

		_fieldClassPKSupplier = null;
	}

	@JsonIgnore
	public void setFieldClassPK(
		UnsafeSupplier<Long, Exception> fieldClassPKUnsafeSupplier) {

		_fieldClassPKSupplier = () -> {
			try {
				return fieldClassPKUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The primary key of the audited object.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long fieldClassPK;

	private Supplier<Long> _fieldClassPKSupplier;

	@Schema(description = "The audit entry's key.")
	public String getKey() {
		if (_keySupplier != null) {
			key = _keySupplier.get();

			_keySupplier = null;
		}

		return key;
	}

	public void setKey(String key) {
		this.key = key;

		_keySupplier = null;
	}

	@JsonIgnore
	public void setKey(UnsafeSupplier<String, Exception> keyUnsafeSupplier) {
		_keySupplier = () -> {
			try {
				return keyUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The audit entry's key.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String key;

	private Supplier<String> _keySupplier;

	@Schema(description = "The new value of the field on the audited object.")
	public String getNewValue() {
		if (_newValueSupplier != null) {
			newValue = _newValueSupplier.get();

			_newValueSupplier = null;
		}

		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;

		_newValueSupplier = null;
	}

	@JsonIgnore
	public void setNewValue(
		UnsafeSupplier<String, Exception> newValueUnsafeSupplier) {

		_newValueSupplier = () -> {
			try {
				return newValueUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(
		description = "The new value of the field on the audited object."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String newValue;

	private Supplier<String> _newValueSupplier;

	@Schema(description = "The old value of the field on the audited object.")
	public String getOldValue() {
		if (_oldValueSupplier != null) {
			oldValue = _oldValueSupplier.get();

			_oldValueSupplier = null;
		}

		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;

		_oldValueSupplier = null;
	}

	@JsonIgnore
	public void setOldValue(
		UnsafeSupplier<String, Exception> oldValueUnsafeSupplier) {

		_oldValueSupplier = () -> {
			try {
				return oldValueUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(
		description = "The old value of the field on the audited object."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String oldValue;

	private Supplier<String> _oldValueSupplier;

	@Schema(description = "A summary of the what occurred.")
	public String getSummary() {
		if (_summarySupplier != null) {
			summary = _summarySupplier.get();

			_summarySupplier = null;
		}

		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;

		_summarySupplier = null;
	}

	@JsonIgnore
	public void setSummary(
		UnsafeSupplier<String, Exception> summaryUnsafeSupplier) {

		_summarySupplier = () -> {
			try {
				return summaryUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "A summary of the what occurred.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String summary;

	private Supplier<String> _summarySupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AuditEntry)) {
			return false;
		}

		AuditEntry auditEntry = (AuditEntry)object;

		return Objects.equals(toString(), auditEntry.toString());
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

		Action action = getAction();

		if (action != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"action\": ");

			sb.append("\"");

			sb.append(action);

			sb.append("\"");
		}

		String agentName = getAgentName();

		if (agentName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"agentName\": ");

			sb.append("\"");

			sb.append(_escape(agentName));

			sb.append("\"");
		}

		String agentUID = getAgentUID();

		if (agentUID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"agentUID\": ");

			sb.append("\"");

			sb.append(_escape(agentUID));

			sb.append("\"");
		}

		Long auditSetId = getAuditSetId();

		if (auditSetId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"auditSetId\": ");

			sb.append(auditSetId);
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

		String field = getField();

		if (field != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"field\": ");

			sb.append("\"");

			sb.append(_escape(field));

			sb.append("\"");
		}

		String fieldClassLabel = getFieldClassLabel();

		if (fieldClassLabel != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fieldClassLabel\": ");

			sb.append("\"");

			sb.append(_escape(fieldClassLabel));

			sb.append("\"");
		}

		Long fieldClassPK = getFieldClassPK();

		if (fieldClassPK != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fieldClassPK\": ");

			sb.append(fieldClassPK);
		}

		String key = getKey();

		if (key != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(key));

			sb.append("\"");
		}

		String newValue = getNewValue();

		if (newValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"newValue\": ");

			sb.append("\"");

			sb.append(_escape(newValue));

			sb.append("\"");
		}

		String oldValue = getOldValue();

		if (oldValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"oldValue\": ");

			sb.append("\"");

			sb.append(_escape(oldValue));

			sb.append("\"");
		}

		String summary = getSummary();

		if (summary != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"summary\": ");

			sb.append("\"");

			sb.append(_escape(summary));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.AuditEntry",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("Action")
	public static enum Action {

		ADD("Add"), ASSIGN("Assign"), DELETE("Delete"), RENEW("Renew"),
		UNASSIGN("Unassign"), UPDATE("Update");

		@JsonCreator
		public static Action create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Action action : values()) {
				if (Objects.equals(action.getValue(), value)) {
					return action;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Action(String value) {
			_value = value;
		}

		private final String _value;

	}

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