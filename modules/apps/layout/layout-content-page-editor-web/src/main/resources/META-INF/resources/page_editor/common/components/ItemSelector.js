/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {config} from '../../app/config/index';
import {useSelector} from '../../app/store/index';
import {useId} from '../../app/utils/useId';
import {openInfoItemSelector} from '../../core/openInfoItemSelector';

export default function ItemSelector({
	eventName,
	itemSelectorURL,
	label,
	onItemSelect,
	quickMappedInfoItems = [],
	selectedItemTitle,
	showAddButton = true,
	showMappedItems = true,
}) {
	const [active, setActive] = useState(false);
	const itemSelectorInputId = useId();
	const mappedInfoItems = useSelector((state) => state.mappedInfoItems || []);

	const mappedItems =
		quickMappedInfoItems.length === 0
			? mappedInfoItems
			: quickMappedInfoItems;

	const defaultEventName = `${config.portletNamespace}selectInfoItem`;

	return (
		<>
			{label && <label htmlFor={itemSelectorInputId}>{label}</label>}

			<div className="d-flex">
				<ClayInput
					className={classNames('mr-2', {
						'page-editor__item-selector__content-input': showAddButton,
					})}
					id={itemSelectorInputId}
					onClick={() => {
						if (showAddButton) {
							openInfoItemSelector(
								onItemSelect,
								eventName || defaultEventName,
								itemSelectorURL || config.infoItemSelectorURL
							);
						}
					}}
					readOnly
					sizing="sm"
					type="text"
					value={selectedItemTitle || ''}
				/>

				{showAddButton &&
					(mappedItems.length > 0 && showMappedItems ? (
						<ClayDropDown
							active={active}
							onActiveChange={setActive}
							trigger={
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get(
										'select-content-button'
									)}
									className={
										'page-editor__item-selector__content-button'
									}
									displayType="secondary"
									onClick={() => setActive(true)}
									small
									symbol="plus"
								/>
							}
						>
							<ClayDropDown.ItemList>
								{mappedItems.map((item) => (
									<ClayDropDown.Item
										key={item.classNameId}
										onClick={() => {
											onItemSelect(item);
											setActive(false);
										}}
									>
										{item.title}
									</ClayDropDown.Item>
								))}
								<ClayDropDown.Divider />
								<ClayDropDown.Item
									onClick={() => {
										openInfoItemSelector(
											onItemSelect,
											eventName || defaultEventName,
											itemSelectorURL ||
												config.infoItemSelectorURL
										);

										setActive(false);
									}}
								>
									{Liferay.Language.get('select-content')}...
								</ClayDropDown.Item>
							</ClayDropDown.ItemList>
						</ClayDropDown>
					) : (
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get(
								'select-content-button'
							)}
							className={
								'page-editor__item-selector__content-button'
							}
							displayType="secondary"
							onClick={() =>
								openInfoItemSelector(
									onItemSelect,
									eventName || defaultEventName,
									itemSelectorURL ||
										config.infoItemSelectorURL
								)
							}
							small
							symbol="plus"
						/>
					))}
			</div>
		</>
	);
}

ItemSelector.propTypes = {
	eventName: PropTypes.string,
	itemSelectorURL: PropTypes.string,
	label: PropTypes.string,
	onItemSelect: PropTypes.func.isRequired,
	selectedItemTitle: PropTypes.string,
};
