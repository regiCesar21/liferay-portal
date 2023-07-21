/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const COLORS = {
	secondary: '#A7A9BC',
};

const COLUMNS = [
	{
		key: 'name',
		sortable: true,
		value: Liferay.Language.get('name'),
	},
	{
		key: 'type',
		value: Liferay.Language.get('deployed-as'),
	},
	{
		key: 'dateCreated',
		sortable: true,
		value: Liferay.Language.get('create-date'),
	},
	{
		asc: false,
		key: 'dateModified',
		sortable: true,
		value: Liferay.Language.get('modified-date'),
	},
	{
		key: 'status',
		value: Liferay.Language.get('status'),
	},
];

const DEPLOYMENT_ACTION = {
	deploy: Liferay.Language.get('deploy'),
	undeploy: Liferay.Language.get('undeploy'),
};

const DEPLOYMENT_TYPES = {
	productMenu: Liferay.Language.get('product-menu'),
	standalone: Liferay.Language.get('standalone'),
	widget: Liferay.Language.get('widget'),
};

const FILTER_NAMES = {
	author: [
		Liferay.Language.get('author'),
		Liferay.Language.get('filter-by-author'),
	],
	'deployment-type': [
		Liferay.Language.get('deployment-type'),
		Liferay.Language.get('filter-by-deployment-type'),
	],
	status: [
		Liferay.Language.get('status'),
		Liferay.Language.get('filter-by-status'),
	],
};

const STATUSES = {
	active: Liferay.Language.get('deployed'),
	inactive: Liferay.Language.get('undeployed'),
};

const FILTERS = [
	{
		items: [
			{label: DEPLOYMENT_TYPES.productMenu, value: 'productMenu'},
			{label: DEPLOYMENT_TYPES.standalone, value: 'standalone'},
			{label: DEPLOYMENT_TYPES.widget, value: 'widget'},
		],
		key: 'deploymentTypes',
		multiple: true,
		name: 'deployment-type',
	},
	{
		items: [
			{label: STATUSES.active, value: 'true'},
			{label: STATUSES.inactive, value: 'false'},
		],
		key: 'active',
		name: 'status',
	},
];

export {
	COLORS,
	COLUMNS,
	DEPLOYMENT_ACTION,
	DEPLOYMENT_TYPES,
	FILTERS,
	FILTER_NAMES,
	STATUSES,
};
