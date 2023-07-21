/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.report;

import com.liferay.dynamic.data.mapping.constants.DDMFormInstanceReportConstants;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.report.DDMFormFieldTypeReportProcessor;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Iterator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcos Martins
 */
@Component(
	immediate = true,
	property = {
		"ddm.form.field.type.name=checkbox_multiple",
		"ddm.form.field.type.name=select"
	},
	service = DDMFormFieldTypeReportProcessor.class
)
public class CheckboxMultipleDDMFormFieldTypeReportProcessor
	implements DDMFormFieldTypeReportProcessor {

	@Override
	public JSONObject process(
			DDMFormFieldValue ddmFormFieldValue, JSONObject fieldJSONObject,
			long formInstanceRecordId, String ddmFormInstanceReportEvent)
		throws Exception {

		JSONObject valuesJSONObject = fieldJSONObject.getJSONObject("values");

		Value value = ddmFormFieldValue.getValue();

		JSONArray valueJSONArray = JSONFactoryUtil.createJSONArray(
			value.getString(value.getDefaultLocale()));

		Iterator<String> iterator = valueJSONArray.iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();

			int count = valuesJSONObject.getInt(key, 0);

			if (ddmFormInstanceReportEvent.equals(
					DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION)) {

				count++;
			}
			else if (ddmFormInstanceReportEvent.equals(
						DDMFormInstanceReportConstants.
							EVENT_DELETE_RECORD_VERSION)) {

				count--;
			}

			valuesJSONObject.put(key, count);
		}

		int totalEntries = fieldJSONObject.getInt("totalEntries");

		if (valueJSONArray.length() != 0) {
			if (ddmFormInstanceReportEvent.equals(
					DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION)) {

				totalEntries++;
			}
			else if (ddmFormInstanceReportEvent.equals(
						DDMFormInstanceReportConstants.
							EVENT_DELETE_RECORD_VERSION)) {

				totalEntries--;
			}
		}

		fieldJSONObject.put("totalEntries", totalEntries);

		return fieldJSONObject;
	}

}