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

import SearchResults from './SearchResults';

function normalizeLayout(layout) {
	return {
		...layout,
		name: layout.value,
		value: layout.url,
	};
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
	checkDisplayPage,
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

	const [selectionData, setSelectionData] = useState([]);

	const handleSelectionChange = useCallback(
		(_, selectedNodes) => {
			if (!selectedNodes.length) {
				return;
			}

			const nodes = selectedNodes.map(normalizeLayout);

			let nextData;

			if (multiSelection) {
				nextData = nodes;

				setSelectionData(nextData);
			}
			else {
				nextData = nodes[0];
			}

			if (followURLOnTitleClick) {
				Liferay.Util.getOpener().document.location.href = nextData.url;
			}
			else {
				Liferay.fire(itemSelectorSaveEvent, {
					data: nextData,
				});

				Liferay.Util.getOpener().Liferay.fire(itemSelectorSaveEvent, {
					data: nextData,
				});
			}
		},
		[followURLOnTitleClick, itemSelectorSaveEvent, multiSelection]
	);

	const onSearchResultSelect = useCallback(
		(layout) => {
			const node = normalizeLayout(layout);

			let nextData = node;

			if (multiSelection) {
				if (selectionData.some(({id}) => id === layout.id)) {
					nextData = selectionData.filter(({id}) => id !== layout.id);
				}
				else {
					nextData = [...selectionData, node];
				}

				setSelectionData(nextData);
			}

			if (followURLOnTitleClick) {
				Liferay.Util.getOpener().document.location.href = nextData.url;
			}
			else {
				Liferay.fire(itemSelectorSaveEvent, {
					data: nextData,
				});

				Liferay.Util.getOpener().Liferay.fire(itemSelectorSaveEvent, {
					data: nextData,
				});
			}
		},
		[
			followURLOnTitleClick,
			itemSelectorSaveEvent,
			multiSelection,
			selectionData,
		]
	);

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
					[`showHiddenLayouts`]: true,
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
					) : filterQuery ? (
						<SearchResults
							checkDisplayPage={checkDisplayPage}
							filter={filterQuery}
							findLayoutsURL={config.findLayoutsURL}
							groupId={groupId}
							multiSelection={multiSelection}
							onSelect={onSearchResultSelect}
							selection={selectionData.map(({id}) => id)}
						/>
					) : (
						<div
							className="layout-tree"
							id={`${namespace}layoutContainer`}
						>
							<Treeview
								NodeComponent={Treeview.Card}
								filterQuery={filterQuery}
								initialSelectedNodeIds={
									new Set(
										selectionData.map((layout) => layout.id)
									)
								}
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
