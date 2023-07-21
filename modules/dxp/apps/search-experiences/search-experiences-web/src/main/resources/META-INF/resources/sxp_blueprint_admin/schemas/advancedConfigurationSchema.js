/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Since json-loader isn't supported in 7.3.x, this is a copy of
 * sxp-query-element.schema.json as a JavaScript object so it can be imported
 * by another JavaScript file.
 */
export default {
	$id: 'advanced-configuration.schema.json',
	$schema: 'http://json-schema.org/draft-07/schema#',
	properties: {
		source: {
			properties: {
				excludes: {
					items: {
						type: 'string',
					},
					type: 'array',
				},
				fetchSource: {
					type: 'boolean',
				},
				includes: {
					items: {
						type: 'string',
					},
					type: 'array',
				},
			},
			type: 'object',
		},
	},
	type: 'object',
};
