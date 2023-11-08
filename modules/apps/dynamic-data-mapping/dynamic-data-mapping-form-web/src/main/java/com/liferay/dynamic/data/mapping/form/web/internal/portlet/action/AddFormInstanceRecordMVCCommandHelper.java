/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, service = AddFormInstanceRecordMVCCommandHelper.class
)
public class AddFormInstanceRecordMVCCommandHelper {

	public void updateNonevaluableDDMFormFields(
			ActionRequest actionRequest, DDMForm ddmForm,
			DDMFormValues ddmFormValues, Locale locale)
		throws Exception {

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			_evaluate(actionRequest, ddmForm, ddmFormValues, locale);

		Set<String> nonevaluableFieldNames = _getNonevaluableFieldNames(
			ddmFormEvaluatorEvaluateResponse);

		Set<String> fieldNamesFromDisabledPages =
			_getFieldNamesFromDisabledPages(
				_getDDMFormLayout(actionRequest),
				ddmFormEvaluatorEvaluateResponse.getDisabledPagesIndexes());

		nonevaluableFieldNames.addAll(fieldNamesFromDisabledPages);

		_removeDDMFormFieldValues(
			ddmFormValues.getDDMFormFieldValuesMap(true),
			nonevaluableFieldNames);
		_updateNonevaluableDDMFormFields(
			ddmForm.getDDMFormFieldsMap(true), nonevaluableFieldNames);
	}

	private DDMFormEvaluatorEvaluateResponse _evaluate(
			ActionRequest actionRequest, DDMForm ddmForm,
			DDMFormValues ddmFormValues, Locale locale)
		throws Exception {

		DDMFormEvaluatorEvaluateRequest.Builder builder =
			DDMFormEvaluatorEvaluateRequest.Builder.newBuilder(
				ddmForm, ddmFormValues, locale);

		return _ddmFormEvaluator.evaluate(
			builder.withCompanyId(
				_portal.getCompanyId(actionRequest)
			).withGroupId(
				ParamUtil.getLong(actionRequest, "groupId")
			).withUserId(
				_portal.getUserId(actionRequest)
			).build());
	}

	private DDMFormLayout _getDDMFormLayout(ActionRequest actionRequest)
		throws Exception {

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceService.getFormInstance(
				ParamUtil.getLong(actionRequest, "formInstanceId"));

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			ddmFormInstance.getStructureId());

		return ddmStructure.getDDMFormLayout();
	}

	private Set<String> _getFieldNamesFromDisabledPages(
		DDMFormLayout ddmFormLayout, Set<Integer> disabledPagesIndexes) {

		Stream<Integer> stream = disabledPagesIndexes.stream();

		return stream.map(
			index -> _getFieldNamesFromPage(
				ddmFormLayout.getDDMFormLayoutPage(index))
		).flatMap(
			field -> field.stream()
		).collect(
			Collectors.toSet()
		);
	}

	private Set<String> _getFieldNamesFromPage(
		DDMFormLayoutPage ddmFormLayoutPage) {

		Set<String> fieldNames = new HashSet<>();

		for (DDMFormLayoutRow ddmFormLayoutRow :
				ddmFormLayoutPage.getDDMFormLayoutRows()) {

			for (DDMFormLayoutColumn ddmFormLayoutColumn :
					ddmFormLayoutRow.getDDMFormLayoutColumns()) {

				fieldNames.addAll(ddmFormLayoutColumn.getDDMFormFieldNames());
			}
		}

		return fieldNames;
	}

	private Set<String> _getNonevaluableFieldNames(
		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse) {

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Set<Map.Entry<DDMFormEvaluatorFieldContextKey, Map<String, Object>>>
			entrySet = ddmFormFieldsPropertyChanges.entrySet();

		Stream<Map.Entry<DDMFormEvaluatorFieldContextKey, Map<String, Object>>>
			stream = entrySet.stream();

		return stream.filter(
			result -> !MapUtil.getBoolean(result.getValue(), "visible", true)
		).map(
			result -> result.getKey()
		).map(
			DDMFormEvaluatorFieldContextKey::getName
		).collect(
			Collectors.toSet()
		);
	}

	private void _removeDDMFormFieldValue(DDMFormFieldValue ddmFormFieldValue) {
		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		if (ddmFormField.isLocalizable()) {
			Value value = ddmFormFieldValue.getValue();

			LocalizedValue localizedValue = new LocalizedValue(
				value.getDefaultLocale());

			for (Locale availableLocale : value.getAvailableLocales()) {
				localizedValue.addString(availableLocale, StringPool.BLANK);
			}

			ddmFormFieldValue.setValue(localizedValue);
		}
		else {
			ddmFormFieldValue.setValue(new UnlocalizedValue(StringPool.BLANK));
		}
	}

	private void _removeDDMFormFieldValues(
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap,
		Set<String> nonevaluableFieldNames) {

		Stream<String> stream = nonevaluableFieldNames.stream();

		stream.map(
			ddmFormFieldValuesMap::get
		).flatMap(
			List::stream
		).filter(
			ddmFormFieldValue -> ddmFormFieldValue.getValue() != null
		).forEach(
			this::_removeDDMFormFieldValue
		);
	}

	private void _updateNonevaluableDDMFormFields(
		Map<String, DDMFormField> ddmFormFieldsMap,
		Set<String> nonevaluableFieldNames) {

		Collection<DDMFormField> ddmFormFields = ddmFormFieldsMap.values();

		Stream<DDMFormField> stream = ddmFormFields.stream();

		stream.filter(
			ddmFormField -> nonevaluableFieldNames.contains(
				ddmFormField.getName())
		).forEach(
			ddmFormField -> {
				ddmFormField.setDDMFormFieldValidation(null);
				ddmFormField.setRequired(false);
			}
		);
	}

	@Reference
	private DDMFormEvaluator _ddmFormEvaluator;

	@Reference
	private DDMFormInstanceService _ddmFormInstanceService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private Portal _portal;

}