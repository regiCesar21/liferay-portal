/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	DefaultEventHandler,
	addParams,
	getPortletId,
	openSelectionModal,
} from 'frontend-js-web';
import dom from 'metal-dom';

class UsersManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteSelectedUsers() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}

	removeUserRole(itemData) {
		if (confirm(itemData.message)) {
			submitForm(this.one('#fm'), itemData.removeUserRoleURL);
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

					submitForm(fm, itemData.editUsersRolesURL);
				}
			},
			selectEventName: this.ns('selectRole'),
			title: Liferay.Language.get('assign-roles'),
			url: itemData.selectRoleURL,
		});
	}

	selectTeams(itemData) {
		openSelectionModal({
			onSelect: (event) => {
				location.href = addParams(
					`${this.ns('teamId')}=${event.id}`,
					itemData.viewTeamURL
				);
			},
			selectEventName: this.ns('selectTeam'),
			title: Liferay.Language.get('select-team'),
			url: itemData.selectTeamsURL,
		});
	}

	selectUsers(itemData) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const addGroupUsersFm = this.one('#addGroupUsersFm');

					selectedItem.forEach((item) => {
						dom.append(addGroupUsersFm, item);
					});

					submitForm(addGroupUsersFm);
				}
			},
			selectEventName: this.ns('selectUsers'),
			title: Liferay.Util.sub(
				Liferay.Language.get('assign-users-to-this-x'),
				itemData.groupTypeLabel
			),
			url: addParams(
				`p_p_id=${getPortletId(this.namespace)}`,
				itemData.selectUsersURL
			),
		});
	}
}

export default UsersManagementToolbarDefaultEventHandler;
