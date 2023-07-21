/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getField} from '../../../util/fieldSupport.es';
import {updateRulesReferences} from '../util/rules.es';
import {updateField} from '../util/settingsContext.es';
import {updatePages} from './fieldEditedHandler.es';

const updateState = (props, state, properties) => {
	const {activePage, focusedField, pages, rules} = state;
	const {fieldName: previousFocusedFieldName} = focusedField;
	let newFocusedField;

	const {defaultLanguageId} = props;

	if (properties.length === 1) {
		const [{name, value}] = properties;
		newFocusedField = updateField(props, focusedField, name, value);
	}
	else {
		newFocusedField = properties.reduce(
			(initialField, {name, value}, index) => {
				let useField = initialField;
				if (index === 1) {
					if (initialField.name === 'nestedFields') {
						initialField.value = initialField.value.map(
							(nestedField) => {
								return updateField(
									props,
									nestedField,
									'label',
									nestedField.label[defaultLanguageId]
								);
							}
						);
					}

					useField = updateField(
						props,
						focusedField,
						initialField.name,
						initialField.value
					);
				}

				return updateField(props, useField, name, value);
			}
		);
	}

	const newPages = updatePages(
		props,
		pages,
		previousFocusedFieldName,
		newFocusedField
	);

	return {
		activePage,
		focusedField: newFocusedField,
		pages: newPages,
		rules: updateRulesReferences(
			rules || [],
			focusedField,
			newFocusedField
		),
	};
};

export default (props, state, event) => {
	const {fieldName, properties} = event;

	if (!Array.isArray(properties) || !properties.length) {
		return;
	}

	properties.filter(({name, value}) => name !== 'name' || value !== '');

	state = {
		...state,
		...(fieldName && {focusedField: getField(state.pages, fieldName)}),
	};

	return updateState(props, state, properties);
};
