/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler, openSelectionModal} from 'frontend-js-web';
import dom from 'metal-dom';

class EditTeamAssignmentsUsersManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	selectUser(itemData) {
		openSelectionModal({
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const addTeamUsersFm = this.one('#addTeamUsersFm');

					selectedItem.forEach((item) => {
						dom.append(addTeamUsersFm, item);
					});

					submitForm(addTeamUsersFm);
				}
			},
			selectEventName: this.ns('selectUser'),
			title: itemData.title,
			url: itemData.selectUserURL,
		});
	}

	deleteUsers() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}
}

export default EditTeamAssignmentsUsersManagementToolbarDefaultEventHandler;
