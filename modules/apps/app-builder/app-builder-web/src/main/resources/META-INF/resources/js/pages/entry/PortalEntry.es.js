/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TranslationManager} from 'data-engine-taglib';
import React, {useContext, useEffect, useState} from 'react';
import {createPortal} from 'react-dom';

import {AppContext} from '../../AppContext.es';
import usePermissions from '../../hooks/usePermissions.es';
import {getItem} from '../../utils/client.es';
import {getLocalizedUserPreferenceValue} from '../../utils/lang.es';
import PersonalMenu from './PersonalMenuEntry.es';

const STORAGE_KEY = '@app-builder/standalone/language';

const setStorageLanguageId = (appId, value) => {
	localStorage.setItem(`${STORAGE_KEY}/${appId}`, value);
};

export const getStorageLanguageId = (appId) => {
	return (
		localStorage.getItem(`${STORAGE_KEY}/${appId}`) ||
		themeDisplay.getLanguageId()
	);
};

const TranslationManagerPortal = ({
	appId,
	dataDefinitionId,
	setUserLanguageId,
	showAppName,
	userLanguageId,
}) => {
	const {view: viewPermission} = usePermissions();

	const [{app, dataDefinition}, setState] = useState({
		app: {
			name: {},
		},
		dataDefinition: {
			availableLanguageIds: [],
			defaultLanguageId: '',
		},
	});

	const defaultLanguageId = dataDefinition.defaultLanguageId;

	const onEditingLanguageIdChange = (languageId) => {
		setStorageLanguageId(appId, languageId);
		setUserLanguageId(languageId);
	};

	useEffect(() => {
		if (viewPermission && showAppName) {
			getItem(`/o/app-builder/v1.0/apps/${appId}`).then((app) =>
				setState((prevState) => ({
					...prevState,
					app,
				}))
			);
		}
	}, [appId, showAppName, viewPermission]);

	useEffect(() => {
		if (viewPermission && dataDefinitionId) {
			getItem(
				`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}`
			).then((dataDefinition) =>
				setState((prevState) => ({
					...prevState,
					dataDefinition,
				}))
			);
		}
	}, [dataDefinitionId, viewPermission]);

	const availableLanguageIds = dataDefinition.availableLanguageIds.reduce(
		(acc, cur) => {
			acc[cur] = cur;

			return acc;
		},
		{}
	);

	const getEditingLanguageId = () => {
		if (availableLanguageIds[userLanguageId]) {
			return userLanguageId;
		}

		return defaultLanguageId;
	};

	const appStandaloneName = document.querySelector('#appStandaloneName');
	const appTranslationManager = document.querySelector(
		'#appTranslationManager'
	);

	if (!viewPermission) {
		return <></>;
	}

	return (
		<div>
			{showAppName &&
				appStandaloneName &&
				createPortal(
					getLocalizedUserPreferenceValue(
						app.name,
						userLanguageId,
						defaultLanguageId
					),
					appStandaloneName
				)}

			{appTranslationManager &&
				createPortal(
					<TranslationManager
						availableLanguageIds={availableLanguageIds}
						editingLanguageId={getEditingLanguageId()}
						onEditingLanguageIdChange={onEditingLanguageIdChange}
						showUserView
					/>,
					appTranslationManager
				)}
		</div>
	);
};

export default (props) => {
	const {appId, portraitURL} = useContext(AppContext);
	const appPersonalMenu = document.querySelector('#app-personal-menu');

	return (
		<>
			{appPersonalMenu &&
				themeDisplay.isSignedIn() &&
				createPortal(
					<PersonalMenu
						items={[
							{
								label: themeDisplay.getUserName(),
								type: 'group',
							},
							{
								type: 'divider',
							},
							{
								label: Liferay.Language.get('sign-out'),
								onClick: () => {
									window.location.href = `${window.location.origin}/c/portal/logout`;
								},
							},
						]}
						portraitURL={portraitURL}
					/>,
					appPersonalMenu
				)}
			<TranslationManagerPortal appId={appId} {...props} />
		</>
	);
};
