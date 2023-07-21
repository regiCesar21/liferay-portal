/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateItemConfigAction from '../actions/updateItemConfig';
import updatePageContents from '../actions/updatePageContents';
import InfoItemService from '../services/InfoItemService';
import LayoutService from '../services/LayoutService';

export default function updateItemConfig({
	itemConfig,
	itemId,
	segmentsExperienceId,
}) {
	return (dispatch) =>
		LayoutService.updateItemConfig({
			itemConfig,
			itemId,
			onNetworkStatus: dispatch,
			segmentsExperienceId,
		})
			.then((layoutData) => {
				dispatch(updateItemConfigAction({itemId, layoutData}));
			})
			.then(() => {
				InfoItemService.getPageContents({
					onNetworkStatus: dispatch,
				}).then((pageContents) => {
					dispatch(
						updatePageContents({
							pageContents,
						})
					);
				});
			});
}
