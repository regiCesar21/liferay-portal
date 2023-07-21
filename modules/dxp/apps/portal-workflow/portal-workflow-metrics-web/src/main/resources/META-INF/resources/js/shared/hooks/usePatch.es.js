/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useContext} from 'react';

import {AppContext} from '../../components/AppContext.es';

const usePatch = ({body = {}, admin = false, url}) => {
	const {getClient} = useContext(AppContext);

	const client = getClient(admin);
	const queryBodyStr = JSON.stringify(body);
	const patchData = useCallback(
		(patchBody) => client.patch(url, patchBody || body),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[queryBodyStr, url, admin]
	);

	return {patchData};
};

export {usePatch};
