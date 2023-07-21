/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {Treeview} from 'frontend-js-components-web';
import React, {useCallback, useMemo, useState} from 'react';

const SelectFolder = ({itemSelectorSaveEvent, nodes}) => {
	const [filterQuery, setFilterQuery] = useState('');

	const nodesById = useMemo(() => {
		const result = {};

		function visit(node) {
			result[node.id] = node;

			if (node.children) {
				node.children.forEach(visit);
			}
		}

		nodes.forEach(visit);

		return result;
	}, [nodes]);

	const handleQueryChange = useCallback((event) => {
		const value = event.target.value;

		setFilterQuery(value);
	}, []);

	const handleSelectionChange = (selectedNodeIds) => {
		const node = nodesById[[...selectedNodeIds][0]];

		if (node) {
			Liferay.Util.getOpener().Liferay.fire(itemSelectorSaveEvent, {
				data: {
					folderId: node.id,
					folderName: node.name,
				},
			});
		}
	};

	return (
		<ClayLayout.ContainerFluid className="select-folder">
			<nav className="collapse-basic-search navbar navbar-default navbar-no-collapse">
				<ClayInput.Group className="basic-search">
					<ClayInput.GroupItem prepend>
						<ClayInput
							aria-label={Liferay.Language.get('search')}
							onChange={handleQueryChange}
							placeholder={`${Liferay.Language.get('search')}`}
							type="text"
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem append shrink>
						<ClayButtonWithIcon
							displayType="unstyled"
							symbol="search"
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</nav>

			<Treeview
				NodeComponent={Treeview.Card}
				filterQuery={filterQuery}
				nodes={nodes}
				onSelectedNodesChange={handleSelectionChange}
			/>
		</ClayLayout.ContainerFluid>
	);
};

export default SelectFolder;
