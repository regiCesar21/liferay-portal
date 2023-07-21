/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useMemo, useState} from 'react';

const InstanceListContext = createContext(null);

const InstanceListPageProvider = ({children}) => {
	const [instanceId, setInstanceId] = useState();
	const [selectAll, setSelectAll] = useState(false);
	const [selectedItem, setSelectedItem] = useState({});
	const [selectedItems, setSelectedItems] = useState([]);

	const selectedInstance = useMemo(
		() => (selectedItems.length === 1 ? selectedItems[0] : selectedItem),
		[selectedItem, selectedItems]
	);

	const value = {
		instanceId,
		selectAll,
		selectedInstance,
		selectedItem,
		selectedItems,
		setInstanceId,
		setSelectAll,
		setSelectedItem,
		setSelectedItems,
	};

	return (
		<InstanceListContext.Provider value={value}>
			{children}
		</InstanceListContext.Provider>
	);
};

export {InstanceListContext};
export default InstanceListPageProvider;
