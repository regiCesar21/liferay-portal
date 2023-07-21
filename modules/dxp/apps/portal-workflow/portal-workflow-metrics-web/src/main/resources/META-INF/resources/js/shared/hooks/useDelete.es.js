/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useContext} from 'react';

import {AppContext} from '../../components/AppContext.es';

const useDelete = ({admin = false, url}) => {
	const {getClient} = useContext(AppContext);

	const client = getClient(admin);

	return useCallback(() => client.delete(url), [client, url]);
};

export {useDelete};
