/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-web';

export function openInfoItemSelector(
	callback,
	eventName,
	itemSelectorURL,
	destroyedCallback = null
) {
	openSelectionModal({
		onClose: destroyedCallback,
		onSelect: (selectedItem) => {
			const infoItem = {
				...JSON.parse(selectedItem.value),
				type: selectedItem.returnType,
			};

			callback(infoItem);
		},
		selectEventName: eventName,
		title: Liferay.Language.get('select'),
		url: itemSelectorURL,
	});
}
