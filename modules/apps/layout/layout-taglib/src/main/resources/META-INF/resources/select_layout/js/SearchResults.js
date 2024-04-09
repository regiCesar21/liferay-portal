/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {debounce, fetch, openToast} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

function findLayouts(url, checkDisplayPage, keywords, groupId, onFindLayouts) {
	fetch(url, {
		body: Liferay.Util.objectToURLSearchParams({
			[`checkDisplayPage`]: checkDisplayPage,
			[`groupId`]: groupId,
			[`keywords`]: keywords,
		}),
		method: 'post',
	})
		.then((response) => response.json())
		.then(({layouts}) => {
			onFindLayouts(layouts);
		})
		.catch(() =>
			openToast({
				message: Liferay.Language.get('an-unexpected-error-occurred'),
				title: Liferay.Language.get('error'),
				type: 'danger',
			})
		);
}

const debouncedFindLayouts = debounce(findLayouts, 300);

export default function SearchResults({
	checkDisplayPage,
	filter,
	findLayoutsURL,
	groupId,
	multiSelection,
	onSelect,
	selection,
}) {
	const [results, setResults] = useState([]);
	const [loading, setLoading] = useState(false);

	const onFindLayouts = useCallback((layouts) => {
		setLoading(false);

		setResults(layouts);
	}, []);

	useEffect(() => {
		setLoading(true);

		debouncedFindLayouts(
			findLayoutsURL,
			checkDisplayPage,
			filter,
			groupId,
			onFindLayouts
		);
	}, [checkDisplayPage, filter, findLayoutsURL, groupId, onFindLayouts]);

	if (loading) {
		return <ClayLoadingIndicator displayType="secondary" />;
	}

	return (
		<div className="pt-3">
			{results.map((layout) => (
				<SearchResult
					key={layout.id}
					layout={layout}
					multiSelection={multiSelection}
					onSelect={onSelect}
					selection={selection}
				/>
			))}
		</div>
	);
}

function SearchResult({layout, multiSelection, onSelect, selection}) {
	return (
		<div className="align-items-center d-flex pb-2">
			{multiSelection && (
				<ClayCheckbox
					checked={selection.includes(layout.id)}
					containerProps={{className: 'mr-3 my-0'}}
					disabled={layout.disabled}
					onChange={() => onSelect(layout)}
				/>
			)}

			{layout.path.map((ancestor, index) => (
				<span className="pr-2 text-secondary" key={index}>
					{ancestor}

					<ClayIcon className="ml-2" symbol="angle-right-small" />
				</span>
			))}

			{multiSelection ? (
				<span className="font-weight-semi-bold p-0">{layout.name}</span>
			) : (
				<ClayButton
					className="font-weight-semi-bold px-0 py-1 search-result-button"
					disabled={layout.disabled}
					displayType="unstyled"
					onClick={() => onSelect(layout)}
				>
					{layout.name}
				</ClayButton>
			)}
		</div>
	);
}
