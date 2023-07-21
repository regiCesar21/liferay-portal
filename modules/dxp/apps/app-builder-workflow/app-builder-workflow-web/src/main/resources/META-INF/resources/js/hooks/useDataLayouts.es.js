/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getItem} from 'app-builder-web/js/utils/client.es';
import {errorToast} from 'app-builder-web/js/utils/toast.es';
import {useEffect, useState} from 'react';

export default function useDataLayouts(dataLayoutIds = []) {
	const [dataLayouts, setDataLayouts] = useState([]);

	useEffect(() => {
		if (dataLayoutIds.length) {
			Promise.all(
				dataLayoutIds.map((dataLayoutId) =>
					getItem(`/o/data-engine/v2.0/data-layouts/${dataLayoutId}`)
				)
			)
				.then(setDataLayouts)
				.catch(() => errorToast());
		}
	}, [dataLayoutIds]);

	return dataLayouts;
}
