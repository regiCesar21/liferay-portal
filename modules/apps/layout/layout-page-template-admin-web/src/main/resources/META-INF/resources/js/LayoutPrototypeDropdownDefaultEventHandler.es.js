/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler, openModal} from 'frontend-js-web';

class LayoutPrototypeDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteLayoutPrototype(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteLayoutPrototypeURL);
		}
	}

	exportLayoutPrototype(itemData) {
		openModal({
			title: Liferay.Language.get('export'),
			url: itemData.exportLayoutPrototypeURL,
		});
	}

	importLayoutPrototype(itemData) {
		openModal({
			title: Liferay.Language.get('import'),
			url: itemData.importLayoutPrototypeURL,
		});
	}

	permissionsLayoutPrototype(itemData) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: itemData.permissionsLayoutPrototypeURL,
		});
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

export default LayoutPrototypeDropdownDefaultEventHandler;
