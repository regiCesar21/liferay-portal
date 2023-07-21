/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	DefaultEventHandler,
	openSelectionModal,
	openSimpleInputModal,
} from 'frontend-js-web';

class FragmentCompositionDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteFragmentComposition(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteFragmentCompositionURL);
		}
	}

	deleteFragmentCompositionPreview(itemData) {
		this._send(itemData.deleteFragmentCompositionPreviewURL);
	}

	moveFragmentComposition(itemData) {
		this._selectFragmentCollection(
			itemData.fragmentCompositionId,
			itemData.selectFragmentCollectionURL,
			itemData.moveFragmentCompositionURL
		);
	}

	renameFragmentComposition(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-fragment'),
			formSubmitURL: itemData.renameFragmentCompositionURL,
			idFieldName: 'id',
			idFieldValue: itemData.fragmentCompositionId,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: itemData.fragmentCompositionName,
			namespace: this.namespace,
			spritemap: this.spritemap,
		});
	}

	updateFragmentCompositionPreview(itemData) {
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					this.one('#fragmentCompositionId').value =
						itemData.fragmentCompositionId;
					this.one('#fragmentCompositionFileEntryId').value =
						itemValue.fileEntryId;

					submitForm(this.one('#fragmentCompositionPreviewFm'));
				}
			},
			selectEventName: this.ns('changePreview'),
			title: Liferay.Language.get('fragment-thumbnail'),
			url: itemData.itemSelectorURL,
		});
	}

	_selectFragmentCollection(
		fragmentCompositionId,
		selectFragmentCollectionURL,
		targetFragmentCompositionURL
	) {
		openSelectionModal({
			id: this.ns('selectFragmentCollection'),
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const form = this.one('#fragmentEntryFm');

					form.querySelector(
						`#${this.namespace}fragmentCollectionId`
					).value = selectedItem.id;

					form.querySelector(
						`#${this.namespace}fragmentCompositionId`
					).value = fragmentCompositionId;

					submitForm(form, targetFragmentCompositionURL);
				}
			},
			selectEventName: this.ns('selectFragmentCollection'),
			title: Liferay.Language.get('select-collection'),
			url: selectFragmentCollectionURL,
		});
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

export default FragmentCompositionDropdownDefaultEventHandler;
