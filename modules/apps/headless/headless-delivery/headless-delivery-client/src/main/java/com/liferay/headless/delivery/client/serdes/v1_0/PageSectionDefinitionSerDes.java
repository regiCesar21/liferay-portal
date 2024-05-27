/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.FragmentViewport;
import com.liferay.headless.delivery.client.dto.v1_0.PageSectionDefinition;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class PageSectionDefinitionSerDes {

	public static PageSectionDefinition toDTO(String json) {
		PageSectionDefinitionJSONParser pageSectionDefinitionJSONParser =
			new PageSectionDefinitionJSONParser();

		return pageSectionDefinitionJSONParser.parseToDTO(json);
	}

	public static PageSectionDefinition[] toDTOs(String json) {
		PageSectionDefinitionJSONParser pageSectionDefinitionJSONParser =
			new PageSectionDefinitionJSONParser();

		return pageSectionDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PageSectionDefinition pageSectionDefinition) {
		if (pageSectionDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (pageSectionDefinition.getBackgroundColor() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"backgroundColor\": ");

			sb.append("\"");

			sb.append(_escape(pageSectionDefinition.getBackgroundColor()));

			sb.append("\"");
		}

		if (pageSectionDefinition.getBackgroundFragmentImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"backgroundFragmentImage\": ");

			sb.append(
				String.valueOf(
					pageSectionDefinition.getBackgroundFragmentImage()));
		}

		if (pageSectionDefinition.getBackgroundImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"backgroundImage\": ");

			sb.append(
				String.valueOf(pageSectionDefinition.getBackgroundImage()));
		}

		if (pageSectionDefinition.getFragmentLink() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentLink\": ");

			sb.append(String.valueOf(pageSectionDefinition.getFragmentLink()));
		}

		if (pageSectionDefinition.getFragmentStyle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentStyle\": ");

			sb.append(String.valueOf(pageSectionDefinition.getFragmentStyle()));
		}

		if (pageSectionDefinition.getFragmentViewports() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentViewports\": ");

			sb.append("[");

			for (int i = 0;
				 i < pageSectionDefinition.getFragmentViewports().length; i++) {

				sb.append(
					String.valueOf(
						pageSectionDefinition.getFragmentViewports()[i]));

				if ((i + 1) <
						pageSectionDefinition.getFragmentViewports().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (pageSectionDefinition.getIndexed() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"indexed\": ");

			sb.append(pageSectionDefinition.getIndexed());
		}

		if (pageSectionDefinition.getLayout() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"layout\": ");

			sb.append(String.valueOf(pageSectionDefinition.getLayout()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PageSectionDefinitionJSONParser pageSectionDefinitionJSONParser =
			new PageSectionDefinitionJSONParser();

		return pageSectionDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PageSectionDefinition pageSectionDefinition) {

		if (pageSectionDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (pageSectionDefinition.getBackgroundColor() == null) {
			map.put("backgroundColor", null);
		}
		else {
			map.put(
				"backgroundColor",
				String.valueOf(pageSectionDefinition.getBackgroundColor()));
		}

		if (pageSectionDefinition.getBackgroundFragmentImage() == null) {
			map.put("backgroundFragmentImage", null);
		}
		else {
			map.put(
				"backgroundFragmentImage",
				String.valueOf(
					pageSectionDefinition.getBackgroundFragmentImage()));
		}

		if (pageSectionDefinition.getBackgroundImage() == null) {
			map.put("backgroundImage", null);
		}
		else {
			map.put(
				"backgroundImage",
				String.valueOf(pageSectionDefinition.getBackgroundImage()));
		}

		if (pageSectionDefinition.getFragmentLink() == null) {
			map.put("fragmentLink", null);
		}
		else {
			map.put(
				"fragmentLink",
				String.valueOf(pageSectionDefinition.getFragmentLink()));
		}

		if (pageSectionDefinition.getFragmentStyle() == null) {
			map.put("fragmentStyle", null);
		}
		else {
			map.put(
				"fragmentStyle",
				String.valueOf(pageSectionDefinition.getFragmentStyle()));
		}

		if (pageSectionDefinition.getFragmentViewports() == null) {
			map.put("fragmentViewports", null);
		}
		else {
			map.put(
				"fragmentViewports",
				String.valueOf(pageSectionDefinition.getFragmentViewports()));
		}

		if (pageSectionDefinition.getIndexed() == null) {
			map.put("indexed", null);
		}
		else {
			map.put(
				"indexed", String.valueOf(pageSectionDefinition.getIndexed()));
		}

		if (pageSectionDefinition.getLayout() == null) {
			map.put("layout", null);
		}
		else {
			map.put(
				"layout", String.valueOf(pageSectionDefinition.getLayout()));
		}

		return map;
	}

	public static class PageSectionDefinitionJSONParser
		extends BaseJSONParser<PageSectionDefinition> {

		@Override
		protected PageSectionDefinition createDTO() {
			return new PageSectionDefinition();
		}

		@Override
		protected PageSectionDefinition[] createDTOArray(int size) {
			return new PageSectionDefinition[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "backgroundColor")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "backgroundFragmentImage")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "backgroundImage")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentLink")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentStyle")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentViewports")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "indexed")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "layout")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			PageSectionDefinition pageSectionDefinition,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "backgroundColor")) {
				if (jsonParserFieldValue != null) {
					pageSectionDefinition.setBackgroundColor(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "backgroundFragmentImage")) {

				if (jsonParserFieldValue != null) {
					pageSectionDefinition.setBackgroundFragmentImage(
						FragmentImageSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "backgroundImage")) {
				if (jsonParserFieldValue != null) {
					pageSectionDefinition.setBackgroundImage(
						BackgroundImageSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentLink")) {
				if (jsonParserFieldValue != null) {
					pageSectionDefinition.setFragmentLink(
						FragmentLinkSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentStyle")) {
				if (jsonParserFieldValue != null) {
					pageSectionDefinition.setFragmentStyle(
						FragmentStyleSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentViewports")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					FragmentViewport[] fragmentViewportsArray =
						new FragmentViewport[jsonParserFieldValues.length];

					for (int i = 0; i < fragmentViewportsArray.length; i++) {
						fragmentViewportsArray[i] =
							FragmentViewportSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					pageSectionDefinition.setFragmentViewports(
						fragmentViewportsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "indexed")) {
				if (jsonParserFieldValue != null) {
					pageSectionDefinition.setIndexed(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "layout")) {
				if (jsonParserFieldValue != null) {
					pageSectionDefinition.setLayout(
						LayoutSerDes.toDTO((String)jsonParserFieldValue));
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