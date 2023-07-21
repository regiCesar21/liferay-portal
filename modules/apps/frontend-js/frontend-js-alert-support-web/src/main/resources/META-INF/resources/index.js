/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import dom from 'metal-dom';

let handle;

export default () => {
	if (!handle) {
		handle = dom.delegate(
			document.body,
			'click',
			'[data-dismiss="liferay-alert"]',
			(event) => {
				event.preventDefault();

				const container = dom.closest(event.delegateTarget, '.alert');

				if (container) {
					container.parentNode.removeChild(container);
				}
			}
		);
	}
};
