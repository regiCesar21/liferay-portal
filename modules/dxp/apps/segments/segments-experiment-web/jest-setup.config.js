/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

window.Liferay.Util.sub = function (string = '', data) {
	const REGEX_SUB = /(?<=-|^)x(?=-|\s)/g;

	if (
		arguments.length > 2 ||
		(typeof data !== 'object' && typeof data !== 'function')
	) {
		data = Array.prototype.slice.call(arguments, 1);
	}

	const dataCopy = [...data];
	const max = REGEX_SUB.exec(string).length;
	let replacedValues = 0;

	const replacestring = string.replace
		? string.replace(REGEX_SUB, () => {
				replacedValues = replacedValues + 1;
				const lastReplacement = replacedValues >= max;

				if (lastReplacement) {
					return dataCopy.join('');
				}
				else {
					return dataCopy.shift();
				}
		  })
		: string;

	return replacestring;
};

window.Liferay.Util.openToast = () => true;
