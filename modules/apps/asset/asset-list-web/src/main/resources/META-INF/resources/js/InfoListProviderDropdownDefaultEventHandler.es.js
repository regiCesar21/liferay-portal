/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler, openModal} from 'frontend-js-web';
import {Config} from 'metal-state';

class InfoListProviderDropdownDefaultEventHandler extends DefaultEventHandler {
	viewInfoListProviderItems(itemData) {
		openModal({
			title: itemData.infoListProviderTitle,
			url: itemData.viewInfoListProviderItemsURL,
		});
	}
}

InfoListProviderDropdownDefaultEventHandler.STATE = {
	spritemap: Config.string(),
};

export default InfoListProviderDropdownDefaultEventHandler;
