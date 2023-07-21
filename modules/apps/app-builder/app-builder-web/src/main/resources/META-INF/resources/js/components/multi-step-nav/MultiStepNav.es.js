/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiStepNav from '@clayui/multi-step-nav';
import React from 'react';

export default ({currentStep, steps = []}) => {
	const isComplete = (index) =>
		index !== steps.length - 1 && currentStep > index;

	return (
		<ClayMultiStepNav>
			{steps.map((label, index) => (
				<ClayMultiStepNav.Item
					active={currentStep === index}
					complete={isComplete(index)}
					expand={index < steps.length - 1}
					key={index}
				>
					<ClayMultiStepNav.Divider />

					<ClayMultiStepNav.Indicator
						complete={isComplete(index)}
						label={label}
					/>
				</ClayMultiStepNav.Item>
			))}
		</ClayMultiStepNav>
	);
};
