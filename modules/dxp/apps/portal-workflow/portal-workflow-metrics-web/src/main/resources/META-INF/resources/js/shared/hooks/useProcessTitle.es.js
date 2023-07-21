/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext, useEffect} from 'react';

import {AppContext} from '../../components/AppContext.es';

const useProcessTitle = (processId, pageTitle = null) => {
	const {client, setTitle} = useContext(AppContext);

	useEffect(() => {
		client.get(`/processes/${processId}/title`).then(({data}) => {
			setTitle(data + `${pageTitle ? ': ' + pageTitle : ''}`);
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);
};

export {useProcessTitle};
