/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import InfoItemService from '../services/InfoItemService';

export default function loadBackgroundImage(backgroundImage) {
	if (!backgroundImage) {
		return Promise.resolve('');
	}
	else if (typeof backgroundImage.url === 'string') {
		return Promise.resolve(backgroundImage.url);
	}
	else if (backgroundImage.fieldId) {
		return InfoItemService.getInfoItemFieldValue({
			classNameId: backgroundImage.classNameId,
			classPK: backgroundImage.classPK,
			fieldId: backgroundImage.fieldId,
			onNetworkStatus: () => {},
		}).then((response) => {
			if (response.fieldValue && response.fieldValue.url) {
				return response.fieldValue.url;
			}

			return '';
		});
	}

	return Promise.resolve('');
}
