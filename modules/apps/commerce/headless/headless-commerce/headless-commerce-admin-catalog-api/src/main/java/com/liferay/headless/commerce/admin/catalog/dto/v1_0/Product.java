/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.dto.v1_0;

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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
@GraphQLName("Product")
@JsonFilter("Liferay.Vulcan")
@Schema(requiredProperties = {"active", "catalogId", "name", "productType"})
@XmlRootElement(name = "Product")
public class Product implements Serializable {

	public static Product toDTO(String json) {
		return ObjectMapperUtil.readValue(Product.class, json);
	}

	public static Product unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Product.class, json);
	}

	@Schema
	@Valid
	public Map<String, Map<String, String>> getActions() {
		if (_actionsSupplier != null) {
			actions = _actionsSupplier.get();

			_actionsSupplier = null;
		}

		return actions;
	}

	public void setActions(Map<String, Map<String, String>> actions) {
		this.actions = actions;

		_actionsSupplier = null;
	}

	@JsonIgnore
	public void setActions(
		UnsafeSupplier<Map<String, Map<String, String>>, Exception>
			actionsUnsafeSupplier) {

		_actionsSupplier = () -> {
			try {
				return actionsUnsafeSupplier.get();
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
	protected Map<String, Map<String, String>> actions;

	@JsonIgnore
	private Supplier<Map<String, Map<String, String>>> _actionsSupplier;

	@Schema(example = "true")
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
	@NotNull
	protected Boolean active;

	@JsonIgnore
	private Supplier<Boolean> _activeSupplier;

	@Schema
	@Valid
	public Attachment[] getAttachments() {
		if (_attachmentsSupplier != null) {
			attachments = _attachmentsSupplier.get();

			_attachmentsSupplier = null;
		}

		return attachments;
	}

	public void setAttachments(Attachment[] attachments) {
		this.attachments = attachments;

		_attachmentsSupplier = null;
	}

	@JsonIgnore
	public void setAttachments(
		UnsafeSupplier<Attachment[], Exception> attachmentsUnsafeSupplier) {

		_attachmentsSupplier = () -> {
			try {
				return attachmentsUnsafeSupplier.get();
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
	protected Attachment[] attachments;

	@JsonIgnore
	private Supplier<Attachment[]> _attachmentsSupplier;

	@Schema
	@Valid
	public Catalog getCatalog() {
		if (_catalogSupplier != null) {
			catalog = _catalogSupplier.get();

			_catalogSupplier = null;
		}

		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;

		_catalogSupplier = null;
	}

	@JsonIgnore
	public void setCatalog(
		UnsafeSupplier<Catalog, Exception> catalogUnsafeSupplier) {

		_catalogSupplier = () -> {
			try {
				return catalogUnsafeSupplier.get();
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
	protected Catalog catalog;

	@JsonIgnore
	private Supplier<Catalog> _catalogSupplier;

	@DecimalMin("0")
	@Schema(example = "30054")
	public Long getCatalogId() {
		if (_catalogIdSupplier != null) {
			catalogId = _catalogIdSupplier.get();

			_catalogIdSupplier = null;
		}

		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;

		_catalogIdSupplier = null;
	}

	@JsonIgnore
	public void setCatalogId(
		UnsafeSupplier<Long, Exception> catalogIdUnsafeSupplier) {

		_catalogIdSupplier = () -> {
			try {
				return catalogIdUnsafeSupplier.get();
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
	@NotNull
	protected Long catalogId;

	@JsonIgnore
	private Supplier<Long> _catalogIdSupplier;

	@Schema
	@Valid
	public Category[] getCategories() {
		if (_categoriesSupplier != null) {
			categories = _categoriesSupplier.get();

			_categoriesSupplier = null;
		}

		return categories;
	}

	public void setCategories(Category[] categories) {
		this.categories = categories;

		_categoriesSupplier = null;
	}

	@JsonIgnore
	public void setCategories(
		UnsafeSupplier<Category[], Exception> categoriesUnsafeSupplier) {

		_categoriesSupplier = () -> {
			try {
				return categoriesUnsafeSupplier.get();
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
	protected Category[] categories;

	@JsonIgnore
	private Supplier<Category[]> _categoriesSupplier;

	@Schema
	@Valid
	public ProductConfiguration getConfiguration() {
		if (_configurationSupplier != null) {
			configuration = _configurationSupplier.get();

			_configurationSupplier = null;
		}

		return configuration;
	}

	public void setConfiguration(ProductConfiguration configuration) {
		this.configuration = configuration;

		_configurationSupplier = null;
	}

	@JsonIgnore
	public void setConfiguration(
		UnsafeSupplier<ProductConfiguration, Exception>
			configurationUnsafeSupplier) {

		_configurationSupplier = () -> {
			try {
				return configurationUnsafeSupplier.get();
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
	protected ProductConfiguration configuration;

	@JsonIgnore
	private Supplier<ProductConfiguration> _configurationSupplier;

	@Schema(example = "2017-07-21")
	public Date getCreateDate() {
		if (_createDateSupplier != null) {
			createDate = _createDateSupplier.get();

			_createDateSupplier = null;
		}

		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;

		_createDateSupplier = null;
	}

	@JsonIgnore
	public void setCreateDate(
		UnsafeSupplier<Date, Exception> createDateUnsafeSupplier) {

		_createDateSupplier = () -> {
			try {
				return createDateUnsafeSupplier.get();
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
	protected Date createDate;

	@JsonIgnore
	private Supplier<Date> _createDateSupplier;

	@Schema
	@Valid
	public CustomField[] getCustomFields() {
		if (_customFieldsSupplier != null) {
			customFields = _customFieldsSupplier.get();

			_customFieldsSupplier = null;
		}

		return customFields;
	}

	public void setCustomFields(CustomField[] customFields) {
		this.customFields = customFields;

		_customFieldsSupplier = null;
	}

	@JsonIgnore
	public void setCustomFields(
		UnsafeSupplier<CustomField[], Exception> customFieldsUnsafeSupplier) {

		_customFieldsSupplier = () -> {
			try {
				return customFieldsUnsafeSupplier.get();
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
	protected CustomField[] customFields;

	@JsonIgnore
	private Supplier<CustomField[]> _customFieldsSupplier;

	@Schema(example = "Blue handle, 00001l, 70cm, lifetime warranty")
	public String getDefaultSku() {
		if (_defaultSkuSupplier != null) {
			defaultSku = _defaultSkuSupplier.get();

			_defaultSkuSupplier = null;
		}

		return defaultSku;
	}

	public void setDefaultSku(String defaultSku) {
		this.defaultSku = defaultSku;

		_defaultSkuSupplier = null;
	}

	@JsonIgnore
	public void setDefaultSku(
		UnsafeSupplier<String, Exception> defaultSkuUnsafeSupplier) {

		_defaultSkuSupplier = () -> {
			try {
				return defaultSkuUnsafeSupplier.get();
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
	protected String defaultSku;

	@JsonIgnore
	private Supplier<String> _defaultSkuSupplier;

	@Schema(
		example = "{hu_HU=Product Description HU, hr_HR=Product Description HR, en_US=Professional hand stainless steel saw for wood. Made to last and saw forever. Made of best steel}"
	)
	@Valid
	public Map<String, String> getDescription() {
		if (_descriptionSupplier != null) {
			description = _descriptionSupplier.get();

			_descriptionSupplier = null;
		}

		return description;
	}

	public void setDescription(Map<String, String> description) {
		this.description = description;

		_descriptionSupplier = null;
	}

	@JsonIgnore
	public void setDescription(
		UnsafeSupplier<Map<String, String>, Exception>
			descriptionUnsafeSupplier) {

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

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Map<String, String> description;

	@JsonIgnore
	private Supplier<Map<String, String>> _descriptionSupplier;

	@Schema(example = "2017-07-21")
	public Date getDisplayDate() {
		if (_displayDateSupplier != null) {
			displayDate = _displayDateSupplier.get();

			_displayDateSupplier = null;
		}

		return displayDate;
	}

	public void setDisplayDate(Date displayDate) {
		this.displayDate = displayDate;

		_displayDateSupplier = null;
	}

	@JsonIgnore
	public void setDisplayDate(
		UnsafeSupplier<Date, Exception> displayDateUnsafeSupplier) {

		_displayDateSupplier = () -> {
			try {
				return displayDateUnsafeSupplier.get();
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
	protected Date displayDate;

	@JsonIgnore
	private Supplier<Date> _displayDateSupplier;

	@Schema
	@Valid
	public Map<String, ?> getExpando() {
		if (_expandoSupplier != null) {
			expando = _expandoSupplier.get();

			_expandoSupplier = null;
		}

		return expando;
	}

	public void setExpando(Map<String, ?> expando) {
		this.expando = expando;

		_expandoSupplier = null;
	}

	@JsonIgnore
	public void setExpando(
		UnsafeSupplier<Map<String, ?>, Exception> expandoUnsafeSupplier) {

		_expandoSupplier = () -> {
			try {
				return expandoUnsafeSupplier.get();
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
	protected Map<String, ?> expando;

	@JsonIgnore
	private Supplier<Map<String, ?>> _expandoSupplier;

	@Schema(example = "2017-08-21")
	public Date getExpirationDate() {
		if (_expirationDateSupplier != null) {
			expirationDate = _expirationDateSupplier.get();

			_expirationDateSupplier = null;
		}

		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;

		_expirationDateSupplier = null;
	}

	@JsonIgnore
	public void setExpirationDate(
		UnsafeSupplier<Date, Exception> expirationDateUnsafeSupplier) {

		_expirationDateSupplier = () -> {
			try {
				return expirationDateUnsafeSupplier.get();
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
	protected Date expirationDate;

	@JsonIgnore
	private Supplier<Date> _expirationDateSupplier;

	@Schema(example = "AB-34098-789-N")
	public String getExternalReferenceCode() {
		if (_externalReferenceCodeSupplier != null) {
			externalReferenceCode = _externalReferenceCodeSupplier.get();

			_externalReferenceCodeSupplier = null;
		}

		return externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		this.externalReferenceCode = externalReferenceCode;

		_externalReferenceCodeSupplier = null;
	}

	@JsonIgnore
	public void setExternalReferenceCode(
		UnsafeSupplier<String, Exception> externalReferenceCodeUnsafeSupplier) {

		_externalReferenceCodeSupplier = () -> {
			try {
				return externalReferenceCodeUnsafeSupplier.get();
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
	protected String externalReferenceCode;

	@JsonIgnore
	private Supplier<String> _externalReferenceCodeSupplier;

	@DecimalMin("0")
	@Schema(example = "30130")
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
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long id;

	@JsonIgnore
	private Supplier<Long> _idSupplier;

	@Schema
	@Valid
	public Attachment[] getImages() {
		if (_imagesSupplier != null) {
			images = _imagesSupplier.get();

			_imagesSupplier = null;
		}

		return images;
	}

	public void setImages(Attachment[] images) {
		this.images = images;

		_imagesSupplier = null;
	}

	@JsonIgnore
	public void setImages(
		UnsafeSupplier<Attachment[], Exception> imagesUnsafeSupplier) {

		_imagesSupplier = () -> {
			try {
				return imagesUnsafeSupplier.get();
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
	protected Attachment[] images;

	@JsonIgnore
	private Supplier<Attachment[]> _imagesSupplier;

	@Schema(
		example = "{en_US=Meta description HU, hr_HR=Meta description HU, hu_HU=Meta description HU}"
	)
	@Valid
	public Map<String, String> getMetaDescription() {
		if (_metaDescriptionSupplier != null) {
			metaDescription = _metaDescriptionSupplier.get();

			_metaDescriptionSupplier = null;
		}

		return metaDescription;
	}

	public void setMetaDescription(Map<String, String> metaDescription) {
		this.metaDescription = metaDescription;

		_metaDescriptionSupplier = null;
	}

	@JsonIgnore
	public void setMetaDescription(
		UnsafeSupplier<Map<String, String>, Exception>
			metaDescriptionUnsafeSupplier) {

		_metaDescriptionSupplier = () -> {
			try {
				return metaDescriptionUnsafeSupplier.get();
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
	protected Map<String, String> metaDescription;

	@JsonIgnore
	private Supplier<Map<String, String>> _metaDescriptionSupplier;

	@Schema(
		example = "{en_US=Meta keyword HU, hr_HR=Meta keyword HU, hu_HU=Meta keyword HU}"
	)
	@Valid
	public Map<String, String> getMetaKeyword() {
		if (_metaKeywordSupplier != null) {
			metaKeyword = _metaKeywordSupplier.get();

			_metaKeywordSupplier = null;
		}

		return metaKeyword;
	}

	public void setMetaKeyword(Map<String, String> metaKeyword) {
		this.metaKeyword = metaKeyword;

		_metaKeywordSupplier = null;
	}

	@JsonIgnore
	public void setMetaKeyword(
		UnsafeSupplier<Map<String, String>, Exception>
			metaKeywordUnsafeSupplier) {

		_metaKeywordSupplier = () -> {
			try {
				return metaKeywordUnsafeSupplier.get();
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
	protected Map<String, String> metaKeyword;

	@JsonIgnore
	private Supplier<Map<String, String>> _metaKeywordSupplier;

	@Schema(
		example = "{en_US=Meta title HU, hr_HR=Meta title HU, hu_HU=Meta title HU}"
	)
	@Valid
	public Map<String, String> getMetaTitle() {
		if (_metaTitleSupplier != null) {
			metaTitle = _metaTitleSupplier.get();

			_metaTitleSupplier = null;
		}

		return metaTitle;
	}

	public void setMetaTitle(Map<String, String> metaTitle) {
		this.metaTitle = metaTitle;

		_metaTitleSupplier = null;
	}

	@JsonIgnore
	public void setMetaTitle(
		UnsafeSupplier<Map<String, String>, Exception>
			metaTitleUnsafeSupplier) {

		_metaTitleSupplier = () -> {
			try {
				return metaTitleUnsafeSupplier.get();
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
	protected Map<String, String> metaTitle;

	@JsonIgnore
	private Supplier<Map<String, String>> _metaTitleSupplier;

	@Schema(example = "2017-08-21")
	public Date getModifiedDate() {
		if (_modifiedDateSupplier != null) {
			modifiedDate = _modifiedDateSupplier.get();

			_modifiedDateSupplier = null;
		}

		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;

		_modifiedDateSupplier = null;
	}

	@JsonIgnore
	public void setModifiedDate(
		UnsafeSupplier<Date, Exception> modifiedDateUnsafeSupplier) {

		_modifiedDateSupplier = () -> {
			try {
				return modifiedDateUnsafeSupplier.get();
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
	protected Date modifiedDate;

	@JsonIgnore
	private Supplier<Date> _modifiedDateSupplier;

	@Schema(
		example = "{en_US=Hand Saw, hr_HR=Product Name HR, hu_HU=Product Name HU}"
	)
	@Valid
	public Map<String, String> getName() {
		if (_nameSupplier != null) {
			name = _nameSupplier.get();

			_nameSupplier = null;
		}

		return name;
	}

	public void setName(Map<String, String> name) {
		this.name = name;

		_nameSupplier = null;
	}

	@JsonIgnore
	public void setName(
		UnsafeSupplier<Map<String, String>, Exception> nameUnsafeSupplier) {

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
	@NotNull
	protected Map<String, String> name;

	@JsonIgnore
	private Supplier<Map<String, String>> _nameSupplier;

	@Schema(example = "true")
	public Boolean getNeverExpire() {
		if (_neverExpireSupplier != null) {
			neverExpire = _neverExpireSupplier.get();

			_neverExpireSupplier = null;
		}

		return neverExpire;
	}

	public void setNeverExpire(Boolean neverExpire) {
		this.neverExpire = neverExpire;

		_neverExpireSupplier = null;
	}

	@JsonIgnore
	public void setNeverExpire(
		UnsafeSupplier<Boolean, Exception> neverExpireUnsafeSupplier) {

		_neverExpireSupplier = () -> {
			try {
				return neverExpireUnsafeSupplier.get();
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
	protected Boolean neverExpire;

	@JsonIgnore
	private Supplier<Boolean> _neverExpireSupplier;

	@Schema
	public Long getProductId() {
		if (_productIdSupplier != null) {
			productId = _productIdSupplier.get();

			_productIdSupplier = null;
		}

		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;

		_productIdSupplier = null;
	}

	@JsonIgnore
	public void setProductId(
		UnsafeSupplier<Long, Exception> productIdUnsafeSupplier) {

		_productIdSupplier = () -> {
			try {
				return productIdUnsafeSupplier.get();
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
	protected Long productId;

	@JsonIgnore
	private Supplier<Long> _productIdSupplier;

	@Schema
	@Valid
	public ProductOption[] getProductOptions() {
		if (_productOptionsSupplier != null) {
			productOptions = _productOptionsSupplier.get();

			_productOptionsSupplier = null;
		}

		return productOptions;
	}

	public void setProductOptions(ProductOption[] productOptions) {
		this.productOptions = productOptions;

		_productOptionsSupplier = null;
	}

	@JsonIgnore
	public void setProductOptions(
		UnsafeSupplier<ProductOption[], Exception>
			productOptionsUnsafeSupplier) {

		_productOptionsSupplier = () -> {
			try {
				return productOptionsUnsafeSupplier.get();
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
	protected ProductOption[] productOptions;

	@JsonIgnore
	private Supplier<ProductOption[]> _productOptionsSupplier;

	@Schema
	@Valid
	public ProductSpecification[] getProductSpecifications() {
		if (_productSpecificationsSupplier != null) {
			productSpecifications = _productSpecificationsSupplier.get();

			_productSpecificationsSupplier = null;
		}

		return productSpecifications;
	}

	public void setProductSpecifications(
		ProductSpecification[] productSpecifications) {

		this.productSpecifications = productSpecifications;

		_productSpecificationsSupplier = null;
	}

	@JsonIgnore
	public void setProductSpecifications(
		UnsafeSupplier<ProductSpecification[], Exception>
			productSpecificationsUnsafeSupplier) {

		_productSpecificationsSupplier = () -> {
			try {
				return productSpecificationsUnsafeSupplier.get();
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
	protected ProductSpecification[] productSpecifications;

	@JsonIgnore
	private Supplier<ProductSpecification[]> _productSpecificationsSupplier;

	@Schema
	public Integer getProductStatus() {
		if (_productStatusSupplier != null) {
			productStatus = _productStatusSupplier.get();

			_productStatusSupplier = null;
		}

		return productStatus;
	}

	public void setProductStatus(Integer productStatus) {
		this.productStatus = productStatus;

		_productStatusSupplier = null;
	}

	@JsonIgnore
	public void setProductStatus(
		UnsafeSupplier<Integer, Exception> productStatusUnsafeSupplier) {

		_productStatusSupplier = () -> {
			try {
				return productStatusUnsafeSupplier.get();
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
	protected Integer productStatus;

	@JsonIgnore
	private Supplier<Integer> _productStatusSupplier;

	@Schema(example = "simple")
	public String getProductType() {
		if (_productTypeSupplier != null) {
			productType = _productTypeSupplier.get();

			_productTypeSupplier = null;
		}

		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;

		_productTypeSupplier = null;
	}

	@JsonIgnore
	public void setProductType(
		UnsafeSupplier<String, Exception> productTypeUnsafeSupplier) {

		_productTypeSupplier = () -> {
			try {
				return productTypeUnsafeSupplier.get();
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
	@NotEmpty
	protected String productType;

	@JsonIgnore
	private Supplier<String> _productTypeSupplier;

	@Schema(example = "simple")
	public String getProductTypeI18n() {
		if (_productTypeI18nSupplier != null) {
			productTypeI18n = _productTypeI18nSupplier.get();

			_productTypeI18nSupplier = null;
		}

		return productTypeI18n;
	}

	public void setProductTypeI18n(String productTypeI18n) {
		this.productTypeI18n = productTypeI18n;

		_productTypeI18nSupplier = null;
	}

	@JsonIgnore
	public void setProductTypeI18n(
		UnsafeSupplier<String, Exception> productTypeI18nUnsafeSupplier) {

		_productTypeI18nSupplier = () -> {
			try {
				return productTypeI18nUnsafeSupplier.get();
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
	protected String productTypeI18n;

	@JsonIgnore
	private Supplier<String> _productTypeI18nSupplier;

	@Schema
	@Valid
	public RelatedProduct[] getRelatedProducts() {
		if (_relatedProductsSupplier != null) {
			relatedProducts = _relatedProductsSupplier.get();

			_relatedProductsSupplier = null;
		}

		return relatedProducts;
	}

	public void setRelatedProducts(RelatedProduct[] relatedProducts) {
		this.relatedProducts = relatedProducts;

		_relatedProductsSupplier = null;
	}

	@JsonIgnore
	public void setRelatedProducts(
		UnsafeSupplier<RelatedProduct[], Exception>
			relatedProductsUnsafeSupplier) {

		_relatedProductsSupplier = () -> {
			try {
				return relatedProductsUnsafeSupplier.get();
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
	protected RelatedProduct[] relatedProducts;

	@JsonIgnore
	private Supplier<RelatedProduct[]> _relatedProductsSupplier;

	@Schema
	@Valid
	public ProductShippingConfiguration getShippingConfiguration() {
		if (_shippingConfigurationSupplier != null) {
			shippingConfiguration = _shippingConfigurationSupplier.get();

			_shippingConfigurationSupplier = null;
		}

		return shippingConfiguration;
	}

	public void setShippingConfiguration(
		ProductShippingConfiguration shippingConfiguration) {

		this.shippingConfiguration = shippingConfiguration;

		_shippingConfigurationSupplier = null;
	}

	@JsonIgnore
	public void setShippingConfiguration(
		UnsafeSupplier<ProductShippingConfiguration, Exception>
			shippingConfigurationUnsafeSupplier) {

		_shippingConfigurationSupplier = () -> {
			try {
				return shippingConfigurationUnsafeSupplier.get();
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
	protected ProductShippingConfiguration shippingConfiguration;

	@JsonIgnore
	private Supplier<ProductShippingConfiguration>
		_shippingConfigurationSupplier;

	@Schema(
		example = "{en_US=Hand stainless steel saw for wood, hr_HR=Product Short Description HR, hu_HU=Product Short Description HU}"
	)
	@Valid
	public Map<String, String> getShortDescription() {
		if (_shortDescriptionSupplier != null) {
			shortDescription = _shortDescriptionSupplier.get();

			_shortDescriptionSupplier = null;
		}

		return shortDescription;
	}

	public void setShortDescription(Map<String, String> shortDescription) {
		this.shortDescription = shortDescription;

		_shortDescriptionSupplier = null;
	}

	@JsonIgnore
	public void setShortDescription(
		UnsafeSupplier<Map<String, String>, Exception>
			shortDescriptionUnsafeSupplier) {

		_shortDescriptionSupplier = () -> {
			try {
				return shortDescriptionUnsafeSupplier.get();
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
	protected Map<String, String> shortDescription;

	@JsonIgnore
	private Supplier<Map<String, String>> _shortDescriptionSupplier;

	@Schema(example = "default")
	public String getSkuFormatted() {
		if (_skuFormattedSupplier != null) {
			skuFormatted = _skuFormattedSupplier.get();

			_skuFormattedSupplier = null;
		}

		return skuFormatted;
	}

	public void setSkuFormatted(String skuFormatted) {
		this.skuFormatted = skuFormatted;

		_skuFormattedSupplier = null;
	}

	@JsonIgnore
	public void setSkuFormatted(
		UnsafeSupplier<String, Exception> skuFormattedUnsafeSupplier) {

		_skuFormattedSupplier = () -> {
			try {
				return skuFormattedUnsafeSupplier.get();
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
	protected String skuFormatted;

	@JsonIgnore
	private Supplier<String> _skuFormattedSupplier;

	@Schema
	@Valid
	public Sku[] getSkus() {
		if (_skusSupplier != null) {
			skus = _skusSupplier.get();

			_skusSupplier = null;
		}

		return skus;
	}

	public void setSkus(Sku[] skus) {
		this.skus = skus;

		_skusSupplier = null;
	}

	@JsonIgnore
	public void setSkus(UnsafeSupplier<Sku[], Exception> skusUnsafeSupplier) {
		_skusSupplier = () -> {
			try {
				return skusUnsafeSupplier.get();
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
	protected Sku[] skus;

	@JsonIgnore
	private Supplier<Sku[]> _skusSupplier;

	@Schema
	@Valid
	public ProductSubscriptionConfiguration getSubscriptionConfiguration() {
		if (_subscriptionConfigurationSupplier != null) {
			subscriptionConfiguration =
				_subscriptionConfigurationSupplier.get();

			_subscriptionConfigurationSupplier = null;
		}

		return subscriptionConfiguration;
	}

	public void setSubscriptionConfiguration(
		ProductSubscriptionConfiguration subscriptionConfiguration) {

		this.subscriptionConfiguration = subscriptionConfiguration;

		_subscriptionConfigurationSupplier = null;
	}

	@JsonIgnore
	public void setSubscriptionConfiguration(
		UnsafeSupplier<ProductSubscriptionConfiguration, Exception>
			subscriptionConfigurationUnsafeSupplier) {

		_subscriptionConfigurationSupplier = () -> {
			try {
				return subscriptionConfigurationUnsafeSupplier.get();
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
	protected ProductSubscriptionConfiguration subscriptionConfiguration;

	@JsonIgnore
	private Supplier<ProductSubscriptionConfiguration>
		_subscriptionConfigurationSupplier;

	@Schema(example = "[tag1, tag2, tag3]")
	public String[] getTags() {
		if (_tagsSupplier != null) {
			tags = _tagsSupplier.get();

			_tagsSupplier = null;
		}

		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;

		_tagsSupplier = null;
	}

	@JsonIgnore
	public void setTags(
		UnsafeSupplier<String[], Exception> tagsUnsafeSupplier) {

		_tagsSupplier = () -> {
			try {
				return tagsUnsafeSupplier.get();
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
	protected String[] tags;

	@JsonIgnore
	private Supplier<String[]> _tagsSupplier;

	@Schema
	@Valid
	public ProductTaxConfiguration getTaxConfiguration() {
		if (_taxConfigurationSupplier != null) {
			taxConfiguration = _taxConfigurationSupplier.get();

			_taxConfigurationSupplier = null;
		}

		return taxConfiguration;
	}

	public void setTaxConfiguration(ProductTaxConfiguration taxConfiguration) {
		this.taxConfiguration = taxConfiguration;

		_taxConfigurationSupplier = null;
	}

	@JsonIgnore
	public void setTaxConfiguration(
		UnsafeSupplier<ProductTaxConfiguration, Exception>
			taxConfigurationUnsafeSupplier) {

		_taxConfigurationSupplier = () -> {
			try {
				return taxConfigurationUnsafeSupplier.get();
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
	protected ProductTaxConfiguration taxConfiguration;

	@JsonIgnore
	private Supplier<ProductTaxConfiguration> _taxConfigurationSupplier;

	@Schema(example = "simple")
	public String getThumbnail() {
		if (_thumbnailSupplier != null) {
			thumbnail = _thumbnailSupplier.get();

			_thumbnailSupplier = null;
		}

		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;

		_thumbnailSupplier = null;
	}

	@JsonIgnore
	public void setThumbnail(
		UnsafeSupplier<String, Exception> thumbnailUnsafeSupplier) {

		_thumbnailSupplier = () -> {
			try {
				return thumbnailUnsafeSupplier.get();
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
	protected String thumbnail;

	@JsonIgnore
	private Supplier<String> _thumbnailSupplier;

	@Schema(
		example = "{en_US=product-url-us, hr_HR=product-url-hr, hu_HU=product-url-hu}"
	)
	@Valid
	public Map<String, String> getUrls() {
		if (_urlsSupplier != null) {
			urls = _urlsSupplier.get();

			_urlsSupplier = null;
		}

		return urls;
	}

	public void setUrls(Map<String, String> urls) {
		this.urls = urls;

		_urlsSupplier = null;
	}

	@JsonIgnore
	public void setUrls(
		UnsafeSupplier<Map<String, String>, Exception> urlsUnsafeSupplier) {

		_urlsSupplier = () -> {
			try {
				return urlsUnsafeSupplier.get();
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
	protected Map<String, String> urls;

	@JsonIgnore
	private Supplier<Map<String, String>> _urlsSupplier;

	@Schema
	@Valid
	public Status getWorkflowStatusInfo() {
		if (_workflowStatusInfoSupplier != null) {
			workflowStatusInfo = _workflowStatusInfoSupplier.get();

			_workflowStatusInfoSupplier = null;
		}

		return workflowStatusInfo;
	}

	public void setWorkflowStatusInfo(Status workflowStatusInfo) {
		this.workflowStatusInfo = workflowStatusInfo;

		_workflowStatusInfoSupplier = null;
	}

	@JsonIgnore
	public void setWorkflowStatusInfo(
		UnsafeSupplier<Status, Exception> workflowStatusInfoUnsafeSupplier) {

		_workflowStatusInfoSupplier = () -> {
			try {
				return workflowStatusInfoUnsafeSupplier.get();
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
	protected Status workflowStatusInfo;

	@JsonIgnore
	private Supplier<Status> _workflowStatusInfoSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Product)) {
			return false;
		}

		Product product = (Product)object;

		return Objects.equals(toString(), product.toString());
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

		Map<String, Map<String, String>> actions = getActions();

		if (actions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(actions));
		}

		Boolean active = getActive();

		if (active != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(active);
		}

		Attachment[] attachments = getAttachments();

		if (attachments != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"attachments\": ");

			sb.append("[");

			for (int i = 0; i < attachments.length; i++) {
				sb.append(String.valueOf(attachments[i]));

				if ((i + 1) < attachments.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		Catalog catalog = getCatalog();

		if (catalog != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"catalog\": ");

			sb.append(String.valueOf(catalog));
		}

		Long catalogId = getCatalogId();

		if (catalogId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"catalogId\": ");

			sb.append(catalogId);
		}

		Category[] categories = getCategories();

		if (categories != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"categories\": ");

			sb.append("[");

			for (int i = 0; i < categories.length; i++) {
				sb.append(String.valueOf(categories[i]));

				if ((i + 1) < categories.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		ProductConfiguration configuration = getConfiguration();

		if (configuration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"configuration\": ");

			sb.append(String.valueOf(configuration));
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(createDate));

			sb.append("\"");
		}

		CustomField[] customFields = getCustomFields();

		if (customFields != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append("[");

			for (int i = 0; i < customFields.length; i++) {
				sb.append(String.valueOf(customFields[i]));

				if ((i + 1) < customFields.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		String defaultSku = getDefaultSku();

		if (defaultSku != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultSku\": ");

			sb.append("\"");

			sb.append(_escape(defaultSku));

			sb.append("\"");
		}

		Map<String, String> description = getDescription();

		if (description != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append(_toJSON(description));
		}

		Date displayDate = getDisplayDate();

		if (displayDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(displayDate));

			sb.append("\"");
		}

		Map<String, ?> expando = getExpando();

		if (expando != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expando\": ");

			sb.append(_toJSON(expando));
		}

		Date expirationDate = getExpirationDate();

		if (expirationDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expirationDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(expirationDate));

			sb.append("\"");
		}

		String externalReferenceCode = getExternalReferenceCode();

		if (externalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(externalReferenceCode));

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

		Attachment[] images = getImages();

		if (images != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"images\": ");

			sb.append("[");

			for (int i = 0; i < images.length; i++) {
				sb.append(String.valueOf(images[i]));

				if ((i + 1) < images.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		Map<String, String> metaDescription = getMetaDescription();

		if (metaDescription != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"metaDescription\": ");

			sb.append(_toJSON(metaDescription));
		}

		Map<String, String> metaKeyword = getMetaKeyword();

		if (metaKeyword != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"metaKeyword\": ");

			sb.append(_toJSON(metaKeyword));
		}

		Map<String, String> metaTitle = getMetaTitle();

		if (metaTitle != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"metaTitle\": ");

			sb.append(_toJSON(metaTitle));
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(modifiedDate));

			sb.append("\"");
		}

		Map<String, String> name = getName();

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(_toJSON(name));
		}

		Boolean neverExpire = getNeverExpire();

		if (neverExpire != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"neverExpire\": ");

			sb.append(neverExpire);
		}

		Long productId = getProductId();

		if (productId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productId\": ");

			sb.append(productId);
		}

		ProductOption[] productOptions = getProductOptions();

		if (productOptions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productOptions\": ");

			sb.append("[");

			for (int i = 0; i < productOptions.length; i++) {
				sb.append(String.valueOf(productOptions[i]));

				if ((i + 1) < productOptions.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		ProductSpecification[] productSpecifications =
			getProductSpecifications();

		if (productSpecifications != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productSpecifications\": ");

			sb.append("[");

			for (int i = 0; i < productSpecifications.length; i++) {
				sb.append(String.valueOf(productSpecifications[i]));

				if ((i + 1) < productSpecifications.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		Integer productStatus = getProductStatus();

		if (productStatus != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productStatus\": ");

			sb.append(productStatus);
		}

		String productType = getProductType();

		if (productType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productType\": ");

			sb.append("\"");

			sb.append(_escape(productType));

			sb.append("\"");
		}

		String productTypeI18n = getProductTypeI18n();

		if (productTypeI18n != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productTypeI18n\": ");

			sb.append("\"");

			sb.append(_escape(productTypeI18n));

			sb.append("\"");
		}

		RelatedProduct[] relatedProducts = getRelatedProducts();

		if (relatedProducts != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"relatedProducts\": ");

			sb.append("[");

			for (int i = 0; i < relatedProducts.length; i++) {
				sb.append(String.valueOf(relatedProducts[i]));

				if ((i + 1) < relatedProducts.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		ProductShippingConfiguration shippingConfiguration =
			getShippingConfiguration();

		if (shippingConfiguration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingConfiguration\": ");

			sb.append(String.valueOf(shippingConfiguration));
		}

		Map<String, String> shortDescription = getShortDescription();

		if (shortDescription != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shortDescription\": ");

			sb.append(_toJSON(shortDescription));
		}

		String skuFormatted = getSkuFormatted();

		if (skuFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuFormatted\": ");

			sb.append("\"");

			sb.append(_escape(skuFormatted));

			sb.append("\"");
		}

		Sku[] skus = getSkus();

		if (skus != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skus\": ");

			sb.append("[");

			for (int i = 0; i < skus.length; i++) {
				sb.append(String.valueOf(skus[i]));

				if ((i + 1) < skus.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		ProductSubscriptionConfiguration subscriptionConfiguration =
			getSubscriptionConfiguration();

		if (subscriptionConfiguration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subscriptionConfiguration\": ");

			sb.append(String.valueOf(subscriptionConfiguration));
		}

		String[] tags = getTags();

		if (tags != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"tags\": ");

			sb.append("[");

			for (int i = 0; i < tags.length; i++) {
				sb.append("\"");

				sb.append(_escape(tags[i]));

				sb.append("\"");

				if ((i + 1) < tags.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		ProductTaxConfiguration taxConfiguration = getTaxConfiguration();

		if (taxConfiguration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxConfiguration\": ");

			sb.append(String.valueOf(taxConfiguration));
		}

		String thumbnail = getThumbnail();

		if (thumbnail != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"thumbnail\": ");

			sb.append("\"");

			sb.append(_escape(thumbnail));

			sb.append("\"");
		}

		Map<String, String> urls = getUrls();

		if (urls != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"urls\": ");

			sb.append(_toJSON(urls));
		}

		Status workflowStatusInfo = getWorkflowStatusInfo();

		if (workflowStatusInfo != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowStatusInfo\": ");

			sb.append(String.valueOf(workflowStatusInfo));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product",
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