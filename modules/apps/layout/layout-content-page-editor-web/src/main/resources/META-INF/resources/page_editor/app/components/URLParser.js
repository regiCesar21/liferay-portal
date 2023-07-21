/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect} from 'react';

import switchSidebarPanel from '../actions/switchSidebarPanel';
import {HIGHLIGHTED_COMMENT_ID_KEY} from '../config/constants/highlightedCommentIdKey';
import {useDispatch, useSelector} from '../store/index';
import {useSelectItem} from './Controls';

export default function URLParser() {
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const layoutData = useSelector((state) => state.layoutData);
	const dispatch = useDispatch();
	const selectItem = useSelectItem();

	const selectFragment = useCallback(
		(messageId) => {
			const {fragmentEntryLinkId} = Object.values(
				fragmentEntryLinks
			).find((fragmentEntryLink) =>
				fragmentEntryLink.comments.find(
					(comment) =>
						comment.commentId === messageId ||
						comment.children.find(
							(childComment) =>
								childComment.commentId === messageId
						)
				)
			) || {fragmentEntryLinkId: null};

			const {itemId} = Object.values(layoutData.items).find(
				(item) =>
					item.config.fragmentEntryLinkId === fragmentEntryLinkId
			) || {itemId: null};

			if (itemId) {
				selectItem(itemId);

				dispatch(
					switchSidebarPanel({
						sidebarOpen: true,
						sidebarPanelId: 'comments',
					})
				);
			}
		},
		[dispatch, fragmentEntryLinks, layoutData.items, selectItem]
	);

	useEffect(() => {
		const url = new URL(window.location.href);

		if (url.searchParams.has('messageId')) {
			window.sessionStorage.setItem(
				HIGHLIGHTED_COMMENT_ID_KEY,
				url.searchParams.get('messageId')
			);

			selectFragment(url.searchParams.get('messageId'));
			url.searchParams.delete('messageId');

			let skipLoadPopstate;

			if (Liferay.SPA && Liferay.SPA.app) {
				skipLoadPopstate = Liferay.SPA.app.skipLoadPopstate;
				Liferay.SPA.app.skipLoadPopstate = true;
			}

			history.replaceState(null, document.head.title, url.href);

			requestAnimationFrame(() => {
				if (
					Liferay.SPA &&
					Liferay.SPA.app &&
					typeof skipLoadPopstate === 'boolean'
				) {
					Liferay.SPA.app.skipLoadPopstate = skipLoadPopstate;
				}
			});
		}
	}, [selectFragment]);

	return null;
}
