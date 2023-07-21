/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React, {useContext} from 'react';

import ToggleSwitch from '../../../../components/toggle-switch/ToggleSwitch.es';
import EditAppContext, {
	ADD_DEPLOYMENT,
	REMOVE_DEPLOYMENT,
} from '../EditAppContext.es';

export default ({
	deploymentType,
	tip = () => null,
	settings = () => null,
	subtitle,
	title,
}) => {
	const {
		dispatch,
		state: {
			app: {appDeployments},
		},
	} = useContext(EditAppContext);

	const checked = appDeployments.some(
		(appDeployment) => appDeployment.type === deploymentType
	);

	return (
		<>
			<ClayLayout.ContentRow className="justify-content-between mb-3 pl-4 pr-4">
				<ClayLayout.ContentCol>
					<ClayLayout.ContentSection containerElement="section">
						<h3>
							{title}

							{tip()}
						</h3>

						<p className="list-group-subtext">
							<small>{subtitle}</small>
						</p>
					</ClayLayout.ContentSection>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol className="right">
					<ToggleSwitch
						checked={checked}
						onChange={(checked) => {
							if (checked) {
								dispatch({
									deploymentType,
									type: ADD_DEPLOYMENT,
								});
							}
							else {
								dispatch({
									deploymentType,
									type: REMOVE_DEPLOYMENT,
								});
							}
						}}
					/>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			{checked && settings()}
		</>
	);
};
