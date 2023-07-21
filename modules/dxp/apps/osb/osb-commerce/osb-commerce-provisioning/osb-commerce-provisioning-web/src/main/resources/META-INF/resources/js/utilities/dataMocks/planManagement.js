/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const planManagementMock = {
	activePlan: {
		cancelPlanURL: 'http://someUrl.com',
		currency: '$',
		endDate: new Date().toDateString(),
		planName: 'Liferay Commerce Basic',
		planPrice: '20.000',
		recurrence: 'year',
		startDate: new Date().toDateString(),
		switchBillingURL: 'http://someUrl.com',
	},

	planFeatures: {
		activeFeatures: [
			{
				description:
					'This is an amazing description of the above Cool Feature',
				id: `feature-${Math.floor(Math.random() * Math.floor(1000))}`,
				name: 'Some Cool Feature',
				toggleURL: 'http://someToggleURL.com',
			},
			{
				description:
					'This is an amazing description of the above Cool Feature',
				id: `feature-${Math.floor(Math.random() * Math.floor(1000))}`,
				name: 'Some Cool Feature',
				toggleURL: 'http://someToggleURL.com',
			},
			{
				description:
					'This is an amazing description of the above Cool Feature',
				id: `feature-${Math.floor(Math.random() * Math.floor(1000))}`,
				name: 'Some Cool Feature',
				toggleURL: 'http://someToggleURL.com',
			},
			{
				description:
					'This is an amazing description of the above Cool Feature',
				id: `feature-${Math.floor(Math.random() * Math.floor(1000))}`,
				name: 'Some Cool Feature',
				toggleURL: 'http://someToggleURL.com',
			},
			{
				description:
					'This is an amazing description of the above Cool Feature',
				id: `feature-${Math.floor(Math.random() * Math.floor(1000))}`,
				name: 'Some Cool Feature',
				toggleURL: 'http://someToggleURL.com',
			},
		],
		inactiveFeatures: [
			{
				description:
					'This is an amazing description of the above Cool Feature',
				id: `feature-${Math.floor(Math.random() * Math.floor(1000))}`,
				name: 'Some Cool Feature',
				toggleURL: 'http://someToggleURL.com',
			},
			{
				description:
					'This is an amazing description of the above Cool Feature',
				id: `feature-${Math.floor(Math.random() * Math.floor(1000))}`,
				name: 'Some Cool Feature',
				toggleURL: 'http://someToggleURL.com',
			},
			{
				description:
					'This is an amazing description of the above Cool Feature',
				id: `feature-${Math.floor(Math.random() * Math.floor(1000))}`,
				name: 'Some Cool Feature',
				toggleURL: 'http://someToggleURL.com',
			},
			{
				description:
					'This is an amazing description of the above Cool Feature',
				id: `feature-${Math.floor(Math.random() * Math.floor(1000))}`,
				name: 'Some Cool Feature',
				toggleURL: 'http://someToggleURL.com',
			},
		],
	},

	spritemap: '',
};

export default planManagementMock;
