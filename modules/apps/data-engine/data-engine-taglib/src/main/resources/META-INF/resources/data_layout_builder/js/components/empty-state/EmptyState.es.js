/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

import lang from '../../utils/lang.es';

const EmptyState = ({emptyState, keywords = '', small = false}) => {
	const defaultEmpty = {
		description: null,
		title: Liferay.Language.get('there-are-no-entries'),
	};

	const defaultSearch = {
		description: lang.sub(
			Liferay.Language.get('there-are-no-results-for-x'),
			[keywords]
		),
		title: Liferay.Language.get('no-results-were-found'),
	};

	emptyState = {
		...defaultEmpty,
		...emptyState,
	};

	const search = {
		...defaultSearch,
		...emptyState.search,
	};

	const isSearch = keywords !== '';
	const {button, description, title} = isSearch ? search : emptyState;

	return (
		<div className="taglib-empty-result-message">
			<div className="text-center">
				<div
					className={classNames(
						{
							'taglib-empty-state': !isSearch,
							'taglib-search-state': isSearch,
						},
						{
							'empty-state-small': small,
						}
					)}
				/>

				{title && (
					<h1
						className={classNames(
							'taglib-empty-result-message-title',
							{'empty-state-title-small': small}
						)}
					>
						{title}
					</h1>
				)}

				{description && (
					<p className="empty-message-color taglib-empty-result-message-description">
						{description}
					</p>
				)}

				{button && button()}
			</div>
		</div>
	);
};

export const withEmpty = (Component) => {
	const Wrapper = ({emptyState, isEmpty, keywords, ...restProps}) => {
		if (isEmpty) {
			return <EmptyState emptyState={emptyState} keywords={keywords} />;
		}

		return <Component {...restProps} />;
	};

	return Wrapper;
};

export default EmptyState;
