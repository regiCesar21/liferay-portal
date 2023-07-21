/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import EditAppContext from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import React, {useContext} from 'react';

import {
	ADD_STEP,
	REMOVE_STEP,
	REMOVE_STEP_EMPTY_FORM_VIEWS,
	UPDATE_STEP_INDEX,
} from '../configReducer.es';
import WorkflowStep from './WorkflowStep.es';

export default function WorkflowBuilder() {
	const {
		config: {dataObject, stepIndex, steps},
		dispatchConfig,
	} = useContext(EditAppContext);

	const badgeLabel = (stepIndex) => {
		if (stepIndex === 0) {
			return Liferay.Language.get('start');
		}
		else if (stepIndex === steps.length - 1) {
			return Liferay.Language.get('end');
		}

		return stepIndex;
	};

	const onClickStep = (index) => {
		if (index !== stepIndex) {
			if (steps[stepIndex].initial === undefined) {
				dispatchConfig({
					stepIndex,
					type: REMOVE_STEP_EMPTY_FORM_VIEWS,
				});
			}

			dispatchConfig({stepIndex: index, type: UPDATE_STEP_INDEX});
		}
	};

	const stepInfo = [
		[
			{
				...dataObject,
				label: Liferay.Language.get('data-object'),
			},
		],
		...steps
			.filter(({initial}) => initial === undefined)
			.map(({appWorkflowRoleAssignments = []}) =>
				appWorkflowRoleAssignments.length > 0
					? [
							{
								label: Liferay.Language.get('assignee'),
								name: appWorkflowRoleAssignments
									.map(({roleName}) => roleName)
									.reduce((acc, cur) => `${acc}, ${cur}`),
							},
					  ]
					: []
			),
	];

	return (
		<div className="app-builder-workflow-app__builder">
			{steps.map((step, index) => (
				<WorkflowStep
					actions={[
						{
							label: Liferay.Language.get('delete-step'),
							onClick: () =>
								dispatchConfig({
									stepIndex: index,
									type: REMOVE_STEP,
								}),
						},
					]}
					addStep={() =>
						dispatchConfig({stepIndex: index, type: ADD_STEP})
					}
					badgeLabel={badgeLabel(index)}
					{...step}
					key={index}
					onClick={() => onClickStep(index)}
					selected={stepIndex === index}
					stepInfo={
						index < steps.length - 1 && stepInfo[index]
							? stepInfo[index]
							: []
					}
				/>
			))}
		</div>
	);
}
