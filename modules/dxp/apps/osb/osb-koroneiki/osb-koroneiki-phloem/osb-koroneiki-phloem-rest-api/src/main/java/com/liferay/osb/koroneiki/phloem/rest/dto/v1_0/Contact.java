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
import javax.validation.constraints.NotEmpty;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Amos Fong
 * @generated
 */
@Generated("")
@GraphQLName(description = "Represents a contact.", value = "Contact")
@JsonFilter("Liferay.Vulcan")
@Schema(
	description = "Represents a contact.",
	requiredProperties = {"emailAddress", "firstName", "lastName"}
)
@XmlRootElement(name = "Contact")
public class Contact implements Serializable {

	public static Contact toDTO(String json) {
		return ObjectMapperUtil.readValue(Contact.class, json);
	}

	public static Contact unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Contact.class, json);
	}

	@Schema(
		description = "The accounts that the contact is assigned to. Optional field that can retrieved with nestedFields."
	)
	@Valid
	public Account[] getAccounts() {
		if (_accountsSupplier != null) {
			accounts = _accountsSupplier.get();

			_accountsSupplier = null;
		}

		return accounts;
	}

	public void setAccounts(Account[] accounts) {
		this.accounts = accounts;

		_accountsSupplier = null;
	}

	@JsonIgnore
	public void setAccounts(
		UnsafeSupplier<Account[], Exception> accountsUnsafeSupplier) {

		_accountsSupplier = () -> {
			try {
				return accountsUnsafeSupplier.get();
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
		description = "The accounts that the contact is assigned to. Optional field that can retrieved with nestedFields."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Account[] accounts;

	private Supplier<Account[]> _accountsSupplier;

	@Schema(
		description = "The contact's roles. Optional field that can retrieved along with the account nestedField."
	)
	@Valid
	public ContactRole[] getContactRoles() {
		if (_contactRolesSupplier != null) {
			contactRoles = _contactRolesSupplier.get();

			_contactRolesSupplier = null;
		}

		return contactRoles;
	}

	public void setContactRoles(ContactRole[] contactRoles) {
		this.contactRoles = contactRoles;

		_contactRolesSupplier = null;
	}

	@JsonIgnore
	public void setContactRoles(
		UnsafeSupplier<ContactRole[], Exception> contactRolesUnsafeSupplier) {

		_contactRolesSupplier = () -> {
			try {
				return contactRolesUnsafeSupplier.get();
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
		description = "The contact's roles. Optional field that can retrieved along with the account nestedField."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected ContactRole[] contactRoles;

	private Supplier<ContactRole[]> _contactRolesSupplier;

	@Schema(description = "The contact's creation date.")
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

	@GraphQLField(description = "The contact's creation date.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateCreated;

	private Supplier<Date> _dateCreatedSupplier;

	@Schema(
		description = "The most recent time that any of the contact's fields changed."
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
		description = "The most recent time that any of the contact's fields changed."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateModified;

	private Supplier<Date> _dateModifiedSupplier;

	@Schema(description = "The email address of the contact.")
	public String getEmailAddress() {
		if (_emailAddressSupplier != null) {
			emailAddress = _emailAddressSupplier.get();

			_emailAddressSupplier = null;
		}

		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;

		_emailAddressSupplier = null;
	}

	@JsonIgnore
	public void setEmailAddress(
		UnsafeSupplier<String, Exception> emailAddressUnsafeSupplier) {

		_emailAddressSupplier = () -> {
			try {
				return emailAddressUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The email address of the contact.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotEmpty
	protected String emailAddress;

	private Supplier<String> _emailAddressSupplier;

	@Schema(
		description = "A flag that identifies whether the email address of this contact is verified."
	)
	public Boolean getEmailAddressVerified() {
		if (_emailAddressVerifiedSupplier != null) {
			emailAddressVerified = _emailAddressVerifiedSupplier.get();

			_emailAddressVerifiedSupplier = null;
		}

		return emailAddressVerified;
	}

	public void setEmailAddressVerified(Boolean emailAddressVerified) {
		this.emailAddressVerified = emailAddressVerified;

		_emailAddressVerifiedSupplier = null;
	}

	@JsonIgnore
	public void setEmailAddressVerified(
		UnsafeSupplier<Boolean, Exception> emailAddressVerifiedUnsafeSupplier) {

		_emailAddressVerifiedSupplier = () -> {
			try {
				return emailAddressVerifiedUnsafeSupplier.get();
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
		description = "A flag that identifies whether the email address of this contact is verified."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean emailAddressVerified;

	private Supplier<Boolean> _emailAddressVerifiedSupplier;

	@Schema(description = "The contact's entitlements.")
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

	@GraphQLField(description = "The contact's entitlements.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Entitlement[] entitlements;

	private Supplier<Entitlement[]> _entitlementsSupplier;

	@Schema(
		description = "The contacts's links to entities in external domains."
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
		description = "The contacts's links to entities in external domains."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected ExternalLink[] externalLinks;

	private Supplier<ExternalLink[]> _externalLinksSupplier;

	@Schema(description = "The first name of the contact.")
	public String getFirstName() {
		if (_firstNameSupplier != null) {
			firstName = _firstNameSupplier.get();

			_firstNameSupplier = null;
		}

		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;

		_firstNameSupplier = null;
	}

	@JsonIgnore
	public void setFirstName(
		UnsafeSupplier<String, Exception> firstNameUnsafeSupplier) {

		_firstNameSupplier = () -> {
			try {
				return firstNameUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The first name of the contact.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotEmpty
	protected String firstName;

	private Supplier<String> _firstNameSupplier;

	@Schema(description = "The contact's key.")
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

	@GraphQLField(description = "The contact's key.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String key;

	private Supplier<String> _keySupplier;

	@Schema(description = "The language ID of the contact.")
	public String getLanguageId() {
		if (_languageIdSupplier != null) {
			languageId = _languageIdSupplier.get();

			_languageIdSupplier = null;
		}

		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;

		_languageIdSupplier = null;
	}

	@JsonIgnore
	public void setLanguageId(
		UnsafeSupplier<String, Exception> languageIdUnsafeSupplier) {

		_languageIdSupplier = () -> {
			try {
				return languageIdUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The language ID of the contact.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String languageId;

	private Supplier<String> _languageIdSupplier;

	@Schema(description = "The last name of the contact.")
	public String getLastName() {
		if (_lastNameSupplier != null) {
			lastName = _lastNameSupplier.get();

			_lastNameSupplier = null;
		}

		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;

		_lastNameSupplier = null;
	}

	@JsonIgnore
	public void setLastName(
		UnsafeSupplier<String, Exception> lastNameUnsafeSupplier) {

		_lastNameSupplier = () -> {
			try {
				return lastNameUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The last name of the contact.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotEmpty
	protected String lastName;

	private Supplier<String> _lastNameSupplier;

	@Schema(description = "The middle name of the contact.")
	public String getMiddleName() {
		if (_middleNameSupplier != null) {
			middleName = _middleNameSupplier.get();

			_middleNameSupplier = null;
		}

		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;

		_middleNameSupplier = null;
	}

	@JsonIgnore
	public void setMiddleName(
		UnsafeSupplier<String, Exception> middleNameUnsafeSupplier) {

		_middleNameSupplier = () -> {
			try {
				return middleNameUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The middle name of the contact.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String middleName;

	private Supplier<String> _middleNameSupplier;

	@Schema(description = "The contact's phones.")
	@Valid
	public Phone[] getPhones() {
		if (_phonesSupplier != null) {
			phones = _phonesSupplier.get();

			_phonesSupplier = null;
		}

		return phones;
	}

	public void setPhones(Phone[] phones) {
		this.phones = phones;

		_phonesSupplier = null;
	}

	@JsonIgnore
	public void setPhones(
		UnsafeSupplier<Phone[], Exception> phonesUnsafeSupplier) {

		_phonesSupplier = () -> {
			try {
				return phonesUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The contact's phones.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Phone[] phones;

	private Supplier<Phone[]> _phonesSupplier;

	@Schema(description = "The teams that the contact is assigned to.")
	@Valid
	public Team[] getTeams() {
		if (_teamsSupplier != null) {
			teams = _teamsSupplier.get();

			_teamsSupplier = null;
		}

		return teams;
	}

	public void setTeams(Team[] teams) {
		this.teams = teams;

		_teamsSupplier = null;
	}

	@JsonIgnore
	public void setTeams(
		UnsafeSupplier<Team[], Exception> teamsUnsafeSupplier) {

		_teamsSupplier = () -> {
			try {
				return teamsUnsafeSupplier.get();
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@GraphQLField(description = "The teams that the contact is assigned to.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Team[] teams;

	private Supplier<Team[]> _teamsSupplier;

	@Schema(description = "A universal identifier to reference this contact.")
	public String getUuid() {
		if (_uuidSupplier != null) {
			uuid = _uuidSupplier.get();

			_uuidSupplier = null;
		}

		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;

		_uuidSupplier = null;
	}

	@JsonIgnore
	public void setUuid(UnsafeSupplier<String, Exception> uuidUnsafeSupplier) {
		_uuidSupplier = () -> {
			try {
				return uuidUnsafeSupplier.get();
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
		description = "A universal identifier to reference this contact."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String uuid;

	private Supplier<String> _uuidSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Contact)) {
			return false;
		}

		Contact contact = (Contact)object;

		return Objects.equals(toString(), contact.toString());
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

		Account[] accounts = getAccounts();

		if (accounts != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accounts\": ");

			sb.append("[");

			for (int i = 0; i < accounts.length; i++) {
				sb.append(String.valueOf(accounts[i]));

				if ((i + 1) < accounts.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		ContactRole[] contactRoles = getContactRoles();

		if (contactRoles != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contactRoles\": ");

			sb.append("[");

			for (int i = 0; i < contactRoles.length; i++) {
				sb.append(String.valueOf(contactRoles[i]));

				if ((i + 1) < contactRoles.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
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

		String emailAddress = getEmailAddress();

		if (emailAddress != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"emailAddress\": ");

			sb.append("\"");

			sb.append(_escape(emailAddress));

			sb.append("\"");
		}

		Boolean emailAddressVerified = getEmailAddressVerified();

		if (emailAddressVerified != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"emailAddressVerified\": ");

			sb.append(emailAddressVerified);
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

		String firstName = getFirstName();

		if (firstName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"firstName\": ");

			sb.append("\"");

			sb.append(_escape(firstName));

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

		String languageId = getLanguageId();

		if (languageId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"languageId\": ");

			sb.append("\"");

			sb.append(_escape(languageId));

			sb.append("\"");
		}

		String lastName = getLastName();

		if (lastName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lastName\": ");

			sb.append("\"");

			sb.append(_escape(lastName));

			sb.append("\"");
		}

		String middleName = getMiddleName();

		if (middleName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"middleName\": ");

			sb.append("\"");

			sb.append(_escape(middleName));

			sb.append("\"");
		}

		Phone[] phones = getPhones();

		if (phones != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"phones\": ");

			sb.append("[");

			for (int i = 0; i < phones.length; i++) {
				sb.append(String.valueOf(phones[i]));

				if ((i + 1) < phones.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		Team[] teams = getTeams();

		if (teams != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"teams\": ");

			sb.append("[");

			for (int i = 0; i < teams.length; i++) {
				sb.append(String.valueOf(teams[i]));

				if ((i + 1) < teams.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		String uuid = getUuid();

		if (uuid != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"uuid\": ");

			sb.append("\"");

			sb.append(_escape(uuid));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Contact",
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