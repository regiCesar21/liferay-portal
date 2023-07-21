/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	DataLayoutBuilderActions,
	DataLayoutVisitor,
	TranslationManager,
	saveDataDefinition,
} from 'data-engine-taglib';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import {AppContext} from '../../AppContext.es';
import UpperToolbar from '../../components/upper-toolbar/UpperToolbar.es';
import {errorToast, successToast} from '../../utils/toast.es';
import {getValidName} from '../../utils/utils.es';
import FormViewContext from './FormViewContext.es';

export default ({newCustomObject, showTranslationManager}) => {
	const [defaultLanguageId, setDefaultLanguageId] = useState('');
	const [editingLanguageId, setEditingLanguageId] = useState('');
	const [isLoading, setLoading] = useState(false);

	const [state, dispatch] = useContext(FormViewContext);
	const {
		dataDefinition,
		dataDefinitionId,
		dataLayout,
		initialAvailableLanguageIds,
	} = state;

	const onEditingLanguageIdChange = useCallback(
		(editingLanguageId) => {
			setEditingLanguageId(editingLanguageId);

			dispatch({
				payload: editingLanguageId,
				type: DataLayoutBuilderActions.UPDATE_EDITING_LANGUAGE_ID,
			});
		},
		[dispatch]
	);

	useEffect(() => {
		if (dataDefinition.defaultLanguageId) {
			setDefaultLanguageId(dataDefinition.defaultLanguageId);

			onEditingLanguageIdChange(dataDefinition.defaultLanguageId);
		}
	}, [dataDefinition.defaultLanguageId, onEditingLanguageIdChange]);

	const {basePortletURL} = useContext(AppContext);
	const listUrl = `${basePortletURL}/#/custom-object/${dataDefinitionId}/form-views`;

	const onDataLayoutNameChange = ({target: {value}}) => {
		dispatch({
			payload: {
				name: {
					...dataLayout.name,
					[editingLanguageId]: value,
				},
			},
			type: DataLayoutBuilderActions.UPDATE_DATA_LAYOUT_NAME,
		});
	};

	const onKeyDown = (event) => {
		if (event.keyCode === 13) {
			event.preventDefault();

			event.target.blur();
		}
	};

	const onCancel = () => {
		if (newCustomObject) {
			Liferay.Util.navigate(basePortletURL);
		}
		else {
			Liferay.Util.navigate(listUrl);
		}
	};

	const onError = (error) => {
		const {title} = error;

		errorToast(title);
	};

	const onSuccess = () => {
		successToast(
			Liferay.Language.get('the-form-view-was-saved-successfully')
		);

		Liferay.Util.navigate(listUrl);
	};

	const onSave = () => {
		if (!dataLayout.name[defaultLanguageId]) {
			dataLayout.name[defaultLanguageId] =
				dataLayout.name[editingLanguageId];
		}

		dataLayout.name[defaultLanguageId] = getValidName(
			Liferay.Language.get('untitled-form-view'),
			dataLayout.name[defaultLanguageId]
		);

		setLoading(true);

		saveDataDefinition(state)
			.then(onSuccess)
			.catch((error) => {
				onError(error);
				setLoading(false);
			});
	};

	if (!defaultLanguageId) {
		return null;
	}

	return (
		<UpperToolbar>
			{showTranslationManager && (
				<UpperToolbar.Group>
					<TranslationManager
						defaultLanguageId={defaultLanguageId}
						editingLanguageId={editingLanguageId}
						onEditingLanguageIdChange={onEditingLanguageIdChange}
						translatedLanguageIds={{
							...dataLayout.name,
							...initialAvailableLanguageIds.reduce(
								(acc, cur) => {
									acc[cur] = cur;

									return acc;
								},
								{}
							),
						}}
					/>
				</UpperToolbar.Group>
			)}

			<UpperToolbar.Input
				onChange={onDataLayoutNameChange}
				onKeyDown={onKeyDown}
				placeholder={Liferay.Language.get('untitled-form-view')}
				value={dataLayout.name[editingLanguageId] || ''}
			/>

			<UpperToolbar.Group>
				<UpperToolbar.Button displayType="secondary" onClick={onCancel}>
					{Liferay.Language.get('cancel')}
				</UpperToolbar.Button>

				<UpperToolbar.Button
					disabled={
						isLoading ||
						!dataLayout.name[editingLanguageId] ||
						DataLayoutVisitor.isDataLayoutEmpty(
							dataLayout.dataLayoutPages
						)
					}
					onClick={onSave}
				>
					{Liferay.Language.get('save')}
				</UpperToolbar.Button>
			</UpperToolbar.Group>
		</UpperToolbar>
	);
};
