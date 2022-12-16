/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayButtonGroup from '@clayui/button/lib/Group';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayManagementToolbar, {
	ClayResultsBar,
} from '@clayui/management-toolbar';
import {ClayTooltipProvider} from '@clayui/tooltip';
import getCN from 'classnames';
import React, {useState} from 'react';

import {ALL, ASCENDING} from '../../utils/constants';
import sub from '../../utils/language/sub';

function ManagementToolbar({
	allItems,
	category,
	filterItems,
	keyword,
	onClearCategory,
	onClearStatus,
	onReverseSort,
	onUpdateSelected,
	selected,
	setKeyword,
	setSelected,
	sortDirection,
	status,
}) {
	const [value, setValue] = useState('');

	return (
		<>
			<ClayManagementToolbar
				className={getCN('clause-contributors-management-bar', {
					'management-bar-primary': selected.length > 0,
				})}
			>
				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item>
						<ClayCheckbox
							aria-label={Liferay.Language.get('checkbox')}
							checked={
								allItems.length > 0 &&
								selected.length === allItems.length
							}
							indeterminate={
								selected.length > 0 &&
								selected.length < allItems.length
							}
							onChange={() =>
								setSelected(
									selected.length !== allItems.length
										? allItems
										: []
								)
							}
						/>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>

				{selected.length > 0 ? (
					<>
						<ClayManagementToolbar.ItemList expand>
							{allItems.length > 0 &&
							selected.length === allItems.length ? (
								<ClayManagementToolbar.Item className="navbar-form">
									<span className="component-text text-truncate-inline">
										<span className="text-truncate">
											{Liferay.Language.get(
												'all-selected'
											)}
										</span>
									</span>
								</ClayManagementToolbar.Item>
							) : (
								<>
									<ClayManagementToolbar.Item className="navbar-form">
										<span className="component-text text-truncate-inline">
											<span className="text-truncate">
												{sub(
													Liferay.Language.get(
														'x-of-x-selected'
													),
													[
														selected.length,
														allItems.length,
													]
												)}
											</span>
										</span>
									</ClayManagementToolbar.Item>

									<ClayManagementToolbar.Item>
										<ClayButton
											displayType="link"
											onClick={() =>
												setSelected(allItems)
											}
											small
										>
											{Liferay.Language.get('select-all')}
										</ClayButton>
									</ClayManagementToolbar.Item>
								</>
							)}
						</ClayManagementToolbar.ItemList>

						<ClayManagementToolbar.ItemList>
							<ClayManagementToolbar.Item>
								<ClayButtonGroup spaced>
									<ClayButton
										aria-label={Liferay.Language.get(
											'turn-on'
										)}
										displayType="secondary"
										onClick={onUpdateSelected(true)}
										small
									>
										{Liferay.Language.get('turn-on')}
									</ClayButton>

									<ClayButton
										aria-label={Liferay.Language.get(
											'turn-off'
										)}
										displayType="secondary"
										onClick={onUpdateSelected(false)}
										small
									>
										{Liferay.Language.get('turn-off')}
									</ClayButton>
								</ClayButtonGroup>
							</ClayManagementToolbar.Item>
						</ClayManagementToolbar.ItemList>
					</>
				) : (
					<>
						<ClayManagementToolbar.ItemList>
							<ClayManagementToolbar.Item>
								<ClayDropDownWithItems
									items={filterItems}
									trigger={
										<ClayButton
											aria-label={Liferay.Language.get(
												'filter'
											)}
											className="nav-link"
											displayType="unstyled"
										>
											<span className="navbar-text-truncate">
												{Liferay.Language.get('filter')}
											</span>

											<ClayIcon
												className="inline-item inline-item-after"
												symbol="caret-bottom"
											/>
										</ClayButton>
									}
								/>

								<ClayTooltipProvider>
									<ClayButton
										aria-label={Liferay.Language.get(
											'reverse-sort-direction'
										)}
										className="nav-link nav-link-monospaced"
										data-tooltip-align="bottom"
										displayType="unstyled"
										onClick={onReverseSort}
										title={Liferay.Language.get(
											'reverse-sort-direction'
										)}
									>
										<ClayIcon
											symbol={
												sortDirection === ASCENDING
													? 'order-list-down'
													: 'order-list-up'
											}
										/>
									</ClayButton>
								</ClayTooltipProvider>
							</ClayManagementToolbar.Item>
						</ClayManagementToolbar.ItemList>

						<ClayManagementToolbar.ItemList expand>
							<ClayManagementToolbar.Item className="search">
								<ClayInput.Group>
									<ClayInput.GroupItem>
										<ClayInput
											aria-label={Liferay.Language.get(
												'search'
											)}
											className="input-group-inset input-group-inset-after"
											onChange={(event) =>
												setValue(event.target.value)
											}
											onKeyDown={(event) => {
												if (event.key === 'Enter') {
													event.preventDefault();

													setKeyword(value);
												}
											}}
											placeholder={Liferay.Language.get(
												'search'
											)}
											type="text"
											value={value}
										/>

										<ClayInput.GroupInsetItem
											after
											tag="span"
										>
											<ClayButtonWithIcon
												aria-label={Liferay.Language.get(
													'search'
												)}
												displayType="unstyled"
												onClick={() =>
													setKeyword(value)
												}
												symbol="search"
											/>
										</ClayInput.GroupInsetItem>
									</ClayInput.GroupItem>
								</ClayInput.Group>
							</ClayManagementToolbar.Item>
						</ClayManagementToolbar.ItemList>
					</>
				)}
			</ClayManagementToolbar>

			{(!!keyword || status !== ALL || category !== ALL) && (
				<ClayResultsBar>
					<ClayResultsBar.Item>
						<span className="component-text text-truncate-inline">
							<span className="text-truncate">
								{sub(Liferay.Language.get('x-results-for-x'), [
									allItems.length,
									keyword,
								])}
							</span>
						</span>
					</ClayResultsBar.Item>

					<ClayResultsBar.Item expand>
						{status !== ALL && (
							<ClayLabel
								className="component-label tbar-label"
								closeButtonProps={{
									onClick: onClearStatus,
								}}
								displayType="unstyled"
							>
								{status}
							</ClayLabel>
						)}

						{category !== ALL && (
							<ClayLabel
								className="component-label tbar-label"
								closeButtonProps={{
									onClick: onClearCategory,
								}}
								displayType="unstyled"
							>
								{category}
							</ClayLabel>
						)}
					</ClayResultsBar.Item>

					<ClayResultsBar.Item>
						<ClayButton
							aria-label={Liferay.Language.get('clear')}
							className="component-link tbar-link"
							displayType="unstyled"
							onClick={() => {
								setValue('');
								setKeyword('');
								onClearCategory();
								onClearStatus();
							}}
						>
							{Liferay.Language.get('clear')}
						</ClayButton>
					</ClayResultsBar.Item>
				</ClayResultsBar>
			)}
		</>
	);
}

export default React.memo(ManagementToolbar);
