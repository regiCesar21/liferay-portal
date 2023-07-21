/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo, useState} from 'react';

import {useWidgets} from '../../../app/components/WidgetsContext';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {useSelector} from '../../../app/store/index';
import SearchForm from '../../../common/components/SearchForm';
import SidebarPanelContent from '../../../common/components/SidebarPanelContent';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import SearchResultsPanel from './SearchResultsPanel';
import TabsPanel from './TabsPanel';

const BASIC_COMPONENT_COLLECTION = 'BASIC_COMPONENT';

const collectionFilter = (collections, searchValue) => {
	const searchValueLowerCase = searchValue.toLowerCase();

	const itemFilter = (item) =>
		item.label.toLowerCase().indexOf(searchValueLowerCase) !== -1;

	const hasChildren = (collection) => {
		if (collection.children?.length) {
			return true;
		}

		return collection.collections?.some(hasChildren) ?? false;
	};

	return collections
		.reduce((acc, collection) => {
			if (itemFilter(collection)) {
				return [...acc, collection];
			}
			else {
				const updateCollection = {
					...collection,
					children: collection.children.filter(itemFilter),
					...(collection.collections?.length && {
						collections: collectionFilter(
							collection.collections,
							searchValueLowerCase
						),
					}),
				};

				return [...acc, updateCollection];
			}
		}, [])
		.filter(hasChildren);
};

const normalizeWidget = (widget) => {
	return {
		data: {
			instanceable: widget.instanceable,
			portletId: widget.portletId,
			portletItemId: widget.portletItemId || null,
			used: widget.used,
		},
		disabled: !widget.instanceable && widget.used,
		icon: widget.instanceable ? 'cards2' : 'square-hole',
		itemId: widget.portletId,
		label: widget.title,
		portletItems: widget.portletItems?.length
			? widget.portletItems.map(normalizeWidget)
			: null,
		preview: '',
		type: LAYOUT_DATA_ITEM_TYPES.fragment,
	};
};

const normalizeCollections = (collection) => {
	const normalizedElement = {
		children: collection.portlets.map(normalizeWidget),
		collectionId: collection.path,
		label: collection.title,
	};

	if (collection.categories?.length) {
		normalizedElement.collections = collection.categories.map(
			normalizeCollections
		);
	}

	return normalizedElement;
};

const normalizeFragmentEntry = (fragmentEntry, collectionId) => {
	if (!fragmentEntry.fragmentEntryKey) {
		return fragmentEntry;
	}

	return {
		data: {
			fragmentEntryKey: fragmentEntry.fragmentEntryKey,
			groupId: fragmentEntry.groupId,
			type: fragmentEntry.type,
		},
		icon: fragmentEntry.icon,
		itemId: fragmentEntry.fragmentEntryKey,
		label: fragmentEntry.name,
		preview:
			collectionId !== BASIC_COMPONENT_COLLECTION
				? fragmentEntry.imagePreviewURL
				: null,
		type: LAYOUT_DATA_ITEM_TYPES.fragment,
	};
};

export default function FragmentsSidebar() {
	const fragments = useSelector((state) => state.fragments);
	const widgets = useWidgets();

	const [searchValue, setSearchValue] = useState('');

	const tabs = useMemo(
		() => [
			{
				collections: fragments.map((collection) => ({
					children: collection.fragmentEntries.map((fragmentEntry) =>
						normalizeFragmentEntry(
							fragmentEntry,
							collection.fragmentCollectionId
						)
					),
					collectionId: collection.fragmentCollectionId,
					label: collection.name,
				})),
				label: Liferay.Language.get('fragments'),
			},
			{
				collections: widgets.map((collection) =>
					normalizeCollections(collection)
				),
				label: Liferay.Language.get('widgets'),
			},
		],
		[fragments, widgets]
	);

	const filteredTabs = useMemo(
		() =>
			searchValue
				? tabs
						.map((tab) => ({
							...tab,
							collections: collectionFilter(
								tab.collections,
								searchValue
							),
						}))
						.filter((item) => item.collections.length)
				: tabs,
		[tabs, searchValue]
	);

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('fragments-and-widgets')}
			</SidebarPanelHeader>

			<SidebarPanelContent className="page-editor__sidebar__fragments-widgets-panel">
				<SearchForm onChange={setSearchValue} />
				{searchValue ? (
					<SearchResultsPanel filteredTabs={filteredTabs} />
				) : (
					<TabsPanel tabs={tabs} />
				)}
			</SidebarPanelContent>
		</>
	);
}
