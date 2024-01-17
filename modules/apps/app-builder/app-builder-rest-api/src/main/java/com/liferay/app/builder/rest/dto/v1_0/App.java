/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.rest.dto.v1_0;

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
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
@GraphQLName("App")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "App")
public class App implements Serializable {

	public static App toDTO(String json) {
		return ObjectMapperUtil.readValue(App.class, json);
	}

	public static App unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(App.class, json);
	}

	@Schema
	public Boolean getActive() {
		if (_activeSupplier != null) {
			active = _activeSupplier.get();

			_activeSupplier = null;
		}

		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;

		_activeSupplier = null;
	}

	@JsonIgnore
	public void setActive(
		UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier) {

		_activeSupplier = () -> {
			try {
				return activeUnsafeSupplier.get();
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
	protected Boolean active;

	@JsonIgnore
	private Supplier<Boolean> _activeSupplier;

	@Schema
	@Valid
	public AppDeployment[] getAppDeployments() {
		if (_appDeploymentsSupplier != null) {
			appDeployments = _appDeploymentsSupplier.get();

			_appDeploymentsSupplier = null;
		}

		return appDeployments;
	}

	public void setAppDeployments(AppDeployment[] appDeployments) {
		this.appDeployments = appDeployments;

		_appDeploymentsSupplier = null;
	}

	@JsonIgnore
	public void setAppDeployments(
		UnsafeSupplier<AppDeployment[], Exception>
			appDeploymentsUnsafeSupplier) {

		_appDeploymentsSupplier = () -> {
			try {
				return appDeploymentsUnsafeSupplier.get();
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
	protected AppDeployment[] appDeployments;

	@JsonIgnore
	private Supplier<AppDeployment[]> _appDeploymentsSupplier;

	@Schema
	public Long getDataDefinitionId() {
		if (_dataDefinitionIdSupplier != null) {
			dataDefinitionId = _dataDefinitionIdSupplier.get();

			_dataDefinitionIdSupplier = null;
		}

		return dataDefinitionId;
	}

	public void setDataDefinitionId(Long dataDefinitionId) {
		this.dataDefinitionId = dataDefinitionId;

		_dataDefinitionIdSupplier = null;
	}

	@JsonIgnore
	public void setDataDefinitionId(
		UnsafeSupplier<Long, Exception> dataDefinitionIdUnsafeSupplier) {

		_dataDefinitionIdSupplier = () -> {
			try {
				return dataDefinitionIdUnsafeSupplier.get();
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
	protected Long dataDefinitionId;

	@JsonIgnore
	private Supplier<Long> _dataDefinitionIdSupplier;

	@Schema
	public String getDataDefinitionName() {
		if (_dataDefinitionNameSupplier != null) {
			dataDefinitionName = _dataDefinitionNameSupplier.get();

			_dataDefinitionNameSupplier = null;
		}

		return dataDefinitionName;
	}

	public void setDataDefinitionName(String dataDefinitionName) {
		this.dataDefinitionName = dataDefinitionName;

		_dataDefinitionNameSupplier = null;
	}

	@JsonIgnore
	public void setDataDefinitionName(
		UnsafeSupplier<String, Exception> dataDefinitionNameUnsafeSupplier) {

		_dataDefinitionNameSupplier = () -> {
			try {
				return dataDefinitionNameUnsafeSupplier.get();
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
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String dataDefinitionName;

	@JsonIgnore
	private Supplier<String> _dataDefinitionNameSupplier;

	@Schema
	public Long getDataLayoutId() {
		if (_dataLayoutIdSupplier != null) {
			dataLayoutId = _dataLayoutIdSupplier.get();

			_dataLayoutIdSupplier = null;
		}

		return dataLayoutId;
	}

	public void setDataLayoutId(Long dataLayoutId) {
		this.dataLayoutId = dataLayoutId;

		_dataLayoutIdSupplier = null;
	}

	@JsonIgnore
	public void setDataLayoutId(
		UnsafeSupplier<Long, Exception> dataLayoutIdUnsafeSupplier) {

		_dataLayoutIdSupplier = () -> {
			try {
				return dataLayoutIdUnsafeSupplier.get();
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
	protected Long dataLayoutId;

	@JsonIgnore
	private Supplier<Long> _dataLayoutIdSupplier;

	@Schema
	public Long getDataListViewId() {
		if (_dataListViewIdSupplier != null) {
			dataListViewId = _dataListViewIdSupplier.get();

			_dataListViewIdSupplier = null;
		}

		return dataListViewId;
	}

	public void setDataListViewId(Long dataListViewId) {
		this.dataListViewId = dataListViewId;

		_dataListViewIdSupplier = null;
	}

	@JsonIgnore
	public void setDataListViewId(
		UnsafeSupplier<Long, Exception> dataListViewIdUnsafeSupplier) {

		_dataListViewIdSupplier = () -> {
			try {
				return dataListViewIdUnsafeSupplier.get();
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
	protected Long dataListViewId;

	@JsonIgnore
	private Supplier<Long> _dataListViewIdSupplier;

	@Schema
	public Long getDataRecordCollectionId() {
		if (_dataRecordCollectionIdSupplier != null) {
			dataRecordCollectionId = _dataRecordCollectionIdSupplier.get();

			_dataRecordCollectionIdSupplier = null;
		}

		return dataRecordCollectionId;
	}

	public void setDataRecordCollectionId(Long dataRecordCollectionId) {
		this.dataRecordCollectionId = dataRecordCollectionId;

		_dataRecordCollectionIdSupplier = null;
	}

	@JsonIgnore
	public void setDataRecordCollectionId(
		UnsafeSupplier<Long, Exception> dataRecordCollectionIdUnsafeSupplier) {

		_dataRecordCollectionIdSupplier = () -> {
			try {
				return dataRecordCollectionIdUnsafeSupplier.get();
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
	protected Long dataRecordCollectionId;

	@JsonIgnore
	private Supplier<Long> _dataRecordCollectionIdSupplier;

	@Schema
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

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Date dateCreated;

	@JsonIgnore
	private Supplier<Date> _dateCreatedSupplier;

	@Schema
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
	protected Date dateModified;

	@JsonIgnore
	private Supplier<Date> _dateModifiedSupplier;

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
	@Valid
	public Map<String, Object> getName() {
		if (_nameSupplier != null) {
			name = _nameSupplier.get();

			_nameSupplier = null;
		}

		return name;
	}

	public void setName(Map<String, Object> name) {
		this.name = name;

		_nameSupplier = null;
	}

	@JsonIgnore
	public void setName(
		UnsafeSupplier<Map<String, Object>, Exception> nameUnsafeSupplier) {

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
	protected Map<String, Object> name;

	@JsonIgnore
	private Supplier<Map<String, Object>> _nameSupplier;

	@Schema
	public String getScope() {
		if (_scopeSupplier != null) {
			scope = _scopeSupplier.get();

			_scopeSupplier = null;
		}

		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;

		_scopeSupplier = null;
	}

	@JsonIgnore
	public void setScope(
		UnsafeSupplier<String, Exception> scopeUnsafeSupplier) {

		_scopeSupplier = () -> {
			try {
				return scopeUnsafeSupplier.get();
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
	protected String scope;

	@JsonIgnore
	private Supplier<String> _scopeSupplier;

	@Schema
	public Long getSiteId() {
		if (_siteIdSupplier != null) {
			siteId = _siteIdSupplier.get();

			_siteIdSupplier = null;
		}

		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;

		_siteIdSupplier = null;
	}

	@JsonIgnore
	public void setSiteId(
		UnsafeSupplier<Long, Exception> siteIdUnsafeSupplier) {

		_siteIdSupplier = () -> {
			try {
				return siteIdUnsafeSupplier.get();
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
	protected Long siteId;

	@JsonIgnore
	private Supplier<Long> _siteIdSupplier;

	@Schema
	public Long getUserId() {
		if (_userIdSupplier != null) {
			userId = _userIdSupplier.get();

			_userIdSupplier = null;
		}

		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;

		_userIdSupplier = null;
	}

	@JsonIgnore
	public void setUserId(
		UnsafeSupplier<Long, Exception> userIdUnsafeSupplier) {

		_userIdSupplier = () -> {
			try {
				return userIdUnsafeSupplier.get();
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
	protected Long userId;

	@JsonIgnore
	private Supplier<Long> _userIdSupplier;

	@Schema
	public String getVersion() {
		if (_versionSupplier != null) {
			version = _versionSupplier.get();

			_versionSupplier = null;
		}

		return version;
	}

	public void setVersion(String version) {
		this.version = version;

		_versionSupplier = null;
	}

	@JsonIgnore
	public void setVersion(
		UnsafeSupplier<String, Exception> versionUnsafeSupplier) {

		_versionSupplier = () -> {
			try {
				return versionUnsafeSupplier.get();
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
	protected String version;

	@JsonIgnore
	private Supplier<String> _versionSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof App)) {
			return false;
		}

		App app = (App)object;

		return Objects.equals(toString(), app.toString());
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

		Boolean active = getActive();

		if (active != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(active);
		}

		AppDeployment[] appDeployments = getAppDeployments();

		if (appDeployments != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appDeployments\": ");

			sb.append("[");

			for (int i = 0; i < appDeployments.length; i++) {
				sb.append(String.valueOf(appDeployments[i]));

				if ((i + 1) < appDeployments.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		Long dataDefinitionId = getDataDefinitionId();

		if (dataDefinitionId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataDefinitionId\": ");

			sb.append(dataDefinitionId);
		}

		String dataDefinitionName = getDataDefinitionName();

		if (dataDefinitionName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataDefinitionName\": ");

			sb.append("\"");

			sb.append(_escape(dataDefinitionName));

			sb.append("\"");
		}

		Long dataLayoutId = getDataLayoutId();

		if (dataLayoutId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataLayoutId\": ");

			sb.append(dataLayoutId);
		}

		Long dataListViewId = getDataListViewId();

		if (dataListViewId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataListViewId\": ");

			sb.append(dataListViewId);
		}

		Long dataRecordCollectionId = getDataRecordCollectionId();

		if (dataRecordCollectionId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataRecordCollectionId\": ");

			sb.append(dataRecordCollectionId);
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

		Long id = getId();

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		Map<String, Object> name = getName();

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(_toJSON(name));
		}

		String scope = getScope();

		if (scope != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"scope\": ");

			sb.append("\"");

			sb.append(_escape(scope));

			sb.append("\"");
		}

		Long siteId = getSiteId();

		if (siteId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\": ");

			sb.append(siteId);
		}

		Long userId = getUserId();

		if (userId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userId\": ");

			sb.append(userId);
		}

		String version = getVersion();

		if (version != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"version\": ");

			sb.append("\"");

			sb.append(_escape(version));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.app.builder.rest.dto.v1_0.App",
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