/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';

import {getField} from '../util/fields.es';
import handleFieldEdited from './fieldEditedHandler.es';

const handleFocusedFieldEvaluationEnded = (
	props,
	state,
	changedEditingLanguage = false,
	changedFieldType = false,
	instanceId,
	settingsContext
) => {
	if (changedEditingLanguage) {
		return state;
	}

	const fieldName = getField(settingsContext.pages, 'name');
	const {focusedField} = state;
	const focusedFieldName = getField(
		focusedField.settingsContext.pages,
		'name'
	);

	if (
		fieldName.instanceId !== focusedFieldName.instanceId &&
		!changedFieldType
	) {
		return state;
	}

	state = {
		...state,
		focusedField: {
			...focusedField,
			instanceId: instanceId || focusedField.instanceId,
			settingsContext,
		},
	};

	const visitor = new PagesVisitor(settingsContext.pages);

	visitor.mapFields(({fieldName, value}) => {
		state = handleFieldEdited(props, state, {
			propertyName: fieldName,
			propertyValue: value,
		});
	});

	return state;
};

export default handleFocusedFieldEvaluationEnded;
