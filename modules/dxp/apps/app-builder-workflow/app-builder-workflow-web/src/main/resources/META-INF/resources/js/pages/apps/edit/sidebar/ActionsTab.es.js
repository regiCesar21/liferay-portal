/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React from 'react';

import {
	ADD_STEP_ACTION,
	REMOVE_STEP_ACTION,
	UPDATE_STEP_ACTION,
} from '../configReducer.es';

export default function ActionsTab({
	config: {currentStep, stepIndex},
	dispatchConfig,
}) {
	const {
		appWorkflowTransitions: [primaryAction = {}, secondaryAction] = [{}],
	} = currentStep;

	const onChangeActionName = ({target}, primary) => {
		dispatchConfig({
			name: target.value,
			primary,
			type: UPDATE_STEP_ACTION,
		});
	};

	return (
		<>
			<label
				className="ml-3 outline-text text-secondary"
				style={{marginTop: '16px'}}
			>
				{Liferay.Language.get('primary-action')}
			</label>

			<ClayForm.Group className="form-group-outlined mt-4">
				<label className="mt-3">
					{Liferay.Language.get('action-name')}
				</label>

				<ClayInput
					maxLength="50"
					onChange={(event) => onChangeActionName(event, true)}
					type="text"
					value={primaryAction.name}
				/>

				<label className="mt-3">
					{Liferay.Language.get('transition-to')}
				</label>

				<ClayButton
					className="clearfix disabled w-100"
					displayType="secondary"
				>
					<span className="float-left text-left text-truncate w90">
						{Liferay.Language.get('next-step')}
					</span>

					<ClayIcon
						className="dropdown-button-asset float-right"
						symbol="caret-bottom"
					/>
				</ClayButton>
			</ClayForm.Group>

			{stepIndex > 1 && !secondaryAction && (
				<ClayButton
					className="w-100"
					displayType="secondary"
					onClick={() => dispatchConfig({type: ADD_STEP_ACTION})}
				>
					{Liferay.Language.get('add-new-action')}
				</ClayButton>
			)}

			{secondaryAction && (
				<>
					<label className="ml-3 outline-text text-secondary">
						{Liferay.Language.get('secondary-action')}
					</label>

					<ClayForm.Group className="form-group-outlined mt-4 pb-2">
						<label className="mt-3">
							{Liferay.Language.get('action-name')}
						</label>

						<ClayInput
							maxLength="50"
							onChange={(event) =>
								onChangeActionName(event, false)
							}
							type="text"
							value={secondaryAction.name}
						/>

						<label className="mt-3">
							{Liferay.Language.get('transition-to')}
						</label>

						<ClayButton
							className="clearfix disabled w-100"
							displayType="secondary"
						>
							<span className="float-left text-left text-truncate w90">
								{Liferay.Language.get('previous-step')}
							</span>

							<ClayIcon
								className="dropdown-button-asset float-right"
								symbol="caret-bottom"
							/>
						</ClayButton>

						<div className="text-right">
							<ClayButton
								className="border-0 mt-2"
								displayType="secondary"
								onClick={() =>
									dispatchConfig({type: REMOVE_STEP_ACTION})
								}
								small
							>
								{Liferay.Language.get('remove')}
							</ClayButton>
						</div>
					</ClayForm.Group>
				</>
			)}
		</>
	);
}
