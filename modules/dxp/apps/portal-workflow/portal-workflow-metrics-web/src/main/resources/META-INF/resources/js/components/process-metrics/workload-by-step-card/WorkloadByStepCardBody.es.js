/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Panel from '../../../shared/components/Panel.es';
import ContentView from '../../../shared/components/content-view/ContentView.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import PaginationBar from '../../../shared/components/pagination-bar/PaginationBar.es';
import {Table} from './WorkloadByStepCardTable.es';

const Body = ({items, page, pageSize, processId, totalCount}) => {
	const statesProps = {
		emptyProps: {
			className: 'py-6',
			hideAnimation: true,
			message: Liferay.Language.get('there-is-no-data-at-the-moment'),
			messageClassName: 'small',
		},
		errorProps: {
			actionButton: <ReloadButton />,
			className: 'py-6',
			hideAnimation: true,
			message: Liferay.Language.get('unable-to-retrieve-data'),
			messageClassName: 'small',
		},
		loadingProps: {className: 'py-6'},
	};

	return (
		<Panel.Body elementClasses="pb-3">
			<ContentView {...statesProps}>
				{totalCount > 0 && (
					<>
						<Body.Table items={items} processId={processId} />

						<PaginationBar
							page={page}
							pageSize={pageSize}
							totalCount={totalCount}
						/>
					</>
				)}
			</ContentView>
		</Panel.Body>
	);
};

Body.Table = Table;

export {Body};
