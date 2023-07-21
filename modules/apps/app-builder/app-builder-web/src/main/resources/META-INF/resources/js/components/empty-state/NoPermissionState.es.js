/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import React from 'react';

export default ({
	children,
	description = '',
	title = Liferay.Language.get('no-permissions'),
}) => (
	<ClayEmptyState
		description={description}
		imgSrc={`${themeDisplay.getPathThemeImages()}/app_builder/illustration_locker.svg`}
		title={title}
	>
		{children}
	</ClayEmptyState>
);
