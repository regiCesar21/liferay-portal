/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler, openSelectionModal} from 'frontend-js-web';

class FragmentCollectionResourcesManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	addFragmentCollectionResource(itemData) {
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					this.one('#fileEntryId').value = itemValue.fileEntryId;

					submitForm(this.one('#fragmentCollectionResourceFm'));
				}
			},
			selectEventName: this.ns('uploadFragmentCollectionResource'),
			title: Liferay.Language.get('upload-fragment-collection-resource'),
			url: itemData.itemSelectorURL,
		});
	}

	deleteSelectedFragmentCollectionResources(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(
				this.one('#fm'),
				itemData.deleteFragmentCollectionResourcesURL
			);
		}
	}
}

export default FragmentCollectionResourcesManagementToolbarDefaultEventHandler;
