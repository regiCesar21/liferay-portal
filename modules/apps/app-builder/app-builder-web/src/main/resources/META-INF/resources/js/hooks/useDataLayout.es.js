/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {getItem} from '../utils/client.es';
import {errorToast} from '../utils/toast.es';

export default function useDataLayout(dataLayoutId, dataDefinitionId) {
	const [state, setState] = useState({
		dataDefinition: null,
		dataLayout: {},
		isLoading: true,
	});

	useEffect(() => {
		Promise.all([
			getItem(`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}`),
			getItem(`/o/data-engine/v2.0/data-layouts/${dataLayoutId}`),
		])
			.then(([dataDefinition, dataLayout]) => {
				setState((prevState) => ({
					...prevState,
					dataDefinition,
					dataLayout,
					isLoading: false,
				}));
			})
			.catch(() => {
				setState((prevState) => ({
					...prevState,
					isLoading: false,
				}));

				errorToast();
			});
	}, [dataDefinitionId, dataLayoutId]);

	return state;
}
