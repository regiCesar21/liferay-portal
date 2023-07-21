/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useState} from 'react';

const SelectedMenuItemIdContext = React.createContext(null);
const SetSelectedMenuItemIdContext = React.createContext(() => {});

export const useSetSelectedMenuItemId = () =>
	useContext(SetSelectedMenuItemIdContext);

export const useSelectedMenuItemId = () =>
	useContext(SelectedMenuItemIdContext);

export const SelectedMenuItemIdProvider = ({children}) => {
	const [selectedMenuItemId, setSelectedMenuItemId] = useState(null);

	return (
		<SetSelectedMenuItemIdContext.Provider value={setSelectedMenuItemId}>
			<SelectedMenuItemIdContext.Provider value={selectedMenuItemId}>
				{children}
			</SelectedMenuItemIdContext.Provider>
		</SetSelectedMenuItemIdContext.Provider>
	);
};
