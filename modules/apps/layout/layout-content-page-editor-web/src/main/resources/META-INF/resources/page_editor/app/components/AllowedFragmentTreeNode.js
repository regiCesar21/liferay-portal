/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import React from 'react';

function hasSomeChildrenSelected(node) {
	return node.children.some(
		(children) => children.selected || hasSomeChildrenSelected(children)
	);
}

export default function AllowedFragmentTreeNode({node}) {
	return (
		<div className="lfr-treeview-label mt-2">
			<ClayCheckbox
				aria-label={node.name}
				checked={node.selected}
				indeterminate={!node.selected && hasSomeChildrenSelected(node)}
				label={node.name}
				onChange={() => {}}
				onDoubleClick={(event) => {
					event.stopPropagation();
				}}
			/>
		</div>
	);
}
