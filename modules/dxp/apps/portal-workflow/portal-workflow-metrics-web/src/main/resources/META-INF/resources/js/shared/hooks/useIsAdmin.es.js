/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';
import {useCallback, useState} from 'react';

const useIsAdmin = () => {
	const [isAdmin, setAdmin] = useState();

	const fetchData = useCallback(() => {
		const fetchURL = new URL(
			'/o/headless-admin-user/v1.0/my-user-account',
			Liferay.ThemeDisplay.getPortalURL()
		);

		const headers = {
			'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
		};

		fetch(fetchURL, {
			headers,
			method: 'GET',
		})
			.then((response) => {
				if (response.ok) {
					return response.json();
				}
				else {
					throw response;
				}
			})
			.then((data) => {
				setAdmin(
					data?.roleBriefs?.some(
						(role) => role.name === 'Administrator'
					)
				);
			})
			.catch((error) => {
				console.error(error);
			});
	}, []);

	return {
		fetchData,
		isAdmin,
	};
};

export {useIsAdmin};
