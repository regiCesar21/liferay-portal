/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {useActiveItemId, useSelectItem} from '../../../app/components/Controls';
import {HIGHLIGHTED_COMMENT_ID_KEY} from '../../../app/config/constants/highlightedCommentIdKey';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {useSelector} from '../../../app/store/index';
import FragmentComments from './FragmentComments';
import FragmentEntryLinksWithComments from './FragmentEntryLinksWithComments';

function getActiveFragmentEntryLink({
	fragmentEntryLinks,
	highlightMessageId,
	itemId,
	layoutData,
}) {
	if (highlightMessageId) {
		return Object.values(fragmentEntryLinks).find((fragmentEntryLink) =>
			fragmentEntryLink.comments.some(
				(comment) =>
					comment.commentId === highlightMessageId ||
					comment.children?.some(
						(childComment) =>
							childComment.commentId === highlightMessageId
					)
			)
		);
	}
	else {
		const item = layoutData.items[itemId];

		if (item) {
			if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
				return fragmentEntryLinks[item.config.fragmentEntryLinkId];
			}
			else if (item.parentId) {
				return getActiveFragmentEntryLink({
					fragmentEntryLinks,
					itemId: item.parentId,
					layoutData,
				});
			}
		}
	}

	return null;
}

export default function CommentsSidebar() {
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const layoutData = useSelector((state) => state.layoutData);

	const activeItemId = useActiveItemId();
	const selectItem = useSelectItem();

	const highlightMessageId = window.sessionStorage.getItem(
		HIGHLIGHTED_COMMENT_ID_KEY
	);

	const activeFragmentEntryLink = getActiveFragmentEntryLink({
		fragmentEntryLinks,
		highlightMessageId,
		itemId: activeItemId,
		layoutData,
	});

	if (highlightMessageId && activeFragmentEntryLink) {
		const activeItem = Object.values(layoutData.items).find(
			(item) =>
				item.config.fragmentEntryLinkId ===
				activeFragmentEntryLink.fragmentEntryLinkId
		);
		selectItem(activeItem.itemId);
	}

	return (
		<div
			onMouseDown={(event) =>
				event.nativeEvent.stopImmediatePropagation()
			}
		>
			{activeFragmentEntryLink ? (
				<FragmentComments fragmentEntryLink={activeFragmentEntryLink} />
			) : (
				<FragmentEntryLinksWithComments />
			)}
		</div>
	);
}
