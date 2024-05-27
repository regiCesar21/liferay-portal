/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.workflow.rest.client.serdes.v1_0;

import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowDataLayoutLink;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowRoleAssignment;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowTask;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowTransition;
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
public class AppWorkflowTaskSerDes {

	public static AppWorkflowTask toDTO(String json) {
		AppWorkflowTaskJSONParser appWorkflowTaskJSONParser =
			new AppWorkflowTaskJSONParser();

		return appWorkflowTaskJSONParser.parseToDTO(json);
	}

	public static AppWorkflowTask[] toDTOs(String json) {
		AppWorkflowTaskJSONParser appWorkflowTaskJSONParser =
			new AppWorkflowTaskJSONParser();

		return appWorkflowTaskJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AppWorkflowTask appWorkflowTask) {
		if (appWorkflowTask == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (appWorkflowTask.getAppWorkflowDataLayoutLinks() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowDataLayoutLinks\": ");

			sb.append("[");

			for (int i = 0;
				 i < appWorkflowTask.getAppWorkflowDataLayoutLinks().length;
				 i++) {

				sb.append(
					String.valueOf(
						appWorkflowTask.getAppWorkflowDataLayoutLinks()[i]));

				if ((i + 1) <
						appWorkflowTask.
							getAppWorkflowDataLayoutLinks().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (appWorkflowTask.getAppWorkflowRoleAssignments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowRoleAssignments\": ");

			sb.append("[");

			for (int i = 0;
				 i < appWorkflowTask.getAppWorkflowRoleAssignments().length;
				 i++) {

				sb.append(
					String.valueOf(
						appWorkflowTask.getAppWorkflowRoleAssignments()[i]));

				if ((i + 1) <
						appWorkflowTask.
							getAppWorkflowRoleAssignments().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (appWorkflowTask.getAppWorkflowTransitions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowTransitions\": ");

			sb.append("[");

			for (int i = 0;
				 i < appWorkflowTask.getAppWorkflowTransitions().length; i++) {

				sb.append(
					String.valueOf(
						appWorkflowTask.getAppWorkflowTransitions()[i]));

				if ((i + 1) <
						appWorkflowTask.getAppWorkflowTransitions().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (appWorkflowTask.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(appWorkflowTask.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AppWorkflowTaskJSONParser appWorkflowTaskJSONParser =
			new AppWorkflowTaskJSONParser();

		return appWorkflowTaskJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AppWorkflowTask appWorkflowTask) {
		if (appWorkflowTask == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (appWorkflowTask.getAppWorkflowDataLayoutLinks() == null) {
			map.put("appWorkflowDataLayoutLinks", null);
		}
		else {
			map.put(
				"appWorkflowDataLayoutLinks",
				String.valueOf(
					appWorkflowTask.getAppWorkflowDataLayoutLinks()));
		}

		if (appWorkflowTask.getAppWorkflowRoleAssignments() == null) {
			map.put("appWorkflowRoleAssignments", null);
		}
		else {
			map.put(
				"appWorkflowRoleAssignments",
				String.valueOf(
					appWorkflowTask.getAppWorkflowRoleAssignments()));
		}

		if (appWorkflowTask.getAppWorkflowTransitions() == null) {
			map.put("appWorkflowTransitions", null);
		}
		else {
			map.put(
				"appWorkflowTransitions",
				String.valueOf(appWorkflowTask.getAppWorkflowTransitions()));
		}

		if (appWorkflowTask.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(appWorkflowTask.getName()));
		}

		return map;
	}

	public static class AppWorkflowTaskJSONParser
		extends BaseJSONParser<AppWorkflowTask> {

		@Override
		protected AppWorkflowTask createDTO() {
			return new AppWorkflowTask();
		}

		@Override
		protected AppWorkflowTask[] createDTOArray(int size) {
			return new AppWorkflowTask[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(
					jsonParserFieldName, "appWorkflowDataLayoutLinks")) {

				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "appWorkflowRoleAssignments")) {

				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "appWorkflowTransitions")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			AppWorkflowTask appWorkflowTask, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "appWorkflowDataLayoutLinks")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					AppWorkflowDataLayoutLink[]
						appWorkflowDataLayoutLinksArray =
							new AppWorkflowDataLayoutLink
								[jsonParserFieldValues.length];

					for (int i = 0; i < appWorkflowDataLayoutLinksArray.length;
						 i++) {

						appWorkflowDataLayoutLinksArray[i] =
							AppWorkflowDataLayoutLinkSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					appWorkflowTask.setAppWorkflowDataLayoutLinks(
						appWorkflowDataLayoutLinksArray);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "appWorkflowRoleAssignments")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					AppWorkflowRoleAssignment[]
						appWorkflowRoleAssignmentsArray =
							new AppWorkflowRoleAssignment
								[jsonParserFieldValues.length];

					for (int i = 0; i < appWorkflowRoleAssignmentsArray.length;
						 i++) {

						appWorkflowRoleAssignmentsArray[i] =
							AppWorkflowRoleAssignmentSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					appWorkflowTask.setAppWorkflowRoleAssignments(
						appWorkflowRoleAssignmentsArray);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "appWorkflowTransitions")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					AppWorkflowTransition[] appWorkflowTransitionsArray =
						new AppWorkflowTransition[jsonParserFieldValues.length];

					for (int i = 0; i < appWorkflowTransitionsArray.length;
						 i++) {

						appWorkflowTransitionsArray[i] =
							AppWorkflowTransitionSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					appWorkflowTask.setAppWorkflowTransitions(
						appWorkflowTransitionsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					appWorkflowTask.setName((String)jsonParserFieldValue);
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