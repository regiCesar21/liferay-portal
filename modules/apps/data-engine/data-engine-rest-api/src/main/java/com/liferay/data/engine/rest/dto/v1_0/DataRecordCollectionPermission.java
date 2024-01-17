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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
@GraphQLName("DataRecordCollectionPermission")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "DataRecordCollectionPermission")
public class DataRecordCollectionPermission implements Serializable {

	public static DataRecordCollectionPermission toDTO(String json) {
		return ObjectMapperUtil.readValue(
			DataRecordCollectionPermission.class, json);
	}

	public static DataRecordCollectionPermission unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			DataRecordCollectionPermission.class, json);
	}

	@Schema
	public Boolean getAddDataRecord() {
		if (_addDataRecordSupplier != null) {
			addDataRecord = _addDataRecordSupplier.get();

			_addDataRecordSupplier = null;
		}

		return addDataRecord;
	}

	public void setAddDataRecord(Boolean addDataRecord) {
		this.addDataRecord = addDataRecord;

		_addDataRecordSupplier = null;
	}

	@JsonIgnore
	public void setAddDataRecord(
		UnsafeSupplier<Boolean, Exception> addDataRecordUnsafeSupplier) {

		_addDataRecordSupplier = () -> {
			try {
				return addDataRecordUnsafeSupplier.get();
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
	protected Boolean addDataRecord;

	@JsonIgnore
	private Supplier<Boolean> _addDataRecordSupplier;

	@Schema
	public Boolean getAddDataRecordCollection() {
		if (_addDataRecordCollectionSupplier != null) {
			addDataRecordCollection = _addDataRecordCollectionSupplier.get();

			_addDataRecordCollectionSupplier = null;
		}

		return addDataRecordCollection;
	}

	public void setAddDataRecordCollection(Boolean addDataRecordCollection) {
		this.addDataRecordCollection = addDataRecordCollection;

		_addDataRecordCollectionSupplier = null;
	}

	@JsonIgnore
	public void setAddDataRecordCollection(
		UnsafeSupplier<Boolean, Exception>
			addDataRecordCollectionUnsafeSupplier) {

		_addDataRecordCollectionSupplier = () -> {
			try {
				return addDataRecordCollectionUnsafeSupplier.get();
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
	protected Boolean addDataRecordCollection;

	@JsonIgnore
	private Supplier<Boolean> _addDataRecordCollectionSupplier;

	@Schema
	public Boolean getDefinePermissions() {
		if (_definePermissionsSupplier != null) {
			definePermissions = _definePermissionsSupplier.get();

			_definePermissionsSupplier = null;
		}

		return definePermissions;
	}

	public void setDefinePermissions(Boolean definePermissions) {
		this.definePermissions = definePermissions;

		_definePermissionsSupplier = null;
	}

	@JsonIgnore
	public void setDefinePermissions(
		UnsafeSupplier<Boolean, Exception> definePermissionsUnsafeSupplier) {

		_definePermissionsSupplier = () -> {
			try {
				return definePermissionsUnsafeSupplier.get();
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
	protected Boolean definePermissions;

	@JsonIgnore
	private Supplier<Boolean> _definePermissionsSupplier;

	@Schema
	public Boolean getDelete() {
		if (_deleteSupplier != null) {
			delete = _deleteSupplier.get();

			_deleteSupplier = null;
		}

		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;

		_deleteSupplier = null;
	}

	@JsonIgnore
	public void setDelete(
		UnsafeSupplier<Boolean, Exception> deleteUnsafeSupplier) {

		_deleteSupplier = () -> {
			try {
				return deleteUnsafeSupplier.get();
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
	protected Boolean delete;

	@JsonIgnore
	private Supplier<Boolean> _deleteSupplier;

	@Schema
	public Boolean getDeleteDataRecord() {
		if (_deleteDataRecordSupplier != null) {
			deleteDataRecord = _deleteDataRecordSupplier.get();

			_deleteDataRecordSupplier = null;
		}

		return deleteDataRecord;
	}

	public void setDeleteDataRecord(Boolean deleteDataRecord) {
		this.deleteDataRecord = deleteDataRecord;

		_deleteDataRecordSupplier = null;
	}

	@JsonIgnore
	public void setDeleteDataRecord(
		UnsafeSupplier<Boolean, Exception> deleteDataRecordUnsafeSupplier) {

		_deleteDataRecordSupplier = () -> {
			try {
				return deleteDataRecordUnsafeSupplier.get();
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
	protected Boolean deleteDataRecord;

	@JsonIgnore
	private Supplier<Boolean> _deleteDataRecordSupplier;

	@Schema
	public Boolean getExportDataRecord() {
		if (_exportDataRecordSupplier != null) {
			exportDataRecord = _exportDataRecordSupplier.get();

			_exportDataRecordSupplier = null;
		}

		return exportDataRecord;
	}

	public void setExportDataRecord(Boolean exportDataRecord) {
		this.exportDataRecord = exportDataRecord;

		_exportDataRecordSupplier = null;
	}

	@JsonIgnore
	public void setExportDataRecord(
		UnsafeSupplier<Boolean, Exception> exportDataRecordUnsafeSupplier) {

		_exportDataRecordSupplier = () -> {
			try {
				return exportDataRecordUnsafeSupplier.get();
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
	protected Boolean exportDataRecord;

	@JsonIgnore
	private Supplier<Boolean> _exportDataRecordSupplier;

	@Schema
	public String[] getRoleNames() {
		if (_roleNamesSupplier != null) {
			roleNames = _roleNamesSupplier.get();

			_roleNamesSupplier = null;
		}

		return roleNames;
	}

	public void setRoleNames(String[] roleNames) {
		this.roleNames = roleNames;

		_roleNamesSupplier = null;
	}

	@JsonIgnore
	public void setRoleNames(
		UnsafeSupplier<String[], Exception> roleNamesUnsafeSupplier) {

		_roleNamesSupplier = () -> {
			try {
				return roleNamesUnsafeSupplier.get();
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
	protected String[] roleNames;

	@JsonIgnore
	private Supplier<String[]> _roleNamesSupplier;

	@Schema
	public Boolean getUpdate() {
		if (_updateSupplier != null) {
			update = _updateSupplier.get();

			_updateSupplier = null;
		}

		return update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;

		_updateSupplier = null;
	}

	@JsonIgnore
	public void setUpdate(
		UnsafeSupplier<Boolean, Exception> updateUnsafeSupplier) {

		_updateSupplier = () -> {
			try {
				return updateUnsafeSupplier.get();
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
	protected Boolean update;

	@JsonIgnore
	private Supplier<Boolean> _updateSupplier;

	@Schema
	public Boolean getUpdateDataRecord() {
		if (_updateDataRecordSupplier != null) {
			updateDataRecord = _updateDataRecordSupplier.get();

			_updateDataRecordSupplier = null;
		}

		return updateDataRecord;
	}

	public void setUpdateDataRecord(Boolean updateDataRecord) {
		this.updateDataRecord = updateDataRecord;

		_updateDataRecordSupplier = null;
	}

	@JsonIgnore
	public void setUpdateDataRecord(
		UnsafeSupplier<Boolean, Exception> updateDataRecordUnsafeSupplier) {

		_updateDataRecordSupplier = () -> {
			try {
				return updateDataRecordUnsafeSupplier.get();
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
	protected Boolean updateDataRecord;

	@JsonIgnore
	private Supplier<Boolean> _updateDataRecordSupplier;

	@Schema
	public Boolean getView() {
		if (_viewSupplier != null) {
			view = _viewSupplier.get();

			_viewSupplier = null;
		}

		return view;
	}

	public void setView(Boolean view) {
		this.view = view;

		_viewSupplier = null;
	}

	@JsonIgnore
	public void setView(UnsafeSupplier<Boolean, Exception> viewUnsafeSupplier) {
		_viewSupplier = () -> {
			try {
				return viewUnsafeSupplier.get();
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
	protected Boolean view;

	@JsonIgnore
	private Supplier<Boolean> _viewSupplier;

	@Schema
	public Boolean getViewDataRecord() {
		if (_viewDataRecordSupplier != null) {
			viewDataRecord = _viewDataRecordSupplier.get();

			_viewDataRecordSupplier = null;
		}

		return viewDataRecord;
	}

	public void setViewDataRecord(Boolean viewDataRecord) {
		this.viewDataRecord = viewDataRecord;

		_viewDataRecordSupplier = null;
	}

	@JsonIgnore
	public void setViewDataRecord(
		UnsafeSupplier<Boolean, Exception> viewDataRecordUnsafeSupplier) {

		_viewDataRecordSupplier = () -> {
			try {
				return viewDataRecordUnsafeSupplier.get();
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
	protected Boolean viewDataRecord;

	@JsonIgnore
	private Supplier<Boolean> _viewDataRecordSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DataRecordCollectionPermission)) {
			return false;
		}

		DataRecordCollectionPermission dataRecordCollectionPermission =
			(DataRecordCollectionPermission)object;

		return Objects.equals(
			toString(), dataRecordCollectionPermission.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Boolean addDataRecord = getAddDataRecord();

		if (addDataRecord != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addDataRecord\": ");

			sb.append(addDataRecord);
		}

		Boolean addDataRecordCollection = getAddDataRecordCollection();

		if (addDataRecordCollection != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addDataRecordCollection\": ");

			sb.append(addDataRecordCollection);
		}

		Boolean definePermissions = getDefinePermissions();

		if (definePermissions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"definePermissions\": ");

			sb.append(definePermissions);
		}

		Boolean delete = getDelete();

		if (delete != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"delete\": ");

			sb.append(delete);
		}

		Boolean deleteDataRecord = getDeleteDataRecord();

		if (deleteDataRecord != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"deleteDataRecord\": ");

			sb.append(deleteDataRecord);
		}

		Boolean exportDataRecord = getExportDataRecord();

		if (exportDataRecord != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"exportDataRecord\": ");

			sb.append(exportDataRecord);
		}

		String[] roleNames = getRoleNames();

		if (roleNames != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleNames\": ");

			sb.append("[");

			for (int i = 0; i < roleNames.length; i++) {
				sb.append("\"");

				sb.append(_escape(roleNames[i]));

				sb.append("\"");

				if ((i + 1) < roleNames.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		Boolean update = getUpdate();

		if (update != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"update\": ");

			sb.append(update);
		}

		Boolean updateDataRecord = getUpdateDataRecord();

		if (updateDataRecord != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"updateDataRecord\": ");

			sb.append(updateDataRecord);
		}

		Boolean view = getView();

		if (view != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"view\": ");

			sb.append(view);
		}

		Boolean viewDataRecord = getViewDataRecord();

		if (viewDataRecord != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"viewDataRecord\": ");

			sb.append(viewDataRecord);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.data.engine.rest.dto.v1_0.DataRecordCollectionPermission",
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