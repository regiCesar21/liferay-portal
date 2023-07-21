/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default [
	{
		description: 'Page Description',
		localizedDescription: {},
		localizedTitle: {
			en_US: 'Localized Title',
		},
		rows: [
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'radio',
								label: 'Radio Field',
								localizedValue: {
									en_US: 'Radio Field',
								},
								options: [
									{
										label: 'Option 1',
										value: 'option1',
									},
									{
										label: 'Option 2',
										value: 'option2',
									},
								],
								settingsContext: {
									pages: [
										{
											rows: [
												{
													columns: [
														{
															fields: [
																{
																	fieldName:
																		'label',
																	localizable: true,
																	localizedValue: {
																		en_US:
																			'Radio Field',
																	},
																	settingsContext: {
																		pages: [
																			{
																				rows: [
																					{
																						columns: [],
																					},
																				],
																			},
																		],
																	},
																},
																{
																	fieldName:
																		'name',
																},
																{
																	fieldName:
																		'required',
																},
																{
																	fieldName:
																		'type',
																},
																{
																	fieldName:
																		'validation',
																	value: {},
																},
															],
															size: 12,
														},
													],
												},
											],
										},
									],
								},
								type: 'radio',
							},
						],
						size: 3,
					},
					{
						fields: [],
						size: 9,
					},
				],
			},
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'text1',
								label: 'Text Field 1',
								options: [],
								required: true,
								settingsContext: {
									pages: [],
								},
								type: 'text',
							},
							{
								fieldName: 'text2',
								label: 'Text Field 2',
								options: [],
								required: true,
								settingsContext: {
									pages: [],
								},
								type: 'text',
							},
						],
						size: 4,
					},
					{
						fields: [
							{
								fieldName: 'select',
								label: 'Select Field',
								options: [
									{
										label: 'Option 1',
										value: 'option1',
									},
									{
										label: 'Option 2',
										value: 'option2',
									},
								],
								required: true,
								settingsContext: {
									pages: [],
								},
								type: 'select',
							},
						],
						size: 6,
					},
					{
						fields: [],
						size: 2,
					},
				],
			},
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'date',
								label: 'Date Field',
								options: [],
								required: true,
								settingsContext: {
									pages: [],
								},
								type: 'date',
							},
						],
						size: 12,
					},
				],
			},
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'numeric',
								label: 'Numeric Field',
								options: [],
								required: true,
								settingsContext: {
									pages: [],
								},
								type: 'numeric',
							},
						],
						size: 12,
					},
				],
			},
		],
		title: 'Untitled name',
	},
];
