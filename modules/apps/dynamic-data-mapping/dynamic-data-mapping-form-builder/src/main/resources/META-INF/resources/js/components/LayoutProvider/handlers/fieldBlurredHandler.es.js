/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {updateField, updateFieldReference} from '../util/settingsContext.es';
import {findInvalidFieldReference, updateState} from './fieldEditedHandler.es';

export const handleFieldBlurred = (props, state, event) => {
	let newState = {
		fieldHovered: {},
		pages: state.pages,
	};

	if (event) {
		const {propertyName, propertyValue} = event;

		if (propertyName === 'name' && propertyValue === '') {
			newState = updateField(state, propertyName, propertyValue);
		}

		if (
			propertyName === 'fieldReference' &&
			(propertyValue === '' ||
				findInvalidFieldReference(
					state.focusedField,
					state.pages,
					propertyValue
				))
		) {
			newState = updateState(
				props,
				{
					...state,
					focusedField: updateFieldReference(
						state.focusedField,
						false,
						true
					),
				},
				propertyName,
				state.focusedField.fieldName
			);
		}
	}

	return newState;
};

export default handleFieldBlurred;
