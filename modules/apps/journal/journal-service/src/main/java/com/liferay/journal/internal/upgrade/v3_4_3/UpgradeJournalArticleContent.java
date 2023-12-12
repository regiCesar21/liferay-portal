/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v3_4_3;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Mateus Santana
 */
public class UpgradeJournalArticleContent extends UpgradeProcess {

	public UpgradeJournalArticleContent(LayoutLocalService layoutLocalService) {
		_layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		PreparedStatement selectPreparedStatement = connection.prepareStatement(
			"select id_, content from JournalArticle");

		ResultSet resultSet = selectPreparedStatement.executeQuery();

		while (resultSet.next()) {
			long id = resultSet.getLong("id_");

			String content = resultSet.getString("content");

			content = _convertDocumentContent(content);

			try (PreparedStatement updatePreparedStatement =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"update JournalArticle set content = ? where id_ = " +
							"?")) {

				updatePreparedStatement.setString(1, content);
				updatePreparedStatement.setLong(2, id);

				updatePreparedStatement.executeUpdate();
			}
		}
	}

	private void _convertDDMFields(
		Locale defaultLocale, Element dynamicElement) {

		String type = dynamicElement.attributeValue("type");

		if (Validator.isNotNull(type)) {
			dynamicElement.addAttribute("type", _convertDDMFieldType(type));
		}

		_convertDDMFieldValue(dynamicElement, type, defaultLocale);

		List<Element> childrenDynamicElements = dynamicElement.elements(
			"dynamic-element");

		for (Element childrenDynamicElement : childrenDynamicElements) {
			_convertDDMFields(defaultLocale, childrenDynamicElement);
		}
	}

	private String _convertDDMFieldType(String ddmFieldType) {
		if (Objects.equals(ddmFieldType, "boolean")) {
			return "checkbox_multiple";
		}

		if (Objects.equals(ddmFieldType, "ddm-color")) {
			return "color";
		}

		if (Objects.equals(ddmFieldType, "ddm-date")) {
			return "date";
		}

		if (Objects.equals(ddmFieldType, "ddm-decimal")) {
			return "numeric";
		}

		if (Objects.equals(ddmFieldType, "ddm-geolocation")) {
			return "geolocation";
		}

		if (Objects.equals(ddmFieldType, "ddm-journal-article")) {
			return "journal_article";
		}

		if (Objects.equals(ddmFieldType, "ddm-image")) {
			return "image";
		}

		if (Objects.equals(ddmFieldType, "ddm-integer")) {
			return "numeric";
		}

		if (Objects.equals(ddmFieldType, "ddm-link-to-page")) {
			return "link_to_layout";
		}

		if (Objects.equals(ddmFieldType, "ddm-number")) {
			return "numeric";
		}

		if (Objects.equals(ddmFieldType, "document_library")) {
			return "document_library";
		}

		if (Objects.equals(ddmFieldType, "text_area")) {
			return "rich_text";
		}

		if (Objects.equals(ddmFieldType, "text_box")) {
			return "text";
		}

		if (Objects.equals(ddmFieldType, "list")) {
			return "select";
		}

		if (Objects.equals(ddmFieldType, "selection_break")) {
			return "separator";
		}

		if (Objects.equals(ddmFieldType, "text")) {
			return "text";
		}

		return ddmFieldType;
	}

	private void _convertDDMFieldValue(
		Element element, String ddmFieldType, Locale defaultLocale) {

		List<Element> dynamicContentElements = element.elements(
			"dynamic-content");

		for (Element dynamicContentElement : dynamicContentElements) {
			if (Objects.equals(ddmFieldType, "list")) {
				continue;
			}

			String text = dynamicContentElement.getText();

			dynamicContentElement.clearContent();

			dynamicContentElement.addCDATA(
				_convertDDMFieldValue(defaultLocale, ddmFieldType, text));
		}
	}

	private String _convertDDMFieldValue(
		Locale defaultLocale, String ddmFieldType, String value) {

		if (Objects.equals(ddmFieldType, "ddm-link-to-page") ||
			Objects.equals(ddmFieldType, "link_to_layout")) {

			return _convertLinkToLayoutValue(defaultLocale, value);
		}

		return value;
	}

	private void _convertDocumentContent(
		Locale defaultLocale, Document document) {

		Element rootElement = document.getRootElement();

		List<Element> dynamicElements = rootElement.elements("dynamic-element");

		for (Element dynamicElement : dynamicElements) {
			_convertDDMFields(defaultLocale, dynamicElement);
		}
	}

	private String _convertDocumentContent(String content) {
		try {
			Document document = SAXReaderUtil.read(content);

			_convertDocumentContent(_getDefaultLocale(document), document);

			return XMLUtil.formatXML(document.asXML());
		}
		catch (Exception exception) {
			return content;
		}
	}

	private String _convertLinkToLayoutValue(
		Locale defaultLocale, String value) {

		String[] values = StringUtil.split(value, CharPool.AT);

		if (ArrayUtil.isEmpty(values)) {
			return StringPool.BLANK;
		}

		JSONObject jsonObject = JSONUtil.put("groupId", 0);

		long layoutId = GetterUtil.getLong(values[0]);
		boolean privateLayout = !Objects.equals(values[1], "public");

		if (values.length > 2) {
			long groupId = GetterUtil.getLong(values[2]);

			jsonObject.put("groupId", groupId);

			Layout layout = _layoutLocalService.fetchLayout(
				groupId, privateLayout, layoutId);

			if (layout != null) {
				jsonObject.put(
					"id", layout.getUuid()
				).put(
					"name", layout.getName(defaultLocale)
				).put(
					"value", layout.getFriendlyURL(defaultLocale)
				);
			}
		}

		jsonObject.put(
			"layoutId", layoutId
		).put(
			"privateLayout", privateLayout
		);

		return jsonObject.toString();
	}

	private Locale _getDefaultLocale(Document document) {
		Element rootElement = document.getRootElement();

		String defaultLanguageId = rootElement.attributeValue("default-locale");

		if (defaultLanguageId == null) {
			return LocaleUtil.getSiteDefault();
		}

		return LocaleUtil.fromLanguageId(defaultLanguageId);
	}

	private final LayoutLocalService _layoutLocalService;

}