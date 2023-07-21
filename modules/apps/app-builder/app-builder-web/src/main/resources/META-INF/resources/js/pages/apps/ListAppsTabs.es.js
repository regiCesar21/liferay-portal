/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useEffect} from 'react';

import {AppContext} from '../../AppContext.es';
import ControlMenu from '../../components/control-menu/ControlMenu.es';
import NavigationBar from '../../components/navigation-bar/NavigationBar.es';
import useLazy from '../../hooks/useLazy.es';

export default (props) => {
	const {appsTabs, appsTabsKeys} = useContext(AppContext);
	const {tab = appsTabsKeys[0]} = props.match.params;

	useEffect(() => {
		if (props.history.location.pathname === '/') {
			props.history.replace(`${tab}`);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const {listEntryPoint, ...otherProps} = appsTabs[tab];
	const navTabs = Object.values(appsTabs).map(({label, scope}) => ({
		active: tab === scope,
		label,
		path: () => `/${scope}`,
	}));
	const TabContent = useLazy();

	return (
		<>
			<ControlMenu title={Liferay.Language.get('apps')} />

			<NavigationBar tabs={navTabs} />

			<TabContent
				module={listEntryPoint}
				props={{...props, ...otherProps}}
			/>
		</>
	);
};
