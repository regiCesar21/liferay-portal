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

class StyleBookEntryDropdownDefaultEventHandler extends DefaultEventHandler {
	copyStyleBookEntry(itemData) {
		this._send(itemData.copyStyleBookEntryURL);
	}

	deleteStyleBookEntry(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteStyleBookEntryURL);
		}
	}

	deleteStyleBookEntryPreview(itemData) {
		this._send(itemData.deleteStyleBookEntryPreviewURL);
	}

	discardDraftStyleBookEntry(itemData) {
		this._send(itemData.discardDraftStyleBookEntryURL);
	}

	markAsDefaultStyleBookEntry(itemData) {
		if (itemData.message !== '') {
			if (confirm(Liferay.Language.get(itemData.message))) {
				this._send(itemData.markAsDefaultStyleBookEntryURL);
			}
		}
		else {
			this._send(itemData.markAsDefaultStyleBookEntryURL);
		}
	}

	renameStyleBookEntry(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-style-book'),
			formSubmitURL: itemData.updateStyleBookEntryURL,
			idFieldName: 'id',
			idFieldValue: itemData.styleBookEntryId,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: itemData.styleBookEntryName,
			namespace: this.namespace,
			spritemap: this.spritemap,
		});
	}

	unmarkAsDefaultStyleBookEntry(itemData) {
		if (confirm(Liferay.Language.get('unmark-default-confirmation'))) {
			this._send(itemData.unmarkAsDefaultStyleBookEntryURL);
		}
	}

	updateStyleBookEntryPreview(itemData) {
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					this.one('#styleBookEntryId').value =
						itemData.styleBookEntryId;
					this.one('#fileEntryId').value = itemValue.fileEntryId;

					submitForm(this.one('#styleBookEntryPreviewFm'));
				}
			},
			selectEventName: this.ns('changePreview'),
			title: Liferay.Language.get('style-book-thumbnail'),
			url: itemData.itemSelectorURL,
		});
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

StyleBookEntryDropdownDefaultEventHandler.STATE = {
	spritemap: Config.string(),
};

export default StyleBookEntryDropdownDefaultEventHandler;
