/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormSupport, PagesVisitor} from 'dynamic-data-mapping-form-renderer';

import {FIELD_TYPE_FIELDSET} from '../../../util/constants.es';
import {getParentField} from '../../../util/fieldSupport.es';
import {updateField} from '../util/settingsContext.es';
import {addField} from './fieldAddedHandler.es';
import handleFieldDeleted from './fieldDeletedHandler.es';
import handleSectionAdded from './sectionAddedHandler.es';

export default (props, state, event) => {
	const {
		sourceFieldName,
		sourceFieldPage,
		targetFieldName,
		targetIndexes,
		targetParentFieldName,
	} = event;

	const sourceField = FormSupport.findFieldByFieldName(
		state.pages,
		sourceFieldName
	);

	let mergedState = {
		...handleFieldDeleted(props, state, {
			activePage: sourceFieldPage,
			editRule: false,
			fieldName: sourceFieldName,
			removeEmptyRows: false,
		}),
	};
	let parentField = getParentField(state.pages, sourceFieldName);

	if (
		parentField &&
		targetFieldName &&
		parentField.fieldName === targetFieldName
	) {
		return state;
	}

	if (
		parentField &&
		parentField.type === FIELD_TYPE_FIELDSET &&
		parentField.nestedFields.length === 1
	) {
		let parentFieldName = parentField ? parentField.fieldName : '';

		do {
			if (parentField) {
				parentFieldName = parentField.fieldName;
			}

			parentField = getParentField(state.pages, parentField.fieldName);
		} while (
			parentField &&
			parentField.type === FIELD_TYPE_FIELDSET &&
			parentField.fieldName !== targetParentFieldName &&
			parentField.nestedFields.length === 1
		);

		if (parentFieldName && parentFieldName !== targetParentFieldName) {
			mergedState = {
				...handleFieldDeleted(props, state, {
					activePage: sourceFieldPage,
					editRule: false,
					fieldName: parentFieldName,
					removeEmptyRows: false,
				}),
			};
		}
	}

	if (targetFieldName) {
		const deletedState = handleFieldDeleted(props, state, {
			activePage: sourceFieldPage,
			editRule: false,
			fieldName: sourceFieldName,
		});

		return {
			...handleSectionAdded(
				props,
				{
					...state,
					pages: deletedState.pages,
				},
				{
					data: {
						fieldName: targetFieldName,
						parentFieldName: targetParentFieldName,
					},
					indexes: targetIndexes,
					newField: sourceField,
				}
			),
		};
	}

	const addedState = addField(props, {
		indexes: targetIndexes,
		newField: sourceField,
		pages: mergedState.pages,
		parentFieldName: targetParentFieldName,
	});

	const visitor = new PagesVisitor(addedState.pages);

	const pages = visitor.mapFields((field) => {
		if (field.type != 'grid' && field.rows) {
			return updateField(
				props,
				field,
				'rows',
				FormSupport.removeEmptyRows([field], 0)
			);
		}

		return field;
	});

	return {
		...addedState,
		pages: pages.map((page, pageIndex) => {
			if (sourceFieldPage === pageIndex) {
				return {
					...page,
					rows: FormSupport.removeEmptyRows(pages, pageIndex),
				};
			}

			return page;
		}),
	};
};
