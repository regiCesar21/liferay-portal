/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useEffect, useState} from 'react';

import {getComponentByModuleURL} from '../../../utilities/modules';
import AutocompleteFilter from './AutocompleteFilter';
import CheckboxesFilter from './CheckboxesFilter';
import DateRangeFilter from './DateRangeFilter';
import NumberFilter from './NumberFilter';
import RadioFilter from './RadioFilter';
import TextFilter from './TextFilter';

export const filterIdToComponentMap = {
	autocomplete: AutocompleteFilter,
	checkbox: CheckboxesFilter,
	dateRange: DateRangeFilter,
	number: NumberFilter,
	radio: RadioFilter,
	text: TextFilter,
};

export function Filter(props) {
	const {moduleURL, type} = props;

	const [Component, updateComponent] = useState(() => {
		if (!moduleURL) {
			const Matched = filterIdToComponentMap[type];

			if (!Matched) {
				throw new Error(`Filter type '${type}' not found.`);
			}

			return Matched;
		}
		else {
			return null;
		}
	});

	useEffect(() => {
		if (moduleURL) {
			getComponentByModuleURL(moduleURL).then((FetchedComponent) =>
				updateComponent(() => FetchedComponent)
			);
		}
	}, [moduleURL]);

	return Component ? (
		<div className="data-set-filter">
			<Component {...props} />
		</div>
	) : (
		<ClayLoadingIndicator small />
	);
}
