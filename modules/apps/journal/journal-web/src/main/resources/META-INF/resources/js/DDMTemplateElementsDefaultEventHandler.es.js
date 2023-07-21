/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler, openModal} from 'frontend-js-web';

class DDMTemplateElementsDefaultEventHandler extends DefaultEventHandler {
	deleteDDMTemplate(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, itemData.deleteDDMTemplateURL);
		}
	}

	permissionsDDMTemplate(itemData) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: itemData.permissionsDDMTemplateURL,
		});
	}
}

export default DDMTemplateElementsDefaultEventHandler;
