/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

if (typeof Array.prototype.flatMap !== 'function') {
	Array.prototype.flatMap = function () {
		return Array.prototype.map
			.apply(this, arguments)
			.reduce((acc, x) => acc.concat(x), []);
	};
}
