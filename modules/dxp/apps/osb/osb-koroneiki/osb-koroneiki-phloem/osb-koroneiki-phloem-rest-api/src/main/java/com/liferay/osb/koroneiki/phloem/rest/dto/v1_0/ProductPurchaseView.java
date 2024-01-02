/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.koroneiki.phloem.rest.dto.v1_0;

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
 * @author Amos Fong
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "An aggregated view of Product Purchases grouped by Product.",
	value = "ProductPurchaseView"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ProductPurchaseView")
public class ProductPurchaseView implements Serializable {

	public static ProductPurchaseView toDTO(String json) {
		return ObjectMapperUtil.readValue(ProductPurchaseView.class, json);
	}

	public static ProductPurchaseView unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			ProductPurchaseView.class, json);
	}

	@Schema
	@Valid
	public Product getProduct() {
		if (_productSupplier != null) {
			product = _productSupplier.get();

			_productSupplier = null;
		}

		return product;
	}

	public void setProduct(Product product) {
		this.product = product;

		_productSupplier = null;
	}

	@JsonIgnore
	public void setProduct(
		UnsafeSupplier<Product, Exception> productUnsafeSupplier) {

		_productSupplier = () -> {
			try {
				return productUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Product product;

	private Supplier<Product> _productSupplier;

	@Schema
	@Valid
	public ProductConsumption[] getProductConsumptions() {
		if (_productConsumptionsSupplier != null) {
			productConsumptions = _productConsumptionsSupplier.get();

			_productConsumptionsSupplier = null;
		}

		return productConsumptions;
	}

	public void setProductConsumptions(
		ProductConsumption[] productConsumptions) {

		this.productConsumptions = productConsumptions;

		_productConsumptionsSupplier = null;
	}

	@JsonIgnore
	public void setProductConsumptions(
		UnsafeSupplier<ProductConsumption[], Exception>
			productConsumptionsUnsafeSupplier) {

		_productConsumptionsSupplier = () -> {
			try {
				return productConsumptionsUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected ProductConsumption[] productConsumptions;

	private Supplier<ProductConsumption[]> _productConsumptionsSupplier;

	@Schema
	@Valid
	public ProductPurchase[] getProductPurchases() {
		if (_productPurchasesSupplier != null) {
			productPurchases = _productPurchasesSupplier.get();

			_productPurchasesSupplier = null;
		}

		return productPurchases;
	}

	public void setProductPurchases(ProductPurchase[] productPurchases) {
		this.productPurchases = productPurchases;

		_productPurchasesSupplier = null;
	}

	@JsonIgnore
	public void setProductPurchases(
		UnsafeSupplier<ProductPurchase[], Exception>
			productPurchasesUnsafeSupplier) {

		_productPurchasesSupplier = () -> {
			try {
				return productPurchasesUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected ProductPurchase[] productPurchases;

	private Supplier<ProductPurchase[]> _productPurchasesSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ProductPurchaseView)) {
			return false;
		}

		ProductPurchaseView productPurchaseView = (ProductPurchaseView)object;

		return Objects.equals(toString(), productPurchaseView.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Product product = getProduct();

		if (product != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"product\": ");

			sb.append(String.valueOf(product));
		}

		ProductConsumption[] productConsumptions = getProductConsumptions();

		if (productConsumptions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productConsumptions\": ");

			sb.append("[");

			for (int i = 0; i < productConsumptions.length; i++) {
				sb.append(String.valueOf(productConsumptions[i]));

				if ((i + 1) < productConsumptions.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		ProductPurchase[] productPurchases = getProductPurchases();

		if (productPurchases != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productPurchases\": ");

			sb.append("[");

			for (int i = 0; i < productPurchases.length; i++) {
				sb.append(String.valueOf(productPurchases[i]));

				if ((i + 1) < productPurchases.length) {
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
		defaultValue = "com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ProductPurchaseView",
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