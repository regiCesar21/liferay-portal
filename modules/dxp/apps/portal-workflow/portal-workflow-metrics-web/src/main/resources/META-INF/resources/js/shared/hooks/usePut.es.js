/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';
import {useCallback} from 'react';

import {adminBaseURL, headers, metricsBaseURL} from '../rest/fetch.es';

const usePut = ({body = {}, admin = false, url}) => {
	const fetchURL = admin
		? `${adminBaseURL}${url}`
		: `${metricsBaseURL}${url}`;

	return useCallback(
		() =>
			fetch(fetchURL, {
				body: JSON.stringify(body),
				headers: {...headers, 'Content-Type': 'application/json'},
				method: 'PUT',
			})
				.then((response) => {
					if (!response.ok) {
						return response.json();
					}
				})
				.then((data) => {
					if (
						data &&
						Array.isArray(data) &&
						'fieldName' in data[0] &&
						'message' in data[0]
					) {
						throw data;
					}
				}),

		[body, fetchURL]
	);
};

export {usePut};
