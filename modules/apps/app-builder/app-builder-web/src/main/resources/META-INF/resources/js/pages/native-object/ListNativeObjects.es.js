/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import ListObjects from '../object/ListObjects.es';

export default ({history}) => (
	<ListObjects
		history={history}
		listViewProps={{
			emptyState: {
				title: Liferay.Language.get('there-are-no-native-objects-yet'),
			},
			endpoint:
				'/o/data-engine/v2.0/data-definitions/by-content-type/native-object',
		}}
		objectType="native-object"
	/>
);
