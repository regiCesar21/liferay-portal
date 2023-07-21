/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useEffect, useState} from 'react';

import {client, hasListPermissionsQuery} from './utils/client.es';

const AppContext = createContext({});

const AppContextProvider = ({children, ...context}) => {
	const [canCreateThread, setCanCreateThread] = useState(false);

	useEffect(() => {
		client
			.query({
				query: hasListPermissionsQuery,
				variables: {
					siteKey: context.siteKey,
				},
			})
			.then(({data: {messageBoardThreads}}) => {
				setCanCreateThread(
					Boolean(messageBoardThreads.actions['create'])
				);
			});
	}, [context.siteKey]);

	return (
		<AppContext.Provider
			value={{
				...context,
				canCreateThread,
			}}
		>
			{children}
		</AppContext.Provider>
	);
};

export {AppContext, AppContextProvider};
