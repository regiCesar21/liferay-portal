/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import App from './App.es';
import EmptyState from './components/empty-state/EmptyState.es';

export default ({data, ...restProps}) => {
	if (!data || data.length === 0) {
		return <EmptyState />;
	}

	return (
		<div className="container-fluid form-report">
			<App {...restProps} data={JSON.parse(data)} />
		</div>
	);
};
