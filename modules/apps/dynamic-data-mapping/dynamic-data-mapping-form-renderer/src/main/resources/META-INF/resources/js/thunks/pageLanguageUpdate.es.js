/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import {fetch} from 'frontend-js-web';

import {EVENT_TYPES} from '../actions/eventTypes.es';

const formatDataRecord = (languageId, pages, preserveValue) => {
	const dataRecordValues = {};

	const visitor = new PagesVisitor(pages);

	const setDataRecord = ({
		fieldName,
		localizable,
		localizedValue,
		repeatable,
		type,
		value,
		visible,
	}) => {
		if (type === 'fieldset') {
			return;
		}

		let _value = value;

		if (!visible) {
			_value = '';
		}

		if (localizable) {
			if (!dataRecordValues[fieldName]) {
				if (preserveValue) {
					dataRecordValues[fieldName] = {
						...localizedValue,
						[languageId]: [],
					};
				}
				else {
					dataRecordValues[fieldName] = {
						[languageId]: [],
						...localizedValue,
					};
				}
			}

			if (repeatable) {
				dataRecordValues[fieldName][languageId].push(_value);
			}
			else {
				dataRecordValues[fieldName] = {
					...localizedValue,
					[languageId]: _value,
				};
			}

			if (preserveValue) {
				Object.keys(dataRecordValues[fieldName]).forEach((key) => {
					dataRecordValues[fieldName][key] =
						dataRecordValues[fieldName][languageId];
				});
			}
		}
		else {
			dataRecordValues[fieldName] = _value;
		}
	};

	visitor.mapFields(
		(field) => {
			setDataRecord(field);
		},
		true,
		true
	);

	return dataRecordValues;
};

const getDataRecordValues = ({
	nextEditingLanguageId,
	pages,
	preserveValue,
	prevDataRecordValues,
	prevEditingLanguageId,
}) => {
	if (preserveValue) {
		return formatDataRecord(nextEditingLanguageId, pages, true);
	}

	const dataRecordValues = formatDataRecord(prevEditingLanguageId, pages);
	const newDataRecordValues = {...prevDataRecordValues};

	Object.keys(dataRecordValues).forEach((key) => {
		if (newDataRecordValues[key]) {
			newDataRecordValues[key][prevEditingLanguageId] =
				dataRecordValues[key][prevEditingLanguageId];
		}
		else {
			newDataRecordValues[key] = dataRecordValues[key];
		}
	});

	return newDataRecordValues;
};

export default function pageLanguageUpdate({
	ddmStructureLayoutId,
	nextEditingLanguageId,
	pages,
	portletNamespace,
	preserveValue,
	prevDataRecordValues,
	prevEditingLanguageId,
	readOnly,
}) {
	return (dispatch) => {
		const newDataRecordValues = getDataRecordValues({
			nextEditingLanguageId,
			pages,
			preserveValue,
			prevDataRecordValues,
			prevEditingLanguageId,
		});

		fetch(
			`/o/data-engine/v2.0/data-layouts/${ddmStructureLayoutId}/context`,
			{
				body: JSON.stringify({
					dataRecordValues: newDataRecordValues,
					namespace: portletNamespace,
					pathThemeImages: themeDisplay.getPathThemeImages(),
					readOnly,
					scopeGroupId: themeDisplay.getScopeGroupId(),
					siteGroupId: themeDisplay.getSiteGroupId(),
				}),
				headers: {
					'Accept-Language': nextEditingLanguageId.replace('_', '-'),
					'Content-Type': 'application/json',
				},
				method: 'POST',
			}
		)
			.then((response) => response.json())
			.then(({pages}) => {
				const visitor = new PagesVisitor(pages);
				const newPages = visitor.mapFields(
					(field, index) => {
						if (!field.localizedValue) {
							field.localizedValue = {};
						}

						if (field.repeatable) {
							let values = {};
							Object.keys(
								newDataRecordValues[field.fieldName]
							).forEach((key) => {
								values = {
									...values,
									[key]:
										newDataRecordValues[field.fieldName][
											key
										][index],
								};
							});
							field.localizedValue = values;
						}
						else if (newDataRecordValues[field.fieldName]) {
							field.localizedValue = {
								...newDataRecordValues[field.fieldName],
							};
						}

						return field;
					},
					true,
					true
				);

				dispatch({
					payload: {
						editingLanguageId: nextEditingLanguageId,
						pages: newPages,
					},
					type: EVENT_TYPES.ALL,
				});

				dispatch({
					payload: newDataRecordValues,
					type: EVENT_TYPES.UPDATE_DATA_RECORD_VALUES,
				});
			});
	};
}
