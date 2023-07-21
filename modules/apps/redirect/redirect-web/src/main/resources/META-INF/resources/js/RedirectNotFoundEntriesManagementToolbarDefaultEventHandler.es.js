/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler} from 'frontend-js-web';
import {Config} from 'metal-state';

class RedirectNotFoundEntriesManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	ignoreSelectedRedirectNotFoundEntries() {
		const form = this.one('#fm');

		Liferay.Util.postForm(form, {
			data: {
				deleteEntryIds: Liferay.Util.listCheckedExcept(
					form,
					this.ns('allRowIds')
				),
				ignored: true,
			},
			url: this.editRedirectNotFoundEntriesURL,
		});
	}

	unignoreSelectedRedirectNotFoundEntries() {
		const form = this.one('#fm');

		Liferay.Util.postForm(form, {
			data: {
				deleteEntryIds: Liferay.Util.listCheckedExcept(
					form,
					this.ns('allRowIds')
				),
				ignored: false,
			},
			url: this.editRedirectNotFoundEntriesURL,
		});
	}
}

RedirectNotFoundEntriesManagementToolbarDefaultEventHandler.STATE = {
	editRedirectNotFoundEntriesURL: Config.string(),
	spritemap: Config.string(),
};

export default RedirectNotFoundEntriesManagementToolbarDefaultEventHandler;
