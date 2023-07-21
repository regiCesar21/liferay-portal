/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	DefaultEventHandler,
	ItemSelectorDialog,
	addParams,
	navigate,
	openSelectionModal,
} from 'frontend-js-web';

class ContentDashboardManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	selectAuthor(itemData) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('select'),
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					var redirectURL = itemData.redirectURL;

					selectedItem.forEach((item) => {
						redirectURL = addParams(
							this.namespace + 'authorIds=' + item.id,
							redirectURL
						);
					});

					navigate(redirectURL);
				}
			},
			selectEventName: this.ns('selectedAuthorItem'),
			title: itemData.dialogTitle,
			url: itemData.selectAuthorURL,
		});
	}

	selectAssetCategory(itemData) {
		const itemSelectorDialog = new ItemSelectorDialog({
			buttonAddLabel: Liferay.Language.get('select'),
			eventName: this.ns('selectedAssetCategory'),
			title: itemData.dialogTitle,
			url: itemData.selectAssetCategoryURL,
		});

		itemSelectorDialog.on('selectedItemChange', (event) => {
			const selectedItem = event.selectedItem;

			if (selectedItem) {
				const assetCategories = Object.keys(selectedItem).filter(
					(key) => !selectedItem[key].unchecked
				);

				var redirectURL = itemData.redirectURL;

				assetCategories.forEach((assetCategory) => {
					redirectURL = addParams(
						this.namespace +
							'assetCategoryId=' +
							selectedItem[assetCategory].categoryId,
						redirectURL
					);
				});

				navigate(redirectURL);
			}
		});

		itemSelectorDialog.open();
	}

	selectAssetTag(itemData) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('select'),
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const assetTags = selectedItem['items'].split(',');

					var redirectURL = itemData.redirectURL;

					assetTags.forEach((assetTag) => {
						redirectURL = addParams(
							this.namespace + 'assetTagId=' + assetTag,
							redirectURL
						);
					});

					navigate(redirectURL);
				}
			},
			selectEventName: this.ns('selectedAssetTag'),
			title: itemData.dialogTitle,
			url: itemData.selectTagURL,
		});
	}

	selectContentDashboardItemType(itemData) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('select'),
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					var redirectURL = itemData.redirectURL;

					selectedItem.forEach((item) => {
						redirectURL = addParams(
							this.namespace +
								'contentDashboardItemTypePayload=' +
								JSON.stringify(item),
							redirectURL
						);
					});

					navigate(redirectURL);
				}
			},
			selectEventName: this.ns('selectedContentDashboardItemTypeItem'),
			title: itemData.dialogTitle,
			url: itemData.selectContentDashboardItemTypeURL,
		});
	}

	selectScope(itemData) {
		openSelectionModal({
			id: this.ns('selectedScopeIdItem'),
			onSelect: (selectedItem) => {
				navigate(
					addParams(
						this.namespace + 'scopeId=' + selectedItem.groupid,
						itemData.redirectURL
					)
				);
			},
			selectEventName: this.ns('selectedScopeIdItem'),
			title: itemData.dialogTitle,
			url: itemData.selectScopeURL,
		});
	}
}

export default ContentDashboardManagementToolbarDefaultEventHandler;
