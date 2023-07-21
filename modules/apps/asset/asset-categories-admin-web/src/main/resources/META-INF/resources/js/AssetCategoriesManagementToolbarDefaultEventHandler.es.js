/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	DefaultEventHandler,
	addParams,
	openSelectionModal,
} from 'frontend-js-web';

class AssetCategoriesManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteSelectedCategories() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}

	selectCategory(itemData) {
		const namespace = this.namespace;

		openSelectionModal({
			onSelect: (selectedItem) => {
				const category = selectedItem
					? selectedItem[Object.keys(selectedItem)[0]]
					: null;

				if (category) {
					location.href = addParams(
						namespace + 'categoryId=' + category.categoryId,
						itemData.viewCategoriesURL
					);
				}
			},
			selectEventName: this.ns('selectCategory'),
			title: Liferay.Language.get('select-category'),
			url: itemData.categoriesSelectorURL,
		});
	}
}

export default AssetCategoriesManagementToolbarDefaultEventHandler;
