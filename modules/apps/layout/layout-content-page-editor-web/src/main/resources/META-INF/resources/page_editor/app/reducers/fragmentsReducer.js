/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ADD_FRAGMENT_COMPOSITION, INIT} from '../actions/types';
import {LAYOUT_DATA_ITEM_TYPE_LABELS} from '../config/constants/layoutDataItemTypeLabels';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

const CONTENT_DISPLAY_COLLECTION_ID = 'content-display';

const DEFAULT_CONTENT_DISPLAY_COLLECTION = {
	fragmentCollectionId: 'collection-display',
	fragmentEntries: [],
	name: Liferay.Language.get('collection-display'),
};

export default function fragmentsReducer(fragments = [], action) {
	switch (action.type) {
		case ADD_FRAGMENT_COMPOSITION: {
			const composition = action.fragmentComposition;
			const existingCollection = fragments.find(
				(collection) =>
					collection.fragmentCollectionId ===
					composition.fragmentCollectionId
			);

			const newCollection = existingCollection
				? {
						...existingCollection,
						fragmentEntries: [
							...existingCollection.fragmentEntries,
							composition,
						],
				  }
				: {
						fragmentCollectionId: composition.fragmentCollectionId,
						fragmentEntries: [composition],
						name: composition.fragmentCollectionName,
				  };

			return [
				...fragments.filter(
					(collection) =>
						collection.fragmentCollectionId !==
						newCollection.fragmentCollectionId
				),

				newCollection,
			];
		}

		case INIT: {
			const contentDisplayCollection = fragments.find(
				(fragment) =>
					fragment.fragmentCollectionId ===
					CONTENT_DISPLAY_COLLECTION_ID
			);

			const newFragments = fragments.filter(
				(fragment) =>
					fragment.fragmentCollectionId !==
					CONTENT_DISPLAY_COLLECTION_ID
			);

			newFragments.unshift({
				fragmentCollectionId: 'layout-elements',
				fragmentEntries: [
					{
						data: {
							itemType: LAYOUT_DATA_ITEM_TYPES.container,
						},
						icon: 'container',
						itemId: 'container',
						label: LAYOUT_DATA_ITEM_TYPE_LABELS.container,
						type: 'container',
					},
					{
						data: {
							itemType: LAYOUT_DATA_ITEM_TYPES.row,
						},
						icon: 'table',
						itemId: 'row',
						label: LAYOUT_DATA_ITEM_TYPE_LABELS.row,
						type: 'row',
					},
				],
				name: Liferay.Language.get('layout-elements'),
			});

			newFragments.splice(2, 0, {
				...(contentDisplayCollection ||
					DEFAULT_CONTENT_DISPLAY_COLLECTION),

				fragmentEntries: [
					...(
						contentDisplayCollection ||
						DEFAULT_CONTENT_DISPLAY_COLLECTION
					).fragmentEntries,

					{
						data: {
							itemType: LAYOUT_DATA_ITEM_TYPES.collection,
						},
						icon: 'list',
						itemId: 'collection-display',
						label: Liferay.Language.get('collection-display'),
						type: LAYOUT_DATA_ITEM_TYPES.collection,
					},
				],
			});

			return newFragments;
		}

		default:
			return fragments;
	}
}
