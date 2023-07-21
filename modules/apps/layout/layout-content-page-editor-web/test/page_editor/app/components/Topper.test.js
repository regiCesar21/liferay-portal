/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {ControlsProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/Controls';
import Topper from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/Topper';
import Row from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout-data-items/Row';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {StoreAPIContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/store';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			frontendTokens: {},
		},
	})
);

const renderTopper = ({
	hasUpdatePermissions = true,
	lockedExperience = false,
} = {}) => {
	const row = {
		children: [],
		config: {
			styles: {},
		},
		itemId: 'row',
		parentId: null,
		type: LAYOUT_DATA_ITEM_TYPES.row,
	};

	const layoutData = {
		items: {row},
	};

	return render(
		<DndProvider backend={HTML5Backend}>
			<ControlsProvider>
				<StoreAPIContextProvider
					getState={() => ({
						layoutData,
						permissions: {
							LOCKED_SEGMENTS_EXPERIMENT: lockedExperience,
							UPDATE: hasUpdatePermissions,
						},
						selectedViewportSize: VIEWPORT_SIZES.desktop,
					})}
				>
					<Topper item={row} layoutData={layoutData}>
						<Row item={row} layoutData={layoutData}></Row>
					</Topper>
				</StoreAPIContextProvider>
			</ControlsProvider>
		</DndProvider>
	);
};

describe('Topper', () => {
	afterEach(cleanup);

	it('does not render Topper if user has no permissions', () => {
		const {baseElement} = renderTopper({hasUpdatePermissions: false});

		expect(baseElement.querySelector('.page-editor__topper')).toBe(null);
	});

	it('renders Topper if user has permissions', () => {
		const {baseElement} = renderTopper({});

		expect(
			baseElement.querySelector('.page-editor__topper')
		).toBeInTheDocument();
	});
});
