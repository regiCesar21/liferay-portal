/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {act, cleanup, getByText, render} from '@testing-library/react';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {CollectionItemWithControls} from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout-data-items';
import Collection from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout-data-items/Collection';
import CollectionService from '../../../../src/main/resources/META-INF/resources/page_editor/app/services/CollectionService';
import {StoreAPIContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/store';
import {DragAndDropContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/dragAndDrop/useDragAndDrop';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/CollectionService',
	() => ({
		getCollectionField: jest.fn(),
		getCollectionMappingFields: jest.fn(() =>
			Promise.resolve([
				{key: 'field-1', label: 'Field 1', type: 'text'},
				{key: 'field-2', label: 'Field 2', type: 'text'},
			])
		),
	})
);

function renderCollection(itemConfig = {}) {
	const state = {
		permissions: {
			UPDATE: true,
			UPDATE_LAYOUT_CONTENT: true,
		},
	};

	const defaultConfig = {
		numberOfColumns: 1,
		numberOfItems: 5,
	};

	return render(
		<DndProvider backend={HTML5Backend}>
			<StoreAPIContextProvider dispatch={() => {}} getState={() => state}>
				<DragAndDropContextProvider
					layoutData={{
						items: [],
					}}
				>
					<Collection
						item={{
							config: {...defaultConfig, ...itemConfig},
							itemId: 'collection',
							parentId: '',
							type: '',
						}}
					>
						<CollectionItemWithControls
							item={{
								children: [],
								itemId: 'collection-item',
								parentId: 'collection',
								type: 'collection-item',
							}}
							layoutData={{}}
						></CollectionItemWithControls>
					</Collection>
				</DragAndDropContextProvider>
			</StoreAPIContextProvider>
		</DndProvider>,
		{
			baseElement: document.body,
		}
	);
}

describe('Collection', () => {
	afterEach(() => {
		cleanup();
	});

	it('renders not collection message when no collection is selected', async () => {
		CollectionService.getCollectionField.mockImplementation(() =>
			Promise.resolve()
		);

		await act(async () => {
			renderCollection();
		});

		expect(
			getByText(document.body, 'no-collection-selected-yet')
		).toBeInTheDocument();
	});

	it('renders an item when the collection is empty', async () => {
		CollectionService.getCollectionField.mockImplementation(() =>
			Promise.resolve({
				items: [],
				length: 0,
			})
		);

		await act(async () => {
			renderCollection({
				collection: {
					itemSubtype: 'CollectionItemSubtype',
					itemType: 'CollectionItemType',
				},
			});
		});

		expect(getByText(document.body, 'title')).toBeInTheDocument();
	});

	it('renders empty collection items', async () => {
		const items = [
			{content: 'Item 1 Content', title: 'Item 1 Title'},
			{content: 'Item 2 Content', title: 'Item 2 Title'},
		];

		CollectionService.getCollectionField.mockImplementation(() =>
			Promise.resolve({
				items,
				length: 2,
			})
		);

		await act(async () => {
			renderCollection({
				collection: {
					itemSubtype: 'CollectionItemSubtype',
					itemType: 'CollectionItemType',
				},
			});
		});

		items.forEach((item) =>
			expect(getByText(document.body, item.title)).toBeInTheDocument()
		);
	});
});
