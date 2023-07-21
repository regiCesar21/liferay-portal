/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TARGET_POSITION} from './constants/targetPosition';

/**
 * Returns the cursor vertical position (extracted from provided dnd monitor)
 * @param {number} clientOffsetY
 * @param {DOMRect} hoverBoundingRect
 * @param {number} elevationBorderSize
 * @return {Array} Returns a tuple with targetPositionWithMiddle and
 *  targetPositionWithoutMiddle
 */
export default function getTargetPosition(
	clientOffsetY,
	hoverBoundingRect,
	elevationBorderSize
) {
	const hoverMiddleY = hoverBoundingRect.top + hoverBoundingRect.height / 2;

	const targetPositionWithoutMiddle =
		clientOffsetY < hoverMiddleY
			? TARGET_POSITION.TOP
			: TARGET_POSITION.BOTTOM;

	const targetPositionWithMiddle =
		clientOffsetY < hoverBoundingRect.bottom - elevationBorderSize &&
		clientOffsetY > hoverBoundingRect.top + elevationBorderSize
			? TARGET_POSITION.MIDDLE
			: targetPositionWithoutMiddle;

	return [targetPositionWithMiddle, targetPositionWithoutMiddle];
}
