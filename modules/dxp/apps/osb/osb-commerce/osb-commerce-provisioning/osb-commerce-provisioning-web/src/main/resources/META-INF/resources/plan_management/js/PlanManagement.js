/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {getDataMock} from '../../js/utilities/index';
import ActivePlan from './views/ActivePlan';

function PlanManagement({mockData = false, ...props}) {
	const {activePlan, spritemap} = mockData
		? getDataMock(PlanManagement.name)
		: props;

	return (
		<>
			<ActivePlan {...activePlan} spritemap={spritemap} />
		</>
	);
}

export default PlanManagement;
