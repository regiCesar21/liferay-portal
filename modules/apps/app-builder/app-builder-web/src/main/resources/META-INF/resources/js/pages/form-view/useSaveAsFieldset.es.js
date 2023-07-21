/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DataLayoutBuilderActions} from 'data-engine-taglib';
import {useContext} from 'react';

import {addItem} from '../../utils/client.es';
import {errorToast, successToast} from '../../utils/toast.es';
import FormViewContext from './FormViewContext.es';

export default ({dataLayoutBuilder}) => {
	const [{dataDefinition, fieldSets}, dispatch] = useContext(FormViewContext);
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

	return (fieldName) => {
		const {
			customProperties: {rows},
			label,
			nestedDataDefinitionFields,
		} = dataDefinition.dataDefinitionFields.find(
			({name}) => fieldName === name
		);

		const fieldLabel = label[defaultLanguageId];

		const dataLayoutRows = JSON.parse(rows).map(({columns}) => {
			return columns.map(({fields: fieldNames, size: columnSize}) => ({
				dataLayoutColumns: [{columnSize, fieldNames}],
			}))[0];
		});

		const fieldSetDefinition = {
			availableLanguageIds: [defaultLanguageId],
			dataDefinitionFields: nestedDataDefinitionFields,
			defaultDataLayout: {
				dataLayoutPages: [
					{
						dataLayoutRows,
						description: {
							[defaultLanguageId]: '',
						},
						title: {
							[defaultLanguageId]: '',
						},
					},
				],
				name: {
					[defaultLanguageId]: `${fieldLabel}Layout`,
				},
			},
			name: {
				[defaultLanguageId]: fieldLabel,
			},
		};

		addItem(
			`/o/data-engine/v2.0/data-definitions/by-content-type/app-builder-fieldset`,
			fieldSetDefinition
		)
			.then((dataDefinitionFieldSet) => {
				const ddmStructureId = dataDefinitionFieldSet.id;

				dispatch({
					payload: {
						fieldSets: [...fieldSets, dataDefinitionFieldSet],
					},
					type: DataLayoutBuilderActions.UPDATE_FIELDSETS,
				});

				const dataDefinitionFields = dataDefinition.dataDefinitionFields.map(
					(definitionField) => {
						if (definitionField.name === fieldName) {
							return {
								...definitionField,
								customProperties: {
									ddmStructureId,
									ddmStructureLayoutId:
										dataDefinitionFieldSet.defaultDataLayout
											.id,
									rows: '',
								},
							};
						}

						return definitionField;
					}
				);

				dispatch({
					payload: {
						dataDefinition: {
							...dataDefinition,
							dataDefinitionFields,
						},
					},
					type: DataLayoutBuilderActions.UPDATE_DATA_DEFINITION,
				});

				dataLayoutBuilder.dispatch('fieldEdited', {
					fieldName,
					propertyName: 'ddmStructureId',
					propertyValue: ddmStructureId,
				});

				successToast(Liferay.Language.get('fieldset-saved'));
			})
			.catch(({message}) => errorToast(message));
	};
};
