/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler} from 'frontend-js-web';
import {Config} from 'metal-state';

class DisplayPageManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteSelectedDisplayPages() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}

	exportDisplayPages(itemData) {
		submitForm(this.one('#fm'), itemData.exportDisplayPageURL);
	}
}

DisplayPageManagementToolbarDefaultEventHandler.STATE = {
	spritemap: Config.string(),
};

export default DisplayPageManagementToolbarDefaultEventHandler;
