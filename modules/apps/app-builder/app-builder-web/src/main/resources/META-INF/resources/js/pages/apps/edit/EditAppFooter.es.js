/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React, {useContext, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../../AppContext.es';
import Button from '../../../components/button/Button.es';
import {addItem, updateItem} from '../../../utils/client.es';
import {errorToast, successToast} from '../../../utils/toast.es';
import EditAppContext from './EditAppContext.es';

export default withRouter(
	({
		currentStep,
		defaultLanguageId,
		editingLanguageId,
		history,
		match: {
			params: {dataDefinitionId},
		},
		onCurrentStepChange,
	}) => {
		const {
			state: {app},
		} = useContext(EditAppContext);

		const {getStandaloneURL} = useContext(AppContext);

		const [isDeploying, setDeploying] = useState(false);

		const {
			appDeployments,
			dataLayoutId,
			dataListViewId,
			id: appId,
			name,
		} = app;

		const appName = name[editingLanguageId];

		const getStandaloneLink = (appId) => {
			const isStandalone = appDeployments.some(
				(deployment) => deployment.type === 'standalone'
			);

			return isStandalone
				? `<a href="${getStandaloneURL(
						appId
				  )}" target="_blank">${Liferay.Language.get(
						'open-standalone-app'
				  )}. ${Liferay.Util.getLexiconIconTpl('shortcut')}</a>`
				: '';
		};

		const onSuccess = (appId) => {
			successToast(
				`${Liferay.Language.get(
					'the-app-was-deployed-successfully'
				)} ${getStandaloneLink(appId)}`
			);

			setDeploying(false);
		};

		const normalizeAppName = (names) => {
			const name = {};

			if (!names[defaultLanguageId]) {
				names[defaultLanguageId] = names[editingLanguageId];
			}

			Object.keys(names).forEach((key) => {
				const value = names[key];
				if (value) {
					name[key] = value;
				}
			});

			return name;
		};

		const onError = (error) => {
			const {title = ''} = error;
			errorToast(`${title}.`);
			setDeploying(false);
		};

		const onCancel = () => {
			history.goBack();
		};

		const onDeploy = () => {
			setDeploying(true);

			const data = {
				...app,
				name: normalizeAppName(app.name),
			};

			if (appId) {
				updateItem({
					endpoint: `/o/app-builder/v1.0/apps/${appId}`,
					item: data,
				})
					.then(() => onSuccess(appId))
					.then(onCancel)
					.catch(onError);
			}
			else {
				addItem(
					`/o/app-builder/v1.0/data-definitions/${dataDefinitionId}/apps`,
					data
				)
					.then((app) => onSuccess(app.id))
					.then(onCancel)
					.catch(onError);
			}
		};

		return (
			<div className="bg-transparent card-footer">
				<ClayLayout.ContentRow>
					<ClayLayout.Col md="4">
						<Button displayType="secondary" onClick={onCancel}>
							{Liferay.Language.get('cancel')}
						</Button>
					</ClayLayout.Col>
					<ClayLayout.Col className="offset-md-4 text-right" md="4">
						{currentStep > 0 && (
							<Button
								className="mr-3"
								displayType="secondary"
								onClick={() =>
									onCurrentStepChange(currentStep - 1)
								}
							>
								{Liferay.Language.get('previous')}
							</Button>
						)}
						{currentStep < 2 && (
							<Button
								disabled={
									(currentStep === 0 && !dataLayoutId) ||
									(currentStep === 1 && !dataListViewId)
								}
								displayType="primary"
								onClick={() =>
									onCurrentStepChange(currentStep + 1)
								}
							>
								{Liferay.Language.get('next')}
							</Button>
						)}
						{currentStep === 2 && (
							<Button
								disabled={
									appDeployments.length === 0 ||
									!appName ||
									isDeploying
								}
								displayType="primary"
								onClick={onDeploy}
							>
								{app.id
									? Liferay.Language.get('save')
									: Liferay.Language.get('deploy')}
							</Button>
						)}
					</ClayLayout.Col>
				</ClayLayout.ContentRow>
			</div>
		);
	}
);
