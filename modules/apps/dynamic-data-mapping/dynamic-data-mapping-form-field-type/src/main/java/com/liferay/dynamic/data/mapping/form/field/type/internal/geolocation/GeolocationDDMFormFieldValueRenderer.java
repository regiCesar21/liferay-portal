/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.geolocation;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=geolocation",
	service = {
		DDMFormFieldValueRenderer.class,
		GeolocationDDMFormFieldValueRenderer.class
	}
)
public class GeolocationDDMFormFieldValueRenderer
	implements DDMFormFieldValueRenderer {

	@Override
	public String render(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		Value value = ddmFormFieldValue.getValue();

		String valueString = value.getString(locale);

		if (Validator.isNotNull(valueString)) {
			JSONObject jsonObject = null;

			try {
				jsonObject = JSONFactoryUtil.createJSONObject(valueString);
			}
			catch (JSONException jsonException) {
				if (_log.isDebugEnabled()) {
					_log.debug(jsonException);
				}

				return StringPool.BLANK;
			}

			StringBundler sb = new StringBundler(5);

			sb.append("Latitude: ");
			sb.append(jsonObject.get("lat"));
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append("Longitude: ");
			sb.append(jsonObject.get("lng"));

			return sb.toString();
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GeolocationDDMFormFieldValueRenderer.class);

}