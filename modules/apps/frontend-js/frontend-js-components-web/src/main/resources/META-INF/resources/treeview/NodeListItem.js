/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useContext, useState} from 'react';

import NodeList from './NodeList';
import TreeviewContext from './TreeviewContext';
import useFocus from './useFocus';
import useKeyboardNavigation from './useKeyboardNavigation';

export default function NodeListItem({NodeComponent, node}) {
	const {dispatch, state} = useContext(TreeviewContext);

	const {onLoadMore} = state;

	const focusable = useFocus(node.id);

	const handleKeyDown = useKeyboardNavigation(node.id);

	const children = node.children || [];

	const nodeListItemClassNames = classNames('lfr-treeview-node-list-item', {
		'with-children': children.length > 0,
	});

	const childrenId = `node-list-item-${node.id}-children`;

	const symbol = node.expanded ? 'hr' : 'plus';

	const [hasMoreChildren, setHasMoreChildren] = useState(node.paginated);

	const loadMoreItems = useCallback(() => {
		onLoadMore(node)
			.then((response) => {
				if (response.items) {
					const items = response.items;

					const alreadyExistingIds = node.children.map(
						(item) => item.id
					);
					const nodesToInsert = Object.values(items).filter(
						(item) => !alreadyExistingIds.includes(item.id)
					);

					if (nodesToInsert) {
						dispatch({
							nodeId: node.id,
							nodes: nodesToInsert,
							type: 'INSERT_NODES',
						});
					}

					if (!response.cursor) {
						setHasMoreChildren(false);
					}
				}
			})
			.catch((error) => {
				console.error(error);
			});
	}, [dispatch, node, onLoadMore]);

	const toggleExpanded = (event) => {
		if (node.children.length || node.hasChildren) {
			event.stopPropagation();

			if (
				onLoadMore &&
				node.hasChildren &&
				!node.expanded &&
				!node.children.length
			) {
				loadMoreItems(node, onLoadMore, dispatch);
			}

			dispatch({nodeId: node.id, type: 'TOGGLE_EXPANDED'});
		}
	};

	const toggleSelected = () => {
		dispatch({nodeId: node.id, type: 'TOGGLE_SELECT'});
	};

	return (
		<>
			<div
				className={nodeListItemClassNames}
				onBlur={() => {
					return;
				}}
				onClick={toggleSelected}
				onDoubleClick={toggleExpanded}
				onKeyDown={handleKeyDown}
				ref={focusable}
				role="treeitem"
				tabIndex="-1"
			>
				{children.length || node.hasChildren ? (
					<button
						aria-controls={childrenId}
						aria-expanded={node.expanded}
						aria-label={`${node.expanded ? 'Collapse' : 'Expand'} ${
							node.name
						}`}
						className="lfr-treeview-node-list-item__button"
						onClick={toggleExpanded}
						tabIndex="-1"
						type="button"
					>
						<ClayIcon
							className="lfr-treeview-node-list-item__button-icon"
							key={symbol}
							symbol={symbol}
						/>
					</button>
				) : null}

				<div className="lfr-treeview-node-list-item__node">
					<NodeComponent node={node} />
				</div>
			</div>

			{node.expanded && (
				<div
					className="lfr-treeview-node-list-item__children"
					id={childrenId}
				>
					<NodeList NodeComponent={NodeComponent} nodes={children} />

					{onLoadMore && hasMoreChildren ? (
						<ClayButton
							className="mb-5"
							displayType="secondary"
							onClick={() =>
								loadMoreItems(node, onLoadMore, dispatch)
							}
						>
							{Liferay.Language.get('load-more-results')}
						</ClayButton>
					) : null}
				</div>
			)}
		</>
	);
}

NodeListItem.propTypes = {
	NodeComponent: PropTypes.func.isRequired,
	node: PropTypes.shape({children: PropTypes.array}),
};
