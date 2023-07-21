/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {act, cleanup, queryByText, render} from '@testing-library/react';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {
	ControlsProvider,
	useSelectItem,
} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/Controls';
import {EditableProcessorContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/fragment-content/EditableProcessorContext';
import FragmentWithControls from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout-data-items/FragmentWithControls';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {StoreAPIContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/store';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			frontendTokens: {},
		},
	})
);

const renderFragment = ({
	activeItemId = 'fragment',
	hasUpdatePermissions = true,
	lockedExperience = false,
} = {}) => {
	const fragmentEntryLink = {
		editableValues: {},
		fragmentEntryLinkId: 'fragmentEntryLink',
	};

	const fragment = {
		children: [],
		config: {
			fragmentEntryLinkId: fragmentEntryLink.fragmentEntryLinkId,
			styles: {},
		},
		itemId: 'fragment',
		parentId: null,
		type: LAYOUT_DATA_ITEM_TYPES.fragment,
	};

	const layoutData = {
		items: {fragment},
	};

	const AutoSelect = () => {
		useSelectItem()(activeItemId);

		return null;
	};

	return render(
		<DndProvider backend={HTML5Backend}>
			<ControlsProvider>
				<StoreAPIContextProvider
					getState={() => ({
						fragmentEntryLinks: {
							[fragmentEntryLink.fragmentEntryLinkId]: fragmentEntryLink,
						},
						permissions: {
							LOCKED_SEGMENTS_EXPERIMENT: lockedExperience,
							UPDATE: hasUpdatePermissions,
						},
						selectedViewportSize: VIEWPORT_SIZES.desktop,
					})}
				>
					<EditableProcessorContextProvider>
						<AutoSelect />
						<FragmentWithControls
							item={fragment}
							layoutData={layoutData}
						/>
					</EditableProcessorContextProvider>
				</StoreAPIContextProvider>
			</ControlsProvider>
		</DndProvider>
	);
};

describe('FragmentWithControls', () => {
	afterEach(cleanup);

	it('does not allow deleting or duplicating the fragment if user has no permissions', async () => {
		await act(async () => {
			renderFragment({hasUpdatePermissions: false});
		});

		expect(queryByText(document.body, 'delete')).not.toBeInTheDocument();
		expect(queryByText(document.body, 'duplicate')).not.toBeInTheDocument();
	});
});
