/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';

const ORDERED_VIEWPORT_SIZES = [
	VIEWPORT_SIZES.desktop,
	VIEWPORT_SIZES.tablet,
	VIEWPORT_SIZES.landscapeMobile,
	VIEWPORT_SIZES.portraitMobile,
];

export function getResponsiveConfig(config, viewportSize) {
	const viewportSizeIndex = ORDERED_VIEWPORT_SIZES.indexOf(viewportSize);

	let responsiveConfig = {};

	for (let i = 0; i <= viewportSizeIndex; i++) {
		const viewPortSizeConfig =
			ORDERED_VIEWPORT_SIZES[i] === VIEWPORT_SIZES.desktop
				? config
				: config[ORDERED_VIEWPORT_SIZES[i]];

		responsiveConfig = {
			...responsiveConfig,
			...viewPortSizeConfig,
			gutters: config.gutters,
			styles: {
				...responsiveConfig.styles,
				...viewPortSizeConfig.styles,
			},
		};
	}

	return responsiveConfig;
}
