/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.workflow.rest.client.serdes.v1_0;

import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflow;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowState;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowTask;
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
public class AppWorkflowSerDes {

	public static AppWorkflow toDTO(String json) {
		AppWorkflowJSONParser appWorkflowJSONParser =
			new AppWorkflowJSONParser();

		return appWorkflowJSONParser.parseToDTO(json);
	}

	public static AppWorkflow[] toDTOs(String json) {
		AppWorkflowJSONParser appWorkflowJSONParser =
			new AppWorkflowJSONParser();

		return appWorkflowJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AppWorkflow appWorkflow) {
		if (appWorkflow == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (appWorkflow.getAppId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appId\": ");

			sb.append(appWorkflow.getAppId());
		}

		if (appWorkflow.getAppVersion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appVersion\": ");

			sb.append("\"");

			sb.append(_escape(appWorkflow.getAppVersion()));

			sb.append("\"");
		}

		if (appWorkflow.getAppWorkflowDefinitionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowDefinitionId\": ");

			sb.append(appWorkflow.getAppWorkflowDefinitionId());
		}

		if (appWorkflow.getAppWorkflowStates() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowStates\": ");

			sb.append("[");

			for (int i = 0; i < appWorkflow.getAppWorkflowStates().length;
				 i++) {

				sb.append(
					String.valueOf(appWorkflow.getAppWorkflowStates()[i]));

				if ((i + 1) < appWorkflow.getAppWorkflowStates().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (appWorkflow.getAppWorkflowTasks() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflowTasks\": ");

			sb.append("[");

			for (int i = 0; i < appWorkflow.getAppWorkflowTasks().length; i++) {
				sb.append(String.valueOf(appWorkflow.getAppWorkflowTasks()[i]));

				if ((i + 1) < appWorkflow.getAppWorkflowTasks().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AppWorkflowJSONParser appWorkflowJSONParser =
			new AppWorkflowJSONParser();

		return appWorkflowJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AppWorkflow appWorkflow) {
		if (appWorkflow == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (appWorkflow.getAppId() == null) {
			map.put("appId", null);
		}
		else {
			map.put("appId", String.valueOf(appWorkflow.getAppId()));
		}

		if (appWorkflow.getAppVersion() == null) {
			map.put("appVersion", null);
		}
		else {
			map.put("appVersion", String.valueOf(appWorkflow.getAppVersion()));
		}

		if (appWorkflow.getAppWorkflowDefinitionId() == null) {
			map.put("appWorkflowDefinitionId", null);
		}
		else {
			map.put(
				"appWorkflowDefinitionId",
				String.valueOf(appWorkflow.getAppWorkflowDefinitionId()));
		}

		if (appWorkflow.getAppWorkflowStates() == null) {
			map.put("appWorkflowStates", null);
		}
		else {
			map.put(
				"appWorkflowStates",
				String.valueOf(appWorkflow.getAppWorkflowStates()));
		}

		if (appWorkflow.getAppWorkflowTasks() == null) {
			map.put("appWorkflowTasks", null);
		}
		else {
			map.put(
				"appWorkflowTasks",
				String.valueOf(appWorkflow.getAppWorkflowTasks()));
		}

		return map;
	}

	public static class AppWorkflowJSONParser
		extends BaseJSONParser<AppWorkflow> {

		@Override
		protected AppWorkflow createDTO() {
			return new AppWorkflow();
		}

		@Override
		protected AppWorkflow[] createDTOArray(int size) {
			return new AppWorkflow[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "appId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "appVersion")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "appWorkflowDefinitionId")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "appWorkflowStates")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "appWorkflowTasks")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			AppWorkflow appWorkflow, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "appId")) {
				if (jsonParserFieldValue != null) {
					appWorkflow.setAppId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "appVersion")) {
				if (jsonParserFieldValue != null) {
					appWorkflow.setAppVersion((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "appWorkflowDefinitionId")) {

				if (jsonParserFieldValue != null) {
					appWorkflow.setAppWorkflowDefinitionId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "appWorkflowStates")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					AppWorkflowState[] appWorkflowStatesArray =
						new AppWorkflowState[jsonParserFieldValues.length];

					for (int i = 0; i < appWorkflowStatesArray.length; i++) {
						appWorkflowStatesArray[i] =
							AppWorkflowStateSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					appWorkflow.setAppWorkflowStates(appWorkflowStatesArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "appWorkflowTasks")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					AppWorkflowTask[] appWorkflowTasksArray =
						new AppWorkflowTask[jsonParserFieldValues.length];

					for (int i = 0; i < appWorkflowTasksArray.length; i++) {
						appWorkflowTasksArray[i] = AppWorkflowTaskSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					appWorkflow.setAppWorkflowTasks(appWorkflowTasksArray);
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