/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export default ({
	description = Liferay.Language.get(
		'the-report-will-show-up-once-the-first-entry-is-submitted'
	),
	title = Liferay.Language.get('there-are-no-entries'),
}) => (
	<div className="empty-message taglib-empty-result-message">
		<div className="taglib-empty-result-message-header"></div>
		<div className="sheet-text text-center text-muted">
			<h1 className="text-default">{title}</h1>
			<p className="text-default">{description}</p>
		</div>
	</div>
);
