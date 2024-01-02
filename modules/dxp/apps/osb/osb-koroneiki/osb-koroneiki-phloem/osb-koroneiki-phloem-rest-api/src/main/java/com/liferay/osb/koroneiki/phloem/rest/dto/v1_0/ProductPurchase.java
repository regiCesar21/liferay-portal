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
	description = "Represents a product purchase.", value = "ProductPurchase"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ProductPurchase")
public class ProductPurchase implements Serializable {

	public static ProductPurchase toDTO(String json) {
		return ObjectMapperUtil.readValue(ProductPurchase.class, json);
	}

	public static ProductPurchase unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(ProductPurchase.class, json);
	}

	@Schema(description = "The key of the account purchasing the product.")
	public String getAccountKey() {
		if (_accountKeySupplier != null) {
			accountKey = _accountKeySupplier.get();

			_accountKeySupplier = null;
		}

		return accountKey;
	}

	public void setAccountKey(String accountKey) {
		this.accountKey = accountKey;

		_accountKeySupplier = null;
	}

	@JsonIgnore
	public void setAccountKey(
		UnsafeSupplier<String, Exception> accountKeyUnsafeSupplier) {

		_accountKeySupplier = () -> {
			try {
				return accountKeyUnsafeSupplier.get();
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
		description = "The key of the account purchasing the product."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String accountKey;

	private Supplier<String> _accountKeySupplier;

	@Schema(description = "The product purchase's creation date.")
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

	@GraphQLField(description = "The product purchase's creation date.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateCreated;

	private Supplier<Date> _dateCreatedSupplier;

	@Schema(
		description = "The product purchase's actual end date including extensions or grace periods."
	)
	public Date getEndDate() {
		if (_endDateSupplier != null) {
			endDate = _endDateSupplier.get();

			_endDateSupplier = null;
		}

		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;

		_endDateSupplier = null;
	}

	@JsonIgnore
	public void setEndDate(
		UnsafeSupplier<Date, Exception> endDateUnsafeSupplier) {

		_endDateSupplier = () -> {
			try {
				return endDateUnsafeSupplier.get();
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
		description = "The product purchase's actual end date including extensions or grace periods."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Date endDate;

	private Supplier<Date> _endDateSupplier;

	@Schema(
		description = "The product purchase's links to entities in external domains."
	)
	@Valid
	public ExternalLink[] getExternalLinks() {
		if (_externalLinksSupplier != null) {
			externalLinks = _externalLinksSupplier.get();

			_externalLinksSupplier = null;
		}

		return externalLinks;
	}

	public void setExternalLinks(ExternalLink[] externalLinks) {
		this.externalLinks = externalLinks;

		_externalLinksSupplier = null;
	}

	@JsonIgnore
	public void setExternalLinks(
		UnsafeSupplier<ExternalLink[], Exception> externalLinksUnsafeSupplier) {

		_externalLinksSupplier = () -> {
			try {
				return externalLinksUnsafeSupplier.get();
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
		description = "The product purchase's links to entities in external domains."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ExternalLink[] externalLinks;

	private Supplier<ExternalLink[]> _externalLinksSupplier;

	@Schema(description = "The product purchase's key.")
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

	@GraphQLField(description = "The product purchase's key.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String key;

	private Supplier<String> _keySupplier;

	@Schema(
		description = "The product purchase's original end date that was purchased from sales."
	)
	public Date getOriginalEndDate() {
		if (_originalEndDateSupplier != null) {
			originalEndDate = _originalEndDateSupplier.get();

			_originalEndDateSupplier = null;
		}

		return originalEndDate;
	}

	public void setOriginalEndDate(Date originalEndDate) {
		this.originalEndDate = originalEndDate;

		_originalEndDateSupplier = null;
	}

	@JsonIgnore
	public void setOriginalEndDate(
		UnsafeSupplier<Date, Exception> originalEndDateUnsafeSupplier) {

		_originalEndDateSupplier = () -> {
			try {
				return originalEndDateUnsafeSupplier.get();
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
		description = "The product purchase's original end date that was purchased from sales."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Date originalEndDate;

	private Supplier<Date> _originalEndDateSupplier;

	@Schema(
		description = "A flag that identifies if the product purchase has a start and end date."
	)
	public Boolean getPerpetual() {
		if (_perpetualSupplier != null) {
			perpetual = _perpetualSupplier.get();

			_perpetualSupplier = null;
		}

		return perpetual;
	}

	public void setPerpetual(Boolean perpetual) {
		this.perpetual = perpetual;

		_perpetualSupplier = null;
	}

	@JsonIgnore
	public void setPerpetual(
		UnsafeSupplier<Boolean, Exception> perpetualUnsafeSupplier) {

		_perpetualSupplier = () -> {
			try {
				return perpetualUnsafeSupplier.get();
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
		description = "A flag that identifies if the product purchase has a start and end date."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean perpetual;

	private Supplier<Boolean> _perpetualSupplier;

	@Schema(description = "The product that is being purchased.")
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

	@GraphQLField(description = "The product that is being purchased.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Product product;

	private Supplier<Product> _productSupplier;

	@Schema(
		description = "The product consumptions that this purchase has. Optional field that can retrieved with nestedFields."
	)
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

	@GraphQLField(
		description = "The product consumptions that this purchase has. Optional field that can retrieved with nestedFields."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected ProductConsumption[] productConsumptions;

	private Supplier<ProductConsumption[]> _productConsumptionsSupplier;

	@Schema(description = "The key of the product being purchased.")
	public String getProductKey() {
		if (_productKeySupplier != null) {
			productKey = _productKeySupplier.get();

			_productKeySupplier = null;
		}

		return productKey;
	}

	public void setProductKey(String productKey) {
		this.productKey = productKey;

		_productKeySupplier = null;
	}

	@JsonIgnore
	public void setProductKey(
		UnsafeSupplier<String, Exception> productKeyUnsafeSupplier) {

		_productKeySupplier = () -> {
			try {
				return productKeyUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The key of the product being purchased.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String productKey;

	private Supplier<String> _productKeySupplier;

	@Schema
	@Valid
	public Map<String, String> getProperties() {
		if (_propertiesSupplier != null) {
			properties = _propertiesSupplier.get();

			_propertiesSupplier = null;
		}

		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;

		_propertiesSupplier = null;
	}

	@JsonIgnore
	public void setProperties(
		UnsafeSupplier<Map<String, String>, Exception>
			propertiesUnsafeSupplier) {

		_propertiesSupplier = () -> {
			try {
				return propertiesUnsafeSupplier.get();
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
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Map<String, String> properties;

	private Supplier<Map<String, String>> _propertiesSupplier;

	@Schema(description = "The quantity of the product purchased.")
	public Integer getQuantity() {
		if (_quantitySupplier != null) {
			quantity = _quantitySupplier.get();

			_quantitySupplier = null;
		}

		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;

		_quantitySupplier = null;
	}

	@JsonIgnore
	public void setQuantity(
		UnsafeSupplier<Integer, Exception> quantityUnsafeSupplier) {

		_quantitySupplier = () -> {
			try {
				return quantityUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The quantity of the product purchased.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer quantity;

	private Supplier<Integer> _quantitySupplier;

	@Schema(description = "The product purchase's start date.")
	public Date getStartDate() {
		if (_startDateSupplier != null) {
			startDate = _startDateSupplier.get();

			_startDateSupplier = null;
		}

		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;

		_startDateSupplier = null;
	}

	@JsonIgnore
	public void setStartDate(
		UnsafeSupplier<Date, Exception> startDateUnsafeSupplier) {

		_startDateSupplier = () -> {
			try {
				return startDateUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The product purchase's start date.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Date startDate;

	private Supplier<Date> _startDateSupplier;

	@Schema(description = "The workflow status of the product purchase.")
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

	@GraphQLField(description = "The workflow status of the product purchase.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Status status;

	private Supplier<Status> _statusSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ProductPurchase)) {
			return false;
		}

		ProductPurchase productPurchase = (ProductPurchase)object;

		return Objects.equals(toString(), productPurchase.toString());
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

		String accountKey = getAccountKey();

		if (accountKey != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountKey\": ");

			sb.append("\"");

			sb.append(_escape(accountKey));

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

		Date endDate = getEndDate();

		if (endDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"endDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(endDate));

			sb.append("\"");
		}

		ExternalLink[] externalLinks = getExternalLinks();

		if (externalLinks != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalLinks\": ");

			sb.append("[");

			for (int i = 0; i < externalLinks.length; i++) {
				sb.append(String.valueOf(externalLinks[i]));

				if ((i + 1) < externalLinks.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
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

		Date originalEndDate = getOriginalEndDate();

		if (originalEndDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"originalEndDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(originalEndDate));

			sb.append("\"");
		}

		Boolean perpetual = getPerpetual();

		if (perpetual != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"perpetual\": ");

			sb.append(perpetual);
		}

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

		String productKey = getProductKey();

		if (productKey != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productKey\": ");

			sb.append("\"");

			sb.append(_escape(productKey));

			sb.append("\"");
		}

		Map<String, String> properties = getProperties();

		if (properties != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"properties\": ");

			sb.append(_toJSON(properties));
		}

		Integer quantity = getQuantity();

		if (quantity != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"quantity\": ");

			sb.append(quantity);
		}

		Date startDate = getStartDate();

		if (startDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"startDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(startDate));

			sb.append("\"");
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

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ProductPurchase",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("Status")
	public static enum Status {

		APPROVED("Approved"), CANCELLED("Cancelled");

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