/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler, openSelectionModal} from 'frontend-js-web';
import {Config} from 'metal-state';

class AccountGroupAccountEntriesManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	removeAccountGroupAccountEntries() {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-remove-the-selected-accounts'
				)
			)
		) {
			const form = this.one('#fm');

			Liferay.Util.postForm(form, {
				data: {
					accountEntryIds: Liferay.Util.listCheckedExcept(
						form,
						this.ns('allRowIds')
					),
				},
				url: this.removeAccountGroupAccountEntriesURL,
			});
		}
	}

	selectAccountGroupAccountEntries() {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('assign'),
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const form = this.one('#fm');

					Liferay.Util.postForm(form, {
						data: {
							accountEntryIds: selectedItem.value,
						},
						url: this.assignAccountGroupAccountEntriesURL,
					});
				}
			},
			selectEventName: this.ns('selectAccountEntries'),
			title: Liferay.Util.sub(
				Liferay.Language.get('assign-accounts-to-x'),
				this.accountGroupName
			),
			url: this.selectAccountGroupAccountEntriesURL,
		});
	}
}

AccountGroupAccountEntriesManagementToolbarDefaultEventHandler.STATE = {
	accountGroupName: Config.string().required(),
	assignAccountGroupAccountEntriesURL: Config.string().required(),
	removeAccountGroupAccountEntriesURL: Config.string().required(),
	selectAccountGroupAccountEntriesURL: Config.string().required(),
};

export default AccountGroupAccountEntriesManagementToolbarDefaultEventHandler;
