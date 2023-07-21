/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DataLayoutBuilderActions} from 'data-engine-taglib';
import React, {useContext, useEffect} from 'react';

import DataLayoutBuilderContext from './DataLayoutBuilderInstanceContext.es';
import FormViewContext from './FormViewContext.es';
import useDeleteDefinitionField from './useDeleteDefinitionField.es';
import useDeleteDefinitionFieldModal from './useDeleteDefinitionFieldModal.es';
import useSaveAsFieldset from './useSaveAsFieldset.es';

export default ({children, dataLayoutBuilder}) => {
	const [
		{
			config: {allowNestedFields},
			dataDefinition: {defaultLanguageId},
			editingLanguageId,
			hoveredField,
		},
		dispatch,
	] = useContext(FormViewContext);
	const deleteDefinitionField = useDeleteDefinitionField({dataLayoutBuilder});
	const onDeleteDefinitionField = useDeleteDefinitionFieldModal((event) => {
		deleteDefinitionField(event);
	});

	const saveAsFieldset = useSaveAsFieldset({dataLayoutBuilder});

	useEffect(() => {
		dataLayoutBuilder.onEditingLanguageIdChange({
			defaultLanguageId,
			editingLanguageId,
		});
	}, [dataLayoutBuilder, defaultLanguageId, editingLanguageId]);

	useEffect(() => {
		const duplicateAction = {
			action: (event) =>
				dataLayoutBuilder.dispatch('fieldDuplicated', event),
			label: Liferay.Language.get('duplicate'),
		};

		const removeAction = {
			action: (event) => {
				dispatch({
					payload: {fieldName: event.fieldName},
					type: DataLayoutBuilderActions.DELETE_DATA_LAYOUT_FIELD,
				});

				dataLayoutBuilder.dispatch('fieldDeleted', event);
			},
			label: Liferay.Language.get('remove'),
		};

		const deleteFromObjectAction = {
			action: (event) => {
				onDeleteDefinitionField(event);
			},
			label: Liferay.Language.get('delete-from-object'),
			style: 'danger',
		};

		let fieldActions = [
			duplicateAction,
			{
				...removeAction,
				separator: true,
			},
			deleteFromObjectAction,
		];

		if (
			allowNestedFields &&
			Object.keys(hoveredField).length &&
			hoveredField.fieldType === 'fieldset' &&
			!hoveredField.customProperties.ddmStructureId
		) {
			fieldActions = [
				duplicateAction,
				removeAction,
				{
					action: ({fieldName}) => saveAsFieldset(fieldName),
					label: Liferay.Language.get('save-as-fieldset'),
					separator: true,
				},
				deleteFromObjectAction,
			];
		}

		if (hoveredField.fieldType === 'fieldset') {
			fieldActions.splice(fieldActions.indexOf(duplicateAction), 1);
		}

		const provider = dataLayoutBuilder.getLayoutProvider();

		provider.props = {
			...provider.props,
			fieldActions,
		};

		provider.getEvents().fieldHovered(hoveredField);
	}, [
		allowNestedFields,
		dataLayoutBuilder,
		dispatch,
		hoveredField,
		onDeleteDefinitionField,
		saveAsFieldset,
	]);

	return (
		<DataLayoutBuilderContext.Provider
			value={[dataLayoutBuilder, dataLayoutBuilder.dispatch]}
		>
			{children}
		</DataLayoutBuilderContext.Provider>
	);
};
