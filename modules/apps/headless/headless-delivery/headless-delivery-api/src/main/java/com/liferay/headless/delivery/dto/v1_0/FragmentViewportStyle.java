/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.dto.v1_0;

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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("FragmentViewportStyle")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "FragmentViewportStyle")
public class FragmentViewportStyle implements Serializable {

	public static FragmentViewportStyle toDTO(String json) {
		return ObjectMapperUtil.readValue(FragmentViewportStyle.class, json);
	}

	public static FragmentViewportStyle unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			FragmentViewportStyle.class, json);
	}

	@Schema
	public String getMarginBottom() {
		if (_marginBottomSupplier != null) {
			marginBottom = _marginBottomSupplier.get();

			_marginBottomSupplier = null;
		}

		return marginBottom;
	}

	public void setMarginBottom(String marginBottom) {
		this.marginBottom = marginBottom;

		_marginBottomSupplier = null;
	}

	@JsonIgnore
	public void setMarginBottom(
		UnsafeSupplier<String, Exception> marginBottomUnsafeSupplier) {

		_marginBottomSupplier = () -> {
			try {
				return marginBottomUnsafeSupplier.get();
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
	protected String marginBottom;

	@JsonIgnore
	private Supplier<String> _marginBottomSupplier;

	@Schema
	public String getMarginLeft() {
		if (_marginLeftSupplier != null) {
			marginLeft = _marginLeftSupplier.get();

			_marginLeftSupplier = null;
		}

		return marginLeft;
	}

	public void setMarginLeft(String marginLeft) {
		this.marginLeft = marginLeft;

		_marginLeftSupplier = null;
	}

	@JsonIgnore
	public void setMarginLeft(
		UnsafeSupplier<String, Exception> marginLeftUnsafeSupplier) {

		_marginLeftSupplier = () -> {
			try {
				return marginLeftUnsafeSupplier.get();
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
	protected String marginLeft;

	@JsonIgnore
	private Supplier<String> _marginLeftSupplier;

	@Schema
	public String getMarginRight() {
		if (_marginRightSupplier != null) {
			marginRight = _marginRightSupplier.get();

			_marginRightSupplier = null;
		}

		return marginRight;
	}

	public void setMarginRight(String marginRight) {
		this.marginRight = marginRight;

		_marginRightSupplier = null;
	}

	@JsonIgnore
	public void setMarginRight(
		UnsafeSupplier<String, Exception> marginRightUnsafeSupplier) {

		_marginRightSupplier = () -> {
			try {
				return marginRightUnsafeSupplier.get();
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
	protected String marginRight;

	@JsonIgnore
	private Supplier<String> _marginRightSupplier;

	@Schema
	public String getMarginTop() {
		if (_marginTopSupplier != null) {
			marginTop = _marginTopSupplier.get();

			_marginTopSupplier = null;
		}

		return marginTop;
	}

	public void setMarginTop(String marginTop) {
		this.marginTop = marginTop;

		_marginTopSupplier = null;
	}

	@JsonIgnore
	public void setMarginTop(
		UnsafeSupplier<String, Exception> marginTopUnsafeSupplier) {

		_marginTopSupplier = () -> {
			try {
				return marginTopUnsafeSupplier.get();
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
	protected String marginTop;

	@JsonIgnore
	private Supplier<String> _marginTopSupplier;

	@Schema
	public String getPaddingBottom() {
		if (_paddingBottomSupplier != null) {
			paddingBottom = _paddingBottomSupplier.get();

			_paddingBottomSupplier = null;
		}

		return paddingBottom;
	}

	public void setPaddingBottom(String paddingBottom) {
		this.paddingBottom = paddingBottom;

		_paddingBottomSupplier = null;
	}

	@JsonIgnore
	public void setPaddingBottom(
		UnsafeSupplier<String, Exception> paddingBottomUnsafeSupplier) {

		_paddingBottomSupplier = () -> {
			try {
				return paddingBottomUnsafeSupplier.get();
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
	protected String paddingBottom;

	@JsonIgnore
	private Supplier<String> _paddingBottomSupplier;

	@Schema
	public String getPaddingLeft() {
		if (_paddingLeftSupplier != null) {
			paddingLeft = _paddingLeftSupplier.get();

			_paddingLeftSupplier = null;
		}

		return paddingLeft;
	}

	public void setPaddingLeft(String paddingLeft) {
		this.paddingLeft = paddingLeft;

		_paddingLeftSupplier = null;
	}

	@JsonIgnore
	public void setPaddingLeft(
		UnsafeSupplier<String, Exception> paddingLeftUnsafeSupplier) {

		_paddingLeftSupplier = () -> {
			try {
				return paddingLeftUnsafeSupplier.get();
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
	protected String paddingLeft;

	@JsonIgnore
	private Supplier<String> _paddingLeftSupplier;

	@Schema
	public String getPaddingRight() {
		if (_paddingRightSupplier != null) {
			paddingRight = _paddingRightSupplier.get();

			_paddingRightSupplier = null;
		}

		return paddingRight;
	}

	public void setPaddingRight(String paddingRight) {
		this.paddingRight = paddingRight;

		_paddingRightSupplier = null;
	}

	@JsonIgnore
	public void setPaddingRight(
		UnsafeSupplier<String, Exception> paddingRightUnsafeSupplier) {

		_paddingRightSupplier = () -> {
			try {
				return paddingRightUnsafeSupplier.get();
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
	protected String paddingRight;

	@JsonIgnore
	private Supplier<String> _paddingRightSupplier;

	@Schema
	public String getPaddingTop() {
		if (_paddingTopSupplier != null) {
			paddingTop = _paddingTopSupplier.get();

			_paddingTopSupplier = null;
		}

		return paddingTop;
	}

	public void setPaddingTop(String paddingTop) {
		this.paddingTop = paddingTop;

		_paddingTopSupplier = null;
	}

	@JsonIgnore
	public void setPaddingTop(
		UnsafeSupplier<String, Exception> paddingTopUnsafeSupplier) {

		_paddingTopSupplier = () -> {
			try {
				return paddingTopUnsafeSupplier.get();
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
	protected String paddingTop;

	@JsonIgnore
	private Supplier<String> _paddingTopSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentViewportStyle)) {
			return false;
		}

		FragmentViewportStyle fragmentViewportStyle =
			(FragmentViewportStyle)object;

		return Objects.equals(toString(), fragmentViewportStyle.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		String marginBottom = getMarginBottom();

		if (marginBottom != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"marginBottom\": ");

			sb.append("\"");

			sb.append(_escape(marginBottom));

			sb.append("\"");
		}

		String marginLeft = getMarginLeft();

		if (marginLeft != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"marginLeft\": ");

			sb.append("\"");

			sb.append(_escape(marginLeft));

			sb.append("\"");
		}

		String marginRight = getMarginRight();

		if (marginRight != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"marginRight\": ");

			sb.append("\"");

			sb.append(_escape(marginRight));

			sb.append("\"");
		}

		String marginTop = getMarginTop();

		if (marginTop != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"marginTop\": ");

			sb.append("\"");

			sb.append(_escape(marginTop));

			sb.append("\"");
		}

		String paddingBottom = getPaddingBottom();

		if (paddingBottom != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingBottom\": ");

			sb.append("\"");

			sb.append(_escape(paddingBottom));

			sb.append("\"");
		}

		String paddingLeft = getPaddingLeft();

		if (paddingLeft != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingLeft\": ");

			sb.append("\"");

			sb.append(_escape(paddingLeft));

			sb.append("\"");
		}

		String paddingRight = getPaddingRight();

		if (paddingRight != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingRight\": ");

			sb.append("\"");

			sb.append(_escape(paddingRight));

			sb.append("\"");
		}

		String paddingTop = getPaddingTop();

		if (paddingTop != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paddingTop\": ");

			sb.append("\"");

			sb.append(_escape(paddingTop));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.FragmentViewportStyle",
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