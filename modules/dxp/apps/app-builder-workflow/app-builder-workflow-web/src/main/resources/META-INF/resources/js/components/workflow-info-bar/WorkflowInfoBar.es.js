/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {concatValues} from 'app-builder-web/js/utils/utils.es';
import classNames from 'classnames';
import React from 'react';

import '../../../css/WorkflowInfoBar.scss';

export default function WorkflowInfo({
	assignees = [],
	appVersion,
	className,
	completed,
	hideColumns = [],
	taskNames = [],
	tasks = [],
}) {
	const emptyValue = '--';

	let assignee = assignees[0]?.name || emptyValue;

	const status = completed ? (
		<ClayLabel displayType="success">
			{Liferay.Language.get('completed')}
		</ClayLabel>
	) : (
		<ClayLabel displayType="info">
			{Liferay.Language.get('pending')}
		</ClayLabel>
	);

	const stepName = taskNames[0] || emptyValue;

	if (assignees[0]?.id === -1) {
		const {appWorkflowRoleAssignments: roles = []} =
			tasks.find(({name}) => name === stepName) || {};

		const roleNames = roles.map(({roleName}) => roleName);

		assignee = roleNames.length ? concatValues(roleNames) : emptyValue;
	}

	const tooltipProps = {
		'data-tooltip-align': 'bottom',
		'data-tooltip-delay': '0',
	};

	const items = [
		{
			label: Liferay.Language.get('status'),
			show: !hideColumns.includes('status'),
			value: status,
		},
		{
			label: Liferay.Language.get('step'),
			show: !hideColumns.includes('step'),
			value: stepName,
		},
		{
			label: Liferay.Language.get('assignee'),
			show: !hideColumns.includes('assignee'),
			tooltip: {
				title: assignee,
				...tooltipProps,
			},
			value: assignee,
		},
		{
			label: Liferay.Language.get('version'),
			show: !hideColumns.includes('status'),
			tooltip: {
				title: Liferay.Language.get('app-version'),
				...tooltipProps,
			},
			value: appVersion ?? '1.0',
		},
	];

	return (
		<ClayTooltipProvider>
			<div className={classNames('workflow-info-bar', className)}>
				{items.map(
					({label, show, tooltip = {}, value}, index) =>
						show && (
							<div className="info-item" key={index} {...tooltip}>
								<span className="font-weight-bold text-secondary">
									{`${label}: `}
								</span>

								{value}
							</div>
						)
				)}
			</div>
		</ClayTooltipProvider>
	);
}
