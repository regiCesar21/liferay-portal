/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addFragmentEntryLinks from '../actions/addFragmentEntryLinks';
import {FRAGMENT_TYPES} from '../config/constants/fragmentTypes';
import FragmentService from '../services/FragmentService';

export default function addFragment({
	fragmentEntryKey,
	groupId,
	parentItemId,
	position,
	selectItem = () => {},
	store,
	type,
}) {
	return (dispatch) => {
		const {segmentsExperienceId} = store;

		const params = {
			fragmentEntryKey,
			groupId,
			onNetworkStatus: dispatch,
			parentItemId,
			position,
			segmentsExperienceId,
			type,
		};

		const updateState = (fragmentEntryLinks, layoutData, itemId) => {
			dispatch(
				addFragmentEntryLinks({
					addedItemId: itemId,
					fragmentEntryLinks,
					layoutData,
				})
			);

			selectItem(itemId);
		};

		if (type === FRAGMENT_TYPES.composition) {
			FragmentService.addFragmentEntryLinks(params).then(
				({addedItemId, fragmentEntryLinks, layoutData}) => {
					updateState(
						Object.values(fragmentEntryLinks),
						layoutData,
						addedItemId
					);
				}
			);
		}
		else {
			FragmentService.addFragmentEntryLink(params).then(
				({addedItemId, fragmentEntryLink, layoutData}) => {
					updateState([fragmentEntryLink], layoutData, addedItemId);
				}
			);
		}
	};
}
