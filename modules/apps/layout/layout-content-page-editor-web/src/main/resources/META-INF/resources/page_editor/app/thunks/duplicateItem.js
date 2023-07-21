/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import duplicateItemAction from '../actions/duplicateItem';
import FragmentService from '../services/FragmentService';

export default function duplicateItem({
	itemId,
	segmentsExperienceId,
	selectItem = () => {},
}) {
	return (dispatch) => {
		FragmentService.duplicateItem({
			itemId,
			onNetworkStatus: dispatch,
			segmentsExperienceId,
		}).then(
			({duplicatedFragmentEntryLinks, duplicatedItemId, layoutData}) => {
				dispatch(
					duplicateItemAction({
						addedFragmentEntryLinks: duplicatedFragmentEntryLinks,
						itemId: duplicatedItemId,
						layoutData,
					})
				);

				if (duplicatedItemId) {
					selectItem(duplicatedItemId);
				}
			}
		);
	};
}
