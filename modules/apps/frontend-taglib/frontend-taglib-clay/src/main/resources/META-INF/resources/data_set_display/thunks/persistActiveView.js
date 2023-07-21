/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {updateActiveView} from '../actions/updateActiveView';
import {saveViewSettings} from '../utilities/saveViewSettings';

export default function persistActiveView({
	activeViewName,
	appURL,
	id,
	portletId,
}) {
	return (dispatch) => {
		dispatch(updateActiveView(activeViewName));

		return saveViewSettings({
			appURL,
			id,
			portletId,
			settings: {name: activeViewName},
		});
	};
}
