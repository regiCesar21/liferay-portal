/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {getItem} from '../utils/client.es';
import {getLocalizedValue} from '../utils/lang.es';

export default function useDataDefinition(dataDefinitionId) {
	const [dataDefinition, setDataDefinition] = useState({});

	useEffect(() => {
		if (dataDefinitionId) {
			getItem(
				`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}`
			).then((dataDefinition) =>
				setDataDefinition({
					...dataDefinition,
					title: getLocalizedValue(
						dataDefinition.defaultLanguageId,
						dataDefinition.name
					),
				})
			);
		}
	}, [dataDefinitionId]);

	return dataDefinition;
}
