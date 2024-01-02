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
	description = "Additional notes for miscellaneous information.",
	value = "Note"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Note")
public class Note implements Serializable {

	public static Note toDTO(String json) {
		return ObjectMapperUtil.readValue(Note.class, json);
	}

	public static Note unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Note.class, json);
	}

	@Schema(description = "The content of the note.")
	public String getContent() {
		if (_contentSupplier != null) {
			content = _contentSupplier.get();

			_contentSupplier = null;
		}

		return content;
	}

	public void setContent(String content) {
		this.content = content;

		_contentSupplier = null;
	}

	@JsonIgnore
	public void setContent(
		UnsafeSupplier<String, Exception> contentUnsafeSupplier) {

		_contentSupplier = () -> {
			try {
				return contentUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The content of the note.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String content;

	private Supplier<String> _contentSupplier;

	@Schema(description = "The full name of the user who created the note.")
	public String getCreatorName() {
		if (_creatorNameSupplier != null) {
			creatorName = _creatorNameSupplier.get();

			_creatorNameSupplier = null;
		}

		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;

		_creatorNameSupplier = null;
	}

	@JsonIgnore
	public void setCreatorName(
		UnsafeSupplier<String, Exception> creatorNameUnsafeSupplier) {

		_creatorNameSupplier = () -> {
			try {
				return creatorNameUnsafeSupplier.get();
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
		description = "The full name of the user who created the note."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String creatorName;

	private Supplier<String> _creatorNameSupplier;

	@Schema(description = "The UUID of the user who created the note.")
	public String getCreatorUID() {
		if (_creatorUIDSupplier != null) {
			creatorUID = _creatorUIDSupplier.get();

			_creatorUIDSupplier = null;
		}

		return creatorUID;
	}

	public void setCreatorUID(String creatorUID) {
		this.creatorUID = creatorUID;

		_creatorUIDSupplier = null;
	}

	@JsonIgnore
	public void setCreatorUID(
		UnsafeSupplier<String, Exception> creatorUIDUnsafeSupplier) {

		_creatorUIDSupplier = () -> {
			try {
				return creatorUIDUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The UUID of the user who created the note.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String creatorUID;

	private Supplier<String> _creatorUIDSupplier;

	@Schema(description = "The note's creation date.")
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

	@GraphQLField(description = "The note's creation date.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateCreated;

	private Supplier<Date> _dateCreatedSupplier;

	@Schema(
		description = "The most recent time that any of the note's fields changed."
	)
	public Date getDateModified() {
		if (_dateModifiedSupplier != null) {
			dateModified = _dateModifiedSupplier.get();

			_dateModifiedSupplier = null;
		}

		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;

		_dateModifiedSupplier = null;
	}

	@JsonIgnore
	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		_dateModifiedSupplier = () -> {
			try {
				return dateModifiedUnsafeSupplier.get();
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
		description = "The most recent time that any of the note's fields changed."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateModified;

	private Supplier<Date> _dateModifiedSupplier;

	@Schema(description = "The style structure of the content.")
	@Valid
	public Format getFormat() {
		if (_formatSupplier != null) {
			format = _formatSupplier.get();

			_formatSupplier = null;
		}

		return format;
	}

	@JsonIgnore
	public String getFormatAsString() {
		Format format = getFormat();

		if (format == null) {
			return null;
		}

		return format.toString();
	}

	public void setFormat(Format format) {
		this.format = format;

		_formatSupplier = null;
	}

	@JsonIgnore
	public void setFormat(
		UnsafeSupplier<Format, Exception> formatUnsafeSupplier) {

		_formatSupplier = () -> {
			try {
				return formatUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The style structure of the content.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Format format;

	private Supplier<Format> _formatSupplier;

	@Schema(description = "The note's key.")
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

	@GraphQLField(description = "The note's key.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String key;

	private Supplier<String> _keySupplier;

	@Schema(
		description = "The full name of the user who last modified the note."
	)
	public String getModifierName() {
		if (_modifierNameSupplier != null) {
			modifierName = _modifierNameSupplier.get();

			_modifierNameSupplier = null;
		}

		return modifierName;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;

		_modifierNameSupplier = null;
	}

	@JsonIgnore
	public void setModifierName(
		UnsafeSupplier<String, Exception> modifierNameUnsafeSupplier) {

		_modifierNameSupplier = () -> {
			try {
				return modifierNameUnsafeSupplier.get();
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
		description = "The full name of the user who last modified the note."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String modifierName;

	private Supplier<String> _modifierNameSupplier;

	@Schema(description = "The UUID of the user who last modified the note.")
	public String getModifierUID() {
		if (_modifierUIDSupplier != null) {
			modifierUID = _modifierUIDSupplier.get();

			_modifierUIDSupplier = null;
		}

		return modifierUID;
	}

	public void setModifierUID(String modifierUID) {
		this.modifierUID = modifierUID;

		_modifierUIDSupplier = null;
	}

	@JsonIgnore
	public void setModifierUID(
		UnsafeSupplier<String, Exception> modifierUIDUnsafeSupplier) {

		_modifierUIDSupplier = () -> {
			try {
				return modifierUIDUnsafeSupplier.get();
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
		description = "The UUID of the user who last modified the note."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String modifierUID;

	private Supplier<String> _modifierUIDSupplier;

	@Schema(
		description = "The importance of this note. A lower number indicates a higher importance."
	)
	public Integer getPriority() {
		if (_prioritySupplier != null) {
			priority = _prioritySupplier.get();

			_prioritySupplier = null;
		}

		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;

		_prioritySupplier = null;
	}

	@JsonIgnore
	public void setPriority(
		UnsafeSupplier<Integer, Exception> priorityUnsafeSupplier) {

		_prioritySupplier = () -> {
			try {
				return priorityUnsafeSupplier.get();
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
		description = "The importance of this note. A lower number indicates a higher importance."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer priority;

	private Supplier<Integer> _prioritySupplier;

	@Schema(description = "The workflow status of the note.")
	@Valid
	public Status getStatus() {
		if (_statusSupplier != null) {
			status = _statusSupplier.get();

			_statusSupplier = null;
		}

		return status;
	}

	@JsonIgnore
	public String getStatusAsString() {
		Status status = getStatus();

		if (status == null) {
			return null;
		}

		return status.toString();
	}

	public void setStatus(Status status) {
		this.status = status;

		_statusSupplier = null;
	}

	@JsonIgnore
	public void setStatus(
		UnsafeSupplier<Status, Exception> statusUnsafeSupplier) {

		_statusSupplier = () -> {
			try {
				return statusUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The workflow status of the note.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Status status;

	private Supplier<Status> _statusSupplier;

	@Schema(description = "The type of information the note contains.")
	@Valid
	public Type getType() {
		if (_typeSupplier != null) {
			type = _typeSupplier.get();

			_typeSupplier = null;
		}

		return type;
	}

	@JsonIgnore
	public String getTypeAsString() {
		Type type = getType();

		if (type == null) {
			return null;
		}

		return type.toString();
	}

	public void setType(Type type) {
		this.type = type;

		_typeSupplier = null;
	}

	@JsonIgnore
	public void setType(UnsafeSupplier<Type, Exception> typeUnsafeSupplier) {
		_typeSupplier = () -> {
			try {
				return typeUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The type of information the note contains.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Type type;

	private Supplier<Type> _typeSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Note)) {
			return false;
		}

		Note note = (Note)object;

		return Objects.equals(toString(), note.toString());
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

		String content = getContent();

		if (content != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"content\": ");

			sb.append("\"");

			sb.append(_escape(content));

			sb.append("\"");
		}

		String creatorName = getCreatorName();

		if (creatorName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creatorName\": ");

			sb.append("\"");

			sb.append(_escape(creatorName));

			sb.append("\"");
		}

		String creatorUID = getCreatorUID();

		if (creatorUID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creatorUID\": ");

			sb.append("\"");

			sb.append(_escape(creatorUID));

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

		Date dateModified = getDateModified();

		if (dateModified != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(dateModified));

			sb.append("\"");
		}

		Format format = getFormat();

		if (format != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"format\": ");

			sb.append("\"");

			sb.append(format);

			sb.append("\"");
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

		String modifierName = getModifierName();

		if (modifierName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifierName\": ");

			sb.append("\"");

			sb.append(_escape(modifierName));

			sb.append("\"");
		}

		String modifierUID = getModifierUID();

		if (modifierUID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifierUID\": ");

			sb.append("\"");

			sb.append(_escape(modifierUID));

			sb.append("\"");
		}

		Integer priority = getPriority();

		if (priority != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(priority);
		}

		Status status = getStatus();

		if (status != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");

			sb.append(status);

			sb.append("\"");
		}

		Type type = getType();

		if (type != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(type);

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Note",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("Format")
	public static enum Format {

		HTML("HTML"), PLAIN("Plain");

		@JsonCreator
		public static Format create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Format format : values()) {
				if (Objects.equals(format.getValue(), value)) {
					return format;
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

		private Format(String value) {
			_value = value;
		}

		private final String _value;

	}

	@GraphQLName("Status")
	public static enum Status {

		APPROVED("Approved"), ARCHIVED("Archived");

		@JsonCreator
		public static Status create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Status status : values()) {
				if (Objects.equals(status.getValue(), value)) {
					return status;
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

		private Status(String value) {
			_value = value;
		}

		private final String _value;

	}

	@GraphQLName("Type")
	public static enum Type {

		GENERAL("General"), SALES("Sales");

		@JsonCreator
		public static Type create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Type type : values()) {
				if (Objects.equals(type.getValue(), value)) {
					return type;
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

		private Type(String value) {
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