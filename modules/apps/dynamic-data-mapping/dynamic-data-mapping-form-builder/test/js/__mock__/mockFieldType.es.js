/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default {
	dataSourceType: 'manual',
	description: 'Single line or multiline text area.',
	group: 'basic',
	icon: 'text',
	initialConfig_: {
		locale: 'en_US',
	},
	label: 'Text Field',
	name: 'TextField',
	required: true,
	settingsContext: {
		pages: [
			{
				rows: [
					{
						columns: [
							{
								fields: [
									{
										fieldName: 'label',
										localizable: true,
										localizedValue: {
											en_US: 'Text Field',
										},
										type: 'text',
										value: 'Text Field',
										visible: true,
									},
									{
										fieldName: 'name',
										value: 'TextField',
										visible: true,
									},
									{
										fieldName: 'required',
										value: true,
										visible: true,
									},
									{
										fieldName: 'showLabel',
										value: false,
										visible: true,
									},
								],
							},
						],
					},
				],
			},
		],
	},
	showLabel: true,
	type: 'text',
};
