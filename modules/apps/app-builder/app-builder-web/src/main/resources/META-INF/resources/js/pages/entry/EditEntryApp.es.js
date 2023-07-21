/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';

import {AppContextProvider} from '../../AppContext.es';
import useLazy from '../../hooks/useLazy.es';
import {PermissionsContextProvider} from './PermissionsContext.es';
import PortalEntry, {getStorageLanguageId} from './PortalEntry.es';

export default ({appTab, ...props}) => {
	const EditPage = useLazy(true);
	const {appId, dataDefinitionId} = props;
	const defaultLanguageId = getStorageLanguageId(appId);
	const [userLanguageId, setUserLanguageId] = useState(defaultLanguageId);

	props.userLanguageId = userLanguageId;

	return (
		<AppContextProvider {...props}>
			<PermissionsContextProvider dataDefinitionId={dataDefinitionId}>
				<PortalEntry
					dataDefinitionId={props.dataDefinitionId}
					setUserLanguageId={setUserLanguageId}
					userLanguageId={userLanguageId}
				/>

				<EditPage module={appTab.editEntryPoint} props={props} />
			</PermissionsContextProvider>
		</AppContextProvider>
	);
};
