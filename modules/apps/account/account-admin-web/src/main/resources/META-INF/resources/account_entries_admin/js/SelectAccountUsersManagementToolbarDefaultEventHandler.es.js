/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler} from 'frontend-js-web';
import {Config} from 'metal-state';

import {MODAL_STATE_ACCOUNT_USERS} from './SessionStorageKeys.es';

class SelectAccountUsersManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	addAccountEntryUser() {
		if (this.openModalOnRedirect) {
			window.sessionStorage.setItem(MODAL_STATE_ACCOUNT_USERS, 'open');
		}

		Liferay.Util.getTop().location.href = this.addAccountEntryUserURL;
	}
}

SelectAccountUsersManagementToolbarDefaultEventHandler.STATE = {
	addAccountEntryUserURL: Config.string().required(),
	openModalOnRedirect: Config.bool().value(false),
};

export default SelectAccountUsersManagementToolbarDefaultEventHandler;
