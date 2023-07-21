/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox, ClayInput} from '@clayui/form';
import {Treeview} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import {useSelector} from '../store/index';
import AllowedFragmentTreeNode from './AllowedFragmentTreeNode';

const toFragmentEntryKeysArray = (collections) => {
	const fragmentEntryKeysArray = [];

	collections.forEach((collection) => {
		collection.fragmentEntries.forEach((fragmentEntry) =>
			fragmentEntryKeysArray.push(fragmentEntry.fragmentEntryKey)
		);

		fragmentEntryKeysArray.push(collection.fragmentCollectionId);
	});

	fragmentEntryKeysArray.push('lfr-all-fragments-id');

	return fragmentEntryKeysArray;
};

const toNodes = (collections) => {
	return [
		{
			children: collections
				.filter(
					(collection) =>
						collection.fragmentCollectionId !== 'layout-elements'
				)
				.map((collection) => {
					const children = collection.fragmentEntries
						.filter(
							(fragmentEntry) =>
								fragmentEntry.fragmentEntryKey &&
								fragmentEntry.name
						)
						.map((fragmentEntry) => ({
							id: fragmentEntry.fragmentEntryKey,
							name: fragmentEntry.name,
						}));

					return {
						children,
						expanded: false,
						id: collection.fragmentCollectionId,
						name: collection.name,
					};
				}),
			expanded: true,
			id: 'lfr-all-fragments-id',
			name: Liferay.Language.get('all-fragments'),
		},
	];
};

const getSelectedNodeIds = (
	allowNewFragmentEntries,
	fragmentEntryKeys = [],
	fragmentEntryKeysArray
) => {
	return allowNewFragmentEntries
		? fragmentEntryKeysArray.filter(
				(fragmentEntryKey) =>
					!fragmentEntryKeys.includes(fragmentEntryKey)
		  )
		: fragmentEntryKeys;
};

const AllowedFragmentSelector = ({dropZoneConfig, onSelectedFragment}) => {
	const fragments = useSelector((state) => state.fragments);

	const fragmentEntryKeysArray = useMemo(
		() => toFragmentEntryKeysArray(fragments),
		[fragments]
	);

	const initialAllowNewFragmentEntries =
		dropZoneConfig.allowNewFragmentEntries == undefined
			? true
			: dropZoneConfig.allowNewFragmentEntries;

	const initialFragmentEntryKeys = dropZoneConfig.fragmentEntryKeys || [];

	const [filter, setFilter] = useState('');

	const nodes = useMemo(() => toNodes(fragments), [fragments]);

	const [allowNewFragmentEntries, setAllowNewFragmentEntries] = useState(
		initialAllowNewFragmentEntries
	);

	const [fragmentEntryKeys, setFragmentEntryKeys] = useState(
		getSelectedNodeIds(
			allowNewFragmentEntries,
			initialFragmentEntryKeys,
			fragmentEntryKeysArray
		)
	);

	useEffect(() => {
		const newFragmentEntryKeys = getSelectedNodeIds(
			allowNewFragmentEntries,
			[...fragmentEntryKeys],
			fragmentEntryKeysArray
		);

		onSelectedFragment({
			allowNewFragmentEntries,
			selectedFragments: newFragmentEntryKeys,
		});
	}, [
		fragmentEntryKeys,
		allowNewFragmentEntries,
		fragmentEntryKeysArray,
		onSelectedFragment,
	]);

	return (
		<>
			<div className="px-4">
				<ClayInput
					className="mb-4"
					onChange={(event) => setFilter(event.target.value)}
					placeholder={`${Liferay.Language.get('search')}...`}
					sizing="sm"
					type="text"
				/>

				<div className="mb-2 page-editor__allowed-fragment__tree">
					<Treeview
						NodeComponent={AllowedFragmentTreeNode}
						filterQuery={filter}
						inheritSelection
						initialSelectedNodeIds={[...fragmentEntryKeys]}
						nodes={nodes}
						onSelectedNodesChange={setFragmentEntryKeys}
					/>
				</div>
			</div>

			<div className="page-editor__allowed-fragment__new-fragments-checkbox">
				<ClayCheckbox
					aria-label={Liferay.Language.get(
						'select-new-fragments-automatically'
					)}
					checked={allowNewFragmentEntries}
					label={Liferay.Language.get(
						'select-new-fragments-automatically'
					)}
					onChange={(event) => {
						setAllowNewFragmentEntries(event.target.checked);
					}}
				/>
			</div>
		</>
	);
};

AllowedFragmentSelector.propTypes = {
	dropZoneConfig: PropTypes.shape({
		allowNewFragmentEntries: PropTypes.bool,
		fragmentEntryKeys: PropTypes.array,
	}).isRequired,
	onSelectedFragment: PropTypes.func.isRequired,
};

export {AllowedFragmentSelector};
export default AllowedFragmentSelector;
