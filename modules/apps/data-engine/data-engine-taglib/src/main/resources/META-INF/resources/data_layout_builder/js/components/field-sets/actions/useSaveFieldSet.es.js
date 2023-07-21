/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext} from 'react';

import AppContext from '../../../AppContext.es';
import {UPDATE_DATA_DEFINITION, UPDATE_FIELDSETS} from '../../../actions.es';
import DataLayoutBuilderContext from '../../../data-layout-builder/DataLayoutBuilderContext.es';
import {updateItem} from '../../../utils/client.es';
import {getDataDefinitionFieldSet} from '../../../utils/dataDefinition.es';
import {
	containsField,
	normalizeDataLayoutRows,
} from '../../../utils/dataLayoutVisitor.es';
import {errorToast, successToast} from '../../../utils/toast.es';

export default ({availableLanguageIds, childrenContext, fieldSet}) => {
	const [context, dispatch] = useContext(AppContext);
	const [dataLayoutBuilder] = useContext(DataLayoutBuilderContext);
	const {dataDefinition, dataLayout, fieldSets} = context;
	const {state: childrenState} = childrenContext;

	return (name) => {
		const {
			dataDefinition: {dataDefinitionFields},
			dataLayout: {dataLayoutPages},
		} = childrenState;

		const normalizedFieldSet = {
			...fieldSet,
			availableLanguageIds,
			dataDefinitionFields,
			defaultDataLayout: {
				...fieldSet.defaultDataLayout,
				dataLayoutPages,
			},
			name,
		};

		return updateItem(
			`/o/data-engine/v2.0/data-definitions/${fieldSet.id}`,
			normalizedFieldSet
		)
			.then(() => {
				const dataDefinitionFieldSet = getDataDefinitionFieldSet(
					dataDefinition.dataDefinitionFields,
					fieldSet.id
				);

				const normalizedDataDefinitionFields = () =>
					dataDefinition.dataDefinitionFields.map((field) => {
						const {
							customProperties: {ddmStructureId},
						} = field;

						if (ddmStructureId == fieldSet.id) {
							return {
								...field,
								nestedDataDefinitionFields: dataDefinitionFields,
							};
						}

						return field;
					});

				if (dataDefinitionFieldSet) {
					const fieldName = dataDefinitionFieldSet.name;

					if (containsField(dataLayout.dataLayoutPages, fieldName)) {
						dataLayoutBuilder.dispatch('fieldEditedProperties', {
							defaultLanguageId: fieldSet.defaultLanguageId,
							fieldName,
							properties: [
								{
									name: 'nestedFields',
									value: dataDefinitionFields.map(({name}) =>
										dataLayoutBuilder.getDDMFormField(
											childrenState.dataDefinition,
											name
										)
									),
								},
								{
									name: 'rows',
									value: normalizeDataLayoutRows(
										dataLayoutPages
									),
								},
							],
						});
					}
					else {
						dispatch({
							payload: {
								dataDefinition: {
									...dataDefinition,
									dataDefinitionFields: normalizedDataDefinitionFields(),
								},
							},
							type: UPDATE_DATA_DEFINITION,
						});
					}
				}

				return Promise.resolve();
			})
			.then(() => {
				dispatch({
					payload: {
						fieldSets: fieldSets.map((field) => {
							if (fieldSet.id === field.id) {
								return normalizedFieldSet;
							}

							return field;
						}),
					},
					type: UPDATE_FIELDSETS,
				});

				successToast(Liferay.Language.get('fieldset-saved'));

				return Promise.resolve();
			})
			.catch(({message}) => errorToast(message));
	};
};
