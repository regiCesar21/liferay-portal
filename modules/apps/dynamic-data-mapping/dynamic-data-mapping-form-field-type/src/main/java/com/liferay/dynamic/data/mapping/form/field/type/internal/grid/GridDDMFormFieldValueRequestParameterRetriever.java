/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.grid;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRequestParameterRetriever;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Queiroz
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=grid",
	service = DDMFormFieldValueRequestParameterRetriever.class
)
public class GridDDMFormFieldValueRequestParameterRetriever
	implements DDMFormFieldValueRequestParameterRetriever {

	@Override
	public String get(
		HttpServletRequest httpServletRequest, String ddmFormFieldParameterName,
		String defaultDDMFormFieldParameterValue) {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		Map<String, String[]> parameterMap =
			httpServletRequest.getParameterMap();

		if (!parameterMap.containsKey(ddmFormFieldParameterName)) {
			return jsonObject.toString();
		}

		String[] parameterValues = parameterMap.get(ddmFormFieldParameterName);

		if (parameterValues.length == 1) {
			try {
				jsonObject = jsonFactory.createJSONObject(parameterValues[0]);

				return jsonObject.toString();
			}
			catch (JSONException jsonException) {
				if (_log.isDebugEnabled()) {
					_log.debug(jsonException, jsonException);
				}

				jsonObject = jsonFactory.createJSONObject();
			}
		}

		for (String parameterValue : parameterValues) {
			if (!parameterValue.isEmpty()) {
				String[] parameterValueParts = parameterValue.split(";");

				jsonObject.put(parameterValueParts[0], parameterValueParts[1]);
			}
		}

		return jsonObject.toString();
	}

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		GridDDMFormFieldValueRequestParameterRetriever.class);

}