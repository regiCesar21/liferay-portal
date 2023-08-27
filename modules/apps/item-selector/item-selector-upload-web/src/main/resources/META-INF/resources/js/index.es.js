/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ItemSelectorRepositoryEntryBrowser} from 'item-selector-taglib';

export default function (props) {
	const itemSelector = new ItemSelectorRepositoryEntryBrowser({...props});

	itemSelector.on('selectedItem', (event) => {
		Liferay.Util.getOpener().Liferay.fire(props.eventName, event);
	});
}
