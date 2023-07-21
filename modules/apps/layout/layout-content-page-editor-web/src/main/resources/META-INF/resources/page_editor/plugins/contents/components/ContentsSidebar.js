/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {useSelector} from '../../../app/store/index';
import SidebarPanelContent from '../../../common/components/SidebarPanelContent';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import NoPageContents from './NoPageContents';
import PageContents from './PageContents';

export default function ContentsSidebar() {
	const pageContents = useSelector((state) => state.pageContents);
	let view = <NoPageContents />;

	if (pageContents.length) {
		view = <PageContents pageContents={pageContents} />;
	}

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('contents')}
			</SidebarPanelHeader>

			<SidebarPanelContent padded={false}>{view}</SidebarPanelContent>
		</>
	);
}
