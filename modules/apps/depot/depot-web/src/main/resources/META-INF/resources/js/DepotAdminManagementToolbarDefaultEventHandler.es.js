/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler, openSimpleInputModal} from 'frontend-js-web';
import {Config} from 'metal-state';

import confirmDepotEntryDeletion from './confirmDepotEntryDeletion.es';

class DepotAdminManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	addDepotEntry(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('add-asset-library'),
			formSubmitURL: itemData.addDepotEntryURL,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			namespace: this.namespace,
			spritemap: this.spritemap,
		});
	}

	deleteSelectedDepotEntries() {
		if (confirmDepotEntryDeletion()) {
			const form = this.one('#fm');

			Liferay.Util.postForm(form, {
				data: {
					deleteEntryIds: Liferay.Util.listCheckedExcept(
						form,
						this.ns('allRowIds')
					),
				},
				url: this.deleteDepotEntriesURL,
			});
		}
	}
}

DepotAdminManagementToolbarDefaultEventHandler.STATE = {
	deleteDepotEntriesURL: Config.string(),
	spritemap: Config.string(),
};

export default DepotAdminManagementToolbarDefaultEventHandler;
