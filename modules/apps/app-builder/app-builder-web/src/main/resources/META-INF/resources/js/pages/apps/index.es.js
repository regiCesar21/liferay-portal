/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayModalProvider} from '@clayui/modal';
import React from 'react';
import {HashRouter as Router, Route, Switch} from 'react-router-dom';

import {AppContextProvider} from '../../AppContext.es';
import useLazy from '../../hooks/useLazy.es';
import ListAppsTabs from './ListAppsTabs.es';

export default (props) => {
	const {appsTabs} = props;

	const appsTabsKeys = Object.keys(appsTabs);
	const EditPage = useLazy();

	const appProps = {appsTabsKeys, ...props};

	const editRoutes = appsTabsKeys.map((scope) => {
		appsTabs[scope] = {
			...appsTabs[scope],
			editPath: [
				`/${scope}/:dataDefinitionId(\\d+)?/deploy`,
				`/${scope}/:dataDefinitionId(\\d+)?/:appId(\\d+)`,
			],
			scope,
		};

		const {editEntryPoint, editPath} = appsTabs[scope];

		return {
			component: (props) => (
				<EditPage module={editEntryPoint} props={{scope, ...props}} />
			),
			path: editPath,
		};
	});

	return (
		<div className="app-builder-root">
			<AppContextProvider {...appProps}>
				<ClayModalProvider>
					<Router>
						<Switch>
							{editRoutes.map((route, index) => (
								<Route key={index} {...route} />
							))}

							<Route component={ListAppsTabs} path="/:tab?" />
						</Switch>
					</Router>
				</ClayModalProvider>
			</AppContextProvider>
		</div>
	);
};
