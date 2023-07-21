/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler, openSimpleInputModal} from 'frontend-js-web';
import {Config} from 'metal-state';

class ManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	addStyleBookEntry(itemData) {
		openSimpleInputModal({
			dialogTitle: itemData.title,
			formSubmitURL: itemData.addStyleBookEntryURL,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			namespace: this.namespace,
			spritemap: this.spritemap,
		});
	}

	copySelectedStyleBookEntries() {
		this.one('#styleBookEntryIds').value = Liferay.Util.listCheckedExcept(
			this.one('#fm'),
			this.ns('allRowIds')
		);

		submitForm(this.one('#styleBookEntryFm'), this.copyStyleBookEntryURL);
	}

	deleteSelectedStyleBookEntries() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}

	exportSelectedStyleBookEntries() {
		submitForm(this.one('#fm'), this.exportStyleBookEntriesURL);
	}
}

ManagementToolbarDefaultEventHandler.STATE = {
	copyStyleBookEntryURL: Config.string(),
	exportStyleBookEntriesURL: Config.string(),
	spritemap: Config.string(),
};

export default ManagementToolbarDefaultEventHandler;
