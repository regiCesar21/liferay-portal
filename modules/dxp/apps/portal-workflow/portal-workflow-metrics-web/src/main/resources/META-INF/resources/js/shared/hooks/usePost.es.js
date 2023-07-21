/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useContext, useState} from 'react';

import {AppContext} from '../../components/AppContext.es';

const usePost = ({admin = false, body = {}, params = {}, url}) => {
	const {getClient} = useContext(AppContext);
	const [data, setData] = useState({});

	const client = getClient(admin);
	const queryBodyStr = JSON.stringify(body);
	const queryParamsStr = JSON.stringify(params);

	const postData = useCallback(
		() =>
			client.post(url, body, {params}).then(({data}) => {
				setData(data);

				return data;
			}),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[admin, queryBodyStr, queryParamsStr, url]
	);

	return {
		data,
		postData,
	};
};

export {usePost};
