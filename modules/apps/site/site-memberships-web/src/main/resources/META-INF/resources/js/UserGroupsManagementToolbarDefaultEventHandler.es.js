/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	DefaultEventHandler,
	addParams,
	openSelectionModal,
} from 'frontend-js-web';
import dom from 'metal-dom';

class UserGroupsManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteSelectedUserGroups() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}

	removeUserGroupRole(itemData) {
		if (confirm(itemData.message)) {
			submitForm(this.one('#fm'), itemData.removeUserGroupRoleURL);
		}
	}

	selectRoles(itemData) {
		openSelectionModal({
			onSelect: (event) => {
				location.href = addParams(
					`${this.ns('roleId')}=${event.id}`,
					itemData.viewRoleURL
				);
			},
			selectEventName: this.ns('selectRole'),
			title: Liferay.Language.get('select-role'),
			url: itemData.selectRolesURL,
		});
	}

	selectRole(itemData) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const fm = this.one('#fm');

					selectedItem.forEach((item) => {
						dom.append(fm, item);
					});

					submitForm(fm, itemData.editUserGroupsRolesURL);
				}
			},
			selectEventName: this.ns('selectRole'),
			title: Liferay.Language.get('assign-roles'),
			url: itemData.selectRoleURL,
		});
	}

	selectUserGroups(itemData) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const addGroupUserGroupsFm = this.one(
						'#addGroupUserGroupsFm'
					);

					selectedItem.forEach((item) => {
						dom.append(addGroupUserGroupsFm, item);
					});

					submitForm(addGroupUserGroupsFm);
				}
			},
			selectEventName: this.ns('selectUserGroups'),
			title: Liferay.Util.sub(
				Liferay.Language.get('assign-user-groups-to-this-x'),
				itemData.groupTypeLabel
			),
			url: itemData.selectUserGroupsURL,
		});
	}
}

export default UserGroupsManagementToolbarDefaultEventHandler;
