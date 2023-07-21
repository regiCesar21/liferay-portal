/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler} from 'frontend-js-web';

class LayoutsManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	convertSelectedPages(itemData) {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-convert-the-selected-pages'
				)
			)
		) {
			this._send(itemData.convertLayoutURL);
		}
	}

	deleteSelectedPages(itemData) {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-delete-the-selected-pages-if-the-selected-pages-have-child-pages-they-will-also-be-removed'
				)
			)
		) {
			this._send(itemData.deleteLayoutURL);
		}
	}

	_send(url) {
		submitForm(this.one('#fm'), url);
	}
}

export default LayoutsManagementToolbarDefaultEventHandler;
