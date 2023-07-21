/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayModalProvider} from '@clayui/modal';
import classNames from 'classnames';
import React, {useContext} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {HashRouter as Router, Route, Switch} from 'react-router-dom';

import {AppContext, AppContextProvider} from './AppContext.es';
import NavigationBar from './components/navigation-bar/NavigationBar.es';
import ListCustomObjects from './pages/custom-object/ListCustomObjects.es';
import ListNativeObjects from './pages/native-object/ListNativeObjects.es';
import ViewObject from './pages/object/ViewObject.es';

export const AppNavigationBar = () => {
	const {showNativeObjectsTab} = useContext(AppContext);

	if (!showNativeObjectsTab) {
		return null;
	}

	return (
		<NavigationBar
			tabs={[
				{
					active: true,
					exact: true,
					label: Liferay.Language.get('custom'),
					path: () => '/',
				},
				{
					label: Liferay.Language.get('native'),
					path: () => '/native-objects',
				},
			]}
		/>
	);
};

export default (props) => (
	<DndProvider backend={HTML5Backend}>
		<AppContextProvider {...props}>
			<ClayModalProvider>
				<Router>
					<div
						className={classNames('custom-object-app', {
							'publications-enabled': document.querySelector(
								'.change-tracking-indicator'
							),
						})}
					>
						<Switch>
							<Route
								component={ListCustomObjects}
								exact
								path="/"
							/>

							<Route
								component={ListNativeObjects}
								path="/native-objects"
							/>

							<Route
								component={ViewObject}
								path="/:objectType/:dataDefinitionId(\d+)"
							/>
						</Switch>
					</div>
				</Router>
			</ClayModalProvider>
		</AppContextProvider>
	</DndProvider>
);
