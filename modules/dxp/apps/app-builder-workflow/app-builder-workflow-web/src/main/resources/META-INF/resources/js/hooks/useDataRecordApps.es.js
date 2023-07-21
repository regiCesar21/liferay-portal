/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addItem} from 'app-builder-web/js/utils/client.es';
import {errorToast} from 'app-builder-web/js/utils/toast.es';
import {useEffect, useState} from 'react';

export default function useDataRecordApps(appId, dataRecordIds = []) {
	const [dataRecordLinks, setDataRecordLinks] = useState({});

	useEffect(() => {
		if (appId && dataRecordIds.length) {
			addItem(
				`/o/app-builder-workflow/v1.0/apps/${appId}/app-workflows/data-record-links`,
				{dataRecordIds}
			)
				.then(({items}) => {
					setDataRecordLinks(
						items.reduce(
							(links, {appWorkflow, dataRecordId}) => ({
								...links,
								[dataRecordId]: appWorkflow,
							}),
							{}
						)
					);
				})
				.catch(() => errorToast());
		}
	}, [appId, dataRecordIds]);

	return dataRecordLinks;
}
