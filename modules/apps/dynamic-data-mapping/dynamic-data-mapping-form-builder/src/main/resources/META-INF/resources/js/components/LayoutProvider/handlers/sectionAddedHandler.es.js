/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormSupport, PagesVisitor} from 'dynamic-data-mapping-form-renderer';

import {FIELD_TYPE_FIELDSET} from '../../../util/constants.es';
import {createField} from '../../../util/fieldSupport.es';
import {createFieldSet} from '../util/fieldset.es';
import {updateField} from '../util/settingsContext.es';
import handleFieldAdded from './fieldAddedHandler.es';
import handleFieldDeleted from './fieldDeletedHandler.es';

const addNestedField = ({field, indexes, nestedField, props}) => {
	const layout = FormSupport.addFieldToColumn(
		[{rows: field.rows}],
		indexes.pageIndex,
		indexes.rowIndex,
		indexes.columnIndex,
		nestedField.fieldName
	);
	const nestedFields = [...field.nestedFields, nestedField];

	field = updateField(props, field, 'nestedFields', nestedFields);

	const {rows} = layout[indexes.pageIndex];

	field = updateField(props, field, 'rows', rows);

	return {
		...field,
		nestedFields,
		rows,
	};
};

const handleSectionAdded = (props, state, event) => {
	const {data, indexes} = event;
	const {fieldName, parentFieldName} = data;
	const {pages} = state;

	const newField = event.newField || createField(props, event);
	const existingField = FormSupport.findFieldByFieldName(pages, fieldName);
	const fieldSetField = createFieldSet(props, event, [
		existingField,
		newField,
	]);

	const visitor = new PagesVisitor(pages);

	let newPages;

	if (parentFieldName) {
		newPages = visitor.mapFields(
			(field) => {
				if (field.fieldName === parentFieldName) {
					const updatedParentField = FormSupport.findFieldByFieldName(
						handleFieldDeleted(props, state, {
							fieldName,
						}).pages,
						parentFieldName
					);

					return addNestedField({
						field: updatedParentField,
						indexes: {
							...indexes,
							pageIndex: 0,
						},
						nestedField: fieldSetField,
						props,
					});
				}

				return field;
			},
			false,
			true
		);
	}
	else if (existingField.type === FIELD_TYPE_FIELDSET) {
		newPages = handleFieldAdded(props, state, {
			...event,
			data: {
				...event.data,
				parentFieldName: existingField.fieldName,
			},
		}).pages;
	}
	else {
		newPages = visitor.mapFields((field) => {
			if (field.fieldName === fieldName) {
				return fieldSetField;
			}

			return field;
		});
	}

	return {
		focusedField: {
			...newField,
		},
		pages: newPages,
		previousFocusedField: fieldSetField,
	};
};

export default handleSectionAdded;
