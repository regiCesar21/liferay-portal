/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler} from 'frontend-js-web';
import {Config} from 'metal-state';

class ListItemsDropdownDefaultEventHandler extends DefaultEventHandler {
	editContent(itemData) {
		this._navigate(itemData.editContentURL);
	}

	editDisplayPageTemplate(itemData) {
		this._navigate(itemData.editDisplayPageTemplateURL);
	}

	viewDisplayPage(itemData) {
		this._navigate(itemData.viewDisplayPageURL);
	}

	_navigate(url) {
		const openerWindow = Liferay.Util.getTop();

		openerWindow.Liferay.Util.navigate(url);
	}
}

ListItemsDropdownDefaultEventHandler.STATE = {
	spritemap: Config.string(),
};

export default ListItemsDropdownDefaultEventHandler;
