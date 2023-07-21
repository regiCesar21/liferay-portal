/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

const useFilterNameWithLabel = ({
	labelPropertyName = 'name',
	multiple,
	selectedItems = [],
	title,
	withSelectionTitle,
}) => {
	return useMemo(() => {
		if (!multiple && withSelectionTitle && selectedItems.length) {
			const [{resultName, [labelPropertyName]: label}] = selectedItems;

			return resultName || label;
		}

		return title;
	}, [labelPropertyName, multiple, selectedItems, title, withSelectionTitle]);
};

const useFilterName = (
	multiple,
	selectedItems = [],
	title,
	withSelectionTitle
) =>
	useFilterNameWithLabel({
		multiple,
		selectedItems,
		title,
		withSelectionTitle,
	});

export {useFilterName, useFilterNameWithLabel};
