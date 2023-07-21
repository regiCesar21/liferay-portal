/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {sub} from '../../utils/lang.es';

const DEFAULT_EMPTY = {
	empty: {
		className: 'taglib-empty-state',
		title: Liferay.Language.get('there-are-no-entries'),
	},
	search: {
		className: 'taglib-search-state',
		title: Liferay.Language.get('no-results-were-found'),
	},
};

const EmptyState = ({
	button,
	className = DEFAULT_EMPTY.empty.className,
	description,
	title = DEFAULT_EMPTY.empty.title,
}) => {
	return (
		<div className="taglib-empty-result-message">
			<div className="text-center">
				<div className={className} />

				{title && (
					<h1 className="taglib-empty-result-message-title">
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

export const FilteredEmpty = (props) => {
	const description = Liferay.Language.get(
		'there-are-no-results-with-these-attributes'
	);

	return <EmptyState description={description} {...props} />;
};

export const SearchEmpty = ({keywords, ...otherProps}) => {
	const description = sub(
		Liferay.Language.get('there-are-no-results-for-x'),
		[keywords]
	);

	return <EmptyState description={description} {...otherProps} />;
};

export const SearchAndFilteredEmpty = ({keywords, ...otherProps}) => {
	const description = sub(
		Liferay.Language.get(
			'there-are-no-results-for-x-with-these-attributes'
		),
		[keywords]
	);

	return <EmptyState description={description} {...otherProps} />;
};

export const withEmpty = (Component) => {
	const Wrapper = ({
		emptyState,
		isEmpty,
		isFiltered,
		keywords,
		...restProps
	}) => {
		if (isEmpty) {
			if (keywords.length) {
				if (isFiltered) {
					return (
						<SearchAndFilteredEmpty
							keywords={keywords}
							{...DEFAULT_EMPTY.search}
							{...emptyState.searchAndFiltered}
						/>
					);
				}

				return (
					<SearchEmpty
						keywords={keywords}
						{...DEFAULT_EMPTY.search}
						{...emptyState.search}
					/>
				);
			}

			if (isFiltered) {
				return (
					<FilteredEmpty
						{...DEFAULT_EMPTY.search}
						{...emptyState.filtered}
					/>
				);
			}

			return <EmptyState {...emptyState} />;
		}

		return <Component {...restProps} />;
	};

	return Wrapper;
};

export default EmptyState;
