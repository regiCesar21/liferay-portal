/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';

import {createFieldSet} from '../util/fieldset.es';
import {updateField} from '../util/settingsContext.es';
import {addField} from './fieldAddedHandler.es';

const handleFieldSetAdded = (props, state, event) => {
	const {
		defaultLanguageId,
		fieldSet,
		indexes,
		parentFieldName,
		properties,
		rows,
		useFieldName,
	} = event;
	const {pages} = state;
	const visitor = new PagesVisitor(fieldSet.pages);
	const nestedFields = [];

	visitor.mapFields((nestedField) => {
		nestedFields.push(
			updateField(
				props,
				nestedField,
				'label',
				nestedField.label[defaultLanguageId]
			)
		);
	});

	let fieldSetField = createFieldSet(
		props,
		{skipFieldNameGeneration: false, useFieldName},
		nestedFields
	);

	if (properties) {
		Object.keys(properties).forEach((key) => {
			fieldSetField = updateField(
				props,
				fieldSetField,
				key,
				properties[key]
			);
		});
	}

	if (fieldSet.id) {
		fieldSetField = updateField(
			props,
			fieldSetField,
			'ddmStructureId',
			fieldSet.id
		);
	}

	if (rows && rows.length) {
		fieldSetField = updateField(props, fieldSetField, 'rows', rows);
	}

	return addField(props, {
		indexes,
		newField: updateField(
			props,
			fieldSetField,
			'label',
			fieldSet.localizedTitle[defaultLanguageId]
		),
		pages,
		parentFieldName,
	});
};

export default handleFieldSetAdded;
