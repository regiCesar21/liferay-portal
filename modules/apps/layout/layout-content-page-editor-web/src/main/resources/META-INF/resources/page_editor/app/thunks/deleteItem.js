/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import deleteItemAction from '../actions/deleteItem';
import updatePageContents from '../actions/updatePageContents';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import InfoItemService from '../services/InfoItemService';
import LayoutService from '../services/LayoutService';
import getFragmentEntryLinkIdsFromItemId from '../utils/getFragmentEntryLinkIdsFromItemId';

export default function deleteItem({itemId, selectItem = () => {}, store}) {
	return (dispatch) => {
		const {fragmentEntryLinks, layoutData, segmentsExperienceId} = store;

		return markItemForDeletion({
			fragmentEntryLinks,
			itemId,
			layoutData,
			onNetworkStatus: dispatch,
			segmentsExperienceId,
		})
			.then(({portletIds = [], layoutData}) => {
				selectItem(null);

				const fragmentEntryLinkIds = getFragmentEntryLinkIdsFromItemId({
					itemId,
					layoutData,
				});

				dispatch(
					deleteItemAction({
						fragmentEntryLinkIds,
						itemId,
						layoutData,
						portletIds,
					})
				);
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
	};
}

function markItemForDeletion({
	fragmentEntryLinks,
	itemId,
	layoutData,
	onNetworkStatus: dispatch,
	segmentsExperienceId,
}) {
	const portletIds = findPortletIds(itemId, layoutData, fragmentEntryLinks);

	return LayoutService.markItemForDeletion({
		itemId,
		onNetworkStatus: dispatch,
		portletIds,
		segmentsExperienceId,
	}).then((response) => {
		return {...response, portletIds};
	});
}

function findPortletIds(itemId, layoutData, fragmentEntryLinks) {
	const item = layoutData.items[itemId];

	const {config = {}, children = []} = item;

	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.fragment &&
		config.fragmentEntryLinkId
	) {
		const {editableValues = {}} = fragmentEntryLinks[
			config.fragmentEntryLinkId
		];

		if (editableValues.portletId && !editableValues.instanceId) {
			return [editableValues.portletId];
		}
	}

	const deletedWidgets = [];

	children.forEach((itemId) => {
		deletedWidgets.push(
			...findPortletIds(itemId, layoutData, fragmentEntryLinks)
		);
	});

	return deletedWidgets;
}
