/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

const EmptyState = ({
	actionButton,
	className = 'pb-5 pt-6 sheet',
	filtered,
	filteredMessage = Liferay.Language.get('no-results-were-found'),
	hideAnimation,
	message = Liferay.Language.get('there-is-no-data-at-the-moment'),
	messageClassName,
	title,
}) => {
	const animationClassName = `taglib-empty-${
		filtered ? 'search-' : ''
	}result-message-header`;

	return (
		<div className={`${className} taglib-empty-result-message`}>
			{!hideAnimation && <div className={animationClassName} />}

			{title && <h3 className="text-center">{title}</h3>}

			<div className="sheet-text text-center">
				<p className={messageClassName}>
					{filtered ? filteredMessage : message}
				</p>

				{actionButton}
			</div>
		</div>
	);
};

export default EmptyState;
