/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import handleFieldAdded from '../../../../../src/main/resources/META-INF/resources/js/components/LayoutProvider/handlers/fieldAddedHandler.es';
import handleFieldBlurred from '../../../../../src/main/resources/META-INF/resources/js/components/LayoutProvider/handlers/fieldBlurredHandler.es';
import handleFieldEdited from '../../../../../src/main/resources/META-INF/resources/js/components/LayoutProvider/handlers/fieldEditedHandler.es';
import {generateFieldName} from '../../../../../src/main/resources/META-INF/resources/js/components/LayoutProvider/util/fields.es';
import mockFieldTypes from '../../../__mock__/mockFieldTypes.es';

const addField = (props, state) => {
	return handleFieldAdded(props, state, {
		data: {},
		fieldType: mockFieldTypes[0],
		indexes: {
			columnIndex: 0,
			pageIndex: 0,
			rowIndex: 0,
		},
	});
};

const blurField = (props, state, value) => {
	return handleFieldBlurred(props, state, {
		propertyName: 'fieldReference',
		propertyValue: value,
	});
};

const editField = (props, state, value) => {
	return handleFieldEdited(props, state, {
		propertyName: 'fieldReference',
		propertyValue: value,
	});
};

const getInitialFormContext = () => {
	const state = {
		focusedField: {},
		pages: [{rows: [{columns: [{fields: []}]}]}],
		rules: [],
	};

	const props = {
		defaultLanguageId: 'en_US',
		editingLanguageId: 'en_US',
		fieldNameGenerator: (desiredName, currentName) => {
			const {pages} = state;

			return generateFieldName(pages, desiredName, currentName);
		},
	};

	return {
		props,
		state,
	};
};

describe('LayoutProvider/handlers/fieldBlurredHandler', () => {
	describe('handleFieldBlured(props, state, event)', () => {
		it('updates the field reference with original fieldReference when field reference is empty', () => {
			const formContext = getInitialFormContext();

			const {props} = formContext;

			let {state} = formContext;

			state = addField(props, state);

			const defaultFieldReference = state.focusedField.fieldName;

			state = editField(props, state, '');

			state = blurField(props, state, '');

			expect(state.focusedField.fieldReference).toEqual(
				defaultFieldReference
			);
		});

		it('updates the field reference with original fieldReference when field reference already exists', () => {
			const formContext = getInitialFormContext();

			const {props} = formContext;

			let {state} = formContext;

			state = addField(props, state);

			state = editField(props, state, 'NewReference');

			state = addField(props, state);

			const defaultFieldReference = state.focusedField.fieldName;

			state = editField(props, state, 'NewReference');

			state = blurField(props, state, 'NewReference');

			expect(state.focusedField.fieldReference).toEqual(
				defaultFieldReference
			);
		});
	});
});
