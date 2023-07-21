/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Treeview} from 'frontend-js-components-web';
import React from 'react';

function findCategory(categoryId, categories = []) {
	// eslint-disable-next-line no-for-of-loops/no-for-of-loops
	for (const category of categories) {
		if (category.id === categoryId) {
			return category;
		}

		const childrenCategory = findCategory(categoryId, category.children);

		if (childrenCategory) {
			return childrenCategory;
		}
	}

	return null;
}

const AssetCategoriesNavigationTreeView = ({
	selectedCategoryId,
	vocabularies,
}) => {
	const handleSelectionChange = ([selectedNodeId]) => {
		if (selectedNodeId && selectedCategoryId !== selectedNodeId) {
			const category = findCategory(selectedNodeId, vocabularies);

			if (category) {
				Liferay.Util.navigate(category.url);
			}
		}
	};

	return (
		<Treeview
			NodeComponent={Treeview.Card}
			initialSelectedNodeIds={
				selectedCategoryId ? [selectedCategoryId] : []
			}
			multiSelection={false}
			nodes={vocabularies}
			onSelectedNodesChange={handleSelectionChange}
		/>
	);
};

export default AssetCategoriesNavigationTreeView;
