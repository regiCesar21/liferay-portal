/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	DefaultEventHandler,
	openModal,
	openSelectionModal,
	openSimpleInputModal,
} from 'frontend-js-web';
import {Config} from 'metal-state';

class DisplayPageDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteDisplayPage(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteDisplayPageURL);
		}
	}

	deleteLayoutPageTemplateEntryPreview(itemData) {
		this._send(itemData.deleteLayoutPageTemplateEntryPreviewURL);
	}

	discardDraft(itemData) {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-discard-current-draft-and-apply-latest-published-changes'
				)
			)
		) {
			this._send(itemData.discardDraftURL);
		}
	}

	markAsDefaultDisplayPage(itemData) {
		if (itemData.message !== '') {
			if (confirm(Liferay.Language.get(itemData.message))) {
				this._send(itemData.markAsDefaultDisplayPageURL);
			}
		}
		else {
			this._send(itemData.markAsDefaultDisplayPageURL);
		}
	}

	permissionsDisplayPage(itemData) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: itemData.permissionsDisplayPageURL,
		});
	}

	renameDisplayPage(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-display-page-template'),
			formSubmitURL: itemData.updateDisplayPageURL,
			idFieldName: 'layoutPageTemplateEntryId',
			idFieldValue: itemData.layoutPageTemplateEntryId,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: itemData.layoutPageTemplateEntryName,
			namespace: this.namespace,
			spritemap: this.spritemap,
		});
	}

	unmarkAsDefaultDisplayPage(itemData) {
		if (confirm(Liferay.Language.get('unmark-default-confirmation'))) {
			this._send(itemData.unmarkAsDefaultDisplayPageURL);
		}
	}

	updateLayoutPageTemplateEntryPreview(itemData) {
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					this.one('#layoutPageTemplateEntryId').value =
						itemData.layoutPageTemplateEntryId;
					this.one('#fileEntryId').value = itemValue.fileEntryId;

					submitForm(this.one('#layoutPageTemplateEntryPreviewFm'));
				}
			},
			selectEventName: this.ns('changePreview'),
			title: Liferay.Language.get('page-template-thumbnail'),
			url: itemData.itemSelectorURL,
		});
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

DisplayPageDropdownDefaultEventHandler.STATE = {
	spritemap: Config.string(),
};

export default DisplayPageDropdownDefaultEventHandler;
