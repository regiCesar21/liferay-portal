/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	DefaultEventHandler,
	openModal,
	openSimpleInputModal,
} from 'frontend-js-web';
import {Config} from 'metal-state';

class AssetEntryListDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteAssetListEntry(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteAssetListEntryURL);
		}
	}

	permissionsAssetEntryList(itemData) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: itemData.permissionsAssetEntryListURL,
		});
	}

	renameAssetListEntry(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-collection'),
			formSubmitURL: itemData.renameAssetListEntryURL,
			idFieldName: 'id',
			idFieldValue: itemData.assetListEntryId,
			mainFieldLabel: Liferay.Language.get('title'),
			mainFieldName: 'title',
			mainFieldPlaceholder: Liferay.Language.get('title'),
			mainFieldValue: itemData.assetListEntryTitle,
			namespace: this.namespace,
			spritemap: this.spritemap,
		});
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

AssetEntryListDropdownDefaultEventHandler.STATE = {
	spritemap: Config.string(),
};

export default AssetEntryListDropdownDefaultEventHandler;
