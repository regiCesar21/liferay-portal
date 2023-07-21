/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React from 'react';

import {sub} from '../../util/lang.es';

const ToolbarWithSelection = ({
	active,
	checked,
	children,
	handleCheck,
	handleClear,
	handleSelectAll,
	indeterminate,
	selectAll,
	selectedCount,
	totalCount,
}) => {
	return (
		<ClayManagementToolbar
			active={active}
			className="mb-0 show-quick-actions-on-hover"
		>
			<ClayManagementToolbar.ItemList expand>
				<ClayManagementToolbar.Item className="ml-2">
					<ClayCheckbox
						checked={checked}
						indeterminate={indeterminate}
						onChange={handleCheck}
					/>
				</ClayManagementToolbar.Item>

				{active && (
					<>
						<ClayManagementToolbar.Item>
							<span className="ml-0 mr-0 navbar-text">
								{selectAll
									? Liferay.Language.get('all-selected')
									: sub(
											Liferay.Language.get(
												'x-of-x-selected'
											),
											[selectedCount, totalCount]
									  )}
							</span>
						</ClayManagementToolbar.Item>

						<ClayManagementToolbar.Item>
							<button
								className="btn btn-sm btn-unstyled font-weight-bold nav-link"
								onClick={handleClear}
							>
								{Liferay.Language.get('clear')}
							</button>
						</ClayManagementToolbar.Item>

						{!selectAll && checked && (
							<ClayManagementToolbar.Item>
								<button
									className="btn btn-sm btn-unstyled font-weight-bold nav-link"
									onClick={handleSelectAll}
								>
									{Liferay.Language.get('select-all')}
								</button>
							</ClayManagementToolbar.Item>
						)}
					</>
				)}

				{children}
			</ClayManagementToolbar.ItemList>
		</ClayManagementToolbar>
	);
};

export default ToolbarWithSelection;
