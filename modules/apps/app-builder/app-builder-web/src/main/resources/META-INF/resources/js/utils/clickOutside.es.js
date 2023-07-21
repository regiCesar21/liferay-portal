/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import dom from 'metal-dom';

export default (target, ...elements) => {
	return !elements.some((element) => {
		if (typeof element === 'string') {
			return !!dom.closest(target, element);
		}

		return element && dom.contains(element, target);
	});
};
