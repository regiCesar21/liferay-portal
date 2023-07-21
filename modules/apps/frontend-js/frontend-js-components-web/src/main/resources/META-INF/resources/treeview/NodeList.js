/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import NodeListItem from './NodeListItem';

export default function NodeList({
	NodeComponent,
	nodes,
	onBlur,
	onFocus,
	onMouseDown,
	role = 'group',
	tabIndex = -1,
}) {
	const rootNodeId = nodes[0] && nodes[0].id;

	if (!rootNodeId) {

		// All nodes have been filtered.

		return null;
	}

	return (
		<div
			className="lfr-treeview-node-list"
			onBlur={() => {
				if (onBlur) {
					onBlur();
				}
			}}
			onFocus={(event) => {
				if (onFocus) {
					onFocus(event);
				}
			}}
			onMouseDown={(event) => {
				if (onMouseDown) {
					onMouseDown(event);
				}
			}}
			role={role}
			tabIndex={tabIndex}
		>
			{nodes.map((node) => (
				<NodeListItem
					NodeComponent={NodeComponent}
					key={node.id}
					node={node}
				/>
			))}
		</div>
	);
}

NodeList.propTypes = {
	NodeComponent: PropTypes.func.isRequired,
	nodes: PropTypes.arrayOf(
		PropTypes.shape({
			children: PropTypes.array,
			id: PropTypes.string.isRequired,
			name: PropTypes.string.isRequired,
		})
	).isRequired,
	onBlur: PropTypes.func,
	onFocus: PropTypes.func,
	onMouseDown: PropTypes.func,
	tabIndex: PropTypes.number,
};
