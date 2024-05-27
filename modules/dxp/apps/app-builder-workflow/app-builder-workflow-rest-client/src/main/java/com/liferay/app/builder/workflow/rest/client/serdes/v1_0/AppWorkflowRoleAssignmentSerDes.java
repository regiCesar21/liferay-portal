/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.workflow.rest.client.serdes.v1_0;

import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowRoleAssignment;
import com.liferay.app.builder.workflow.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class AppWorkflowRoleAssignmentSerDes {

	public static AppWorkflowRoleAssignment toDTO(String json) {
		AppWorkflowRoleAssignmentJSONParser
			appWorkflowRoleAssignmentJSONParser =
				new AppWorkflowRoleAssignmentJSONParser();

		return appWorkflowRoleAssignmentJSONParser.parseToDTO(json);
	}

	public static AppWorkflowRoleAssignment[] toDTOs(String json) {
		AppWorkflowRoleAssignmentJSONParser
			appWorkflowRoleAssignmentJSONParser =
				new AppWorkflowRoleAssignmentJSONParser();

		return appWorkflowRoleAssignmentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		AppWorkflowRoleAssignment appWorkflowRoleAssignment) {

		if (appWorkflowRoleAssignment == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (appWorkflowRoleAssignment.getRoleId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleId\": ");

			sb.append(appWorkflowRoleAssignment.getRoleId());
		}

		if (appWorkflowRoleAssignment.getRoleName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleName\": ");

			sb.append("\"");

			sb.append(_escape(appWorkflowRoleAssignment.getRoleName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AppWorkflowRoleAssignmentJSONParser
			appWorkflowRoleAssignmentJSONParser =
				new AppWorkflowRoleAssignmentJSONParser();

		return appWorkflowRoleAssignmentJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		AppWorkflowRoleAssignment appWorkflowRoleAssignment) {

		if (appWorkflowRoleAssignment == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (appWorkflowRoleAssignment.getRoleId() == null) {
			map.put("roleId", null);
		}
		else {
			map.put(
				"roleId",
				String.valueOf(appWorkflowRoleAssignment.getRoleId()));
		}

		if (appWorkflowRoleAssignment.getRoleName() == null) {
			map.put("roleName", null);
		}
		else {
			map.put(
				"roleName",
				String.valueOf(appWorkflowRoleAssignment.getRoleName()));
		}

		return map;
	}

	public static class AppWorkflowRoleAssignmentJSONParser
		extends BaseJSONParser<AppWorkflowRoleAssignment> {

		@Override
		protected AppWorkflowRoleAssignment createDTO() {
			return new AppWorkflowRoleAssignment();
		}

		@Override
		protected AppWorkflowRoleAssignment[] createDTOArray(int size) {
			return new AppWorkflowRoleAssignment[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "roleId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "roleName")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			AppWorkflowRoleAssignment appWorkflowRoleAssignment,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "roleId")) {
				if (jsonParserFieldValue != null) {
					appWorkflowRoleAssignment.setRoleId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleName")) {
				if (jsonParserFieldValue != null) {
					appWorkflowRoleAssignment.setRoleName(
						(String)jsonParserFieldValue);
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