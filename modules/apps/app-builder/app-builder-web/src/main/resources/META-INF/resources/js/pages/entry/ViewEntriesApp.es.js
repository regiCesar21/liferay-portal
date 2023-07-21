/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';
import {HashRouter as Router, Route, Switch} from 'react-router-dom';

import {AppContextProvider} from '../../AppContext.es';
import useLazy from '../../hooks/useLazy.es';
import {PermissionsContextProvider} from './PermissionsContext.es';
import PortalEntry, {getStorageLanguageId} from './PortalEntry.es';

export default function ({appTab, ...props}) {
	const PageComponent = useLazy();
	const {appId, dataDefinitionId} = props;
	const defaultLanguageId = getStorageLanguageId(appId);
	const [userLanguageId, setUserLanguageId] = useState(defaultLanguageId);
	const [showAppName, setShowAppName] = useState(false);

	props.userLanguageId = userLanguageId;

	const ListPage = (props) => {
		useEffect(() => {
			setShowAppName(true);
		}, []);

		return <PageComponent module={appTab.listEntryPoint} props={props} />;
	};

	const ViewPage = (props) => {
		useEffect(() => {
			setShowAppName(false);
		}, []);

		return <PageComponent module={appTab.viewEntryPoint} props={props} />;
	};

	return (
		<div className="app-builder-root">
			<AppContextProvider {...props}>
				<PermissionsContextProvider dataDefinitionId={dataDefinitionId}>
					<PortalEntry
						dataDefinitionId={dataDefinitionId}
						setUserLanguageId={setUserLanguageId}
						showAppName={showAppName}
						userLanguageId={userLanguageId}
					/>
					<Router>
						<Switch>
							<Route component={ListPage} exact path="/" />
							<Route
								component={ViewPage}
								path="/entries/:entryIndex(\d+)"
							/>
						</Switch>
					</Router>
				</PermissionsContextProvider>
			</AppContextProvider>
		</div>
	);
}
