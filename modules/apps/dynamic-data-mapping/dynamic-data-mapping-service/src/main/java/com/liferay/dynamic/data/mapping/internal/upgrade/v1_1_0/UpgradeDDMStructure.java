/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_0;

import com.liferay.dynamic.data.mapping.internal.util.ExpressionParameterValueExtractor;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.util.DDMFormDeserializeUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormSerializeUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Inácio Nery
 */
public class UpgradeDDMStructure extends UpgradeProcess {

	public UpgradeDDMStructure(
		DDMFormDeserializer ddmFormDeserializer,
		DDMFormSerializer ddmFormSerializer) {

		_ddmFormDeserializer = ddmFormDeserializer;
		_ddmFormSerializer = ddmFormSerializer;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeDDMStructureDefinition();
		upgradeDDMStructureVersionDefinition();
	}

	protected String updateDefinition(String definition) throws Exception {
		DDMForm ddmForm = DDMFormDeserializeUtil.deserialize(
			_ddmFormDeserializer, definition);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
			String visibilityExpression =
				ddmFormField.getVisibilityExpression();

			if (Validator.isNull(visibilityExpression)) {
				continue;
			}

			visibilityExpression = _convertExpression(visibilityExpression);

			DDMFormRule ddmFormRule = new DDMFormRule(
				Arrays.asList(
					"setVisible('" + ddmFormField.getName() + "', true)"),
				visibilityExpression);

			ddmFormRules.add(ddmFormRule);

			ddmFormField.setVisibilityExpression(StringPool.BLANK);
		}

		ddmForm.setDDMFormRules(ddmFormRules);

		return DDMFormSerializeUtil.serialize(ddmForm, _ddmFormSerializer);
	}

	protected void upgradeDDMStructureDefinition() throws Exception {
		StringBundler sb = new StringBundler(2);

		sb.append("select DDMStructure.definition, DDMStructure.structureId ");
		sb.append("from DDMStructure");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?")) {

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String definition = rs.getString(1);
					long structureId = rs.getLong(2);

					String newDefinition = updateDefinition(definition);

					ps2.setString(1, newDefinition);

					ps2.setLong(2, structureId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	protected void upgradeDDMStructureVersionDefinition() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("select DDMStructureVersion.definition, ");
		sb.append("DDMStructureVersion.structureVersionId from ");
		sb.append("DDMStructureVersion");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureVersionId = ?")) {

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String definition = rs.getString(1);
					long structureVersionId = rs.getLong(2);

					String newDefinition = updateDefinition(definition);

					ps2.setString(1, newDefinition);

					ps2.setLong(2, structureVersionId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private String _convertExpression(String visibilityExpression) {
		List<String> parameterValues =
			ExpressionParameterValueExtractor.extractParameterValues(
				visibilityExpression);

		StringBundler sb1 = new StringBundler();

		for (String parameterValue : parameterValues) {
			if (Validator.isNull(parameterValue) ||
				Validator.isNumber(parameterValue) ||
				StringUtil.startsWith(parameterValue, StringPool.QUOTE)) {

				continue;
			}

			StringBundler sb2 = new StringBundler(5);

			sb2.append("getValue(");
			sb2.append(StringPool.APOSTROPHE);
			sb2.append(parameterValue);
			sb2.append(StringPool.APOSTROPHE);
			sb2.append(")");

			int index = visibilityExpression.indexOf(parameterValue);

			sb1.append(visibilityExpression.substring(0, index));

			sb1.append(sb2.toString());

			visibilityExpression = visibilityExpression.substring(
				index + parameterValue.length());
		}

		sb1.append(visibilityExpression);

		return sb1.toString();
	}

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormSerializer _ddmFormSerializer;

}