/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useContext, useState} from 'react';

import {AppContext} from '../../components/AppContext.es';

const useFetch = ({
	admin = false,
	callback = (data) => data,
	params = {},
	url,
}) => {
	const {getClient} = useContext(AppContext);
	const [data, setData] = useState({});

	const client = getClient(admin);
	const queryParamsStr = JSON.stringify(params);

	const fetchData = useCallback(
		() =>
			client.get(url, {params}).then(({data}) => {
				setData(data);

				return callback(data);
			}),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[client, queryParamsStr, url]
	);

	return {
		data,
		fetchData,
	};
};

export {useFetch};
