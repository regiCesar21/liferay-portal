/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.rest.client.serdes.v1_0;

import com.liferay.account.rest.client.dto.v1_0.AccountUser;
import com.liferay.account.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Drew Brokke
 * @generated
 */
@Generated("")
public class AccountUserSerDes {

	public static AccountUser toDTO(String json) {
		AccountUserJSONParser accountUserJSONParser =
			new AccountUserJSONParser();

		return accountUserJSONParser.parseToDTO(json);
	}

	public static AccountUser[] toDTOs(String json) {
		AccountUserJSONParser accountUserJSONParser =
			new AccountUserJSONParser();

		return accountUserJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AccountUser accountUser) {
		if (accountUser == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (accountUser.getEmailAddress() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"emailAddress\": ");

			sb.append("\"");

			sb.append(_escape(accountUser.getEmailAddress()));

			sb.append("\"");
		}

		if (accountUser.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(accountUser.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (accountUser.getFirstName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"firstName\": ");

			sb.append("\"");

			sb.append(_escape(accountUser.getFirstName()));

			sb.append("\"");
		}

		if (accountUser.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(accountUser.getId());
		}

		if (accountUser.getLastName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lastName\": ");

			sb.append("\"");

			sb.append(_escape(accountUser.getLastName()));

			sb.append("\"");
		}

		if (accountUser.getMiddleName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"middleName\": ");

			sb.append("\"");

			sb.append(_escape(accountUser.getMiddleName()));

			sb.append("\"");
		}

		if (accountUser.getPrefix() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"prefix\": ");

			sb.append("\"");

			sb.append(_escape(accountUser.getPrefix()));

			sb.append("\"");
		}

		if (accountUser.getScreenName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"screenName\": ");

			sb.append("\"");

			sb.append(_escape(accountUser.getScreenName()));

			sb.append("\"");
		}

		if (accountUser.getSuffix() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"suffix\": ");

			sb.append("\"");

			sb.append(_escape(accountUser.getSuffix()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AccountUserJSONParser accountUserJSONParser =
			new AccountUserJSONParser();

		return accountUserJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AccountUser accountUser) {
		if (accountUser == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (accountUser.getEmailAddress() == null) {
			map.put("emailAddress", null);
		}
		else {
			map.put(
				"emailAddress", String.valueOf(accountUser.getEmailAddress()));
		}

		if (accountUser.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(accountUser.getExternalReferenceCode()));
		}

		if (accountUser.getFirstName() == null) {
			map.put("firstName", null);
		}
		else {
			map.put("firstName", String.valueOf(accountUser.getFirstName()));
		}

		if (accountUser.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(accountUser.getId()));
		}

		if (accountUser.getLastName() == null) {
			map.put("lastName", null);
		}
		else {
			map.put("lastName", String.valueOf(accountUser.getLastName()));
		}

		if (accountUser.getMiddleName() == null) {
			map.put("middleName", null);
		}
		else {
			map.put("middleName", String.valueOf(accountUser.getMiddleName()));
		}

		if (accountUser.getPrefix() == null) {
			map.put("prefix", null);
		}
		else {
			map.put("prefix", String.valueOf(accountUser.getPrefix()));
		}

		if (accountUser.getScreenName() == null) {
			map.put("screenName", null);
		}
		else {
			map.put("screenName", String.valueOf(accountUser.getScreenName()));
		}

		if (accountUser.getSuffix() == null) {
			map.put("suffix", null);
		}
		else {
			map.put("suffix", String.valueOf(accountUser.getSuffix()));
		}

		return map;
	}

	public static class AccountUserJSONParser
		extends BaseJSONParser<AccountUser> {

		@Override
		protected AccountUser createDTO() {
			return new AccountUser();
		}

		@Override
		protected AccountUser[] createDTOArray(int size) {
			return new AccountUser[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "emailAddress")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "firstName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "lastName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "middleName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "prefix")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "screenName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "suffix")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			AccountUser accountUser, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "emailAddress")) {
				if (jsonParserFieldValue != null) {
					accountUser.setEmailAddress((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					accountUser.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "firstName")) {
				if (jsonParserFieldValue != null) {
					accountUser.setFirstName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					accountUser.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastName")) {
				if (jsonParserFieldValue != null) {
					accountUser.setLastName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "middleName")) {
				if (jsonParserFieldValue != null) {
					accountUser.setMiddleName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "prefix")) {
				if (jsonParserFieldValue != null) {
					accountUser.setPrefix((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "screenName")) {
				if (jsonParserFieldValue != null) {
					accountUser.setScreenName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "suffix")) {
				if (jsonParserFieldValue != null) {
					accountUser.setSuffix((String)jsonParserFieldValue);
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
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
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}