/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PortletBase} from 'frontend-js-web';
import dom from 'metal-dom';

export default class TopLinkEventHandler extends PortletBase {
	attached() {
		this._delegateHandler = dom.delegate(
			document.body,
			'click',
			'a',
			(event) => {
				const openerWindow = Liferay.Util.getTop();

				if (openerWindow && event.delegateTarget.target === '_top') {
					event.preventDefault();

					openerWindow.Liferay.Util.navigate(
						event.delegateTarget.href
					);
				}
			}
		);
	}

	dispose() {
		this._delegateHandler.removeListener();
	}
}
