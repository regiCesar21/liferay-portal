/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Route, Switch} from 'react-router-dom';

import ControlMenu from '../../components/control-menu/ControlMenu.es';
import NavigationBar from '../../components/navigation-bar/NavigationBar.es';
import useDataDefinition from '../../hooks/useDataDefinition.es';
import ListApps from '../apps/ListApps.es';
import EditApp from '../apps/edit/EditApp.es';
import EditFormView from '../form-view/EditFormView.es';
import ListFormViews from '../form-view/ListFormViews.es';
import EditTableView from '../table-view/EditTableView.es';
import ListTableViews from '../table-view/ListTableViews.es';

const URL = {
	'custom-object': '/',
	'native-object': '/native-objects',
};

export default ({
	match: {
		params: {dataDefinitionId, objectType},
		path,
	},
}) => {
	const {title = ''} = useDataDefinition(dataDefinitionId);

	return (
		<Switch>
			<Route
				component={EditFormView}
				path={[
					`${path}/form-views/add`,
					`${path}/form-views/:dataLayoutId(\\d+)`,
				]}
			/>

			<Route
				component={EditTableView}
				path={[
					`${path}/table-views/add`,
					`${path}/table-views/:dataListViewId(\\d+)`,
				]}
			/>

			<Route
				component={EditApp}
				path={[`${path}/apps/deploy`, `${path}/apps/:appId(\\d+)`]}
			/>

			<Route
				path={path}
				render={() => (
					<>
						<ControlMenu backURL={URL[objectType]} title={title} />
						<NavigationBar
							tabs={[
								{
									active: true,
									label: Liferay.Language.get('form-views'),
									path: (url) => `${url}/form-views`,
								},
								{
									label: Liferay.Language.get('table-views'),
									path: (url) => `${url}/table-views`,
								},
								{
									label: Liferay.Language.get('apps'),
									path: (url) => `${url}/apps`,
								},
							]}
						/>

						<Switch>
							<Route
								component={ListFormViews}
								path={`${path}/form-views`}
							/>

							<Route
								component={ListTableViews}
								path={`${path}/table-views`}
							/>

							<Route component={ListApps} path={`${path}/apps`} />
						</Switch>
					</>
				)}
			/>
		</Switch>
	);
};
