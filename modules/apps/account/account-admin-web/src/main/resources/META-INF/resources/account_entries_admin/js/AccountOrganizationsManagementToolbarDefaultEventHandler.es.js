/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler, openSelectionModal} from 'frontend-js-web';

class AccountOrganizationsManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	removeOrganizations(itemData) {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-remove-the-selected-organizations'
				)
			)
		) {
			const form = this.one('#fm');

			Liferay.Util.postForm(form, {
				data: {
					accountOrganizationIds: Liferay.Util.listCheckedExcept(
						form,
						this.ns('allRowIds')
					),
				},
				url: itemData.removeOrganizationsURL,
			});
		}
	}

	selectAccountOrganizations(itemData) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('assign'),
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const form = this.one('#fm');

					Liferay.Util.postForm(form, {
						data: {
							accountOrganizationIds: selectedItem.value,
						},
						url: itemData.assignAccountOrganizationsURL,
					});
				}
			},
			selectEventName: this.ns('assignAccountOrganizations'),
			title: Liferay.Util.sub(
				Liferay.Language.get('assign-organizations-to-x'),
				itemData.accountEntryName
			),
			url: itemData.selectAccountOrganizationsURL,
		});
	}
}

export default AccountOrganizationsManagementToolbarDefaultEventHandler;
