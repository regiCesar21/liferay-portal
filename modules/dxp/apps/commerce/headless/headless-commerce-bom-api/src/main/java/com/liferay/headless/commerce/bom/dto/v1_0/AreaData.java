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
@GraphQLName("AreaData")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AreaData")
public class AreaData implements Serializable {

	public static AreaData toDTO(String json) {
		return ObjectMapperUtil.readValue(AreaData.class, json);
	}

	public static AreaData unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(AreaData.class, json);
	}

	@Schema(example = "Name 1")
	public String getId() {
		if (_idSupplier != null) {
			id = _idSupplier.get();

			_idSupplier = null;
		}

		return id;
	}

	public void setId(String id) {
		this.id = id;

		_idSupplier = null;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<String, Exception> idUnsafeSupplier) {
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
	protected String id;

	@JsonIgnore
	private Supplier<String> _idSupplier;

	@Schema(example = "Name 1")
	public String getImageUrl() {
		if (_imageUrlSupplier != null) {
			imageUrl = _imageUrlSupplier.get();

			_imageUrlSupplier = null;
		}

		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;

		_imageUrlSupplier = null;
	}

	@JsonIgnore
	public void setImageUrl(
		UnsafeSupplier<String, Exception> imageUrlUnsafeSupplier) {

		_imageUrlSupplier = () -> {
			try {
				return imageUrlUnsafeSupplier.get();
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
	protected String imageUrl;

	@JsonIgnore
	private Supplier<String> _imageUrlSupplier;

	@Schema(example = "Name 1")
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

	@Schema(
		example = "[{id=29130, name=Product 1, price=$ 12.99, sku=SKU01, thumbnailUrl=/product_thumbnail.png, url=/productUrl}]"
	)
	@Valid
	public Product[] getProducts() {
		if (_productsSupplier != null) {
			products = _productsSupplier.get();

			_productsSupplier = null;
		}

		return products;
	}

	public void setProducts(Product[] products) {
		this.products = products;

		_productsSupplier = null;
	}

	@JsonIgnore
	public void setProducts(
		UnsafeSupplier<Product[], Exception> productsUnsafeSupplier) {

		_productsSupplier = () -> {
			try {
				return productsUnsafeSupplier.get();
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
	protected Product[] products;

	@JsonIgnore
	private Supplier<Product[]> _productsSupplier;

	@Schema(
		example = "[{id=34130, number=3, positionX=33.54, positionY=33.54, productId=29130}]"
	)
	@Valid
	public Spot[] getSpots() {
		if (_spotsSupplier != null) {
			spots = _spotsSupplier.get();

			_spotsSupplier = null;
		}

		return spots;
	}

	public void setSpots(Spot[] spots) {
		this.spots = spots;

		_spotsSupplier = null;
	}

	@JsonIgnore
	public void setSpots(
		UnsafeSupplier<Spot[], Exception> spotsUnsafeSupplier) {

		_spotsSupplier = () -> {
			try {
				return spotsUnsafeSupplier.get();
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
	protected Spot[] spots;

	@JsonIgnore
	private Supplier<Spot[]> _spotsSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AreaData)) {
			return false;
		}

		AreaData areaData = (AreaData)object;

		return Objects.equals(toString(), areaData.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		String id = getId();

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append("\"");

			sb.append(_escape(id));

			sb.append("\"");
		}

		String imageUrl = getImageUrl();

		if (imageUrl != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"imageUrl\": ");

			sb.append("\"");

			sb.append(_escape(imageUrl));

			sb.append("\"");
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

		Product[] products = getProducts();

		if (products != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"products\": ");

			sb.append("[");

			for (int i = 0; i < products.length; i++) {
				sb.append(String.valueOf(products[i]));

				if ((i + 1) < products.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		Spot[] spots = getSpots();

		if (spots != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"spots\": ");

			sb.append("[");

			for (int i = 0; i < spots.length; i++) {
				sb.append(String.valueOf(spots[i]));

				if ((i + 1) < spots.length) {
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
		defaultValue = "com.liferay.headless.commerce.bom.dto.v1_0.AreaData",
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