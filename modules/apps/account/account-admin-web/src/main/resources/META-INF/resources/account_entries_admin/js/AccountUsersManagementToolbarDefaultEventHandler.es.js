/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler, openSelectionModal} from 'frontend-js-web';
import {Config} from 'metal-state';

import {MODAL_STATE_ACCOUNT_USERS} from './SessionStorageKeys.es';

class AccountUsersManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	attached() {
		if (
			window.sessionStorage.getItem(MODAL_STATE_ACCOUNT_USERS) === 'open'
		) {
			window.sessionStorage.removeItem(MODAL_STATE_ACCOUNT_USERS);

			this.selectAccountUsers();
		}
	}

	removeUsers(itemData) {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-remove-the-selected-users'
				)
			)
		) {
			const form = this.one('#fm');

			Liferay.Util.postForm(form, {
				data: {
					accountUserIds: Liferay.Util.listCheckedExcept(
						form,
						this.ns('allRowIds')
					),
				},
				url: itemData.removeUsersURL,
			});
		}
	}

	selectAccountUsers() {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('assign'),
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const form = this.one('#fm');

					Liferay.Util.postForm(form, {
						data: {
							accountUserIds: selectedItem.value,
						},
						url: this.assignAccountUsersURL,
					});
				}
			},
			selectEventName: this.ns('assignAccountUsers'),
			title: Liferay.Util.sub(
				Liferay.Language.get('assign-users-to-x'),
				this.accountEntryName
			),
			url: this.selectAccountUsersURL,
		});
	}
}

AccountUsersManagementToolbarDefaultEventHandler.STATE = {
	accountEntryName: Config.string().required(),
	assignAccountUsersURL: Config.string().required(),
	selectAccountUsersURL: Config.string().required(),
};

export default AccountUsersManagementToolbarDefaultEventHandler;
