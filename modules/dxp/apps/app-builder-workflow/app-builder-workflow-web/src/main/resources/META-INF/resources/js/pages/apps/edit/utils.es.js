/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isEqualObjects} from 'app-builder-web/js/utils/utils.es';

export function canDeployApp(app, config) {
	const isValidSteps = config.steps.every((step) => {
		const assigneeRoles = step.appWorkflowRoleAssignments || [{}];
		const duplicatedFields = step.errors?.formViews.duplicatedFields || [];
		const isValidFormViews = step.appWorkflowDataLayoutLinks?.every(
			({dataLayoutId}) => dataLayoutId
		);
		const transitions = step.appWorkflowTransitions || [];

		const isValidTransitionNames = transitions.every(
			({name}) => name.trim().length
		);

		return (
			assigneeRoles.length &&
			!duplicatedFields.length &&
			(isValidFormViews || step.initial !== undefined) &&
			isValidTransitionNames &&
			step.name.trim().length
		);
	});

	return (
		app.dataDefinitionId &&
		app.dataLayoutId &&
		app.dataListViewId &&
		app.appName?.trim().length &&
		isValidSteps
	);
}

export function getFormViewFields({dataLayoutPages = []}) {
	return dataLayoutPages.reduce(
		(fields, {dataLayoutRows}) => [
			...fields,
			...dataLayoutRows.reduce(
				(fields, {dataLayoutColumns}) => [
					...fields,
					...dataLayoutColumns.reduce(
						(fields, {fieldNames}) => [...fields, ...fieldNames],
						[]
					),
				],
				[]
			),
		],
		[]
	);
}

export function hasConfigBreakChanges({draftConfig, ...config}) {
	const props = ['dataObject', 'formView', 'steps'];

	return props
		.map((prop) => !isEqualObjects(draftConfig[prop], config[prop]))
		.some((isDifferent) => isDifferent);
}

export function validateSelectedFormViews(formViews = []) {
	const duplicatedFields = [];
	const errorIndexes = [];

	formViews.forEach(({fields}, index) => {
		formViews.forEach(({fields: nextFields}, nextIndex) => {
			const isNotRepeated =
				!errorIndexes.includes(index) ||
				!errorIndexes.includes(nextIndex);

			if (isNotRepeated && index !== nextIndex) {
				const duplicated = fields.filter((field) =>
					nextFields.includes(field)
				);

				if (duplicated.length) {
					duplicatedFields.push(
						...duplicated.filter(
							(field) => !duplicatedFields.includes(field)
						)
					);
					errorIndexes.push(
						...[index, nextIndex].filter(
							(errorIndex) => !errorIndexes.includes(errorIndex)
						)
					);
				}
			}
		});
	});

	return {duplicatedFields, errorIndexes};
}
