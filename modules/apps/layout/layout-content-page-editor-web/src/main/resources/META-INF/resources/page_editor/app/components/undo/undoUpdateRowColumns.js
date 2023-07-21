/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateRowColumns from '../../thunks/updateRowColumns';

function undoAction({action}) {
	const {itemId, numberOfColumns, segmentsExperienceId} = action;

	return updateRowColumns({
		itemId,
		numberOfColumns,
		segmentsExperienceId,
	});
}

function getDerivedStateForUndo({action, state}) {
	const {itemId} = action;
	const {layoutData} = state;

	const config = layoutData.items[itemId]?.config ?? {};

	return {
		itemId,
		numberOfColumns: config.numberOfColumns,
	};
}

export {undoAction, getDerivedStateForUndo};
