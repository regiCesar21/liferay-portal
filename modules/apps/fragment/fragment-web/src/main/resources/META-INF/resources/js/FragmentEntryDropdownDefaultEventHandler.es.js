/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	DefaultEventHandler,
	openSelectionModal,
	openSimpleInputModal,
} from 'frontend-js-web';
import {Config} from 'metal-state';

class FragmentEntryDropdownDefaultEventHandler extends DefaultEventHandler {
	copyFragmentEntry(itemData) {
		this.one('#fragmentCollectionId').value = itemData.fragmentCollectionId;
		this.one('#fragmentEntryIds').value = itemData.fragmentEntryId;

		submitForm(this.one('#fragmentEntryFm'), itemData.copyFragmentEntryURL);
	}

	copyToContributedFragmentEntry(itemData) {
		openSelectionModal({
			id: this.ns('selectFragmentCollection'),
			onSelect: (selectedItem) => {
				if (selectedItem) {
					this.one('#fragmentCollectionId').value = selectedItem.id;
					this.one('#fragmentEntryKeys').value =
						itemData.fragmentEntryKey;

					submitForm(
						this.one('#fragmentEntryFm'),
						itemData.copyContributedFragmentEntryURL
					);
				}
			},
			selectEventName: this.ns('selectFragmentCollection'),
			title: Liferay.Language.get('select-collection'),
			url: itemData.selectFragmentCollectionURL,
		});
	}

	copyToFragmentEntry(itemData) {
		this._selectFragmentCollection(
			itemData.fragmentEntryId,
			itemData.selectFragmentCollectionURL,
			itemData.copyFragmentEntryURL
		);
	}

	deleteDraftFragmentEntry(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteDraftFragmentEntryURL);
		}
	}

	deleteFragmentEntry(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteFragmentEntryURL);
		}
	}

	deleteFragmentEntryPreview(itemData) {
		this._send(itemData.deleteFragmentEntryPreviewURL);
	}

	moveFragmentEntry(itemData) {
		this._selectFragmentCollection(
			itemData.fragmentEntryId,
			itemData.selectFragmentCollectionURL,
			itemData.moveFragmentEntryURL
		);
	}

	renameFragmentEntry(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-fragment'),
			formSubmitURL: itemData.updateFragmentEntryURL,
			idFieldName: 'id',
			idFieldValue: itemData.fragmentEntryId,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: itemData.fragmentEntryName,
			namespace: this.namespace,
			spritemap: this.spritemap,
		});
	}

	updateFragmentEntryPreview(itemData) {
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					this.one('#fragmentEntryId').value =
						itemData.fragmentEntryId;
					this.one('#fileEntryId').value = itemValue.fileEntryId;

					submitForm(this.one('#fragmentEntryPreviewFm'));
				}
			},
			selectEventName: this.ns('changePreview'),
			title: Liferay.Language.get('fragment-thumbnail'),
			url: itemData.itemSelectorURL,
		});
	}

	_selectFragmentCollection(
		fragmentEntryId,
		selectFragmentCollectionURL,
		targetFragmentEntryURL
	) {
		openSelectionModal({
			id: this.ns('selectFragmentCollection'),
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const form = this.one('#fragmentEntryFm');

					form.querySelector(
						`#${this.ns('fragmentCollectionId')}`
					).value = selectedItem.id;

					form.querySelector(
						`#${this.ns('fragmentEntryIds')}`
					).value = fragmentEntryId;

					submitForm(form, targetFragmentEntryURL);
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

FragmentEntryDropdownDefaultEventHandler.STATE = {
	copyFragmentEntryURL: Config.string(),
	fragmentCollectionId: Config.string(),
	moveFragmentEntryURL: Config.string(),
	spritemap: Config.string(),
};

export default FragmentEntryDropdownDefaultEventHandler;
