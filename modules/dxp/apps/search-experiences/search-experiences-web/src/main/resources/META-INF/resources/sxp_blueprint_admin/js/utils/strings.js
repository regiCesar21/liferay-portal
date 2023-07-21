/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moment from 'moment';

/**
 * Formats a date string from "2022-02-11T06:26:17Z" to a displayed date
 * like "Feb 11, 2022 6:26 AM" for 'en-US'.
 * @param {string} date
 * @returns {string}
 */
export function formatDate(date) {
	return moment(date)
		.locale(Liferay.ThemeDisplay.getBCP47LanguageId() || 'en-US')
		.format('lll');
}

/**
 * Truncates a string and adds an ellipsis if it is longer than the specified
 * length. `truncateLength` is optional and defaults to `200`.
 *
 * Example:
 * truncateString('hello', 2)
 * => 'he...'
 *
 * @param {string} value The value to be truncated.
 * @param {number} truncateLength Length to truncate at.
 * @returns
 */
export function truncateString(value, truncateLength = 200) {
	return value.length > truncateLength
		? value.substring(0, truncateLength).concat('...')
		: value;
}
