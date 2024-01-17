/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.bom.dto.v1_0;

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
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
@GraphQLName("Folder")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Folder")
public class Folder implements Serializable {

	public static Folder toDTO(String json) {
		return ObjectMapperUtil.readValue(Folder.class, json);
	}

	public static Folder unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Folder.class, json);
	}

	@Schema(
		example = "[{id=31130, name=Item 1, slug=/Item-1, street2=/Item/31130, thumbnail=/schema.jpg}]"
	)
	@Valid
	public Breadcrumb[] getBreadcrumbs() {
		if (_breadcrumbsSupplier != null) {
			breadcrumbs = _breadcrumbsSupplier.get();

			_breadcrumbsSupplier = null;
		}

		return breadcrumbs;
	}

	public void setBreadcrumbs(Breadcrumb[] breadcrumbs) {
		this.breadcrumbs = breadcrumbs;

		_breadcrumbsSupplier = null;
	}

	@JsonIgnore
	public void setBreadcrumbs(
		UnsafeSupplier<Breadcrumb[], Exception> breadcrumbsUnsafeSupplier) {

		_breadcrumbsSupplier = () -> {
			try {
				return breadcrumbsUnsafeSupplier.get();
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
	protected Breadcrumb[] breadcrumbs;

	@JsonIgnore
	private Supplier<Breadcrumb[]> _breadcrumbsSupplier;

	@Schema
	@Valid
	public ItemData getData() {
		if (_dataSupplier != null) {
			data = _dataSupplier.get();

			_dataSupplier = null;
		}

		return data;
	}

	public void setData(ItemData data) {
		this.data = data;

		_dataSupplier = null;
	}

	@JsonIgnore
	public void setData(
		UnsafeSupplier<ItemData, Exception> dataUnsafeSupplier) {

		_dataSupplier = () -> {
			try {
				return dataUnsafeSupplier.get();
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
	protected ItemData data;

	@JsonIgnore
	private Supplier<ItemData> _dataSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Folder)) {
			return false;
		}

		Folder folder = (Folder)object;

		return Objects.equals(toString(), folder.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Breadcrumb[] breadcrumbs = getBreadcrumbs();

		if (breadcrumbs != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"breadcrumbs\": ");

			sb.append("[");

			for (int i = 0; i < breadcrumbs.length; i++) {
				sb.append(String.valueOf(breadcrumbs[i]));

				if ((i + 1) < breadcrumbs.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		ItemData data = getData();

		if (data != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"data\": ");

			sb.append(String.valueOf(data));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.commerce.bom.dto.v1_0.Folder",
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