/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PortletBase} from 'frontend-js-web';
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';

class AccountEntriesAdminPortlet extends PortletBase {

	/**
	 * @inheritDoc
	 */
	created() {
		this.eventHandler_ = new EventHandler();
	}

	/**
	 * @inheritDoc
	 */
	attached() {
		this.businessAccountOnlySection = this.one('.business-account-only');

		const typeSelect = this.one('#type');

		if (typeSelect) {
			this.updateVisibility_(typeSelect);

			this.eventHandler_.add(
				dom.on(typeSelect, 'change', (e) => {
					this.updateVisibility_(e.currentTarget);
				})
			);
		}
	}

	/**
	 * Hides or shows the business-account-only fields in the edit form.
	 *
	 * @param {HTMLSelectElement} typeSelect
	 * @private
	 */
	updateVisibility_(typeSelect) {
		if (typeSelect.value === 'business') {
			dom.removeClasses(this.businessAccountOnlySection, 'hide');
		}
		else {
			dom.addClasses(this.businessAccountOnlySection, 'hide');
		}
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		super.detached();
		this.eventHandler_.removeAllListeners();
	}
}

export default AccountEntriesAdminPortlet;
