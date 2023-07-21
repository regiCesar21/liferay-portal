/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const concatValues = (values) =>
	values
		.join(', ')
		.replace(
			/, ([^,]*)$/,
			` ${Liferay.Language.get('and').toLowerCase()} $1`
		);

export const isEqualObjects = (firstObj = {}, secondObj = {}) => {
	if (typeof firstObj !== 'object' || typeof secondObj !== 'object') {
		return false;
	}

	return JSON.stringify(firstObj) === JSON.stringify(secondObj);
};

export const getValidName = (defaultName, name) => {
	return name && name.toLowerCase() !== 'null' ? name : defaultName;
};
