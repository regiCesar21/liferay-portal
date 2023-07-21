/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import ClayModal, {useModal} from '@clayui/modal';
import {SearchInput} from 'data-engine-taglib';
import React, {useEffect, useState} from 'react';

import {Loading} from '../../components/loading/Loading.es';
import Table from '../../components/table/Table.es';
import {getItem, updateItem} from '../../utils/client.es';
import {errorToast, successToast} from '../../utils/toast.es';

export default ({
	actions,
	endpoint,
	isDisabled = () => false,
	isOpen,
	onClose = () => {},
	onSave = () => Promise.resolve(),
	rolesFilter = () => true,
	title,
}) => {
	const {observer, onClose: close} = useModal({
		onClose,
	});

	const columns = [
		{
			key: 'name',
			sortable: false,
			value: Liferay.Language.get('role'),
		},
		...actions,
	];

	const [state, setState] = useState({
		isLoading: true,
		permissions: [],
		roles: [],
		searchText: '',
	});

	useEffect(() => {
		if (!isOpen || !endpoint) {
			return;
		}

		setState({
			isLoading: true,
			permissions: [],
			roles: [],
			searchText: '',
		});

		getItem('/o/headless-admin-user/v1.0/roles')
			.then(({items: roles = []}) => {
				roles = roles.filter(rolesFilter);

				setState((prevState) => ({
					...prevState,
					roles,
				}));

				const roleNames = roles.map(({name}) => name).join(',');

				return getItem(endpoint, {roleNames});
			})
			.then(({items: permissions = []}) => {
				setState((prevState) => ({
					...prevState,
					isLoading: false,
					permissions,
				}));
			})
			.catch(() =>
				setState((prevState) => ({
					...prevState,
					isLoading: false,
				}))
			);
	}, [endpoint, isOpen, rolesFilter]);

	const {isLoading, permissions, roles, searchText} = state;

	if (!isOpen) {
		return <></>;
	}

	const isChecked = (roleName, actionId) =>
		permissions.some(
			({actionIds, roleName: name}) =>
				name === roleName && actionIds.includes(actionId)
		);

	const handleOnSave = () =>
		Promise.all([
			updateItem({endpoint, item: permissions}),
			onSave(permissions),
		])
			.then(() => close())
			.then(() => successToast())
			.catch(() => errorToast());

	const togglePermission = (roleName, actionId) => {
		const exists = permissions.some(
			({roleName: name}) => roleName === name
		);

		let newPermissions = exists
			? permissions
			: permissions.concat({actionIds: [], roleName});

		newPermissions = newPermissions.map((permission) => {
			if (permission.roleName !== roleName) {
				return permission;
			}

			const {actionIds} = permission;

			return {
				...permission,
				actionIds: actionIds.includes(actionId)
					? actionIds.filter((id) => id !== actionId)
					: actionIds.concat(actionId),
			};
		});

		setState((prevState) => ({
			...prevState,
			permissions: newPermissions,
		}));
	};

	const filteredRoles = roles
		.filter(({name}) => new RegExp(searchText, 'ig').test(name))
		.map(({id, name}) => {
			let item = {
				id,
				name: (
					<>
						<ClayIcon symbol="user" /> {name}
					</>
				),
			};

			actions.forEach(({key}) => {
				item = {
					...item,
					[key]: (
						<input
							checked={isChecked(name, key)}
							disabled={isDisabled(name, key)}
							name={key}
							onChange={() => togglePermission(name, key)}
							type="checkbox"
						/>
					),
				};
			});

			return item;
		});

	return (
		<ClayModal observer={observer} size="full-screen">
			<ClayModal.Header>
				{title || Liferay.Language.get('permissions')}
			</ClayModal.Header>
			<ClayModal.Body>
				<ClayManagementToolbar>
					<SearchInput
						onChange={(searchText) =>
							setState((prevState) => ({
								...prevState,
								searchText,
							}))
						}
						searchText={searchText}
					/>
				</ClayManagementToolbar>
				<Loading isLoading={isLoading}>
					<Table
						align="center"
						columns={columns}
						items={filteredRoles}
					/>
				</Loading>
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => close()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton onClick={() => handleOnSave()}>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};
