/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import ItemConfiguration from './ItemConfiguration';
import StructureTree from './StructureTree';

export default function PageStructureSidebar() {
	return (
		<div className="page-editor__page-structure">
			<SidebarPanelHeader>
				{Liferay.Language.get('selection')}
			</SidebarPanelHeader>

			<div className="page-editor__page-structure__content">
				<StructureTree />
				<ItemConfiguration />
			</div>
		</div>
	);
}
