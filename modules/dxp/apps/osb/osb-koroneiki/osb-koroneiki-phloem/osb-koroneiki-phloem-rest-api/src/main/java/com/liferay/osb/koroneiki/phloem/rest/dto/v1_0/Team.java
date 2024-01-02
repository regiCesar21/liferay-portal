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
@GraphQLName(description = "Represents a team.", value = "Team")
@JsonFilter("Liferay.Vulcan")
@Schema(description = "Represents a team.", requiredProperties = {"name"})
@XmlRootElement(name = "Team")
public class Team implements Serializable {

	public static Team toDTO(String json) {
		return ObjectMapperUtil.readValue(Team.class, json);
	}

	public static Team unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Team.class, json);
	}

	@Schema(
		description = "The team's account. Optional field that can retrieved with nestedFields."
	)
	@Valid
	public Account getAccount() {
		if (_accountSupplier != null) {
			account = _accountSupplier.get();

			_accountSupplier = null;
		}

		return account;
	}

	public void setAccount(Account account) {
		this.account = account;

		_accountSupplier = null;
	}

	@JsonIgnore
	public void setAccount(
		UnsafeSupplier<Account, Exception> accountUnsafeSupplier) {

		_accountSupplier = () -> {
			try {
				return accountUnsafeSupplier.get();
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
		description = "The team's account. Optional field that can retrieved with nestedFields."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Account account;

	private Supplier<Account> _accountSupplier;

	@Schema(description = "The team's account's key.")
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

	@GraphQLField(description = "The team's account's key.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String accountKey;

	private Supplier<String> _accountKeySupplier;

	@Schema(
		description = "The team's contacts. Optional field that can retrieved with nestedFields."
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
		description = "The team's contacts. Optional field that can retrieved with nestedFields."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Contact[] contacts;

	private Supplier<Contact[]> _contactsSupplier;

	@Schema(description = "The team's creation date.")
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

	@GraphQLField(description = "The team's creation date.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateCreated;

	private Supplier<Date> _dateCreatedSupplier;

	@Schema(
		description = "The most recent time that any of the team's fields changed."
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
		description = "The most recent time that any of the team's fields changed."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateModified;

	private Supplier<Date> _dateModifiedSupplier;

	@Schema(description = "The team's links to entities in external domains.")
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
		description = "The team's links to entities in external domains."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected ExternalLink[] externalLinks;

	private Supplier<ExternalLink[]> _externalLinksSupplier;

	@Schema(description = "The team's key.")
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

	@GraphQLField(description = "The team's key.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String key;

	private Supplier<String> _keySupplier;

	@Schema(description = "The name of the team.")
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

	@GraphQLField(description = "The name of the team.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotEmpty
	protected String name;

	private Supplier<String> _nameSupplier;

	@Schema(
		description = "A flag that identifies whether this is a system team."
	)
	public Boolean getSystem() {
		if (_systemSupplier != null) {
			system = _systemSupplier.get();

			_systemSupplier = null;
		}

		return system;
	}

	public void setSystem(Boolean system) {
		this.system = system;

		_systemSupplier = null;
	}

	@JsonIgnore
	public void setSystem(
		UnsafeSupplier<Boolean, Exception> systemUnsafeSupplier) {

		_systemSupplier = () -> {
			try {
				return systemUnsafeSupplier.get();
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
		description = "A flag that identifies whether this is a system team."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Boolean system;

	private Supplier<Boolean> _systemSupplier;

	@Schema(
		description = "The team's account team roles. Optional field that can retrieved with nestedFields."
	)
	@Valid
	public TeamRole[] getTeamRoles() {
		if (_teamRolesSupplier != null) {
			teamRoles = _teamRolesSupplier.get();

			_teamRolesSupplier = null;
		}

		return teamRoles;
	}

	public void setTeamRoles(TeamRole[] teamRoles) {
		this.teamRoles = teamRoles;

		_teamRolesSupplier = null;
	}

	@JsonIgnore
	public void setTeamRoles(
		UnsafeSupplier<TeamRole[], Exception> teamRolesUnsafeSupplier) {

		_teamRolesSupplier = () -> {
			try {
				return teamRolesUnsafeSupplier.get();
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
		description = "The team's account team roles. Optional field that can retrieved with nestedFields."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected TeamRole[] teamRoles;

	private Supplier<TeamRole[]> _teamRolesSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Team)) {
			return false;
		}

		Team team = (Team)object;

		return Objects.equals(toString(), team.toString());
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

		Account account = getAccount();

		if (account != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"account\": ");

			sb.append(String.valueOf(account));
		}

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

		Boolean system = getSystem();

		if (system != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"system\": ");

			sb.append(system);
		}

		TeamRole[] teamRoles = getTeamRoles();

		if (teamRoles != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"teamRoles\": ");

			sb.append("[");

			for (int i = 0; i < teamRoles.length; i++) {
				sb.append(String.valueOf(teamRoles[i]));

				if ((i + 1) < teamRoles.length) {
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
		defaultValue = "com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Team",
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