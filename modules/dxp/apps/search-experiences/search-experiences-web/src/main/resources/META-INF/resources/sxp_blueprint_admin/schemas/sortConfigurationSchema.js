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
	$id: 'sort-configuration.schema.json',
	$schema: 'http://json-schema.org/draft-07/schema#',
	definitions: {
		Geopoint: {
			items: {
				maxItems: 2,
				minItems: 2,
				type: ['string', 'number'],
			},
			type: 'array',
		},
		NestedSort: {
			properties: {
				filter: {
					type: 'object',
				},
				nested: {
					$ref: '#/definitions/NestedSort',
				},
				path: {
					type: 'string',
				},
			},
			required: ['path'],
			type: 'object',
		},
		Script: {
			anyOf: [
				{
					properties: {
						id: {
							type: 'string',
						},
						params: {
							type: 'object',
						},
					},
					required: ['id'],
					type: 'object',
				},
				{
					properties: {
						_options: {
							type: 'object',
						},
						lang: {
							enum: [
								'expression',
								'java',
								'mustache',
								'painless',
							],
							type: 'string',
						},
						params: {
							type: 'object',
						},
						source: {
							type: 'string',
						},
					},
					required: ['source'],
					type: 'object',
				},
			],
			type: 'object',
		},
		Sort: {
			properties: {
				missing: {
					type: 'string',
				},
				mode: {
					enum: ['avg', 'max', 'median', 'min', 'sum'],
					type: 'string',
				},
				nested: {
					$ref: '#/definitions/NestedSort',
				},
				order: {
					$ref: '#/definitions/SortOrder',
				},
			},
			type: ['string', 'object'],
		},
		SortOrder: {
			enum: ['asc', 'desc'],
			type: 'string',
		},
		Sorts: {
			properties: {
				_geo_distance: {
					allOf: [
						{
							$ref: '#/definitions/Sort',
						},
					],
					properties: {
						distance_type: {
							enum: ['arc', 'plane'],
							type: 'string',
						},
						field: {
							type: 'string',
						},
						locations: {
							items: {
								$ref: '#/definitions/Geopoint',
							},
							type: 'array',
						},
						unit: {
							enum: [
								'cm',
								'ft',
								'in',
								'km',
								'm',
								'mi',
								'mm',
								'yd',
							],
							type: 'string',
						},
					},
					required: ['field', 'locations'],
					type: 'object',
				},
				_score: {
					$ref: '#/definitions/Sort',
				},
				_script: {
					allOf: [
						{
							$ref: '#/definitions/Sort',
						},
					],
					properties: {
						script: {
							$ref: '#/definitions/Script',
						},
						type: {
							enum: ['number', 'string'],
							type: 'string',
						},
					},
					required: ['script', 'type'],
					type: 'object',
				},
			},
			type: 'object',
		},
	},
	properties: {
		sorts: {
			items: {
				$ref: '#/definitions/Sorts',
			},
			type: 'array',
		},
	},
	type: 'object',
};
