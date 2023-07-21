/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler} from 'frontend-js-web';

class AccountGroupsManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteAccountGroups(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			const form = this.one('#fm');

			Liferay.Util.postForm(form, {
				data: {
					accountGroupIds: Liferay.Util.listCheckedExcept(
						form,
						this.ns('allRowIds')
					),
				},
				url: itemData.deleteAccountGroupsURL,
			});
		}
	}
}

export default AccountGroupsManagementToolbarDefaultEventHandler;
