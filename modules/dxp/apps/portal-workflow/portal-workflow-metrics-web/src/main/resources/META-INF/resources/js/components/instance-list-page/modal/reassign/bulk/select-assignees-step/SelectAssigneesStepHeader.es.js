/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useState,
} from 'react';

import {Autocomplete} from '../../../../../../shared/components/autocomplete/Autocomplete.es';
import PromisesResolver from '../../../../../../shared/components/promises-resolver/PromisesResolver.es';
import {ModalContext} from '../../../ModalProvider.es';

const Header = ({data}) => {
	const {
		bulkReassign,
		selectTasks: {tasks},
		setBulkReassign,
	} = useContext(ModalContext);
	const {reassigning, selectedAssignee, useSameAssignee} = bulkReassign;

	const [assignees, setAssignees] = useState([]);

	useEffect(() => {
		const {workflowTaskAssignableUsers: users} = data || {};

		if (users && users.length) {
			const {assignableUsers = []} =
				users.find((item) => item.workflowTaskId === 0) || {};

			setAssignees(assignableUsers);
		}
	}, [data]);

	const defaultValue = useMemo(
		() => (selectedAssignee ? selectedAssignee.name : ''),
		[selectedAssignee]
	);

	const disableBulk = useMemo(() => reassigning || assignees.length === 0, [
		assignees,
		reassigning,
	]);

	const handleCheck = ({target}) => {
		setBulkReassign({
			...bulkReassign,
			reassignedTasks: [],
			selectedAssignee: null,
			useSameAssignee: target.checked,
		});
	};

	const handleSelect = useCallback(
		(newAssignee) => {
			const reassignedTasks = [];

			if (newAssignee) {
				tasks.forEach((task) => {
					reassignedTasks.push({
						assigneeId: newAssignee.id,
						workflowTaskId: task.id,
					});
				});
			}

			setBulkReassign({
				...bulkReassign,
				reassignedTasks,
				selectedAssignee: newAssignee,
			});
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[bulkReassign, tasks, setBulkReassign]
	);

	return (
		<PromisesResolver.Resolved>
			<ClayManagementToolbar className="border-bottom mb-0 px-3">
				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item className="pt-2">
						<ClayCheckbox
							checked={useSameAssignee}
							disabled={disableBulk}
							label={Liferay.Language.get(
								'use-the-same-assignee-for-all-tasks'
							)}
							onChange={handleCheck}
						/>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>
				<ClayManagementToolbar.Search>
					<Autocomplete
						defaultValue={defaultValue}
						disabled={disableBulk || !useSameAssignee}
						items={assignees}
						onSelect={handleSelect}
						placeholder={Liferay.Language.get(
							'search-for-an-assignee'
						)}
					>
						<ClayInput.GroupInsetItem after tag="span">
							<ClayIcon
								className="m-2"
								displayType="unstyled"
								symbol="search"
							/>
						</ClayInput.GroupInsetItem>
					</Autocomplete>
				</ClayManagementToolbar.Search>
			</ClayManagementToolbar>
		</PromisesResolver.Resolved>
	);
};

export {Header};
