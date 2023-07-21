/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

class CommerceCookie {
	constructor(scope = null) {
		if (!scope) {
			throw new Error('Scope must be defined');
		}

		this.scope = scope;
	}

	getValue(key) {
		const [, value] = document.cookie.split(`${this.scope}${key}=`);

		return !value ? null : value.split(';')[0];
	}

	setValue(key, value, expires, path = '/') {
		const cookieValue = `${this.scope}${key}=${value};`,
			cookiePath = `path=${path};`;

		let cookieExp = '';

		if (expires) {
			const expirationDate =
				expires instanceof Date ? expires : new Date(expires);

			cookieExp = `expires=${expirationDate.toUTCString()};`;
		}

		document.cookie = `${cookieValue}${cookieExp}${cookiePath}`;
	}
}

export default CommerceCookie;
