/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {Treeview} from 'frontend-js-components-web';
import {fetch, openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useState} from 'react';

function visit(nodes, callback) {
	nodes.forEach((node) => {
		callback(node);

		if (node.children) {
			visit(node.children, callback);
		}
	});
}

/**
 * SelectLayout
 *
 * This component shows a list of available layouts to select in expanded tree
 * and allows to filter them by searching.
 *
 * @review
 */

const SelectLayout = ({
	config,
	followURLOnTitleClick,
	groupId,
	itemSelectorSaveEvent,
	multiSelection,
	nodes,
	privateLayout,
}) => {
	const {loadMoreItemsURL, maxPageSize, namespace} = config;

	const [filterQuery, setFilterQuery] = useState();

	const handleSelectionChange = (selectedNodeIds, nodeMap) => {
		if (!selectedNodeIds.size) {
			return;
		}

		let data = [];

		if (nodeMap) {
			selectedNodeIds.forEach((selectedNodeId) => {
				const node = nodeMap[selectedNodeId];

				if (node) {
					data.push({
						groupId: node.groupId,
						id: node.id,
						layoutId: node.layoutId,
						name: node.value,
						privateLayout: node.privateLayout,
						value: node.url,
					});
				}
			});
		}
		else {
			visit(nodes, (node) => {
				if (selectedNodeIds.has(node.id)) {
					data.push({
						groupId: node.groupId,
						id: node.id,
						layoutId: node.layoutId,
						name: node.value,
						privateLayout: node.privateLayout,
						value: node.url,
					});
				}
			});
		}

		if (!multiSelection) {
			data = data[0];
		}

		if (followURLOnTitleClick) {
			Liferay.Util.getOpener().document.location.href = data.url;
		}
		else {
			Liferay.fire(itemSelectorSaveEvent, {
				data,
			});

			Liferay.Util.getOpener().Liferay.fire(itemSelectorSaveEvent, {
				data,
			});
		}
	};

	const empty = nodes.length === 0;

	const onLoadMore = useCallback(
		(item) => {
			if (!item.hasChildren) {
				return Promise.resolve({
					cursor: null,
					items: null,
				});
			}

			const cursor = item.children
				? Math.floor(item.children.length / maxPageSize)
				: 0;

			return fetch(loadMoreItemsURL, {
				body: Liferay.Util.objectToURLSearchParams({
					[`groupId`]: groupId,
					[`layoutUuid`]: item.id,
					[`parentLayoutId`]: item.layoutId,
					[`privateLayout`]: privateLayout,
					[`redirect`]:
						window.location.pathname + window.location.search,
					[`start`]: cursor * maxPageSize,
				}),
				method: 'post',
			})
				.then((response) => response.json())
				.then(({hasMoreElements, items: nextItems}) => ({
					cursor: hasMoreElements ? cursor + 1 : null,
					items: nextItems,
				}))
				.catch(() =>
					openToast({
						message: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
						title: Liferay.Language.get('error'),
						type: 'danger',
					})
				);
		},
		[groupId, loadMoreItemsURL, maxPageSize, privateLayout]
	);

	return (
		<div className="select-layout">
			<ClayManagementToolbar>
				<ClayManagementToolbar.Search
					onSubmit={(event) => {
						event.preventDefault();
					}}
				>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayInput
								className="form-control input-group-inset input-group-inset-after"
								disabled={empty}
								name={`filterKeywords`}
								onInput={(event) => {
									setFilterQuery(
										event.target.value.toLowerCase()
									);
								}}
								placeholder={Liferay.Language.get('search-for')}
								type="text"
							/>
							<ClayInput.GroupInsetItem after tag="span">
								<ClayButtonWithIcon
									className="navbar-breakpoint-d-none"
									disabled={empty}
									displayType="unstyled"
									symbol="times"
								/>
								<ClayButtonWithIcon
									className="navbar-breakpoint-d-block"
									disabled={empty}
									displayType="unstyled"
									symbol="search"
								/>
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayManagementToolbar.Search>
			</ClayManagementToolbar>
			<ClayLayout.ContainerFluid
				className="layouts-selector"
				id={`${namespace}selectLayoutFm`}
			>
				<fieldset className="panel-body">
					{empty ? (
						<EmptyState />
					) : (
						<div
							className="layout-tree"
							id={`${namespace}layoutContainer`}
						>
							<Treeview
								NodeComponent={Treeview.Card}
								filterQuery={filterQuery}
								multiSelection={multiSelection}
								nodes={nodes}
								onLoadMore={onLoadMore}
								onSelectedNodesChange={handleSelectionChange}
							/>
						</div>
					)}
				</fieldset>
			</ClayLayout.ContainerFluid>
		</div>
	);
};

const EmptyState = () => {
	return (
		<div className="sheet taglib-empty-result-message">
			<div className="taglib-empty-result-message-header"></div>

			<div className="sheet-text text-center">
				{Liferay.Language.get('there-are-no-pages')}
			</div>
		</div>
	);
};

SelectLayout.propTypes = {
	followURLOnTitleClick: PropTypes.bool,
	itemSelectorSaveEvent: PropTypes.string,
	multiSelection: PropTypes.bool,
	namespace: PropTypes.string,
	nodes: PropTypes.array.isRequired,
};

export default SelectLayout;
