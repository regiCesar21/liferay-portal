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
@GraphQLName(description = "Represents an account.", value = "Account")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Account")
public class Account implements Serializable {

	public static Account toDTO(String json) {
		return ObjectMapperUtil.readValue(Account.class, json);
	}

	public static Account unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Account.class, json);
	}

	@Schema(
		description = "The teams that are assigned to this account. Optional field that can retrieved with nestedFields."
	)
	@Valid
	public Team[] getAssignedTeams() {
		if (_assignedTeamsSupplier != null) {
			assignedTeams = _assignedTeamsSupplier.get();

			_assignedTeamsSupplier = null;
		}

		return assignedTeams;
	}

	public void setAssignedTeams(Team[] assignedTeams) {
		this.assignedTeams = assignedTeams;

		_assignedTeamsSupplier = null;
	}

	@JsonIgnore
	public void setAssignedTeams(
		UnsafeSupplier<Team[], Exception> assignedTeamsUnsafeSupplier) {

		_assignedTeamsSupplier = () -> {
			try {
				return assignedTeamsUnsafeSupplier.get();
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
		description = "The teams that are assigned to this account. Optional field that can retrieved with nestedFields."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Team[] assignedTeams;

	private Supplier<Team[]> _assignedTeamsSupplier;

	@Schema(description = "The code of the account.")
	public String getCode() {
		if (_codeSupplier != null) {
			code = _codeSupplier.get();

			_codeSupplier = null;
		}

		return code;
	}

	public void setCode(String code) {
		this.code = code;

		_codeSupplier = null;
	}

	@JsonIgnore
	public void setCode(UnsafeSupplier<String, Exception> codeUnsafeSupplier) {
		_codeSupplier = () -> {
			try {
				return codeUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The code of the account.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String code;

	private Supplier<String> _codeSupplier;

	@Schema(description = "The account's contact email address.")
	public String getContactEmailAddress() {
		if (_contactEmailAddressSupplier != null) {
			contactEmailAddress = _contactEmailAddressSupplier.get();

			_contactEmailAddressSupplier = null;
		}

		return contactEmailAddress;
	}

	public void setContactEmailAddress(String contactEmailAddress) {
		this.contactEmailAddress = contactEmailAddress;

		_contactEmailAddressSupplier = null;
	}

	@JsonIgnore
	public void setContactEmailAddress(
		UnsafeSupplier<String, Exception> contactEmailAddressUnsafeSupplier) {

		_contactEmailAddressSupplier = () -> {
			try {
				return contactEmailAddressUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The account's contact email address.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String contactEmailAddress;

	private Supplier<String> _contactEmailAddressSupplier;

	@Schema(
		description = "The account's contacts. Optional field that can retrieved with nestedFields."
	)
	@Valid
	public Contact[] getContacts() {
		if (_contactsSupplier != null) {
			contacts = _contactsSupplier.get();

			_contactsSupplier = null;
		}

		return contacts;
	}

	public void setContacts(Contact[] contacts) {
		this.contacts = contacts;

		_contactsSupplier = null;
	}

	@JsonIgnore
	public void setContacts(
		UnsafeSupplier<Contact[], Exception> contactsUnsafeSupplier) {

		_contactsSupplier = () -> {
			try {
				return contactsUnsafeSupplier.get();
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
		description = "The account's contacts. Optional field that can retrieved with nestedFields."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Contact[] contacts;

	private Supplier<Contact[]> _contactsSupplier;

	@Schema(
		description = "The account's customer contacts. Optional field that can retrieved with nestedFields."
	)
	@Valid
	public Contact[] getCustomerContacts() {
		if (_customerContactsSupplier != null) {
			customerContacts = _customerContactsSupplier.get();

			_customerContactsSupplier = null;
		}

		return customerContacts;
	}

	public void setCustomerContacts(Contact[] customerContacts) {
		this.customerContacts = customerContacts;

		_customerContactsSupplier = null;
	}

	@JsonIgnore
	public void setCustomerContacts(
		UnsafeSupplier<Contact[], Exception> customerContactsUnsafeSupplier) {

		_customerContactsSupplier = () -> {
			try {
				return customerContactsUnsafeSupplier.get();
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
		description = "The account's customer contacts. Optional field that can retrieved with nestedFields."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Contact[] customerContacts;

	private Supplier<Contact[]> _customerContactsSupplier;

	@Schema(
		description = "The region where we should store the account's customer owned data."
	)
	@Valid
	public DataRegion getDataRegion() {
		if (_dataRegionSupplier != null) {
			dataRegion = _dataRegionSupplier.get();

			_dataRegionSupplier = null;
		}

		return dataRegion;
	}

	@JsonIgnore
	public String getDataRegionAsString() {
		DataRegion dataRegion = getDataRegion();

		if (dataRegion == null) {
			return null;
		}

		return dataRegion.toString();
	}

	public void setDataRegion(DataRegion dataRegion) {
		this.dataRegion = dataRegion;

		_dataRegionSupplier = null;
	}

	@JsonIgnore
	public void setDataRegion(
		UnsafeSupplier<DataRegion, Exception> dataRegionUnsafeSupplier) {

		_dataRegionSupplier = () -> {
			try {
				return dataRegionUnsafeSupplier.get();
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
		description = "The region where we should store the account's customer owned data."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected DataRegion dataRegion;

	private Supplier<DataRegion> _dataRegionSupplier;

	@Schema(description = "The account's creation date.")
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

	@GraphQLField(description = "The account's creation date.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateCreated;

	private Supplier<Date> _dateCreatedSupplier;

	@Schema(
		description = "The most recent time that any of the account's fields changed."
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
		description = "The most recent time that any of the account's fields changed."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateModified;

	private Supplier<Date> _dateModifiedSupplier;

	@Schema(description = "The description of the account.")
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

	@GraphQLField(description = "The description of the account.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String description;

	private Supplier<String> _descriptionSupplier;

	@Schema(description = "The account's entitlements.")
	@Valid
	public Entitlement[] getEntitlements() {
		if (_entitlementsSupplier != null) {
			entitlements = _entitlementsSupplier.get();

			_entitlementsSupplier = null;
		}

		return entitlements;
	}

	public void setEntitlements(Entitlement[] entitlements) {
		this.entitlements = entitlements;

		_entitlementsSupplier = null;
	}

	@JsonIgnore
	public void setEntitlements(
		UnsafeSupplier<Entitlement[], Exception> entitlementsUnsafeSupplier) {

		_entitlementsSupplier = () -> {
			try {
				return entitlementsUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The account's entitlements.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Entitlement[] entitlements;

	private Supplier<Entitlement[]> _entitlementsSupplier;

	@Schema(
		description = "The account's links to entities in external domains."
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
		description = "The account's links to entities in external domains."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ExternalLink[] externalLinks;

	private Supplier<ExternalLink[]> _externalLinksSupplier;

	@Schema(description = "The account's fax number.")
	public String getFaxNumber() {
		if (_faxNumberSupplier != null) {
			faxNumber = _faxNumberSupplier.get();

			_faxNumberSupplier = null;
		}

		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;

		_faxNumberSupplier = null;
	}

	@JsonIgnore
	public void setFaxNumber(
		UnsafeSupplier<String, Exception> faxNumberUnsafeSupplier) {

		_faxNumberSupplier = () -> {
			try {
				return faxNumberUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The account's fax number.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String faxNumber;

	private Supplier<String> _faxNumberSupplier;

	@Schema(
		description = "A flag that identifies whether this account is an internal or test account."
	)
	public Boolean getInternal() {
		if (_internalSupplier != null) {
			internal = _internalSupplier.get();

			_internalSupplier = null;
		}

		return internal;
	}

	public void setInternal(Boolean internal) {
		this.internal = internal;

		_internalSupplier = null;
	}

	@JsonIgnore
	public void setInternal(
		UnsafeSupplier<Boolean, Exception> internalUnsafeSupplier) {

		_internalSupplier = () -> {
			try {
				return internalUnsafeSupplier.get();
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
		description = "A flag that identifies whether this account is an internal or test account."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean internal;

	private Supplier<Boolean> _internalSupplier;

	@Schema(description = "The account's key.")
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

	@GraphQLField(description = "The account's key.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String key;

	private Supplier<String> _keySupplier;

	@Schema(description = "The account's language.")
	@Valid
	public Language getLanguage() {
		if (_languageSupplier != null) {
			language = _languageSupplier.get();

			_languageSupplier = null;
		}

		return language;
	}

	@JsonIgnore
	public String getLanguageAsString() {
		Language language = getLanguage();

		if (language == null) {
			return null;
		}

		return language.toString();
	}

	public void setLanguage(Language language) {
		this.language = language;

		_languageSupplier = null;
	}

	@JsonIgnore
	public void setLanguage(
		UnsafeSupplier<Language, Exception> languageUnsafeSupplier) {

		_languageSupplier = () -> {
			try {
				return languageUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The account's language.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Language language;

	private Supplier<Language> _languageSupplier;

	@Schema(
		deprecated = true,
		description = "The assetAttachmentId of the account's logo."
	)
	public Long getLogoId() {
		if (_logoIdSupplier != null) {
			logoId = _logoIdSupplier.get();

			_logoIdSupplier = null;
		}

		return logoId;
	}

	public void setLogoId(Long logoId) {
		this.logoId = logoId;

		_logoIdSupplier = null;
	}

	@JsonIgnore
	public void setLogoId(
		UnsafeSupplier<Long, Exception> logoIdUnsafeSupplier) {

		_logoIdSupplier = () -> {
			try {
				return logoIdUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@Deprecated
	@GraphQLField(description = "The assetAttachmentId of the account's logo.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long logoId;

	private Supplier<Long> _logoIdSupplier;

	@Schema(description = "The name of the account.")
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
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The name of the account.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String name;

	private Supplier<String> _nameSupplier;

	@Schema(description = "The account's parent account key.")
	public String getParentAccountKey() {
		if (_parentAccountKeySupplier != null) {
			parentAccountKey = _parentAccountKeySupplier.get();

			_parentAccountKeySupplier = null;
		}

		return parentAccountKey;
	}

	public void setParentAccountKey(String parentAccountKey) {
		this.parentAccountKey = parentAccountKey;

		_parentAccountKeySupplier = null;
	}

	@JsonIgnore
	public void setParentAccountKey(
		UnsafeSupplier<String, Exception> parentAccountKeyUnsafeSupplier) {

		_parentAccountKeySupplier = () -> {
			try {
				return parentAccountKeyUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The account's parent account key.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String parentAccountKey;

	private Supplier<String> _parentAccountKeySupplier;

	@Schema(description = "The account's phone number.")
	public String getPhoneNumber() {
		if (_phoneNumberSupplier != null) {
			phoneNumber = _phoneNumberSupplier.get();

			_phoneNumberSupplier = null;
		}

		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;

		_phoneNumberSupplier = null;
	}

	@JsonIgnore
	public void setPhoneNumber(
		UnsafeSupplier<String, Exception> phoneNumberUnsafeSupplier) {

		_phoneNumberSupplier = () -> {
			try {
				return phoneNumberUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The account's phone number.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String phoneNumber;

	private Supplier<String> _phoneNumberSupplier;

	@Schema(description = "The account's postal addresses.")
	@Valid
	public PostalAddress[] getPostalAddresses() {
		if (_postalAddressesSupplier != null) {
			postalAddresses = _postalAddressesSupplier.get();

			_postalAddressesSupplier = null;
		}

		return postalAddresses;
	}

	public void setPostalAddresses(PostalAddress[] postalAddresses) {
		this.postalAddresses = postalAddresses;

		_postalAddressesSupplier = null;
	}

	@JsonIgnore
	public void setPostalAddresses(
		UnsafeSupplier<PostalAddress[], Exception>
			postalAddressesUnsafeSupplier) {

		_postalAddressesSupplier = () -> {
			try {
				return postalAddressesUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The account's postal addresses.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected PostalAddress[] postalAddresses;

	private Supplier<PostalAddress[]> _postalAddressesSupplier;

	@Schema(
		description = "The products that the account has purchased. Optional field that can retrieved with nestedFields."
	)
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

	@GraphQLField(
		description = "The products that the account has purchased. Optional field that can retrieved with nestedFields."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ProductPurchase[] productPurchases;

	private Supplier<ProductPurchase[]> _productPurchasesSupplier;

	@Schema(description = "The account's profile email address.")
	public String getProfileEmailAddress() {
		if (_profileEmailAddressSupplier != null) {
			profileEmailAddress = _profileEmailAddressSupplier.get();

			_profileEmailAddressSupplier = null;
		}

		return profileEmailAddress;
	}

	public void setProfileEmailAddress(String profileEmailAddress) {
		this.profileEmailAddress = profileEmailAddress;

		_profileEmailAddressSupplier = null;
	}

	@JsonIgnore
	public void setProfileEmailAddress(
		UnsafeSupplier<String, Exception> profileEmailAddressUnsafeSupplier) {

		_profileEmailAddressSupplier = () -> {
			try {
				return profileEmailAddressUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The account's profile email address.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String profileEmailAddress;

	private Supplier<String> _profileEmailAddressSupplier;

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

	@Schema(description = "The region responsible for the account.")
	@Valid
	public Region getRegion() {
		if (_regionSupplier != null) {
			region = _regionSupplier.get();

			_regionSupplier = null;
		}

		return region;
	}

	@JsonIgnore
	public String getRegionAsString() {
		Region region = getRegion();

		if (region == null) {
			return null;
		}

		return region.toString();
	}

	public void setRegion(Region region) {
		this.region = region;

		_regionSupplier = null;
	}

	@JsonIgnore
	public void setRegion(
		UnsafeSupplier<Region, Exception> regionUnsafeSupplier) {

		_regionSupplier = () -> {
			try {
				return regionUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The region responsible for the account.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Region region;

	private Supplier<Region> _regionSupplier;

	@Schema(description = "The status of the account.")
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

	@GraphQLField(description = "The status of the account.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Status status;

	private Supplier<Status> _statusSupplier;

	@Schema(description = "The tier of the account.")
	@Valid
	public Tier getTier() {
		if (_tierSupplier != null) {
			tier = _tierSupplier.get();

			_tierSupplier = null;
		}

		return tier;
	}

	@JsonIgnore
	public String getTierAsString() {
		Tier tier = getTier();

		if (tier == null) {
			return null;
		}

		return tier.toString();
	}

	public void setTier(Tier tier) {
		this.tier = tier;

		_tierSupplier = null;
	}

	@JsonIgnore
	public void setTier(UnsafeSupplier<Tier, Exception> tierUnsafeSupplier) {
		_tierSupplier = () -> {
			try {
				return tierUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The tier of the account.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Tier tier;

	private Supplier<Tier> _tierSupplier;

	@Schema(description = "The account's website.")
	public String getWebsite() {
		if (_websiteSupplier != null) {
			website = _websiteSupplier.get();

			_websiteSupplier = null;
		}

		return website;
	}

	public void setWebsite(String website) {
		this.website = website;

		_websiteSupplier = null;
	}

	@JsonIgnore
	public void setWebsite(
		UnsafeSupplier<String, Exception> websiteUnsafeSupplier) {

		_websiteSupplier = () -> {
			try {
				return websiteUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The account's website.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String website;

	private Supplier<String> _websiteSupplier;

	@Schema(
		description = "The account's worker contacts. Optional field that can retrieved with nestedFields."
	)
	@Valid
	public Contact[] getWorkerContacts() {
		if (_workerContactsSupplier != null) {
			workerContacts = _workerContactsSupplier.get();

			_workerContactsSupplier = null;
		}

		return workerContacts;
	}

	public void setWorkerContacts(Contact[] workerContacts) {
		this.workerContacts = workerContacts;

		_workerContactsSupplier = null;
	}

	@JsonIgnore
	public void setWorkerContacts(
		UnsafeSupplier<Contact[], Exception> workerContactsUnsafeSupplier) {

		_workerContactsSupplier = () -> {
			try {
				return workerContactsUnsafeSupplier.get();
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
		description = "The account's worker contacts. Optional field that can retrieved with nestedFields."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Contact[] workerContacts;

	private Supplier<Contact[]> _workerContactsSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Account)) {
			return false;
		}

		Account account = (Account)object;

		return Objects.equals(toString(), account.toString());
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

		Team[] assignedTeams = getAssignedTeams();

		if (assignedTeams != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"assignedTeams\": ");

			sb.append("[");

			for (int i = 0; i < assignedTeams.length; i++) {
				sb.append(String.valueOf(assignedTeams[i]));

				if ((i + 1) < assignedTeams.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		String code = getCode();

		if (code != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"code\": ");

			sb.append("\"");

			sb.append(_escape(code));

			sb.append("\"");
		}

		String contactEmailAddress = getContactEmailAddress();

		if (contactEmailAddress != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contactEmailAddress\": ");

			sb.append("\"");

			sb.append(_escape(contactEmailAddress));

			sb.append("\"");
		}

		Contact[] contacts = getContacts();

		if (contacts != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contacts\": ");

			sb.append("[");

			for (int i = 0; i < contacts.length; i++) {
				sb.append(String.valueOf(contacts[i]));

				if ((i + 1) < contacts.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		Contact[] customerContacts = getCustomerContacts();

		if (customerContacts != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customerContacts\": ");

			sb.append("[");

			for (int i = 0; i < customerContacts.length; i++) {
				sb.append(String.valueOf(customerContacts[i]));

				if ((i + 1) < customerContacts.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		DataRegion dataRegion = getDataRegion();

		if (dataRegion != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataRegion\": ");

			sb.append("\"");

			sb.append(dataRegion);

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

		Entitlement[] entitlements = getEntitlements();

		if (entitlements != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"entitlements\": ");

			sb.append("[");

			for (int i = 0; i < entitlements.length; i++) {
				sb.append(String.valueOf(entitlements[i]));

				if ((i + 1) < entitlements.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
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

		String faxNumber = getFaxNumber();

		if (faxNumber != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"faxNumber\": ");

			sb.append("\"");

			sb.append(_escape(faxNumber));

			sb.append("\"");
		}

		Boolean internal = getInternal();

		if (internal != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"internal\": ");

			sb.append(internal);
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

		Language language = getLanguage();

		if (language != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"language\": ");

			sb.append("\"");

			sb.append(language);

			sb.append("\"");
		}

		Long logoId = getLogoId();

		if (logoId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"logoId\": ");

			sb.append(logoId);
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

		String parentAccountKey = getParentAccountKey();

		if (parentAccountKey != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentAccountKey\": ");

			sb.append("\"");

			sb.append(_escape(parentAccountKey));

			sb.append("\"");
		}

		String phoneNumber = getPhoneNumber();

		if (phoneNumber != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"phoneNumber\": ");

			sb.append("\"");

			sb.append(_escape(phoneNumber));

			sb.append("\"");
		}

		PostalAddress[] postalAddresses = getPostalAddresses();

		if (postalAddresses != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"postalAddresses\": ");

			sb.append("[");

			for (int i = 0; i < postalAddresses.length; i++) {
				sb.append(String.valueOf(postalAddresses[i]));

				if ((i + 1) < postalAddresses.length) {
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

		String profileEmailAddress = getProfileEmailAddress();

		if (profileEmailAddress != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"profileEmailAddress\": ");

			sb.append("\"");

			sb.append(_escape(profileEmailAddress));

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

		Region region = getRegion();

		if (region != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"region\": ");

			sb.append("\"");

			sb.append(region);

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

		Tier tier = getTier();

		if (tier != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"tier\": ");

			sb.append("\"");

			sb.append(tier);

			sb.append("\"");
		}

		String website = getWebsite();

		if (website != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"website\": ");

			sb.append("\"");

			sb.append(_escape(website));

			sb.append("\"");
		}

		Contact[] workerContacts = getWorkerContacts();

		if (workerContacts != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workerContacts\": ");

			sb.append("[");

			for (int i = 0; i < workerContacts.length; i++) {
				sb.append(String.valueOf(workerContacts[i]));

				if ((i + 1) < workerContacts.length) {
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
		defaultValue = "com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Account",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("DataRegion")
	public static enum DataRegion {

		BRAZIL("Brazil"), HUNGARY("Hungary"), JAPAN("Japan"),
		UNITED_STATES("United States");

		@JsonCreator
		public static DataRegion create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (DataRegion dataRegion : values()) {
				if (Objects.equals(dataRegion.getValue(), value)) {
					return dataRegion;
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

		private DataRegion(String value) {
			_value = value;
		}

		private final String _value;

	}

	@GraphQLName("Language")
	public static enum Language {

		CHINESE("Chinese"), ENGLISH("English"), JAPANESE("Japanese"),
		PORTUGUESE("Portuguese"), SPANISH("Spanish");

		@JsonCreator
		public static Language create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Language language : values()) {
				if (Objects.equals(language.getValue(), value)) {
					return language;
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

		private Language(String value) {
			_value = value;
		}

		private final String _value;

	}

	@GraphQLName("Region")
	public static enum Region {

		AUSTRALIA("Australia"), BRAZIL("Brazil"), CHINA("China"),
		GLOBAL("Global"), HUNGARY("Hungary"), INDIA("India"), JAPAN("Japan"),
		SPAIN("Spain"), UNITED_STATES("United States");

		@JsonCreator
		public static Region create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Region region : values()) {
				if (Objects.equals(region.getValue(), value)) {
					return region;
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

		private Region(String value) {
			_value = value;
		}

		private final String _value;

	}

	@GraphQLName("Status")
	public static enum Status {

		ACTIVE("Active"), CLOSED("Closed");

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

	@GraphQLName("Tier")
	public static enum Tier {

		T1("T1"), T2("T2"), T3("T3"), T4("T4");

		@JsonCreator
		public static Tier create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Tier tier : values()) {
				if (Objects.equals(tier.getValue(), value)) {
					return tier;
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

		private Tier(String value) {
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