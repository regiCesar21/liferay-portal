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
	$id: 'highlight-configuration.schema.json',
	$schema: 'http://json-schema.org/draft-07/schema#',
	definitions: {
		HighlightField: {
			properties: {
				fragment_offset: {
					type: 'integer',
				},
				fragment_size: {
					type: 'integer',
				},
				number_of_fragments: {
					type: 'integer',
				},
			},
			type: 'object',
		},
	},
	properties: {
		fields: {
			additionalProperties: {
				$ref: '#/definitions/HighlightField',
			},
			type: 'object',
		},
		fragment_size: {
			type: 'integer',
		},
		number_of_fragments: {
			type: 'integer',
		},
		post_tags: {
			items: {
				type: 'string',
			},
			type: 'array',
		},
		pre_tags: {
			items: {
				type: 'string',
			},
			type: 'array',
		},
		require_field_match: {
			type: 'boolean',
		},
		type: {
			type: 'string',
		},
	},
	type: 'object',
};
