/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.validator;

import com.liferay.fragment.exception.FragmentEntryConfigurationException;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.petra.json.validator.JSONValidator;
import com.liferay.petra.json.validator.JSONValidatorException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = FragmentEntryValidator.class)
public class FragmentEntryValidatorImpl implements FragmentEntryValidator {

	@Override
	public void validateConfiguration(String configuration)
		throws FragmentEntryConfigurationException {

		validateConfigurationValues(configuration, null);
	}

	@Override
	public void validateConfigurationValues(
			String configuration, JSONObject valuesJSONObject)
		throws FragmentEntryConfigurationException {

		if (Validator.isNull(configuration)) {
			return;
		}

		InputStream configurationJSONSchemaInputStream =
			FragmentEntryValidatorImpl.class.getResourceAsStream(
				"dependencies/configuration-json-schema.json");

		try {
			JSONValidator.validate(
				configuration, configurationJSONSchemaInputStream);

			JSONObject configurationJSONObject =
				JSONFactoryUtil.createJSONObject(configuration);

			JSONArray fieldSetsJSONArray = configurationJSONObject.getJSONArray(
				"fieldSets");

			Set<String> fieldNames = new HashSet<>();

			for (int fieldSetIndex = 0;
				 fieldSetIndex < fieldSetsJSONArray.length(); fieldSetIndex++) {

				JSONObject fieldSetJSONObject =
					fieldSetsJSONArray.getJSONObject(fieldSetIndex);

				JSONArray fieldsJSONArray = fieldSetJSONObject.getJSONArray(
					"fields");

				for (int fieldIndex = 0; fieldIndex < fieldsJSONArray.length();
					 fieldIndex++) {

					JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(
						fieldIndex);

					String fieldName = fieldJSONObject.getString("name");

					if (fieldNames.contains(fieldName)) {
						throw new FragmentEntryConfigurationException(
							"Field names must be unique");
					}

					JSONObject typeOptionsJSONObject =
						fieldJSONObject.getJSONObject("typeOptions");

					if (typeOptionsJSONObject != null) {
						String defaultValue = fieldJSONObject.getString(
							"defaultValue");

						if (!_checkValidationRules(
								defaultValue,
								typeOptionsJSONObject.getJSONObject(
									"validation"))) {

							throw new FragmentEntryConfigurationException(
								"Invalid default configuration value for " +
									"field " + fieldName);
						}

						if (valuesJSONObject != null) {
							String value = valuesJSONObject.getString(
								fieldName);

							if (!_checkValidationRules(
									value,
									typeOptionsJSONObject.getJSONObject(
										"validation"))) {

								throw new FragmentEntryConfigurationException(
									"Invalid configuration value for field " +
										fieldName);
							}
						}
					}

					fieldNames.add(fieldName);
				}
			}
		}
		catch (JSONException jsonException) {
			throw new FragmentEntryConfigurationException(
				_getMessage(jsonException.getMessage()), jsonException);
		}
		catch (JSONValidatorException jsonValidatorException) {
			throw new FragmentEntryConfigurationException(
				_getMessage(jsonValidatorException.getMessage()),
				jsonValidatorException);
		}
	}

	private boolean _checkValidationRules(
		String value, JSONObject validationJSONObject) {

		if (Validator.isNull(value) || (validationJSONObject == null)) {
			return true;
		}

		String type = validationJSONObject.getString("type");

		if (Objects.equals(type, "email")) {
			return Validator.isEmailAddress(value);
		}
		else if (Objects.equals(type, "number")) {
			long max = validationJSONObject.getLong("max", Long.MAX_VALUE);
			long min = validationJSONObject.getLong("min", Long.MIN_VALUE);

			boolean valid = false;

			if (Validator.isNumber(value) &&
				(GetterUtil.getLong(value) <= max) &&
				(GetterUtil.getLong(value) >= min)) {

				valid = true;
			}

			return valid;
		}
		else if (Objects.equals(type, "pattern")) {
			String regexp = validationJSONObject.getString("regexp");

			return value.matches(regexp);
		}
		else if (Objects.equals(type, "url")) {
			return Validator.isUrl(value);
		}

		long maxLength = validationJSONObject.getLong(
			"maxLength", Long.MAX_VALUE);
		long minLength = validationJSONObject.getLong(
			"minLength", Long.MIN_VALUE);

		if ((value.length() <= maxLength) && (value.length() >= minLength)) {
			return true;
		}

		return false;
	}

	private String _getMessage(String message) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		StringBundler sb = new StringBundler(3);

		sb.append(
			LanguageUtil.get(
				resourceBundle, "fragment-configuration-is-invalid"));
		sb.append(System.lineSeparator());
		sb.append(message);

		return sb.toString();
	}

}