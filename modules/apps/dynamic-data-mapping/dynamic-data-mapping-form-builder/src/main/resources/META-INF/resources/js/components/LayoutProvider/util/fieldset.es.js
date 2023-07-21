/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormSupport, PagesVisitor} from 'dynamic-data-mapping-form-renderer';

import {FIELD_TYPE_FIELDSET} from '../../../util/constants.es';
import {createField, generateInstanceId} from '../../../util/fieldSupport.es';
import {updateField} from '../util/settingsContext.es';

const addNestedFields = ({field, indexes, nestedFields, props}) => {
	let layout = [{rows: field.rows}];
	const visitor = new PagesVisitor(layout);

	visitor.mapFields((field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
		if (
			!nestedFields.some(
				(nestedField) => nestedField.fieldName === field.fieldName
			)
		) {
			layout = FormSupport.removeFields(
				layout,
				pageIndex,
				rowIndex,
				columnIndex
			);
		}
	});

	[...nestedFields].reverse().forEach((nestedField) => {
		if (!nestedField.instanceId) {
			nestedField.instanceId = generateInstanceId(8);
		}
		layout = FormSupport.addFieldToColumn(
			layout,
			indexes.pageIndex,
			indexes.rowIndex,
			indexes.columnIndex,
			nestedField.fieldName
		);
	});

	field = updateField(props, field, 'nestedFields', nestedFields);

	const {rows} = layout[indexes.pageIndex];

	return {
		...updateField(props, field, 'rows', rows),
		nestedFields,
		rows,
	};
};

export const createFieldSet = (
	props,
	event,
	nestedFields,
	rows = [{columns: [{fields: [], size: 12}]}]
) => {
	const {fieldTypes} = props;
	const fieldType = fieldTypes.find((fieldType) => {
		return fieldType.name === FIELD_TYPE_FIELDSET;
	});
	const fieldSetField = createField(props, {...event, fieldType});

	return addNestedFields({
		field: {
			...fieldSetField,
			rows,
		},
		indexes: {
			columnIndex: 0,
			pageIndex: 0,
			rowIndex: 0,
		},
		nestedFields,
		props,
	});
};
