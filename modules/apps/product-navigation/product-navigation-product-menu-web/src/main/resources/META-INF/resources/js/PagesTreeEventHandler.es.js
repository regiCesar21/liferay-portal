/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Component from 'metal-component';
import dom from 'metal-dom';

class PagesTreeEventHandler extends Component {
	attached() {
		Liferay.on('liferay.dropdown.show', this._handleDropdownOpened);
	}

	disposed() {
		Liferay.detach('liferay.dropdown.show', this._handleDropdownOpened);
	}

	_handleDropdownOpened({menu, trigger}) {
		if (dom.closest(menu, '.pages-tree-dropdown')) {
			const handler = (event) => {
				if (!dom.closest(event.target, '.pages-tree-dropdown')) {
					Liferay.DropdownProvider.hide({menu, trigger});

					window.removeEventListener('click', handler);
				}
			};

			window.addEventListener('click', handler);
		}
	}
}

export default PagesTreeEventHandler;
