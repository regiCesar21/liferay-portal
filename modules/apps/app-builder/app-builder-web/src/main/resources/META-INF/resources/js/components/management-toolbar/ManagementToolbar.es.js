/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayManagementToolbar from '@clayui/management-toolbar';
import React, {useContext, useState} from 'react';

import ManagementToolbarFilterAndOrder from './ManagementToolbarFilterAndOrder.es';
import ManagementToolbarRight from './ManagementToolbarRight.es';
import ManagementToolbarSearch from './ManagementToolbarSearch';
import SearchContext from './SearchContext.es';

export default ({addButton, columns, disabled, filters}) => {
	const [{keywords}, dispatch] = useContext(SearchContext);
	const [showMobile, setShowMobile] = useState(false);

	return (
		<ClayManagementToolbar>
			<ManagementToolbarFilterAndOrder
				columns={columns}
				disabled={disabled}
				filters={filters}
			/>

			<ManagementToolbarSearch
				disabled={disabled}
				onSubmit={(searchText) =>
					dispatch({keywords: searchText, type: 'SEARCH'})
				}
				searchText={keywords}
				setShowMobile={setShowMobile}
				showMobile={showMobile}
			/>

			<ManagementToolbarRight
				addButton={addButton}
				setShowMobile={setShowMobile}
			/>
		</ClayManagementToolbar>
	);
};
