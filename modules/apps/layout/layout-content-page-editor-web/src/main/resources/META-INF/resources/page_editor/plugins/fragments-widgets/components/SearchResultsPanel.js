/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import PropTypes from 'prop-types';
import React from 'react';

import TabCollection from './TabCollection';

export default function SearchResultsPanel({filteredTabs}) {
	return filteredTabs.length ? (
		filteredTabs.map((tab, index) => (
			<div key={index}>
				<div className="page-editor__fragments-widgets__search-results-panel__filter-subtitle">
					{tab.label}
				</div>
				{tab.collections.map((collection, index) => (
					<TabCollection
						collection={collection}
						isSearchResult
						key={index}
						open
					/>
				))}
			</div>
		))
	) : (
		<ClayAlert displayType="info" title={Liferay.Language.get('info')}>
			{Liferay.Language.get(
				'there-are-no-fragments-or-widgets-on-this-page'
			)}
		</ClayAlert>
	);
}

SearchResultsPanel.proptypes = {
	filteredTabs: PropTypes.object.isRequired,
};
