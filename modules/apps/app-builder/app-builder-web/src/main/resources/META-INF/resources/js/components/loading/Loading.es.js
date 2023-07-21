/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import React from 'react';

export const withLoading = (Component) => {
	const Wrapper = (props) => {
		const {isLoading, ...restProps} = props;

		if (isLoading) {
			return (
				<div className="align-items-center d-flex loading-wrapper w-100">
					<ClayLoadingIndicator />
				</div>
			);
		}

		return <Component {...restProps} />;
	};

	return Wrapper;
};

export const Loading = withLoading(({children}) => <>{children}</>);
