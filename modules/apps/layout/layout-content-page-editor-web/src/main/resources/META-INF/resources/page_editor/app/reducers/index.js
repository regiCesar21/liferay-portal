/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import baseReducer from './baseReducer';
import collectionsReducer from './collectionsReducer';
import defaultFragmentEntryLinksReducer from './defaultFragmentEntryLinksReducer';
import fragmentEntryLinksReducer from './fragmentEntryLinksReducer';
import fragmentsReducer from './fragmentsReducer';
import languageIdReducer from './languageIdReducer';
import layoutDataReducer from './layoutDataReducer';
import mappedInfoItemsReducer from './mappedInfoItemsReducer';
import masterLayoutReducer from './masterLayoutReducer';
import networkReducer from './networkReducer';
import pageContentsReducer from './pageContentsReducer';
import permissionsReducer from './permissionsReducer';
import selectedViewportSizeReducer from './selectedViewportSizeReducer';
import showResolvedCommentsReducer from './showResolvedCommentsReducer';
import sidebarReducer from './sidebarReducer';
import undoReducer from './undoReducer';

const combinedReducer = (state, action) =>
	Object.entries({
		collections: collectionsReducer,
		fragmentEntryLinks: fragmentEntryLinksReducer,
		fragments: fragmentsReducer,
		languageId: languageIdReducer,
		layoutData: layoutDataReducer,
		mappedInfoItems: mappedInfoItemsReducer,
		masterLayout: masterLayoutReducer,
		network: networkReducer,
		pageContents: pageContentsReducer,
		permissions: permissionsReducer,
		reducers: baseReducer,
		selectedViewportSize: selectedViewportSizeReducer,
		showResolvedComments: showResolvedCommentsReducer,
		sidebar: sidebarReducer,
	}).reduce(
		(nextState, [namespace, reducer]) => ({
			...nextState,
			[namespace]: reducer(nextState[namespace], action),
		}),
		state
	);

/**
 * Runs the base reducer plus any dynamically loaded reducers that have
 * been registered from plugins.
 */
export function reducer(state, action) {
	let nextState = undoReducer(state, action);
	nextState = defaultFragmentEntryLinksReducer(nextState, action);

	return [combinedReducer, ...Object.values(state.reducers || {})].reduce(
		(nextState, nextReducer) => {
			return nextReducer(nextState, action);
		},
		nextState
	);
}
