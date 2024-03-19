/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.io;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;

import java.util.Map;

/**
 * @author Leonardo Barros
 */
public final class DDMFormValuesDeserializerDeserializeRequest {

	public String getContent() {
		return _content;
	}

	public DDMForm getDDMForm() {
		return _ddmForm;
	}

	public Map<String, DDMFormField> getNewDDMFormFieldsReferencesMap() {
		return _newDDMFormFieldsReferencesMap;
	}

	public Map<String, DDMFormField> getOldDDMFormFieldsReferencesMap() {
		return _oldDDMFormFieldsReferencesMap;
	}

	public static class Builder {

		public static Builder newBuilder(String content, DDMForm ddmForm) {
			return new Builder(content, ddmForm);
		}

		public DDMFormValuesDeserializerDeserializeRequest build() {
			return _ddmFormValuesDeserializerDeserializeRequest;
		}

		public Builder withNewDDMFormFieldsReferencesMap(
			Map<String, DDMFormField> newDDMFormFieldsReferencesMap) {

			_ddmFormValuesDeserializerDeserializeRequest.
				_newDDMFormFieldsReferencesMap = newDDMFormFieldsReferencesMap;

			return this;
		}

		public Builder withOldDDMFormFieldsReferencesMap(
			Map<String, DDMFormField> oldDDMFormFieldsReferencesMap) {

			_ddmFormValuesDeserializerDeserializeRequest.
				_oldDDMFormFieldsReferencesMap = oldDDMFormFieldsReferencesMap;

			return this;
		}

		private Builder(String content, DDMForm ddmForm) {
			_ddmFormValuesDeserializerDeserializeRequest._content = content;
			_ddmFormValuesDeserializerDeserializeRequest._ddmForm = ddmForm;
		}

		private final DDMFormValuesDeserializerDeserializeRequest
			_ddmFormValuesDeserializerDeserializeRequest =
				new DDMFormValuesDeserializerDeserializeRequest();

	}

	private DDMFormValuesDeserializerDeserializeRequest() {
	}

	private String _content;
	private DDMForm _ddmForm;
	private Map<String, DDMFormField> _newDDMFormFieldsReferencesMap;
	private Map<String, DDMFormField> _oldDDMFormFieldsReferencesMap;

}