/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler} from 'frontend-js-web';
import {Config} from 'metal-state';

class RedirectManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteSelectedRedirectEntries() {
		const form = this.one('#fm');

		Liferay.Util.postForm(form, {
			data: {
				deleteEntryIds: Liferay.Util.listCheckedExcept(
					form,
					this.ns('allRowIds')
				),
			},
			url: this.deleteRedirectEntriesURL,
		});
	}
}

RedirectManagementToolbarDefaultEventHandler.STATE = {
	deleteRedirectEntriesURL: Config.string(),
	spritemap: Config.string(),
};

export default RedirectManagementToolbarDefaultEventHandler;
