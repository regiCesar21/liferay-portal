/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler, openSelectionModal} from 'frontend-js-web';

class UserDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteGroupUsers(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, itemData.deleteGroupUsersURL);
		}
	}

	assignRoles(itemData) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			getSelectedItemsOnly: false,
			multiple: true,
			onSelect: (items) => {
				if (items.length) {
					const editUserGroupRoleFm = this.one(
						'#editUserGroupRoleFm'
					);

					if (!editUserGroupRoleFm) {
						return;
					}

					const allInput = document.createElement('input');

					allInput.name = this.ns('availableRowIds');
					allInput.value = items.map((item) => item.value);

					editUserGroupRoleFm.appendChild(allInput);

					const checkedInput = document.createElement('input');

					checkedInput.name = this.ns('rowIds');
					checkedInput.value = items
						.filter((item) => item.checked)
						.map((item) => item.value);

					editUserGroupRoleFm.appendChild(checkedInput);

					submitForm(
						editUserGroupRoleFm,
						itemData.editUserGroupRoleURL
					);
				}
			},
			title: Liferay.Language.get('assign-roles'),
			url: itemData.assignRolesURL,
		});
	}
}

export default UserDropdownDefaultEventHandler;
