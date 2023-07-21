/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.image;

import com.liferay.dynamic.data.mapping.configuration.DDMWebConfiguration;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	configurationPid = "com.liferay.dynamic.data.mapping.configuration.DDMWebConfiguration",
	immediate = true, property = "ddm.form.field.type.name=image",
	service = {
		DDMFormFieldValueAccessor.class, ImageDDMFormFieldValueAccessor.class
	}
)
public class ImageDDMFormFieldValueAccessor
	implements DDMFormFieldValueAccessor<JSONObject> {

	@Override
	public JSONObject getValue(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return jsonFactory.createJSONObject();
		}

		try {
			return jsonFactory.createJSONObject(value.getString(locale));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON object", jsonException);
			}

			return jsonFactory.createJSONObject();
		}
	}

	@Override
	public JSONObject getValueForEvaluation(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		return getValue(ddmFormFieldValue, locale);
	}

	@Override
	public boolean isEmpty(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		JSONObject jsonObject = getValue(ddmFormFieldValue, locale);

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		if (((!_ddmWebConfiguration.
				enableSettingTheImageDescriptionAsOptional() ||
			  GetterUtil.getBoolean(
				  ddmFormField.getProperty("requiredDescription"))) &&
			 Validator.isNull(jsonObject.getString("description"))) ||
			Validator.isNull(jsonObject.getString("url"))) {

			return true;
		}

		return false;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ddmWebConfiguration = ConfigurableUtil.createConfigurable(
			DDMWebConfiguration.class, properties);
	}

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		ImageDDMFormFieldValueAccessor.class);

	private volatile DDMWebConfiguration _ddmWebConfiguration;

}