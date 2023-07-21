/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler, openSelectionModal} from 'frontend-js-web';
import dom from 'metal-dom';

class OrganizationsManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteSelectedOrganizations() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}

	selectOrganizations(itemData) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const addGroupOrganizationsFm = this.one(
						'#addGroupOrganizationsFm'
					);

					selectedItem.forEach((item) => {
						dom.append(addGroupOrganizationsFm, item);
					});

					submitForm(addGroupOrganizationsFm);
				}
			},
			selectEventName: this.ns('selectOrganizations'),
			title: Liferay.Util.sub(
				Liferay.Language.get('assign-organizations-to-this-x'),
				itemData.groupTypeLabel
			),
			url: itemData.selectOrganizationsURL,
		});
	}
}

export default OrganizationsManagementToolbarDefaultEventHandler;
