/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

window.addEventListener('load', function () {
	var hasControlMenu = document.querySelector('.has-control-menu');
	if (hasControlMenu) {
		var controlMenu = document.querySelector('.control-menu-container');
		hasControlMenu.setAttribute(
			'style',
			'margin-top: ' + controlMenu.offsetHeight + 'px'
		);
		controlMenu.classList.add('has-control-menu-fixed');
	}
});
