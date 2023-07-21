/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getLayoutDataItemLabel from '../../utils/getLayoutDataItemLabel';

/**
 * Obtain the name associated to the undo action,
 * for those cases where there is not name associated, returns null
 *
 * @param {object} options
 * @param {object} options.action
 * @param {object} options.state
 * @return {string|null}
 */
export function getItemNameFromAction({action, state}) {
	const fragmentEntryLinks = action.fragmentEntryLinks
		? Object.values(action.fragmentEntryLinks).reduce(
				(acc, fragmentEntryLink) => {
					acc[
						fragmentEntryLink.fragmentEntryLinkId
					] = fragmentEntryLink;

					return acc;
				},
				{}
		  )
		: state.fragmentEntryLinks;

	const item =
		state.layoutData?.items[action.itemId] ||
		action.layoutData?.items[action.itemId] ||
		Object.values(state.layoutData?.items ?? {}).find(
			(item) =>
				item.config.fragmentEntryLinkId === action.fragmentEntryLinkId
		) ||
		Object.values(action.layoutData?.items ?? {}).find(
			(item) =>
				item.config.fragmentEntryLinkId === action.fragmentEntryLinkId
		);

	if (!item) {
		return null;
	}

	return getLayoutDataItemLabel(item, fragmentEntryLinks);
}
