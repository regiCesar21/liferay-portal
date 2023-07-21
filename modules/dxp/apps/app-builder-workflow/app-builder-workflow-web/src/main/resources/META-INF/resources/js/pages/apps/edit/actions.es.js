/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getItem} from 'app-builder-web/js/utils/client.es';
import {getLocalizedValue} from 'app-builder-web/js/utils/lang.es';

import {getFormViewFields, validateSelectedFormViews} from './utils.es';

const PARAMS = {keywords: '', page: -1, pageSize: -1, sort: ''};

export function buildLocalizedItems(defaultLanguageId) {
	return (items) =>
		items.map((item) => ({
			...item,
			name: getLocalizedValue(defaultLanguageId, item.name),
		}));
}

export function getAssigneeRoles() {
	return getItem('/o/headless-admin-user/v1.0/roles').then(({items}) =>
		items.filter(({name}) => name !== 'Owner')
	);
}

export function getDataDefinition(dataDefinitionId) {
	return getItem(`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}`);
}

export function getFormViews(dataDefinitionId, defaultLanguageId) {
	return getItem(
		`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-layouts`,
		PARAMS
	)
		.then(getItems)
		.then(buildLocalizedItems(defaultLanguageId));
}

function getItems({items}) {
	return items;
}

export function getTableViews(dataDefinitionId, defaultLanguageId) {
	return getItem(
		`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-list-views`,
		PARAMS
	)
		.then(getItems)
		.then(buildLocalizedItems(defaultLanguageId));
}

export function populateConfigData([
	app,
	appWorkflow,
	assigneeRoles,
	dataObjects,
	formViews,
	tableViews,
]) {
	appWorkflow.appWorkflowTasks.forEach((task) => {
		task.appWorkflowDataLayoutLinks = task.appWorkflowDataLayoutLinks.map(
			(item) => {
				const {name, ...formView} = formViews.find(
					({id}) => id === item.dataLayoutId
				);

				return {...item, fields: getFormViewFields(formView), name};
			}
		);

		task.appWorkflowTransitions.sort(
			(actionA, actionB) => actionB.primary - actionA.primary
		);

		task.errors = {
			formViews: validateSelectedFormViews(
				task.appWorkflowDataLayoutLinks
			),
		};
	});

	const {appWorkflowStates = [], appWorkflowTasks = []} = appWorkflow;
	const initialState = appWorkflowStates.find(({initial}) => initial);
	const finalState = appWorkflowStates.find(({initial}) => !initial);
	const formView = formViews.find(({id}) => id === app.dataLayoutId);

	const config = {
		currentStep: initialState,
		dataObject: dataObjects.find(({id}) => id === app.dataDefinitionId),
		formView: {...formView, fields: getFormViewFields(formView)},
		listItems: {
			assigneeRoles,
			dataObjects,
			fetching: false,
			formViews,
			tableViews,
		},
		steps: [initialState, ...appWorkflowTasks, finalState],
		tableView: tableViews.find(({id}) => id === app.dataListViewId),
	};

	return [app, config];
}
